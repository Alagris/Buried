package net.swing.engine;

import org.lwjgl.util.vector.Vector2f;

public class FVector extends Vector2f
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	public FVector(Vector2f vector)
	{
		this.set(vector);
	}
	
	public FVector(float x, float y)
	{
		this.set(x, y);
	}
	
	public FVector()
	{
	}
	
	public static Vector2f parseCoordinates(String coords)
	{
		Vector2f p = new Vector2f();
		boolean nowX = true, wasDot = false;
		;
		String number = "";
		for (char c : coords.toCharArray())
		{
			if (Character.isDigit(c))
			{
				number = number + c;
			}
			else if (c == '.' && !wasDot)
			{
				number = number + c;
				wasDot = true;
			}
			else if (nowX)
			{
				nowX = false;
				p.setX(Float.parseFloat(number));
				number = "";
			}
			else
			{
				break;
			}
		}
		p.setY(Float.parseFloat(number));
		return p;
	}
	
	/** Moves a FVector point (body) to the place pointed by this vector */
	public void apply(FVector body)
	{
		body.addY(getX());
		body.addX(getY());
	}
	
	/**
	 * Moves a FVector point (body) to the place pointed by this vector (but
	 * only x axis)
	 */
	public void applyA(FVector body)
	{
		body.addX(getY());
	}
	
	/**
	 * Moves a FVector point (body) to the place pointed by this vector (but
	 * only y axis)
	 */
	public void applyB(FVector body)
	{
		body.addY(getX());
	}
	
	/** Moves a FVector point (body) to the place pointed by this vector */
	public void apply(FVector body, float delta)
	{
		body.addY(getX() * delta);
		body.addX(getY() * delta);
	}
	
	/**
	 * Moves a FVector point (body) to the place pointed by this vector (but
	 * only x axis)
	 */
	public void applyA(FVector body, float delta)
	{
		body.addX(getY() * delta);
	}
	
	/**
	 * Moves a FVector point (body) to the place pointed by this vector (but
	 * only y axis)
	 */
	public void applyB(FVector body, float delta)
	{
		body.addY(getX() * delta);
	}
	
	/**
	 * Returns a new FVector point moved with this vector (but not applies
	 * vector to already existing point)
	 */
	public FVector abstractApply(FVector body)
	{
		FVector w = new FVector(body);
		w.addY(getX());
		w.addX(getY());
		return w;
	}
	
	public void addVector(float x, float y)
	{
		addX(x);
		addY(y);
	}
	
	public void addVector(Vector2f anotherVector)
	{
		addX(anotherVector.getX());
		addY(anotherVector.getY());
	}
	
	public void addVectors(Vector2f[] anotherVectors)
	{
		for (Vector2f anotherVector : anotherVectors)
		{
			addVector(anotherVector);
		}
	}
	
	public void addVector(FVector anotherVector)
	{
		addX(anotherVector.getX());
		addY(anotherVector.getY());
	}
	
	public void addVectors(FVector[] anotherVectors)
	{
		for (FVector anotherVector : anotherVectors)
		{
			addVector(anotherVector);
		}
	}
	
	@Override
	public String toString()
	{
		return "Vector( x:" + x + " y:" + y + " )";
	}
	
	public void subtractVector(float x, float y)
	{
		substractX(x);
		substractY(y);
	}
	
	public void subtractVector(Vector2f anotherVector)
	{
		substractX(anotherVector.getX());
		substractY(anotherVector.getY());
	}
	
	public void subtractVectors(Vector2f[] anotherVectors)
	{
		for (Vector2f anotherVector : anotherVectors)
		{
			subtractVector(anotherVector);
		}
	}
	
	public void subtractVector(FVector anotherVector)
	{
		substractX(anotherVector.getX());
		substractY(anotherVector.getY());
	}
	
	public void subtractVectors(FVector[] anotherVectors)
	{
		for (FVector anotherVector : anotherVectors)
		{
			subtractVector(anotherVector);
		}
	}
	
	public void reset()
	{
		x = 0;
		y = 0;
	}
	
	public FVector abstractApplyAxisX(FVector body, float delta)
	{
		FVector w = new FVector(body);
		w.addX(getY() * delta);
		return w;
	}
	
	public FVector abstractApplyAxisY(FVector body, float delta)
	{
		FVector w = new FVector(body);
		w.addY(getX() * delta);
		return w;
	}
	
	public FVector abstractApplyAxisX(FVector body)
	{
		FVector w = new FVector(body);
		w.addX(getY());
		return w;
	}
	
	public FVector abstractApplyAxisY(FVector body)
	{
		FVector w = new FVector(body);
		w.addY(getX());
		return w;
	}
	
	public void resetA()
	{
		x = 0;
	}
	
	public void resetB()
	{
		y = 0;
	}
	
	public void subtractVectorAxisX(Vector2f vector)
	{
		this.substractX(vector.getX());
	}
	
	public void subtractVectorAxisY(Vector2f vector)
	{
		this.substractY(vector.getY());
	}
	
	public void addVectorAxisA(Vector2f vector)
	{
		this.addX(vector.getX());
	}
	
	public void addVectorAxisB(Vector2f vector)
	{
		this.addY(vector.getY());
	}
	
	public void subtractVectorAxisX(float x)
	{
		this.substractX(x);
	}
	
	public void subtractVectorAxisY(float y)
	{
		this.substractY(y);
	}
	
	public void addVectorAxisA(float x)
	{
		this.addX(x);
	}
	
	public void addVectorAxisB(float y)
	{
		this.addY(y);
	}
	
	public void setA(float x)
	{
		this.x = x;
	}
	
	public void setB(float y)
	{
		this.y = y;
	}
	
	public void addX(float x)
	{
		this.x += x;
	}
	
	public void addY(float y)
	{
		this.y += y;
	}
	
	public void substractX(float x)
	{
		this.x -= x;
	}
	
	public void substractY(float y)
	{
		this.y -= y;
	}
	
	@Override
	public void set(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void set(Vector2f vector)
	{
		this.x = vector.x;
		this.y = vector.y;
	}
	
}
