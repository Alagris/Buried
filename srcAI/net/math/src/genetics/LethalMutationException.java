package net.math.src.genetics;

public class LethalMutationException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public LethalMutationException()
	{
		super();
	}

	public LethalMutationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public LethalMutationException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public LethalMutationException(String message)
	{
		super(message);
	}

	public LethalMutationException(Throwable cause)
	{
		super(cause);
	}
	
}
