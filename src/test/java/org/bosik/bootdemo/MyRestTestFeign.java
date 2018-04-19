package org.bosik.bootdemo;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Feign-based test implementation
 */
@RunWith(SpringRunner.class)
@WebMvcTest(MyRestController.class)
public class MyRestTestFeign extends MyRestTest
{
	@Override
	public void test(String input, int expectedMin, int expectedMax) throws Exception
	{
		MyRestInterface client = Feign.builder()
				//	.client(new OkHttpClient())
				.encoder(new GsonEncoder()).decoder(new GsonDecoder())
				//	.logger(new Slf4jLogger(BookClient.class))
				//	.logLevel(Logger.Level.FULL)
				.contract(new SpringMvcContract()).target(MyRestInterface.class, "http://localhost:8080");

		MinMaxEntity result = client.process(input);
		Assert.assertEquals(expectedMin, result.getMin());
		Assert.assertEquals(expectedMax, result.getMax());
	}
}