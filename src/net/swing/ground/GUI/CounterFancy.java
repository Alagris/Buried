package net.swing.ground.GUI;

import net.swing.ground.BitmapFont;
import net.swing.ground.Rectanglef;

public class CounterFancy extends Counter
{
	private static final Object[] emptyList = {"empty"};
	
	private Object[]		objects;
	private TextField	text;
	private boolean			isEmpty, allowRepeat = false;
	
	public CounterFancy(Rectanglef area, Object[] list, float defValueIndex)
	{
		super(area, 0, list.length - 1, 1, defValueIndex);
		objects = list;
		text = new TextField(font, this,false);
		correctLack();
	}
	
	public CounterFancy(float x, float y, float width, float height, Object[] list, float defValueIndex)
	{
		super(x, y, width, height, 0, list.length - 1, 1, defValueIndex);
		objects = list;
		text = new TextField(font, this,false);
		correctLack();
	}
	
	public CounterFancy(Rectanglef area, Object[] list, float defValueIndex, BitmapFont f)
	{
		super(area, 0, list.length - 1, 1, defValueIndex, f);
		objects = list;
		text = new TextField(font, this,false);
		correctLack();
	}
	
	public CounterFancy(float x, float y, float width, float height, Object[] list, float defValueIndex, BitmapFont f)
	{
		super(x, y, width, height, 0, list.length - 1, 1, defValueIndex, f);
		objects = list;
		text = new TextField(font, this,false);
		correctLack();
	}
	
	private void correctLack()
	{
		if (objects.length == 0)
		{
			objects = emptyList;
			min = 0;
			max = 0;
			text.setText(objects[0].toString());
			isEmpty = true;
		}
		else
		{
			isEmpty = false;
			updateText();
		}
	}
	
	private void updateText()
	{
		text.setText(objects[(int) v].toString());
	}
	
	@Override
	public void goDown()
	{
		if (allowRepeat)
		{
			if (v == min)
			{
				v = max;
				updateText();
				return;
			}
		}
		super.goDown();
		updateText();
	}
	
	@Override
	public void goUp()
	{
		if (allowRepeat)
		{
			if (v == max)
			{
				v = min;
				updateText();
				return;
			}
		}
		super.goUp();
		updateText();
	}
	
	public void setFontColor(float r, float g, float b)
	{
		text.setFontColor(r, g, b);
	}
	
	@Override
	public void setColor(float r, float g, float b)
	{
		text.setColor(r, g, b);
	}
	
	@Override
	public void update()
	{
		text.update();
	}
	
	/** returns objects from list at selected index */
	public Object listVaule()
	{
		return objects[(int) v];
	}
	
	public void setList(Object[] savesNames)
	{
		objects = savesNames;
		v = 0;
		correctLack();
		max = objects.length - 1;
		
	}
	
	public boolean isEmpty()
	{
		return isEmpty;
	}
	
	public void allowRepeat(boolean b)
	{
		allowRepeat = b;
	}
	
	public boolean isRepeatAllowed()
	{
		return allowRepeat;
	}
}
