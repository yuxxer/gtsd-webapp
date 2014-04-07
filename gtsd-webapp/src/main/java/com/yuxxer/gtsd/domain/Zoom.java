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
 *	派送范围
 * @author yuxxer
 * @date 2014-2-22
 * @version $Id$
 */
@Entity(name = ProjectConfig.PREFIX + "zoom")
@Table(name = ProjectConfig.NAME + "_zoom")
//@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class Zoom implements DomainObject {

	private static final long serialVersionUID = 1L;

	@PrimaryKey
	private String id;
	
	private String name;
	
	private String parentId;
	
	private String master;//负责人
	
	private String telephone;//联系电话
	
	private String fax;//联系电话

	private String inZoom;//派送范围
	
	private String outZoom ;//不派送地区
	
	private String cityId;//所属城市
	
	private String provinceId;//所属省份
	
	private String address;
	
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
	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	@Column
	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	@Column
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	@Column(length=240)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@Column
	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}
	@Column
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@Column(length=240)
	public String getInZoom() {
		return inZoom;
	}

	public void setInZoom(String inZoom) {
		this.inZoom = inZoom;
	}
	@Column(length=240)
	public String getOutZoom() {
		return outZoom;
	}

	public void setOutZoom(String outZoom) {
		this.outZoom = outZoom;
	}
	@Column
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	@Column
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
