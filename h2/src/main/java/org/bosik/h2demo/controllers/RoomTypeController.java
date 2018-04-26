package org.bosik.h2demo.controllers;

import org.bosik.h2demo.entities.RoomType;
import org.bosik.h2demo.services.RoomTypeService;
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
public class RoomTypeController
{
	@Autowired
	private RoomTypeService roomTypeService;

	@RequestMapping(value = "/roomTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RoomType> getRoomTypes()
	{
		return roomTypeService.findAll();
	}

	@RequestMapping(value = "/roomTypes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getRoomType(@PathVariable(value = "id") String id)
	{
		final RoomType roomType = roomTypeService.find(id);
		if (roomType != null)
		{
			return ResponseEntity.ok(roomType);
		}
		else
		{
			return new ResponseEntity<>("Room type " + id + " not found", HttpStatus.NOT_FOUND);
		}
	}
}
