package net.math.src;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Set<E> extends ArrayList<E>
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	/** @throws NoSuchElementException */
	public int find(E e) throws NoSuchElementException
	{
		int i = 0;
		for (E e2 : this)
		{
			if (e.equals(e2))
			{
				return i;
			}
			i++;
		}
		throw new NoSuchElementException();
	}
}
