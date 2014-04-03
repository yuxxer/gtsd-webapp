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
import com.yuxxer.gtsd.condition.ProvinceCondition;
import com.yuxxer.gtsd.domain.Province;

@DomainEngine(types = Province.class)
@Transactional(readOnly = true)
public class ProvinceManager {

	@Resource(name = ProjectConfig.NAME+".provinceDao")
	private DomainObjectDao<Province> provinceDao;

	@EventMethod
	@DomainEngine.C(type = Province.class)
	@Transactional
	public String createProvince(Province object) {
		if (object.getId() == null) {
			object.setId(UUID.generateUUID());
		}
		provinceDao.createObject(object);
		return object.getId();
	}

	@EventMethod
	@DomainEngine.U(type = Province.class)
	@Transactional
	public void updateProvince(Province object) {
		provinceDao.updateObject(object);
	}

	@EventMethod
	@DomainEngine.D(type = Province.class)
	@Transactional
	public void deleteProvince(Province object) {
		if (object != null) {
			provinceDao.deleteObject(object);
		}
	}

	@EventMethod("deleteProvince")
	@Transactional
	public void deleteProvince(String id) {
		deleteProvince(getProvince(id));
	}

	@DomainEngine.R(type = Province.class)
	public Province getProvince(String id) {
		return provinceDao.loadObject(id);
	}

	public List<Province> getAllProvinces() {
		return provinceDao.findAll();
	}

	@EventMethod("deleteallProvince")
	@Transactional
	public void deleteAllProvinces() {
		provinceDao.deleteAll();
	}
	
	public int count(ProvinceCondition condition) {
		return provinceDao.countByCondition(condition);
	}
	
	public PaginationSupport<Province> searchProvinces(ProvinceCondition condtion,
			Range range, Sorter sorter) {
		return provinceDao.findByCondition(condtion, range, sorter);
	}
	
}