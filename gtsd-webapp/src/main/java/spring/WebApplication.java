package spring;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WebApplication {

	protected static Log log = LogFactory.getLog(WebApplication.class);


	@PostConstruct
	public void init() {

		log.info("Set web application default locale: zh_CN.");
		Locale.setDefault(Locale.SIMPLIFIED_CHINESE);

	}
}
