/*
 * @(#)UserAdapter.java 0.1 Nov 28, 2007
 * Copyright 2004-2007 Homolo Co., Ltd. All rights reserved.
 */

package com.yuxxer.gtsd.usersystem.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.homolo.framework.security.Group;
import com.homolo.framework.security.Role;
import com.homolo.framework.security.User;
import com.homolo.framework.security.acl.SubjectAdapter;

/**
 * Class: AcegiUserAdapter Remark: the adapter class for User and UserDetails
 * 
 * @author: Wan Changhua
 */
public class UserDetailAdapter implements User, UserDetails {

	private static final long serialVersionUID = 7385676551184045897L;
	
	private UserAdapter userAdapter;
	
	public UserDetailAdapter(UserAdapter userAdapter) {
		this.userAdapter = userAdapter;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		list.add(new USGrantedAuthority(new SubjectAdapter(userAdapter)));
		for (Role role: userAdapter.getRoles()) {
			list.add(new USGrantedAuthority(new SubjectAdapter(role)));
		}
		for (Group group: userAdapter.getGroups()) {
			list.add(new USGrantedAuthority(new SubjectAdapter(group)));
		}
		return list;
	}

	public String getPassword() {
		return userAdapter.getPassword();
	}

	public String getUsername() {
		return userAdapter.getLoginId();
	}

	public boolean isAccountNonLocked() {
		return !userAdapter.isLocked();
	}

	public boolean isCredentialsNonExpired() {
		return isAccountNonExpired();
	}

	public boolean isEnabled() {
		return !userAdapter.isDisabled();
	}

	public Collection<Group> getGroups() {
		return userAdapter.getGroups();
	}

	public Collection<Role> getRoles() {
		return userAdapter.getRoles();
	}

	public String getUserId() {
		return userAdapter.getUserId();
	}

	public String getUserName() {
		return userAdapter.getUserName();
	}

	public String getPersonId() {
		return userAdapter.getPersonId();
	}

	public String getDomainId() {
		return userAdapter.getDomainId();
	}

	public String getLoginId() {
		return userAdapter.getLoginId();
	}

	public String getEmail() {
		return userAdapter.getEmail();
	}
	
	public String getAvatar() {
		return userAdapter.getAvatar();
	}

	public String getNickName() {
		return userAdapter.getNickName();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T getAttribute(String name, Class<T> requiredType) {
		return userAdapter.getAttribute(name, requiredType);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getAttributes() {
		return userAdapter.getAttributes();
	}
	
	
	/** {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
			.append("username", getUsername())
			.append("nickname", getNickName())
			.append("userId", getUserId())
			.append("personId", getPersonId()).toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMobile() {
		return userAdapter.getMobile();
	}

	@Override
	public String getWeixin() {
		return userAdapter.getWeixin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public Collection<?> getTokens() {
		return null;
	}
}
