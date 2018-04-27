package com.example.demonext;

import com.example.demonext.model.Room;
import com.example.demonext.services.RoomService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = {  RoomRepository.class, RoomService.class, RoomController.class })
@SpringBootTest
@WebAppConfiguration
public class DemoNextApplicationTests
{
	//	@Autowired
	private MockMvc mvc;

	@Autowired
	private WebApplicationContext webConfig;

	@Autowired
	private RoomService roomService;

	@Before
	public void init()
	{
		this.mvc = MockMvcBuilders.webAppContextSetup(webConfig).build();
		roomService.init();
	}

	@Test
	public void test_getAvailableRooms() throws Exception
	{
		mvc.perform(get("/")).andExpect(status().isOk());//.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	public void test_book_wrong() throws Exception
	{
		mvc.perform(post("/").content("-42")).andExpect(status().isNotFound()).andExpect(content().string("Room -42 not found"));
	}

	@Test
	public void test_book_free() throws Exception
	{
		//		mvc.perform(get("/")).andDo(r -> r.getResponse().getContentAsString()})
		List<Room> rooms = roomService.findRooms(e -> !e.getBooked());
		Assert.assertTrue(!rooms.isEmpty());

		Long id = rooms.get(0).getId();
		mvc.perform(post("/").content(String.valueOf(id))).andExpect(status().isOk()).andExpect(content().string("Room " + id + " booked"));
	}

	@Test
	public void test_book_booked() throws Exception
	{
		List<Room> rooms = roomService.findRooms(e -> !e.getBooked());
		Assert.assertTrue(!rooms.isEmpty());

		Long id = rooms.get(0).getId();
		mvc.perform(post("/").content(String.valueOf(id))).andExpect(status().isOk()).andExpect(content().string("Room " + id + " booked"));
		mvc.perform(post("/").content(String.valueOf(id))).andExpect(status().isBadRequest())
				.andExpect(content().string("Room " + id + " already booked"));
	}
}
