package org.bosik.bootdemo;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nikita Bosik
 * @since 2018-04-12
 */
@RestController
public class MyRestController implements MyRestInterface
{
	@Override
	public MinMaxEntity process(@RequestParam(value = "q", defaultValue = "") String parNumbers)
	//	public MinMaxEntity process(String parNumbers)
	{
		List<Integer> numbers = parseNumbers(parNumbers);

		int sum = numbers.stream().mapToInt(e -> e).sum();
		int min = numbers.stream().mapToInt(e -> e).min().orElse(0);
		int max = numbers.stream().mapToInt(e -> e).max().orElse(0);

		return new MinMaxEntity(sum - max, sum - min);
	}

	private static List<Integer> parseNumbers(String s)
	{
		if (s.trim().isEmpty())
		{
			return Collections.emptyList();
		}

		String[] items = s.split(",");
		return Arrays.stream(items).map(item -> Integer.parseInt(item.trim())).collect(Collectors.toList());
	}
}