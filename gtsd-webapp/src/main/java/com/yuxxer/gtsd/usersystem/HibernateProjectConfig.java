package com.yuxxer.gtsd.usersystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.homolo.framework.bean.ProjectBeanNameGenerator;
import com.homolo.framework.dao.DomainObjectDao;
import com.homolo.framework.dao.hibernate.BaseHibernateDaoSupport;
import com.yuxxer.gtsd.ProjectConfig;
import com.yuxxer.gtsd.usersystem.domain.User;

@ComponentScan(basePackages={"com.yuxxer.gtsd.usersystem"},nameGenerator=ProjectBeanNameGenerator.class,
		excludeFilters = {@ComponentScan.Filter(Configuration.class)})
@PropertySource(name=ProjectConfig.PREFIX+"usersystem.env", value={"classpath:/com/yuxxer/gtsd/proj.properties"})
@Configuration(ProjectConfig.PREFIX+"usersystem.ProjectConfig")
public class HibernateProjectConfig {
	
	@Bean(name = ProjectConfig.PREFIX+"passwordEncoder")
	public PasswordEncoder generatePasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean(name = ProjectConfig.PREFIX+"userDao")
	public DomainObjectDao<User> generateUserDao() {
		return new BaseHibernateDaoSupport<User>(User.class);
	}
}