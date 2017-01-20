package net.math.src;

public abstract class SetRough<E>
{
	
	private Set<E>	U	= new Set<E>();
	
	// private Set<?> Q,V;
	
	public Object doFunction(E x, int qIndex)
	{
		U.find(x);
		return 1;
	}
	
}
