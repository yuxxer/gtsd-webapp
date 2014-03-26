package com.yuxxer.gtsd.usersystem.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class UserLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements ApplicationEventPublisherAware {
	
    private ApplicationEventPublisher applicationEventPublisher;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		super.onLogoutSuccess(request, response, authentication);
		if (authentication != null) {
			applicationEventPublisher.publishEvent(new UserLogoutEvent(authentication));
		}
	}

	@Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


}
