package org.bosik.bootdemo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Nikita Bosik
 * @since 2018-04-17
 */
@FeignClient("any-id-works") // TODO
public interface MyRestInterface
{
	//	@RequestLine("GET /process/?q={q}")
	//	@RequestMapping(value = "/process?q={q}", method = RequestMethod.GET)
	//	MinMaxEntity process(@PathVariable("q") String numbers);

	@RequestMapping(value = "/process", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	MinMaxEntity process(@RequestParam(value = "q", defaultValue = "") String numbers);
}
