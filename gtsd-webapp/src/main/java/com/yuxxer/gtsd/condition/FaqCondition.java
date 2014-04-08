package com.yuxxer.gtsd.condition;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.homolo.framework.dao.hibernate.HibernateCondition;

public class FaqCondition implements HibernateCondition{
	private String partWord;
	
	private String question;

	public String getPartWord() {
		return partWord;
	}

	public void setPartWord(String partWord) {
		this.partWord = partWord;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	@Override
	public void populateDetachedCriteria(DetachedCriteria criteria) {
		if (StringUtils.isNotBlank(partWord)) {
			criteria.add(Restrictions.disjunction().add(Restrictions.ilike("question", partWord, MatchMode.ANYWHERE)).add(Restrictions.ilike("answer", partWord, MatchMode.ANYWHERE)));
		}
		if (StringUtils.isNotBlank(question)) {
			criteria.add(Restrictions.eq("question", question));
		}
	}

}
