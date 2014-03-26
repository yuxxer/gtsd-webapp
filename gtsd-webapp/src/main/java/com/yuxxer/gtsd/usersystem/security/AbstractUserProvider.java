/*
 * @(#)AbstractUserProvider.java 0.1 Nov 28, 2007
 * Copyright 2004-2007 Homolo Co., Ltd. All rights reserved.
 */

package com.yuxxer.gtsd.usersystem.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Class: AbstractUserProvider
 * 
 * @author: wch3116
 */
public abstract class AbstractUserProvider implements UserDetailsService {

	/**
	 * load a user
	 * 
	 * @param userId
	 * @return
	 */
	protected abstract UserAdapter loadUserAdapter(String userName);

	/**
	 * {@inheritDoc}
	 */
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
		UserAdapter userAdapter = loadUserAdapter(userName);
		if (userAdapter == null) {
			throw new UsernameNotFoundException("User not found!");
		} else {
			return new UserDetailAdapter(userAdapter);
		}

	}

}
