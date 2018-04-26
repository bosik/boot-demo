package org.bosik.h2demo.dao;

import org.bosik.h2demo.entities.RoomType;
import org.springframework.stereotype.Repository;

/**
 * @author Nikita Bosik
 * @since 2018-04-25
 */
@Repository
public class RoomTypeDao extends Dao<RoomType>
{
	@Override
	protected Class<RoomType> getEntityClass()
	{
		return RoomType.class;
	}
}
