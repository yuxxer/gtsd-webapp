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
 *	 常见问题
 * @author yuxxer
 * @date 2014-2-22
 * @version $Id$
 */
@Entity(name = ProjectConfig.PREFIX + "faq")
@Table(name = ProjectConfig.NAME + "_faq")
//@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class Faq implements DomainObject {

	private static final long serialVersionUID = 1L;

	@PrimaryKey
	private String id;
	
	private String question;//问题
	
	private String answer;//答案

	@Id
	@Column
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	@Column(length=5000)
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
