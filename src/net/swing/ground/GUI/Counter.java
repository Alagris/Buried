package net.swing.ground.GUI;

import net.swing.ground.BitmapFont;
import net.swing.ground.Graphics;
import net.swing.ground.Rectanglef;
import net.swing.ground.Window;

public class Counter extends Graphics implements GUIcounter
{
	
	protected float			min, max, hop, v;
	protected BitmapFont	font;
	private boolean			intOnly	= false;
	
	public Counter(Rectanglef area, float min, float max, float hop, float defValue)
	{
		this.min = min;
		this.max = max;
		this.v = defValue;
		this.hop = hop;
		this.set(area);
		font = Window.textDisplayer;
	}
	
	public Counter(float x, float y, float width, float height, float min, float max, float hop, float defValue)
	{
		this.min = min;
		this.max = max;
		this.v = defValue;
		this.hop = hop;
		this.set(x, y, width, height);
		font = Window.textDisplayer;
	}
	
	public Counter(Rectanglef area, float min, float max, float hop, float defValue, BitmapFont f)
	{
		this.min = min;
		this.max = max;
		this.v = defValue;
		this.hop = hop;
		this.set(area);
		font = new BitmapFont(f);
	}
	
	public Counter(float x, float y, float width, float height, float min, float max, float hop, float defValue, BitmapFont f)
	{
		this.min = min;
		this.max = max;
		this.v = defValue;
		this.hop = hop;
		this.set(x, y, width, height);
		font = new BitmapFont(f);
	}
	
	public void showAsInteger(boolean arg0)
	{
		intOnly = arg0;
	}
	
	@Override
	public float minValue()
	{
		return min;
	}
	
	@Override
	public float maxValue()
	{
		return max;
	}
	
	@Override
	public float vaule()
	{
		return v;
	}
	
	@Override
	public float hop()
	{
		return hop;
	}
	
	public void goUp()
	{
		if (v + hop <= max) v += hop;
	}
	
	public void goDown()
	{
		if (v - hop >= min) v -= hop;
	}
	
	@Override
	public void update()
	{
		if (intOnly)
		{
			font.render("" + (int) v, getX(), getY());
		}
		else
		{
			font.render("" + v, getX(), getY());
		}
	}
	
	@Override
	public void setValue(float arg0)
	{
		v = arg0;
	}
	
}
