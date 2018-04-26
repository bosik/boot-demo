package org.bosik.h2demo.controllers;

import org.bosik.h2demo.entities.Booking;
import org.bosik.h2demo.entities.Room;
import org.bosik.h2demo.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Nikita Bosik
 * @since 2018-04-25
 */
@RestController
@EnableAutoConfiguration
public class RoomController
{
	@Autowired
	private RoomService roomService;

	@RequestMapping(value = "/rooms", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Room> getRooms()
	{
		return roomService.findAll();
	}

	@RequestMapping(value = "/rooms/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getRoom(@PathVariable(value = "id") String id)
	{
		final Room room = roomService.find(id);
		if (room != null)
		{
			return ResponseEntity.ok(room);
		}
		else
		{
			return new ResponseEntity<>("Room " + id + " not found", HttpStatus.NOT_FOUND);
		}
	}
}
