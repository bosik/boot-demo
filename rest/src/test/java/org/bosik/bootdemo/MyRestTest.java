package org.bosik.bootdemo;

import org.junit.Test;

/**
 * This class contains basic REST tests and demonstrates two options for building REST clients. See descendants for details.
 *
 * @author Nikita Bosik
 * @since 2018-04-19
 */
public abstract class MyRestTest
{
	@Test
	public void test_normal() throws Exception
	{
		test("1,2,3", 3, 5);
	}

	@Test
	public void test_empty() throws Exception
	{
		test("", 0, 0);
	}

	@Test
	public void test_blank() throws Exception
	{
		test("   ", 0, 0);
	}

	@Test
	public void test_duplicates() throws Exception
	{
		test("5,4,3,1,2,5,2,3", 20, 24);
	}

	@Test
	public void test_spaces() throws Exception
	{
		test(" 1 , 2 , 3 ", 3, 5);
	}

	protected abstract void test(String input, int expectedMin, int expectedMax) throws Exception;
}
