package com.example.demonext.services;

public class RoomNotFoundException extends Exception
{
	public RoomNotFoundException(Long roomId)
	{
		super("Room " + roomId + " not found");
	}
}
