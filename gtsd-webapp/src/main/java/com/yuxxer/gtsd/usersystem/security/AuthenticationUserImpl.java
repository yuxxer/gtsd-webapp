/*
 * @(#)AuthenticationAcegiUserAdapter.java 0.1 Jul 23, 2008
 * Copyright 2004-2007 Homolo Co., Ltd. All rights reserved.
 */

package com.yuxxer.gtsd.usersystem.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.homolo.framework.security.AuthenticatedUser;
import com.homolo.framework.security.Group;
import com.homolo.framework.security.Role;

/**
 * Class <code>AuthenticationAcegiUserAdapter</code> is ...
 * 
 * @author wch3116
 * @version 0.1, Jul 23, 2008
 */
public class AuthenticationUserImpl implements AuthenticatedUser {

	private static final long serialVersionUID = -2907852481702402111L;

	private Authentication authentication;

	public AuthenticationUserImpl(Authentication authentication) {
		this.authentication = authentication;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authentication == null ? new ArrayList<GrantedAuthority>() : authentication.getAuthorities();
	}

	@Override
	public Object getCredentials() {
		return authentication == null ? null : authentication.getCredentials();
	}

	@Override
	public Object getDetails() {
		return authentication == null ? null : authentication.getDetails();
	}

	@Override
	public String getName() {
		return authentication == null ? null : authentication.getName();
	}

	@Override
	public Object getPrincipal() {
		return authentication == null ? null : authentication.getPrincipal();
	}

	@Override
	public boolean isAuthenticated() {
		if (authentication == null) {
			return false;
		}
		return authentication.isAuthenticated();
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (authentication != null) {
			authentication.setAuthenticated(isAuthenticated);
		}
	}

	@Override
	public boolean isAnonymousUser() {
		if (authentication == null) {
			return true;
		}
		Object principal = authentication.getPrincipal();
		return (principal instanceof String && principal.equals("anonymousUser"));
	}

	@Override
	public String getUserId() {
		if (isAnonymousUser()) {
			return AnonymousUser.INSTANCE.getUserId();
		}
		UserDetailAdapter user = (UserDetailAdapter) authentication.getPrincipal();
		return user.getUserId();
	}

	@Override
	public String getUserName() {
		if (isAnonymousUser()) {
			return AnonymousUser.INSTANCE.getUserName();
		}
		UserDetailAdapter user = (UserDetailAdapter) authentication.getPrincipal();
		return user.getUserName();
	}
	
	@Override
	public String getNickName() {
		if (isAnonymousUser()) {
			return AnonymousUser.INSTANCE.getUserName();
		}
		UserDetailAdapter user = (UserDetailAdapter) authentication.getPrincipal();
		return user.getNickName();
	}

	@Override
	public String getPersonId() {
		if (isAnonymousUser()) {
			return AnonymousUser.INSTANCE.getPersonId();
		}
		UserDetailAdapter user = (UserDetailAdapter) authentication.getPrincipal();
		return user.getPersonId();
	}

	@Override
	public Collection<Group> getGroups() {
		if (isAnonymousUser()) {
			return new ArrayList<Group>();
		}
		UserDetailAdapter user = (UserDetailAdapter) authentication.getPrincipal();
		return user.getGroups();
	}

	@Override
	public Collection<Role> getRoles() {
		if (isAnonymousUser()) {
			return new ArrayList<Role>();
		}
		UserDetailAdapter user = (UserDetailAdapter) authentication.getPrincipal();
		return user.getRoles();
	}

	@Override
	public String getDomainId() {
		if (isAnonymousUser()) {
			return AnonymousUser.INSTANCE.getDomainId();
		}
		UserDetailAdapter user = (UserDetailAdapter) authentication.getPrincipal();
		return user.getDomainId();
	}

	@Override
	public String getLoginId() {
		if (isAnonymousUser()) {
			return AnonymousUser.INSTANCE.getLoginId();
		}
		UserDetailAdapter user = (UserDetailAdapter) authentication.getPrincipal();
		return user.getLoginId();
	}
	
	/** {@inheritDoc}
	 * @see com.homolo.framework.security.AuthenticatedUser#getAuthentication()
	 */
	@Override
	public Authentication getAuthentication() {
		return authentication;
	}

	@Override
	public String getRemoteAddress() {
		if (authentication != null) {
			WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
			if (details != null) {
				return details.getRemoteAddress();
			}
		}
		return null;
	}
	
	/** {@inheritDoc}
	 * @see com.homolo.framework.security.AuthenticatedUser#hasRole(java.lang.String)
	 */
	@Override
	public boolean hasRole(String roleId) {
		if (getRoles() != null) {
			for (Role role : getRoles()) {
				if (StringUtils.equals(roleId, role.getId())) {
					return true;
				}
			}
		}
		return false;
	}
	
	/** {@inheritDoc}
	 * @see com.homolo.framework.security.AuthenticatedUser#hasGroup(java.lang.String)
	 */
	@Override
	public boolean hasGroup(String groupId) {
		if (getGroups() != null) {
			for (Group group : getGroups()) {
				if (StringUtils.equals(group.getId(), groupId)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void setAttribute(String name, Object value) {
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetailAdapter) {
				UserDetailAdapter user = (UserDetailAdapter) principal;
				user.getAttributes().put(name, value);
			}
		}
	}

	public void removeAttribute(String name) {
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetailAdapter) {
				UserDetailAdapter user = (UserDetailAdapter) principal;
				user.getAttributes().remove(name);
			}
		}
	}

	public Object getAttribute(String name) {
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetailAdapter) {
				UserDetailAdapter user = (UserDetailAdapter) principal;
				return user.getAttributes().get(name);
			}
		}
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getAttributes() {
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetailAdapter) {
				UserDetailAdapter user = (UserDetailAdapter) principal;
				return user.getAttributes();
			}
		}
		return null;
	}
	
	public String getAvatar() {
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetailAdapter) {
				UserDetailAdapter user = (UserDetailAdapter) principal;
				return user.getAvatar();
			}
		}
		return null;
	}

	public <T> T getAttribute(String name, Class<T> clz) {
		return clz.cast(getAttribute(name));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getEmail() {
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetailAdapter) {
				UserDetailAdapter user = (UserDetailAdapter) principal;
				return user.getEmail();
			}
		}
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMobile() {
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetailAdapter) {
				UserDetailAdapter user = (UserDetailAdapter) principal;
				return user.getMobile();
			}
		}
		return null;
	}

	@Override
	public String getWeixin() {
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetailAdapter) {
				UserDetailAdapter user = (UserDetailAdapter) principal;
				return user.getWeixin();
			}
		}
		return null;
	}

	@Override
	public Collection<?> getTokens() {
		return null;
	}

}
