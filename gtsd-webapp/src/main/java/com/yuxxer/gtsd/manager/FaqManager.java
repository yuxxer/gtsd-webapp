package com.yuxxer.gtsd.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.homolo.framework.annotation.DomainEngine;
import com.homolo.framework.dao.DomainObjectDao;
import com.homolo.framework.dao.util.PaginationSupport;
import com.homolo.framework.dao.util.Range;
import com.homolo.framework.dao.util.Sorter;
import com.homolo.framework.events.EventMethod;
import com.homolo.framework.util.UUID;
import com.yuxxer.gtsd.ProjectConfig;
import com.yuxxer.gtsd.condition.FaqCondition;
import com.yuxxer.gtsd.domain.Faq;

@DomainEngine(types = Faq.class)
@Transactional(readOnly = true)
public class FaqManager {

	@Resource(name = ProjectConfig.NAME+".faqDao")
	private DomainObjectDao<Faq> faqDao;

	@EventMethod
	@DomainEngine.C(type = Faq.class)
	@Transactional
	public String createFaq(Faq object) {
		if (object.getId() == null) {
			object.setId(UUID.generateUUID());
		}
		faqDao.createObject(object);
		return object.getId();
	}

	@EventMethod
	@DomainEngine.U(type = Faq.class)
	@Transactional
	public void updateFaq(Faq object) {
		faqDao.updateObject(object);
	}

	@EventMethod
	@DomainEngine.D(type = Faq.class)
	@Transactional
	public void deleteFaq(Faq object) {
		if (object != null) {
			faqDao.deleteObject(object);
		}
	}

	@EventMethod("deleteFaq")
	@Transactional
	public void deleteFaq(String id) {
		deleteFaq(getFaq(id));
	}

	@DomainEngine.R(type = Faq.class)
	public Faq getFaq(String id) {
		return faqDao.loadObject(id);
	}

	public List<Faq> getAllFaqs() {
		return faqDao.findAll();
	}

	@EventMethod("deleteallFaq")
	@Transactional
	public void deleteAllFaqs() {
		faqDao.deleteAll();
	}
	
	public int count(FaqCondition condition) {
		return faqDao.countByCondition(condition);
	}
	
	public PaginationSupport<Faq> searchFaqs(FaqCondition condtion,
			Range range, Sorter sorter) {
		return faqDao.findByCondition(condtion, range, sorter);
	}
	
	public List<Faq> list(FaqCondition condition) {
		return faqDao.findAllByCondition(condition);
	}
}