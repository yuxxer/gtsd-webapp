package com.yuxxer.gtsd;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.homolo.framework.bean.ProjectBeanNameGenerator;
import com.homolo.framework.dao.DomainObjectDao;
import com.homolo.framework.dao.hibernate.BaseHibernateDaoSupport;
import com.homolo.framework.events.EventTarget;
import com.homolo.framework.module.ModuleConfig;
import com.yuxxer.gtsd.domain.Zoom;

@ModuleConfig(name = ProjectConfig.NAME, domainPackages = {"com.yuxxer.gtsd.domain","com.yuxxer.gtsd.usersystem.domain"})
@ComponentScan(basePackages = { "com.yuxxer.gtsd" }, nameGenerator = ProjectBeanNameGenerator.class,
excludeFilters = {@ComponentScan.Filter(Configuration.class)})
@PropertySource(name = "gtsd.env", value = { "classpath:com/yuxxer/gtsd/proj.properties" })
public class ProjectConfig {

	public static final String NAME = "gtsd";

	public static final String PREFIX = NAME + ".";

	@Bean(name = PREFIX + "eventTarget")
	public EventTarget generateEventTarget() {
		return new EventTarget();
	}

	@Bean(name = PREFIX + "zoomDao")
	public DomainObjectDao<Zoom> generateZoomDao() {
		return new BaseHibernateDaoSupport<Zoom>(Zoom.class);
	}
}
