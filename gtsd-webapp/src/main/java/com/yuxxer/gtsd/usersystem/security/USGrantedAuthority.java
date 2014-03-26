/*
 * @(#)USGrantedAuthority.java 0.1 Nov 28, 2007
 * Copyright 2004-2007 Homolo Co., Ltd. All rights reserved.
 */

package com.yuxxer.gtsd.usersystem.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import com.homolo.framework.security.acl.Subject;

/**
 * Class: USGrantedAuthority Remark:
 * 
 * @author: wch3116
 */
public final class USGrantedAuthority implements GrantedAuthority {

	// ~ Static fields ======================================================================================

	private static final long serialVersionUID = 2399402528854385710L;
	
	//~ Instance fields ================================================================================================
	
    private final String role;

	private String subjectId;
	
    //~ Constructors ===================================================================================================
	
	public USGrantedAuthority(Subject subject) {
		Assert.notNull(subject, "A granted authority textual representation is required");
		this.role = subject.getPrefix() + subject.getSubjectId();
		this.subjectId = subject.getSubjectId();
	}
	
    //~ Methods ========================================================================================================

    public boolean equals(Object obj) {
        if (obj instanceof String) {
            return obj.equals(this.role);
        }

        if (obj instanceof GrantedAuthority) {
            GrantedAuthority attr = (GrantedAuthority) obj;

            return this.role.equals(attr.getAuthority());
        }

        return false;
    }

    public String getAuthority() {
        return this.role;
    }

    public int hashCode() {
        return this.role.hashCode();
    }

    public String toString() {
        return this.role;
    }
    
	public String getSubjectId() {
		return subjectId;
	}

}
