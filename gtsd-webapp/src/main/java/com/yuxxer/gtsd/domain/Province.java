/**
 * Project:w201403
 * File:Product.java
 * Copyright 2004-2014 Homolo Co., Ltd. All rights reserved.
 */
package com.yuxxer.gtsd.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.homolo.framework.annotation.PrimaryKey;
import com.homolo.framework.bean.DomainObject;
import com.yuxxer.gtsd.ProjectConfig;

/**
 *	省
 * @author yuxxer
 * @date 2014-2-22
 * @version $Id$
 */
@Entity(name = ProjectConfig.PREFIX + "province")
@Table(name = ProjectConfig.NAME + "_province")
//@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class Province implements DomainObject {

	private static final long serialVersionUID = 1L;

	@PrimaryKey
	private String id;
	
	private String name;
	
	private String code;//代码

	private String description;
	@Id
	@Column
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(length=240)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
