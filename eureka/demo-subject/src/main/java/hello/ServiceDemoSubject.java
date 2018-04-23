package hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceDemoSubject
{
	public static void main(String[] args)
	{
		SpringApplication.run(ServiceDemoSubject.class, args);
	}
}

@RestController
class ServiceRest
{
	@Value("#{'${words}'.split(',')}")
	private List<String> words;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getData()
	{
		return words.get(new Random().nextInt(words.size()));
	}
}