package net.swing.ground;

public class Rectanglef extends Pointf
{
	public float	width;
	public float	height;
	
	public Rectanglef()
	{
	}
	
	public Rectanglef(Rectanglef otherRectanglef)
	{
		this(otherRectanglef.getX(), otherRectanglef.getY(), otherRectanglef.width, otherRectanglef.height);
	}
	
	public Rectanglef(float x, float y, float width, float height)
	{
		// calling those methods in constructor is wrong.
		// Remember that they are overridable
		// this.setX(x);
		// this.setY(y);
		// this.setWidth(width);
		// this.setHeight(height);
		super(x, y);
		this.width = width;
		this.height = height;
		
	}
	
	public Rectanglef(Pointf location, float width, float height)
	{
		super(location);
		this.width = width;
		this.height = height;
	}
	
	public boolean isPointInside(float x, float y)
	{
		if (x > getX() && y > getY() && x < getAbsoluteWidth() && y < getAbsoluteHeight()) return true;
		return false;
	}
	
	public float getWidth()
	{
		return width;
	}
	
	public void setWidth(float width)
	{
		this.width = width;
	}
	
	public float getHeight()
	{
		return height;
	}
	
	public void setHeight(float height)
	{
		this.height = height;
	}
	
	public void setSize(float width, float height)
	{
		setWidth(width);
		setHeight(height);
	}
	
	public float getAbsoluteWidth()
	{
		return width + getX();
	}
	
	public float getAbsoluteHeight()
	{
		return height + getY();
	}
	
	@Override
	public String toString()
	{
		return "Rectangle{ x=" + getX() + " ,y=" + getY() + " ,width=" + width + " ,height=" + height + "}";
	}
	
	public void set(Rectanglef area)
	{
		this.setLocation(area.getX(), area.getX());
		this.setWidth(area.width);
		this.setHeight(area.height);
	}
	
	public void set(float x, float y, float width, float height)
	{
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
	}
	
	public boolean isPointInside(Pointf p)
	{
		return isPointInside(p.getX(), p.getY());
	}
	
	public float getCenterX()
	{
		return getX() + getWidth() / 2;
	}
	
	public float getCenterY()
	{
		return getY() + getHeight() / 2;
	}
	
	public Pointf getCenterLocation()
	{
		return new Pointf(getCenterX(), getCenterY());
	}
	
	public void addSize(float extraWidth, float extraHeight)
	{
		this.width += extraWidth;
		this.height += extraHeight;
	}
}
