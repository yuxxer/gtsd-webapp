package com.yuxxer.gtsd.condition;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.homolo.framework.dao.hibernate.HibernateCondition;

public class CityCondition implements HibernateCondition{
	private String name;
	
	private String code;
	
	private String provinceId;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void populateDetachedCriteria(DetachedCriteria criteria) {
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(provinceId)) {
			criteria.add(Restrictions.eq("provinceId", provinceId));
		}
		if (StringUtils.isNotBlank(code)) {
			criteria.add(Restrictions.eq("code", code));
		}
	}

}
