package org.bosik.h2demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Nikita Bosik
 * @since 2018-04-25
 */
@Entity
@Table(name = "room")
public class Room
{
	@Id
	@Column(name = "id", length = 36)
	private String id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "room_type_id")
	private RoomType type;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "room_capacity_id")
	private RoomCapacity capacity;

	@Column(name = "has_tv")
	private boolean hasTV;

	@Column(name = "has_fridge")
	private boolean hasFridge;

	@Column(name = "price")
	private int price;

	public Room()
	{
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public RoomType getType()
	{
		return type;
	}

	public void setType(RoomType type)
	{
		this.type = type;
	}

	public RoomCapacity getCapacity()
	{
		return capacity;
	}

	public void setCapacity(RoomCapacity capacity)
	{
		this.capacity = capacity;
	}

	public boolean isHasTV()
	{
		return hasTV;
	}

	public void setHasTV(boolean hasTV)
	{
		this.hasTV = hasTV;
	}

	public boolean isHasFridge()
	{
		return hasFridge;
	}

	public void setHasFridge(boolean hasFridge)
	{
		this.hasFridge = hasFridge;
	}

	public int getPrice()
	{
		return price;
	}

	public void setPrice(int price)
	{
		this.price = price;
	}
}
