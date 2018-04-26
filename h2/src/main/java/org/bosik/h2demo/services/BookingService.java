package org.bosik.h2demo.services;

import org.bosik.h2demo.dao.BookingDao;
import org.bosik.h2demo.entities.Booking;
import org.bosik.h2demo.entities.Room;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author Nikita Bosik
 * @since 2018-04-25
 */
@Service
@Transactional
public class BookingService
{
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private BookingDao bookingDao;

	public List<Room> findAvailableRooms(Date dateFrom, Date dateTo, Integer minPrice, Integer maxPrice, Set<String> typeIds,
			Set<Integer> capacity)
	{
		Session session = (Session) entityManager.getDelegate();

		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Room> query = cb.createQuery(Room.class);

		Root<Room> root = query.from(Room.class);
		query.select(root);

		Subquery<Booking> subquery = query.subquery(Booking.class);
		Root<Booking> subRoot = subquery.from(Booking.class);
		subquery.select(subRoot);

		subquery.where(cb.and(cb.equal(subRoot.get("room"), root), cb.lessThan(subRoot.get("dateFrom"), dateTo),
				cb.greaterThan(subRoot.get("dateTo"), dateFrom)));

		List<Predicate> roomConditions = new ArrayList<>();
		roomConditions.add(cb.not(cb.exists(subquery)));
		if (minPrice != null)
		{
			roomConditions.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
		}

		if (maxPrice != null)
		{
			roomConditions.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
		}

		if (typeIds != null && !typeIds.isEmpty())
		{
			roomConditions.add(cb.in(root.get("type").get("id")).value(typeIds));
		}

		if (capacity != null && !capacity.isEmpty())
		{
			roomConditions.add(cb.in(root.get("capacity").get("capacity")).value(capacity));
		}

		query.where(cb.and(roomConditions.toArray(new Predicate[] {})));

		query.orderBy(cb.asc(root.get("price")));

		return entityManager.createQuery(query).getResultList();
	}

	public Booking book(String roomId, Date dateFrom, Date dateTo, String userId)
	{
		// TODO: implement proper locking

		List<Room> rooms = findAvailableRooms(dateFrom, dateTo, null, null, null, null);
		for (Room room : rooms)
		{
			if (room.getId().equals(roomId))
			{
				Booking booking = new Booking();
				booking.setId(UUID.randomUUID().toString());
				booking.setRoom(room);
				booking.setDateFrom(dateFrom);
				booking.setDateTo(dateTo);
				booking.setUserId(userId);

				bookingDao.create(booking);

				return booking;
			}
		}

		throw new IllegalArgumentException("Room " + roomId + " not available");
	}

	public void create(Booking entity)
	{
		bookingDao.create(entity);
	}

	public List<Booking> findAll()
	{
		return bookingDao.findAll();
	}

	public Booking find(String id)
	{
		return bookingDao.find(id);
	}

	public boolean cancel(String id)
	{
		return bookingDao.delete(id);
	}
}
