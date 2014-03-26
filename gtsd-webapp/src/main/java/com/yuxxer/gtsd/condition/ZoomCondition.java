package com.yuxxer.gtsd.condition;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.homolo.framework.dao.hibernate.HibernateCondition;

public class ZoomCondition implements HibernateCondition{
	private String name;
	
	private String parentId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public void populateDetachedCriteria(DetachedCriteria criteria) {
		if (StringUtils.isNotBlank(name)) {
			criteria.add(Restrictions.like("name", name));
		}
		if (StringUtils.isNotBlank(parentId)) {
			criteria.add(Restrictions.eq("parentId", parentId));
		}
	}

}
