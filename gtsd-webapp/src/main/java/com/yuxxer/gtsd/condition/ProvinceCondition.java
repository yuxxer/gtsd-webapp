package com.yuxxer.gtsd.condition;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.homolo.framework.dao.hibernate.HibernateCondition;

public class ProvinceCondition implements HibernateCondition{
	private String name;
	
	private String code;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
		if (StringUtils.isNotBlank(code)) {
			criteria.add(Restrictions.eq("code", code));
		}
	}

}
