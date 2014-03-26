package com.yuxxer.gtsd.usersystem.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import com.homolo.framework.dao.util.PaginationSupport;
import com.homolo.framework.dao.util.Range;
import com.homolo.framework.dao.util.Sorter;
import com.homolo.framework.protocol.ProtocolRegistry;
import com.homolo.framework.rest.ActionMethod;
import com.homolo.framework.rest.BaseDomainObjectServiceSupport;
import com.homolo.framework.rest.RequestParameters;
import com.homolo.framework.rest.RestService;
import com.homolo.framework.rest.ResultSet;
import com.homolo.framework.rest.ResultSet.Fetcher;
import com.homolo.framework.rest.ResultSet.Row;
import com.homolo.framework.rest.ReturnResult;
import com.homolo.framework.rest.bind.annotation.ParameterObject;
import com.homolo.framework.rest.bind.annotation.ParameterVariable;
import com.homolo.framework.rest.bind.annotation.ResourceVariable;
import com.homolo.framework.security.AuthenticatedUser;
import com.homolo.framework.service.ServiceResult;
import com.homolo.framework.util.BeanUpdater;
import com.homolo.framework.util.EmailValidator;
import com.homolo.framework.util.EnvironmentHelper;
import com.homolo.framework.util.IDNumberUtil;
import com.yuxxer.gtsd.ProjectConfig;
import com.yuxxer.gtsd.usersystem.condition.UserCondition;
import com.yuxxer.gtsd.usersystem.domain.User;
import com.yuxxer.gtsd.usersystem.enums.Gender;
import com.yuxxer.gtsd.usersystem.manager.UserManager;
import com.yuxxer.gtsd.usersystem.security.ProxyWebAuthenticationDetails;
import com.yuxxer.gtsd.usersystem.security.UserAdapter;
import com.yuxxer.gtsd.usersystem.security.UserDetailAdapter;
import com.yuxxer.gtsd.usersystem.security.UserSessionFactory;

@RestService
public class UserService extends BaseDomainObjectServiceSupport<User> {

	@Autowired
	private Environment environment;

	@Autowired
	private UserManager userManager;

	@Autowired
	private ProtocolRegistry protocolRegistry;

	@Autowired
	private TaskExecutor taskExecutor;

	@ActionMethod(response = "json")
	@Override
	public Object meta() {
		return extrametas("uri");
	}

	private PasswordEncoder passwordEncoder;

	private List<String> badPasswordList = Arrays.asList("123456", "654321");

	private String defaultPassword = "password";

	@PostConstruct
	public void init() {
		EnvironmentHelper envHelper = new EnvironmentHelper(environment);
		String[] badPasswords = envHelper.getProperty("us.badPasswordList", String[].class);
		String defaultPassword = envHelper.getProperty("us.defaultPassword");
		if (StringUtils.isNotBlank(defaultPassword)) {
			this.defaultPassword = defaultPassword;
		}
		if (badPasswords != null) {
			badPasswordList = Arrays.asList(badPasswords);
		}
	}

