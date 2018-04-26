package org.bosik.h2demo.controllers;

import org.bosik.h2demo.entities.Booking;
import org.bosik.h2demo.entities.Room;
import org.bosik.h2demo.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Nikita Bosik
 * @since 2018-04-25
 */
@RestController
@EnableAutoConfiguration
public class BookingController
{
	@Autowired
	private BookingService bookingService;

	@RequestMapping(value = "/bookings", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity book(@RequestParam(name = "roomId") String roomId, @RequestParam("from") String sDateFrom,
			@RequestParam("to") String sDateTo, @RequestParam(name = "userId") String userId)
	{
		// TODO: parse date params automatically
		// TODO: pass data via body

		try
		{
			final SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			Date dateFrom = f.parse(sDateFrom);
			Date dateTo = f.parse(sDateTo);

			final Booking booking = bookingService.book(roomId, dateFrom, dateTo, userId);
			return ResponseEntity.created(new URI("/bookings/" + booking.getId())).body(booking);
		}
		catch (IllegalArgumentException e)
		{
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		catch (Exception e)
		{
			// TODO: common exception handling
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(value = "/bookings/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String cancel(@PathVariable("id") String id)
	{
		boolean result = bookingService.cancel(id);
		return result ? "Cancelled" : "Booking " + id + " not found";
	}

	@RequestMapping(value = "/bookings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Room> getAvailableRooms(@RequestParam("from") String sDateFrom, @RequestParam("to") String sDateTo,
			@RequestParam(name = "minPrice", required = false) Integer minPrice,
			@RequestParam(name = "maxPrice", required = false) Integer maxPrice, @RequestParam(name = "type", required = false) String type,
			@RequestParam(name = "capacity", required = false) String capacities)
	{
		// TODO: array params

		try
		{
			final SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			Date dateFrom = f.parse(sDateFrom);
			Date dateTo = f.parse(sDateTo);

			String[] types = (type != null) ? type.split(",") : new String[] {};
			Set<String> typeIds = buildTypeIds(types);

			Set<Integer> capacity = buildIntSet(capacities != null ? capacities.split(",") : new String[] {});

			return bookingService.findAvailableRooms(dateFrom, dateTo, minPrice, maxPrice, typeIds, capacity);
		}
		catch (ParseException e)
		{
			throw new RuntimeException(e);
		}
	}

	private static Set<Integer> buildIntSet(String[] array)
	{
		Set<Integer> values = new HashSet<>();

		for (String item : array)
		{
			values.add(Integer.parseInt(item));
		}

		return values;
	}

	private static Set<String> buildTypeIds(String[] types)
	{
		Set<String> ids = new HashSet<>();

		for (String type : types)
		{
			switch (type)
			{
				case "e":
				{
					ids.add("f6c478fa-151a-41fa-a253-552490d4c104"); // economy
					break;
				}

				case "s":
				{
					ids.add("447dca2b-f994-4b6d-8b50-ef1d83845067"); // standard
					break;
				}

				case "l":
				{
					ids.add("dffc6eb3-f794-42e3-9e94-74ced32a17e4"); // luxury
					break;
				}
			}
		}

		return ids;
	}

	@RequestMapping(value = "/bookings/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getBooking(@PathVariable(value = "id") String id)
	{
		final Booking booking = bookingService.find(id);
		if (booking != null)
		{
			return ResponseEntity.ok(booking);
		}
		else
		{
			return new ResponseEntity<>("Booking " + id + " not found", HttpStatus.NOT_FOUND);
		}
	}
}
