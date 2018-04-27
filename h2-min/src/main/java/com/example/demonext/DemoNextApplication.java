package com.example.demonext;

import com.example.demonext.model.Room;
import com.example.demonext.services.RoomService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoNextApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(DemoNextApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(RoomService roomService)
	{
		return (args) ->
		{
			roomService.init();
		};
	}
}
