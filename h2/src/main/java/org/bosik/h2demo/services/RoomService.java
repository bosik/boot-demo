package org.bosik.h2demo.services;

import org.bosik.h2demo.dao.RoomDao;
import org.bosik.h2demo.entities.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Nikita Bosik
 * @since 2018-04-25
 */
@Service
@Transactional
public class RoomService
{
	@Autowired
	private RoomDao dao;

	public void create(Room entity)
	{
		dao.create(entity);
	}

	public List<Room> findAll()
	{
		return dao.findAll();
	}

	public Room find(String id)
	{
		return dao.find(id);
	}
}
