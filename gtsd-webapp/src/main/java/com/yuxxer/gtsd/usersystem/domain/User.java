package com.yuxxer.gtsd.usersystem.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.homolo.framework.annotation.Index;
import com.homolo.framework.annotation.Indexes;
import com.homolo.framework.annotation.NamePosition;
import com.homolo.framework.annotation.PrimaryKey;
import com.homolo.framework.bean.DomainObject;
import com.yuxxer.gtsd.ProjectConfig;
import com.yuxxer.gtsd.usersystem.enums.Gender;
import com.yuxxer.gtsd.usersystem.enums.Role;

/**
 * Class: User Remark: 用户
 * 
 */
@Entity(name = ProjectConfig.PREFIX+"user")
@Table(name = ProjectConfig.NAME+"_user")
@Indexes({ @Index(name = "__userName__", fields = { @Index.Field(key = "userName") }) })
public class User implements DomainObject {


	private static final long serialVersionUID = 270387579980873884L;

	@PrimaryKey
	private String id;

	private String password;


	@NamePosition
	@Index
	private String userName;//loginId

	private String nickName;

	private String nationality;

	private String timeZone;

	@Index
	private String personId;

	private String zipCode;

	private String email;

	private String mobile;

	private Date regDate;

	private String description;

	private Gender gender; // 性别

	private String avatar; // 用户头像

	private boolean disabled = false;

	private boolean locked = false;

	private String weixin; // 用户的微信账号或openId

	private Role role=Role.General;
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Column(length = 50)
	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getRegDate() {
		return regDate;
	}

	@Enumerated(EnumType.STRING)
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Column(length = 150)
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	@Column(length = 36)
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	@Column(length = 150)
	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	@Id
	@Column(length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(length = 150)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(length = 50)
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(length = 150)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(length = 150)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(length = 50)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(length = 150)
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}


	@Column(length = 1024)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(length = 150)
	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
}