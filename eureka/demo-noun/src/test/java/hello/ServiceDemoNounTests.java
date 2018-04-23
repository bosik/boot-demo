package hello;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceDemoNoun.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ServiceDemoNounTests
{
	private static ConfigurableApplicationContext eurekaServer;

	@BeforeClass
	public static void startEureka()
	{
		eurekaServer = SpringApplication.run(EurekaServer.class, "--server.port=8771", "--eureka.instance.leaseRenewalIntervalInSeconds=1");
	}

	@AfterClass
	public static void closeEureka()
	{
		eurekaServer.close();
	}

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void shouldRegisterClientInEurekaServer() throws InterruptedException
	{
		// registration has to take place...
		Thread.sleep(3000);

		ResponseEntity<String> response = testRestTemplate.getForEntity("http://localhost:" + port + "/", String.class);
		then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Configuration
	@EnableAutoConfiguration
	@EnableEurekaServer
	static class EurekaServer
	{
	}
}