/*
 * @(#)UserGroupRoleProvider.java 0.1 Aug 21, 2008
 * Copyright 2004-2007 Homolo Co., Ltd. All rights reserved.
 */

package com.yuxxer.gtsd.usersystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

import com.yuxxer.gtsd.usersystem.domain.User;
import com.yuxxer.gtsd.usersystem.manager.UserManager;

public class UserProvider {


	@Autowired
	protected UserManager userSystemManager;

	public UserAdapter getUserAdapter(String userId)  throws AccountStatusException{
		User user = userSystemManager.getUser(userId);
		return getUserAdapter(user);
	}

	public UserAdapter getUserAdapter(User user) throws AccountStatusException {
		if (user == null) {
			return null;
		} else {
			if (user.isLocked()) {
				throw new LockedException("User locked");
			}
			if (user.isDisabled()) {
				throw new DisabledException("User disabled");
			}
			return new UserAdapter(user);
		}
	}

	public UserAdapter getUserAdapterByUserName(String userName)  throws AccountStatusException {
		User user = userSystemManager.getUserByUserName(userName);
		return getUserAdapter(user);
	}
}
