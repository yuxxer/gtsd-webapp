package spring;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.hql.ast.tree.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;

import com.homolo.framework.bean.DynamicBeanFactory;
import com.homolo.framework.cache.CacheFactory;
import com.homolo.framework.cache.CacheFactoryFactory;
import com.homolo.framework.cache.MapCacheFactoryImpl;
import com.homolo.framework.cluster.Global;
import com.homolo.framework.cluster.Standalone;
import com.homolo.framework.dao.mongo.GlobalSynchronizer;
import com.homolo.framework.manager.BeanFieldWirer;
import com.homolo.framework.manager.DomainEngineFactory;
import com.homolo.framework.module.ModuleRegistry;
import com.homolo.framework.util.EnvironmentHelper;
import com.homolo.framework.util.MessageUtils;

@Configuration
@PropertySource(name = "web.env", value = "classpath:/env.properties")
@Import({
	/*** import framework configs ***/
	com.homolo.framework.protocol.BeanConfig.class, 
	com.homolo.framework.rest.BeanConfig.class,
	/*** import self configs ***/
	com.yuxxer.gtsd.ProjectConfig.class
	})
public class WebConfig {
	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired
	Environment environment;

	@Bean
	WebApplication webApplication() {
		return new WebApplication();
	}

	@Bean(name = "global.cacheFactory")
	CacheFactory globalCacheFactory() {
		return new MapCacheFactoryImpl();
	}
	
	@Bean(name = "local.cacheFactory")
	CacheFactory localCacheFactory() {
		return new MapCacheFactoryImpl();
	}
	
	@Bean
	CacheFactoryFactory cacheFactoryFactory() {
		return new CacheFactoryFactory();
	}
	
	@Bean
	ModuleRegistry moduleRegistry() {
		return new ModuleRegistry();
	}

	@Bean
	DomainEngineFactory domainEngineFactory() {
		return new DomainEngineFactory();
	}
	
	@Bean
	BeanFieldWirer beanFieldWirer() {
		return new BeanFieldWirer();
	}
	
	@Bean
	GlobalSynchronizer globalSynchronizer() {
		return new GlobalSynchronizer();
	}
	
	@Bean
	DynamicBeanFactory dynamicBeanFactory() {
		return new DynamicBeanFactory();
	}
	
	@Bean
	Global global() {
		return new Standalone();
	}

	@Bean
	Node node() {
		return new Node();
	}
	
	@Bean(name = "hibernate.packagesToScan")
	List<String> hibernateScanPackages() {
		List<String> packageNames = new ArrayList<String>();
		packageNames.add("com.homolo.framework.hibernate");
//		Map<String, Object> moduleConfigBeans = applicationContext.getBeansWithAnnotation(ModuleConfig.class);
//		for (Map.Entry<String, Object> entry : moduleConfigBeans.entrySet()) {
//			ModuleConfig mc = entry.getValue().getClass().getAnnotation(ModuleConfig.class);
//			if (mc!=null && mc.domainPackages() != null && mc.domainPackages().length > 0) {
//				packageNames.addAll(Arrays.asList(mc.domainPackages()));
//			}
//		}
		packageNames.add("com.yuxxer.gtsd.domain");
		return packageNames;
	}
	
	@Bean
	MessageSource messageSource() {
		ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
		EnvironmentHelper helper = new EnvironmentHelper(environment);
		MutablePropertySources sources = helper.getPropertySources();
		List<String> messages = new LinkedList<String>();
		for (Iterator<org.springframework.core.env.PropertySource<?>> iterator = sources.iterator(); iterator.hasNext();) {
			org.springframework.core.env.PropertySource<?> source = iterator.next();
			Object original = source.getSource();
			if (Properties.class.isInstance(original)) {
				Properties props = Properties.class.cast(original);
				for (String key : props.stringPropertyNames()) {
					if (key.startsWith("message.i18n.")) {
						messages.add(props.getProperty(key));
					}
				}
			}
		}
		String[] messageArray = messages.toArray(new String[messages.size()]);
		CollectionUtils.reverseArray(messageArray);
		ms.setBasenames(messageArray);
		MessageUtils.setDefaultMessageSource(ms);
		return ms;
	}
}