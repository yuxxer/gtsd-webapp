/*
 * @(#)AnonymousUser.java 0.1 Aug 22, 2008
 * Copyright 2004-2007 Homolo Co., Ltd. All rights reserved.
 */

package com.yuxxer.gtsd.usersystem.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.token.Token;

import com.google.common.collect.Maps;
import com.homolo.framework.security.Group;
import com.homolo.framework.security.Role;
import com.homolo.framework.security.User;

/**
 * Class <code>AnonymousUser</code> is ...
 * 
 * @author wch3116
 * @version 0.1, Aug 22, 2008
 */
public class AnonymousUser implements User {

	private static final long serialVersionUID = -8192164825878671579L;
	public static final String PERSON_ID = "7236560a2a7a4bdda069208f958c5946";
	public static final String USER_ID = "7cf5ef23f8fc409982064de7f251f42b";
	public static final String USER_NAME = "anonymousUser";

	public static final AnonymousUser INSTANCE = new AnonymousUser();

	public Collection<Group> getGroups() {
		return new ArrayList<Group>();
	}

	public Collection<Role> getRoles() {
		return new ArrayList<Role>();
	}

	public String getPersonId() {
		return AnonymousUser.PERSON_ID;
	}

	public String getUserId() {
		return AnonymousUser.USER_ID;
	}

	public String getUserName() {
		return AnonymousUser.USER_NAME;
	}
	
	@Override
	public String getNickName() {
		return AnonymousUser.USER_NAME;
	}

	public String getLoginId() {
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T getAttribute(String name, Class<T> requiredType) {
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAvatar() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Token> getTokens() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getEmail() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMobile() {
		return null;
	}

	public String getWeixin() {
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getAttributes() {
		return Maps.newHashMap();
	}

	@Override
	public String getDomainId() {
		return null;
	}

}
