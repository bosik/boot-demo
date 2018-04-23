package org.bosik.bootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpringBootDemoApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(SpringBootDemoApplication.class, args);
	}
}