	@Resource(name = ProjectConfig.PREFIX+"passwordEncoder")
	public void setPasswordEncoder(Object injectedPasswordEncoder) {
		if (injectedPasswordEncoder instanceof PasswordEncoder) {
			this.passwordEncoder = (PasswordEncoder) injectedPasswordEncoder;
		} else if (injectedPasswordEncoder instanceof org.springframework.security.crypto.password.PasswordEncoder) {
			final org.springframework.security.crypto.password.PasswordEncoder delegate = (org.springframework.security.crypto.password.PasswordEncoder) injectedPasswordEncoder;
			this.passwordEncoder = new PasswordEncoder() {
				public String encodePassword(String rawPass, Object salt) {
					checkSalt(salt);
					return delegate.encode(rawPass);
				}

				public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
					checkSalt(salt);
					return delegate.matches(rawPass, encPass);
				}

				private void checkSalt(Object salt) {
					Assert.isNull(salt, "Salt value must be null when used with crypto module PasswordEncoder");
				}
			};
		}
	}

	@ActionMethod(response = "json")
	public Object retrieve(@ResourceVariable String id) {
		User entity = userManager.getUser(id);
		if (entity == null) {
			entity = userManager.getUserByPersonId(id);
		}
		if (entity == null) {
			entity = userManager.getUserByUserName(id);
		}
		return entity;
	}

	@ActionMethod(response = "json")
	public Object findByPersonId(@ResourceVariable String id) {
		return userManager.getUserByPersonId(id);
	}

	@ActionMethod(request = "json", response = "json")
	public Object update(@ResourceVariable String id, @ParameterObject JSONObject updateObj) {
		User entity = userManager.getUser(id);
		if (entity == null) {
			return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT);
		}
		updateObj.remove("password");
		BeanUpdater.updateObject(entity, updateObj);
		entity.setUserName(entity.getUserName().toLowerCase());
		User exist = userManager.getUserByUserName(entity.getUserName());
		if (exist != null && !StringUtils.equals(exist.getId(), entity.getId())) {
			return new ServiceResult(ReturnResult.VALIDATION_ERROR, "用户名已存在");
		}
		userManager.updateUser(entity);
		return new ServiceResult(ReturnResult.SUCCESS);
	}

	@ActionMethod(response = "json")
	public ServiceResult updateUserPassword(@ResourceVariable String personId, @ParameterVariable("oldPwd") String oldPwd, @ParameterVariable("newPwd") String newPwd, HttpServletRequest request) {
		newPwd = newPwd.trim();
		AuthenticatedUser currentUser = UserSessionFactory.currentUser();
		if (StringUtils.isBlank(personId)) {
			personId = currentUser.getPersonId();
		}
		newPwd = StringUtils.trim(newPwd);
		if (newPwd.length() < 6) {
			return new ServiceResult(ReturnResult.VALIDATION_ERROR, "为了您账号的安全，密码不能少于 6 位");
		}
		if (badPasswordList.contains(newPwd) || newPwd.equals(StringUtils.repeat(newPwd.substring(0, 1), newPwd.length()))) {
			return new ServiceResult(ReturnResult.VALIDATION_ERROR, "您的新密码不够安全请重新设定\n\n请不要使用 " + StringUtils.join(badPasswordList, ',') + ",或者重复的字符(eg:111111)做为密码");
		}
		User user = userManager.getUserByPersonId(personId);
		if (user == null) {
			return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT, "用户并不存在");
		}
		if (!passwordEncoder.isPasswordValid(user.getPassword(), oldPwd, null)) {
			return new ServiceResult(ReturnResult.VALIDATION_ERROR, "原密码不正确");
		}
		try {
			String newPassword = passwordEncoder.encodePassword(newPwd, null);
			user.setPassword(newPassword);
			userManager.updateUser(user);
			return new ServiceResult(ReturnResult.SUCCESS, "密码修改成功");
		} catch (Exception e) {
			return new ServiceResult(ReturnResult.FAILURE, "密码修改失败：" + e.getMessage(), e);
		}
	}


	@ActionMethod(response = "json")
	public Object register(RequestParameters params, HttpServletRequest request) {
		String userName = params.getParameter("userName", String.class);
		String nickName = params.getParameter("nickName", String.class);
		String password = params.getParameter("password", String.class);
		String gender = params.getParameter("gender", String.class);
		if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
			return new ServiceResult(ReturnResult.VALIDATION_ERROR, "用户名和密码为必填!");
		}
		userName = StringUtils.trim(userName);
		password = StringUtils.trim(password);
		if (password.length() < 6) {
			return new ServiceResult(ReturnResult.VALIDATION_ERROR, "为了您账号的安全，密码不能少于 6 位");
		}
		if (badPasswordList.contains(password) || password.equals(StringUtils.repeat(password.substring(0, 1), password.length()))) {
			return new ServiceResult(ReturnResult.VALIDATION_ERROR, "您的新密码不够安全请重新设定\n\n请不要使用 " + StringUtils.join(badPasswordList, ',') + ",或者重复的字符(eg:111111)做为密码");
		}
		String email = params.getParameter("email", String.class);
		User user = null;
		if (StringUtils.isNotBlank(email)) {
			email = StringUtils.trim(email);
			if (!EmailValidator.validate(email)) {
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "不合法的Email地址!");
			}
			user = userManager.getUserByEmail(email);
			if (user != null) {
				return new ServiceResult(ReturnResult.VALIDATION_ERROR, "该Email已被注册!");
			}
		} 
		user = userManager.getUserByUserName(userName);
		if (user != null) {
			return new ServiceResult(ReturnResult.VALIDATION_ERROR, "用户名已存在!");
		}
		user = new User();
		user.setUserName(userName);
		if (StringUtils.isNotBlank(gender)) {
			try {
				user.setGender(Gender.valueOf(gender));
			} catch (Exception e) {
				user.setGender(Gender.Unknow);
			}
		} else {
			user.setGender(Gender.Unknow);
		}
		if (StringUtils.isBlank(nickName)) {
			user.setNickName(userName);
		} else {
			user.setNickName(nickName);
		}
		if (StringUtils.isNotBlank(email)) {
			user.setEmail(email.toLowerCase());
		}
		user.setRegDate(new Date());
		encodeUserPassword(user);
		userManager.createUser(user);
		return new ServiceResult(ReturnResult.SUCCESS);
	}

	@ActionMethod(response = "ext-grid")
	public Object query(RequestParameters reqParams, Range range, Sorter sorter) {
		if (sorter == null) {
			sorter = new Sorter().asc("userName");
		}
		String partWord = (String) reqParams.getParameter("partWord");
		UserCondition condition = new UserCondition();
		condition.setPartWord(partWord);
		PaginationSupport<User> ps = userManager.searchUsers(condition, range, sorter);
		ResultSet rs = new ResultSet(ps);
		rs.addField("uri", new Fetcher<String>() {
			@Override
			public String fetch(Row row, Object propName) {
				Object original = row.getOriginal();
				if (original != null) {
					return protocolRegistry.getURI(original);
				}
				return null;
			}
		});
		return rs;
	}


	@ActionMethod(request = "json", response = "json")
	public Object listByPersonIds(RequestParameters reqParams) {
		@SuppressWarnings("unchecked")
		List<String> ids = reqParams.getParameter("ids", List.class);
		List<User> users = new ArrayList<User>();
		Boolean excludeCurrentUser = reqParams.getParameter("excludeCurrentUser", Boolean.class);

		for (String personId : ids) {
			if (excludeCurrentUser != null && excludeCurrentUser && personId.equals(UserSessionFactory.currentUser().getPersonId())) {
				continue;
			}
			User user = userManager.getUserByPersonId(personId);
			if (user != null) {
				users.add(user);
			}
		}
		return users;
	}

	@ActionMethod(response = "json")
	public Object simpleLogin(@ParameterVariable("loginId") String loginId, @ParameterVariable("password") String password, HttpServletRequest request) {
		if (StringUtils.isBlank(loginId) && StringUtils.isBlank(password)) {
			return new ServiceResult(ReturnResult.VALIDATION_ERROR, "loginId and password is required.");
		}
		User user = userManager.getUserByUserName(loginId);
		if (user == null) {
			return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT);
		}
		if (!passwordEncoder.isPasswordValid(user.getPassword(), password, null)) {
			return new ServiceResult(ReturnResult.VALIDATION_ERROR, "bad login or password.");
		}
		// Auto login
		UserAdapter userAdapter = new UserAdapter(user);
		UserDetailAdapter userDetailAdapter = new UserDetailAdapter(userAdapter);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetailAdapter, userDetailAdapter.getPassword(), userDetailAdapter.getAuthorities());
		authentication.setDetails(new ProxyWebAuthenticationDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ServiceResult(ReturnResult.SUCCESS, user);
	}

	@ActionMethod(response = "json")
	public Object refreshCurrentUser(HttpServletRequest request) {
		AuthenticatedUser currentUser = UserSessionFactory.currentUser();
		if (!currentUser.isAnonymousUser() && currentUser.isAuthenticated()) {
			User user = userManager.getUser(currentUser.getUserId());
			if (user != null) {
				UserAdapter userAdapter = new UserAdapter(user);
				UserDetailAdapter userDetailAdapter = new UserDetailAdapter(userAdapter);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetailAdapter, userDetailAdapter.getPassword(), userDetailAdapter.getAuthorities());
				authentication.setDetails(new ProxyWebAuthenticationDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				return new ServiceResult(ReturnResult.SUCCESS);
			} else {
				return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT, "not found user, do not refresh.");
			}
		} else {
			return new ServiceResult(ReturnResult.FAILURE, "no login user or anonymous user, do not refresh.");
		}
	}

	private void encodeUserPassword(User object) {
		String password = object.getPassword();
		if (StringUtils.isBlank(password)) {
			password = defaultPassword;
		}
		object.setPassword(passwordEncoder.encodePassword(password, null));
	}


	@ActionMethod(response = "json")
	public Object parsebirthday(@ResourceVariable String idNumber, RequestParameters params) {
		if ("collection".equalsIgnoreCase(idNumber)) {
			idNumber = params.getParameter("idNumber", String.class);
		}
		String format = params.getParameter("format", String.class);
		if (StringUtils.isBlank(idNumber)) {
			return new ServiceResult(ReturnResult.VALIDATION_ERROR, "未传入身份证号!");
		}
		Date birthday = IDNumberUtil.parseBirthday(idNumber);
		if (birthday == null) {
			return new ServiceResult(ReturnResult.NOT_FOUND_OBJECT, "不能解析出生日期!");
		}
		if (StringUtils.isNotBlank(format)) {
			try {
				return new ServiceResult(ReturnResult.SUCCESS, "success", DateFormatUtils.format(birthday, format));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ServiceResult(ReturnResult.SUCCESS, birthday);
	}

	/**
	 * 自动登录用户
	 * @param user
	 * @param request
	 */
	public void autoLogin(User user, HttpServletRequest request) {
		// Auto login
		UserAdapter userAdapter = new UserAdapter(user);
		UserDetailAdapter userDetailAdapter = new UserDetailAdapter(userAdapter);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetailAdapter, userDetailAdapter.getPassword(), userDetailAdapter.getAuthorities());
		authentication.setDetails(new ProxyWebAuthenticationDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
