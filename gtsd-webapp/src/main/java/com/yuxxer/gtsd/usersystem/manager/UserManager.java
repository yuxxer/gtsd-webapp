package com.yuxxer.gtsd.usersystem.manager;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.homolo.framework.annotation.DomainEngine;
import com.homolo.framework.dao.DomainObjectDao;
import com.homolo.framework.dao.util.PaginationSupport;
import com.homolo.framework.dao.util.Range;
import com.homolo.framework.dao.util.Sorter;
import com.homolo.framework.events.EventMethod;
import com.homolo.framework.exception.BusinessException;
import com.homolo.framework.util.UUID;
import com.yuxxer.gtsd.ProjectConfig;
import com.yuxxer.gtsd.usersystem.condition.UserCondition;
import com.yuxxer.gtsd.usersystem.domain.User;


@DomainEngine(types = User.class)
@Transactional(readOnly = true)
public class UserManager {

	private final Logger logger = LoggerFactory.getLogger(UserManager.class);
	
	@Resource(name = ProjectConfig.PREFIX+"userDao")
	private DomainObjectDao<User> userDao;

	@DomainEngine.R(type = User.class)
	public User getUser(String id) {
		return userDao.loadObject(id);
	}

	public User getUserByPersonId(String personId) {
		if (StringUtils.isBlank(personId)) {
			return null;
		}
		UserCondition condition = new UserCondition();
		condition.setPersonId(personId);
		List<User> list = userDao.findAllByCondition(condition);
		return list.isEmpty() ? null : list.get(0);
	}

	@EventMethod("createuser")
	@DomainEngine.C(type = User.class)
	@Transactional
	public String createUser(User user) {
		if (user.getId() == null) {
			user.setId(UUID.generateUUID());
		}
		if (user.getPersonId() == null) {
			user.setPersonId(UUID.generateUUID());
		}
		userDao.createObject(user);
		return user.getId();
	}


	@EventMethod("updateuser")
	@DomainEngine.U(type = User.class)
	@Transactional
	public void updateUser(User object) {
		userDao.updateObject(object);
	}

	@EventMethod("deleteuser")
	@DomainEngine.D(type = User.class)
	@Transactional
	public void deleteUser(User object) {
		userDao.deleteObject(object);
	}

	@EventMethod("updatealluser")
	@Transactional
	public void deleteAllUsers() {
		userDao.deleteAll();
	}

	@EventMethod("deleteuser")
	@Transactional
	public void deleteUserById(String id) throws BusinessException {
		User object = getUser(id);
		if (object != null) {
			deleteUser(object);
		}
	}

	public User getUserByEmail(String email) {
		if (StringUtils.isBlank(email)) {
			return null;
		}
		UserCondition condition = new UserCondition();
		condition.setEmail(email);
		List<User> list = userDao.findAllByCondition(condition);
		return list.isEmpty() ? null : list.get(0);
	}
	/**
	 * 这里应该返回唯一的值
	 * 
	 * @param userName
	 * @return
	 */
	public User getUserByUserName(String userName) {
		if (StringUtils.isBlank(userName)) {
			return null;
		}
		List<User> list = userDao.findByProperty("userName", userName);
		if (list.isEmpty()) {
			return null;
		} else if (list.size() > 1) {
			logger.warn(String
					.format("There are %d users found with userName[%s], but unique required.",
							list.size(), userName));
		}
		return list.get(0);
	}

	public List<User> getAllUsers() {
		return userDao.findAll();
	}

	public List<String> getAllPersonIds() {
		return userDao.findByCondition(new UserCondition(), "personId",
				String.class);
	}

	public PaginationSupport<User> searchUsers(UserCondition condtion,
			Range range, Sorter sorter) {
		return userDao.findByCondition(condtion, range, sorter);
	}

	public List<User> listUsers(UserCondition condtion) {
		return userDao.findAllByCondition(condtion);
	}

	public int countUsers(UserCondition condition) {
		return userDao.countByCondition(condition);
	}
}