/*
 * @(#)UserAdapter.java 0.1 Aug 21, 2008
 * Copyright 2004-2007 Homolo Co., Ltd. All rights reserved.
 */

package com.yuxxer.gtsd.usersystem.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.homolo.framework.security.Group;
import com.homolo.framework.security.Role;
import com.homolo.framework.security.User;

/**
 * Class <code>UserAdapter</code> is ...
 * 
 * @author wch3116
 * @version 0.1, Aug 21, 2008
 */
public class UserAdapter implements User {

	private static final long serialVersionUID = 7167477109456569370L;
	
	private com.yuxxer.gtsd.usersystem.domain.User user;

	public UserAdapter(com.yuxxer.gtsd.usersystem.domain.User user) {
		this.user = user;
	}

	public String getUserId() {
		return user.getId();
	}

	public String getUserName() {
		return user.getUserName();
	}

	public String getDescription() {
		return user.getDescription();
	}

	public String getEmail() {
		return user.getEmail();
	}
	
	public String getAvatar() {
		return user.getAvatar();
	}

	public String getNationality() {
		return user.getNationality();
	}

	public String getNickName() {
		return user.getNickName();
	}

	public String getPassword() {
		return user.getPassword();
	}

	public String getPersonId() {
		return user.getPersonId();
	}

	public Date getRegDate() {
		return user.getRegDate();
	}

	public String getTimeZone() {
		return user.getTimeZone();
	}

	public String getZipCode() {
		return user.getZipCode();
	}

	public boolean isDisabled() {
		return user.isDisabled();
	}

	public boolean isLocked() {
		return user.isLocked();
	}

	public String getLoginId() {
		return user.getUserName();
	}

	public com.yuxxer.gtsd.usersystem.enums.Role getRole() {
		return user.getRole();
	}

	@Override
	public String getMobile() {
		return user.getMobile();
	}

	@Override
	public String getWeixin() {
		return user.getWeixin();
	}

	@Override
	public String getDomainId() {
		return null;
	}

	@Override
	public Collection<Role> getRoles() {
		return new ArrayList<Role>();
	}

	@Override
	public Collection<Group> getGroups() {
		return new ArrayList<Group>();
	}

	@Override
	public Collection<?> getTokens() {
		return new ArrayList<>();
	}

	@Override
	public <T> T getAttribute(String name, Class<T> requiredType) {
		return null;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}

}
