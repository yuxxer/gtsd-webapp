/**
 * Project:homolo-usersystem
 * File:ProxyWebAuthenticationDetailsSource.java
 * Copyright 2004-2012 Homolo Co., Ltd. All rights reserved.
 */
package com.yuxxer.gtsd.usersystem.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;

/**
 * @author Rory
 * @date Feb 10, 2012
 * @version $Id: ProxyWebAuthenticationDetailsSource.java 16484 2012-02-10 10:42:59Z rory $
 */
public class ProxyWebAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, ProxyWebAuthenticationDetails> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProxyWebAuthenticationDetails buildDetails(HttpServletRequest context) {
		return new ProxyWebAuthenticationDetails(context);
	}

}
