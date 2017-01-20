package net.swing.ground;

public class Pointf
{
	private float	x, y;
	
	public Pointf()
	{
	}
	
	public Pointf(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Pointf(Pointf anotherPoint)
	{
		this(anotherPoint.x, anotherPoint.y);
	}
	
	public float getY()
	{
		return y;
	}
	
	public void setY(float y)
	{
		this.y = y;
	}
	
	public float getX()
	{
		return x;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public void setLocation(float x, float y)
	{
		this.y = y;
		this.x = x;
	}
	
	public void setLocation(Pointf p)
	{
		setLocation(p.x, p.y);
	}
	
	@Override
	public String toString()
	{
		return "Pointf(X:" + x + ",Y:" + y + ")";
	}
	
	public void addY(float i)
	{
		y += i;
	}
	
	public void addX(float i)
	{
		x += i;
	}
	
	public Pointf shiftY(float s)
	{
		return new Pointf(x, y + s);
	}
	
	public Pointf shiftX(float s)
	{
		return new Pointf(x + s, y);
	}
	
	/** Point p is the center of circle. */
	public double getAngleTo(Pointf p)
	{
		// TODO:calculate angle
		return 0;
	}
	
	/** This point is the center of circle. */
	public double getAngleFrom(Pointf p)
	{
		return 0;
	}
	
	public Pointf getDifference(Pointf location)
	{
		return new Pointf(location.x - x, location.y - y);
	}
}
