package com.example.demonext.services;

import com.example.demonext.dao.RoomRepository;
import com.example.demonext.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class RoomService
{
	@Autowired
	private RoomRepository dao;

	public List<Room> findRooms(Predicate<Room> condition)
	{
		return dao.findAll().stream().filter(condition).collect(Collectors.toList());
	}

	public void book(Long roomId) throws AlreadyBookedException, RoomNotFoundException
	{
		Optional<Room> room = dao.findById(roomId);
		if (room.isPresent())
		{
			if (!room.get().getBooked())
			{
				room.get().setBooked(true);
				dao.save(room.get());
			}
			else
			{
				throw new AlreadyBookedException(roomId);
			}
		}
		else
		{
			throw new RoomNotFoundException(roomId);
		}
	}

	public void init()
	{
		dao.deleteAll();

		dao.save(new Room(Room.Type.ECONOMY, Room.Capacity.SINGLE, false, false, 20, false));
		dao.save(new Room(Room.Type.ECONOMY, Room.Capacity.SINGLE, false, false, 20, false));
		dao.save(new Room(Room.Type.ECONOMY, Room.Capacity.DOUBLE, false, true, 40, false));
		dao.save(new Room(Room.Type.ECONOMY, Room.Capacity.DOUBLE, false, false, 35, false));
		dao.save(new Room(Room.Type.STANDARD, Room.Capacity.SINGLE, true, true, 50, false));
		dao.save(new Room(Room.Type.STANDARD, Room.Capacity.SINGLE, true, true, 50, false));
		dao.save(new Room(Room.Type.STANDARD, Room.Capacity.SINGLE, true, true, 50, false));
		dao.save(new Room(Room.Type.STANDARD, Room.Capacity.DOUBLE, true, true, 100, false));
		dao.save(new Room(Room.Type.STANDARD, Room.Capacity.DOUBLE, true, true, 100, false));
		dao.save(new Room(Room.Type.STANDARD, Room.Capacity.TRIPLE, true, true, 150, false));
		dao.save(new Room(Room.Type.LUXURY, Room.Capacity.SINGLE, true, false, 200, false));
		dao.save(new Room(Room.Type.LUXURY, Room.Capacity.SINGLE, true, false, 200, false));
		dao.save(new Room(Room.Type.LUXURY, Room.Capacity.DOUBLE, true, true, 300, false));
		dao.save(new Room(Room.Type.LUXURY, Room.Capacity.DOUBLE, true, true, 300, false));
		dao.save(new Room(Room.Type.LUXURY, Room.Capacity.TRIPLE, true, true, 500, false));
		dao.save(new Room(Room.Type.LUXURY, Room.Capacity.TRIPLE, true, true, 500, false));
	}
}
