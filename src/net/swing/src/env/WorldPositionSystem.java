package net.swing.src.env;

import net.swing.engine.WPS;
import net.swing.ground.Dimensionf;
import net.swing.ground.Pointf;

public class WorldPositionSystem extends Dimensionf
{
	
	public WorldPositionSystem(float width, float height)
	{
		super(width, height);
	}
	
	public double getA(float x)
	{
		return x / getWidth();
	}
	
	public double getB(float y)
	{
		return y / getHeight();
	}
	
	public float getX(double a)
	{
		return (float) (a * getWidth());
	}
	
	public float getY(double b)
	{
		return (float) (b * getHeight());
	}
	
	public WPS getWPS(Pointf location)
	{
		return new WPS(getA(location.getX()), getB(location.getY()));
	}
	
	public WPS getWPS(float x, float y)
	{
		return new WPS(getA(x), getB(y));
	}
	
	public Pointf getLocation(WPS wps)
	{
		return new Pointf(getX(wps.a), getY(wps.b));
	}
	
	public Pointf getLocation(double a, double b)
	{
		return new Pointf(getX(a), getY(b));
	}
	
}
