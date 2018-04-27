package com.example.demonext.services;

public class AlreadyBookedException extends Exception
{
	public AlreadyBookedException(Long roomId)
	{
		super("Room " + roomId + " already booked");
	}
}
