/*
 * @(#)DataEnvInitializer.java 0.1 Jul 10, 2009
 * Copyright 2004-2007 Homolo Co., Ltd. All rights reserved.
 */

package com.yuxxer.gtsd.setup;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.homolo.framework.protocol.ProtocolRegistry;
import com.homolo.framework.setup.Initializer;
import com.yuxxer.gtsd.Constants;
import com.yuxxer.gtsd.ProjectConfig;
import com.yuxxer.gtsd.usersystem.domain.User;
import com.yuxxer.gtsd.usersystem.enums.Role;
import com.yuxxer.gtsd.usersystem.manager.UserManager;

@Component
public class SetupInitializer extends Initializer {

	@Autowired
	private UserManager userManager;
	

	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ProtocolRegistry protocolRegistry;
	
	@Resource(name = ProjectConfig.PREFIX+"passwordEncoder")
	public void setPasswordEncoder(Object injectedPasswordEncoder) {
		if (injectedPasswordEncoder instanceof PasswordEncoder) {
			this.passwordEncoder = (PasswordEncoder) injectedPasswordEncoder;
		} else if (injectedPasswordEncoder instanceof org.springframework.security.crypto.password.PasswordEncoder) {
			final org.springframework.security.crypto.password.PasswordEncoder delegate =
                    (org.springframework.security.crypto.password.PasswordEncoder) injectedPasswordEncoder;
            this.passwordEncoder = new PasswordEncoder() {
                public String encodePassword(String rawPass, Object salt) {
                    checkSalt(salt);
                    return delegate.encode(rawPass);
                }

                public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
                    checkSalt(salt);
                    return delegate.matches(rawPass, encPass);
                }

                private void checkSalt(Object salt) {
                    Assert.isNull(salt, "Salt value must be null when used with crypto module PasswordEncoder");
                }
            };
		}
	}

	private void checkAndInitializeAdminUser() {
		User user = userManager.getUser(Constants.ADMINISTRATOR_USERID);
		if (user == null) {
			user = new User();
			user.setId(Constants.ADMINISTRATOR_USERID);
			user.setUserName(Constants.ADMINISTRATOR_USERNAME);
			user.setPassword(passwordEncoder.encodePassword(Constants.ADMINISTRATOR_PASSWORD, null));
			user.setNickName(Constants.ADMINISTRATOR_NICKNAME);
			user.setDescription(Constants.ADMINISTRATOR_DESCRIPTION);
			user.setRegDate(new Date());
			user.setDisabled(false);
			user.setLocked(false);
			user.setEmail(Constants.ADMINISTRATOR_EMAIL);
			user.setPersonId(Constants.ADMINISTRATOR_PERSONID);
			user.setRole(Role.Admin);
			userManager.createUser(user);
		}
	}

	public void initialize() {
		checkAndInitializeAdminUser();
	}
}
