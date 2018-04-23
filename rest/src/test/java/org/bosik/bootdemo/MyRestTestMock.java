package org.bosik.bootdemo;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * MockMvc-based test implementation
 */
@RunWith(SpringRunner.class)
@WebMvcTest(MyRestController.class)
public class MyRestTestMock extends MyRestTest
{
	@Autowired
	private MockMvc mvc;

	@Override
	protected void test(String input, int expectedMin, int expectedMax) throws Exception
	{
		mvc.perform(get("/process").param("q", input).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.min", is(expectedMin))).andExpect(jsonPath("$.max", is(expectedMax)));
	}
}