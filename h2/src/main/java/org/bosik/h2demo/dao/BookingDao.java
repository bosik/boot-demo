package org.bosik.h2demo.dao;

import org.bosik.h2demo.entities.Booking;
import org.bosik.h2demo.entities.Room;
import org.springframework.stereotype.Repository;

/**
 * @author Nikita Bosik
 * @since 2018-04-25
 */
@Repository
public class BookingDao extends Dao<Booking>
{
	@Override
	protected Class<Booking> getEntityClass()
	{
		return Booking.class;
	}
}
