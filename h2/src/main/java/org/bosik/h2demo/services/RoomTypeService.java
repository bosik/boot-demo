package org.bosik.h2demo.services;

import org.bosik.h2demo.dao.RoomTypeDao;
import org.bosik.h2demo.entities.RoomType;
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
public class RoomTypeService
{
	@Autowired
	private RoomTypeDao dao;

	public void create(RoomType entity)
	{
		dao.create(entity);
	}

	public List<RoomType> findAll()
	{
		return dao.findAll();
	}

	public RoomType find(String id)
	{
		return dao.find(id);
	}
}
