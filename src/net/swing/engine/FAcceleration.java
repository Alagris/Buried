package net.swing.engine;

import org.lwjgl.util.vector.Vector2f;

public class FAcceleration extends Vector2f
{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	public FAcceleration(Vector2f v)
	{
		setAcceleration(v);
	}
	
	public FAcceleration()
	{
	}
	
	public FAcceleration(float x, float y)
	{
		setAcceleration(x, y);
	}
	
	public FAcceleration(FAcceleration acc)
	{
		this.set(acc);
	}
	
	public void accelerate(FVector vector)
	{
		vector.addVector(this);
	}
	
	public void accelerateOnlyAaxis(FVector vector)
	{
		vector.addX(this.x);
	}
	
	public void accelerateOnlyBaxis(FVector vector)
	{
		vector.addY(this.y);
	}
	
	public void setAcceleration(float x, float y)
	{
		this.setX(x);
		this.setY(y);
	}
	
	public void setAcceleration(Vector2f v)
	{
		setAcceleration(v.x, v.y);
	}
	
}
