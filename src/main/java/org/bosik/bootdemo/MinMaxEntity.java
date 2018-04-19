package org.bosik.bootdemo;

/**
 * @author Nikita Bosik
 * @since 2018-04-12
 */
public class MinMaxEntity
{
	private int min;
	private int max;

	public MinMaxEntity()
	{
	}

	public MinMaxEntity(int min, int max)
	{
		this.min = min;
		this.max = max;
	}

	public int getMin()
	{
		return min;
	}

	public void setMin(int min)
	{
		this.min = min;
	}

	public int getMax()
	{
		return max;
	}

	public void setMax(int max)
	{
		this.max = max;
	}
}
