/*
 * @(#)DefaultUserProviderImpl.java 0.1 Nov 28, 2007
 * Copyright 2004-2007 Homolo Co., Ltd. All rights reserved.
 */


package com.yuxxer.gtsd.usersystem.security;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * Class:	DefaultUserProviderImpl
 * Remark:	
 * @author: wch3116
 */
public class BasedAccountUserProvider extends AbstractUserProvider {

    private UserProvider userProvider;


    @Autowired
	public void setUserGroupRoleProvider(UserProvider userGroupRoleProvider) {
		this.userProvider = userGroupRoleProvider;
	}

	protected UserAdapter loadUserAdapter(String userName) {
        return userProvider.getUserAdapterByUserName(userName);
    }
}

