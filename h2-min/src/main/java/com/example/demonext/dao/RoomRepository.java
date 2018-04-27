package com.example.demonext.dao;

import com.example.demonext.model.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoomRepository extends CrudRepository<Room, Long>
{
	List<Room> findAll();
}
