package com.yuxxer.gtsd.usersystem.condition;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.homolo.framework.dao.hibernate.HibernateCondition;

public class UserCondition implements HibernateCondition  {
	
	private String partWord;
	
	private String personId;
	
	private String email;
	
	public String getPartWord() {
		return partWord;
	}

	public void setPartWord(String partWord) {
		this.partWord = partWord;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	@Override
	public void populateDetachedCriteria(DetachedCriteria criteria) {
		if (StringUtils.isNotBlank(personId)) {
			criteria.add(Restrictions.eq("personId", personId));
		}
		if (StringUtils.isNotBlank(email)) {
			criteria.add(Restrictions.eq("email", email));
		}
		if (StringUtils.isNotBlank(partWord)) {
			criteria.add(Restrictions.disjunction().add(Restrictions.ilike("userName", partWord, MatchMode.ANYWHERE)).add(Restrictions.ilike("nickName", partWord, MatchMode.ANYWHERE))
					.add(Restrictions.ilike("description", partWord, MatchMode.ANYWHERE)));
		}
	}

}
