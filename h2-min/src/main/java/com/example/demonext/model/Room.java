package com.example.demonext.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Room
{
	public static final class Type
	{
		public static final int ECONOMY  = 1;
		public static final int STANDARD = 2;
		public static final int LUXURY   = 3;
	}

	public static final class Capacity
	{
		public static final int SINGLE = 1;
		public static final int DOUBLE = 2;
		public static final int TRIPLE = 3;
	}

	@Id
	@GeneratedValue
	private Long    id;
	private Integer type;
	private Integer capacity;
	private Boolean hasTv;
	private Boolean hasFridge;
	private Integer price;
	private Boolean booked;

	public Room()
	{
	}

	public Room(Integer type, Integer capacity, Boolean hasTv, Boolean hasFridge, Integer price, Boolean booked)
	{
		this.type = type;
		this.capacity = capacity;
		this.hasTv = hasTv;
		this.hasFridge = hasFridge;
		this.price = price;
		this.booked = booked;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Integer getType()
	{
		return type;
	}

	public void setType(Integer type)
	{
		this.type = type;
	}

	public Integer getCapacity()
	{
		return capacity;
	}

	public void setCapacity(Integer capacity)
	{
		this.capacity = capacity;
	}

	public Boolean getHasTv()
	{
		return hasTv;
	}

	public void setHasTv(Boolean hasTv)
	{
		this.hasTv = hasTv;
	}

	public Boolean getHasFridge()
	{
		return hasFridge;
	}

	public void setHasFridge(Boolean hasFridge)
	{
		this.hasFridge = hasFridge;
	}

	public Integer getPrice()
	{
		return price;
	}

	public void setPrice(Integer price)
	{
		this.price = price;
	}

	public Boolean getBooked()
	{
		return booked;
	}

	public void setBooked(Boolean booked)
	{
		this.booked = booked;
	}
}
