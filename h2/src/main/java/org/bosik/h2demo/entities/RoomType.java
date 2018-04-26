package org.bosik.h2demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Nikita Bosik
 * @since 2018-04-25
 */
@Entity
@Table(name = "room_type")
public class RoomType
{
	@Id
	@Column(name = "id", length = 36)
	private String id;

	@Column(name = "description")
	private String description;

	public RoomType()
	{
	}

	public RoomType(String id, String description)
	{
		this.id = id;
		this.description = description;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
}
