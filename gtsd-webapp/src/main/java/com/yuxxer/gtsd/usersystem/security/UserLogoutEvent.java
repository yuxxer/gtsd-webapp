package com.yuxxer.gtsd.usersystem.security;

import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.core.Authentication;

public class UserLogoutEvent extends AbstractAuthenticationEvent {

	private static final long serialVersionUID = 2424822710734762922L;

	public UserLogoutEvent(Authentication authentication) {
        super(authentication);
    }
}
