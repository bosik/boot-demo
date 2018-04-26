package org.bosik.h2demo;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nikita Bosik
 * @since 2018-04-25
 */
@Configuration
public class WebConfiguration
{
	@Bean
	ServletRegistrationBean h2servletRegistration()
	{
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
		registrationBean.addUrlMappings("/console/*");
		return registrationBean;
	}
}
