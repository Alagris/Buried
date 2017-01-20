package net.swing.engine;

public class PAcceleration
{
	
	private WPS	acceleration	= new WPS();
	
	public PAcceleration(WPS acceleration)
	{
		setAcceleration(acceleration);
	}
	
	public PAcceleration()
	{
	}
	
	public PAcceleration(double a, double b)
	{
		setAcceleration(a, b);
	}
	
	public PAcceleration(PAcceleration acc)
	{
		acceleration = acc.acceleration;
	}
	
	public void accelerate(PVector vector)
	{
		vector.addVector(acceleration);
	}
	
	public void accelerateOnlyAaxis(PVector vector)
	{
		vector.vector.addA(acceleration.a);
	}
	
	public void accelerateOnlyBaxis(PVector vector)
	{
		vector.vector.addB(acceleration.b);
	}
	
	public WPS getAcceleration()
	{
		return acceleration;
	}
	
	public void setAcceleration(double a, double b)
	{
		this.acceleration.setA(a);
		this.acceleration.setB(b);
	}
	
	public void setAcceleration(WPS acceleration)
	{
		this.acceleration.set(acceleration);
	}
	
	public void setAcceleration(PAcceleration acceleration)
	{
		setAcceleration(acceleration.acceleration);
	}
	
	public WPS toWPS()
	{
		return acceleration;
	}
	
}
