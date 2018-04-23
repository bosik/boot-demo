package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceDemoSentence
{
	public static void main(String[] args)
	{
		SpringApplication.run(ServiceDemoSentence.class, args);
	}
}

@RestController
class ServiceRest
{
	private final List<String> services = new ArrayList<String>()
	{
		{
			add("demo-subject");
			add("demo-verb");
			add("demo-article");
			add("demo-adjective");
			add("demo-noun");
		}
	};

	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getData()
	{
		// return buildSentenceSync();
		return buildSentenceAsync();
	}

	private String buildSentenceAsync()
	{
		ExecutorService executor = Executors.newFixedThreadPool(services.size());

		try
		{
			List<Future<String>> futures = new ArrayList<>();

			// run
			for (final String service : services)
			{
				futures.add(executor.submit(() -> getData(service)));
			}

			// collect
			StringBuilder s = new StringBuilder();
			for (Future<String> future : futures)
			{
				String data;
				try
				{
					data = future.get();
				}
				catch (InterruptedException | ExecutionException e)
				{
					e.printStackTrace();
					data = "[calling service interrupted]";
				}

				if (s.length() > 0)
				{
					s.append(' ');
				}

				s.append(data);
			}

			return s.toString();
		}
		finally
		{
			executor.shutdownNow();
		}
	}

	private String buildSentenceSync()
	{
		StringBuilder s = new StringBuilder();

		for (String service : services)
		{
			final String data = getData(service);

			if (s.length() > 0)
			{
				s.append(' ');
			}

			s.append(data);
		}

		return s.toString();
	}

	private String getData(String service)
	{
		List<ServiceInstance> instances = discoveryClient.getInstances(service);

		if (instances != null && !instances.isEmpty())
		{
			URI uri = instances.get(0).getUri();

			try
			{
				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<String> r = restTemplate.getForEntity(uri, String.class);
				Thread.sleep(1000);
				return r.getBody();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return "[service " + service + " failed]";
			}
		}
		else
		{
			return "[service " + service + " not found]";
		}
	}
}