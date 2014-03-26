/*
 * @(#)AcegiUserSessionFactory.java 0.1 Jul 23, 2008
 * Copyright 2004-2007 Homolo Co., Ltd. All rights reserved.
 */

package com.yuxxer.gtsd.usersystem.security;

import org.springframework.security.core.context.SecurityContextHolder;

import com.homolo.framework.security.AuthenticatedUser;

/**
 * Class <code>AcegiUserSessionFactory</code> is ...
 *
 * @author  wch3116
 * @version 0.1, Jul 23, 2008
 */
public class UserSessionFactory {

    /**
     * 获得当前用户
     * @return
     */
    public static AuthenticatedUser currentUser () {
    	return new AuthenticationUserImpl(SecurityContextHolder.getContext().getAuthentication());
    }
    
    /**
     * 判断当前是否是用户会话
     * @return
     */
    public static boolean isUserSession() {
    	return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    
}
