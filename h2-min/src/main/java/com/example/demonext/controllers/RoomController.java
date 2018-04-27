package com.example.demonext.controllers;

import com.example.demonext.model.Room;
import com.example.demonext.services.AlreadyBookedException;
import com.example.demonext.services.RoomNotFoundException;
import com.example.demonext.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoomController
{
	@Autowired
	private RoomService roomService;

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ResponseEntity findRooms()
	{
		try
		{
			List<Room> rooms = roomService.findRooms(RoomController::getRoomPredicate1);
			return ResponseEntity.ok(rooms);
		}
		catch (Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	private static boolean getRoomPredicate1(Room room)
	{
		return !room.getBooked() && room.getType() == Room.Type.LUXURY && room.getPrice() >= 100 && room.getPrice() <= 300
				&& room.getCapacity() == Room.Capacity.DOUBLE;
	}

	private static boolean getRoomPredicate2(Room room)
	{
		return room.getBooked() && room.getType() == Room.Type.ECONOMY;
	}

	@RequestMapping(path = "/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity bookRoom(HttpEntity<String> body)
	{
		try
		{
			Long roomId = Long.parseLong(body.getBody());
			roomService.book(roomId);
			return ResponseEntity.ok("Room " + roomId + " booked");
		}
		catch (RoomNotFoundException e)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		catch (AlreadyBookedException e)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		catch (Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
