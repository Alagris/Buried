package net.swing.ground.GUI;

import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Color;

import net.swing.ground.Window;

public class ListVertical<typeOfValues> extends List<typeOfValues>
{
	
	/** Height of element (its about font size) */
	protected float		elemSize;
	protected int		selectedIndex	= -1;
	/**
	 * start + elementsQuantity = end
	 * <p>
	 * (by default) end = elementsQuantity - 1
	 * <p>
	 * (by default) start = 0;
	 * */
	protected int		start			= 0, end;
	private int			maxLettersQuantity;
	private final int	elementsQuantity;
	
	public int getMaxLettersQuantity()
	{
		return maxLettersQuantity;
	}
	
	/**
	 * @param elementsQuantity
	 *            -quantity elements displayed at the same time
	 */
	public ListVertical(float x, float y, float width, float height, int elementsQuantity)
	{
		this.elementsQuantity = elementsQuantity;
		elemSize = height / elementsQuantity;
		end = elementsQuantity - 1;
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		setColor(1, 0, 0);
	}
	
	/**
	 * @param elementsQuantity
	 *            -quantity elements displayed at the same time
	 */
	public ListVertical(float x, float y, float width, float height, int elementsQuantity, typeOfValues defaultElement)
	{
		this.elementsQuantity = elementsQuantity;
		elements.add(defaultElement);
		end = elementsQuantity - 1;
		elemSize = height / elementsQuantity;
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		setColor(1, 0, 0);
	}
	
	/**
	 * @param elementsQuantity
	 *            -quantity elements displayed at the same time
	 */
	public ListVertical(float x, float y, float width, float height, int elementsQuantity, typeOfValues[] defaultElements)
	{
		super(defaultElements);
		this.elementsQuantity = elementsQuantity;
		elemSize = height / elementsQuantity;
		end = elementsQuantity - 1;
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		setColor(1, 0, 0);
	}
	
	@Override
	public void render()
	{
		for (int i = start; i <= end && i < elements.size(); i++)
		{
			if (i == selectedIndex)
			{
				renderElementSelected(i, getY() + (i - start) * elemSize + elemSize / 2);
			}
			else
			{
				renderElement(i, getY() + (i - start) * elemSize + elemSize / 2);
			}
		}
	}
	
	@Override
	public void onClick(float x, float y)
	{
		selectedIndex = (int) Math.ceil((y - getY()) / elemSize) + start - 1;
		if (selectedIndex > elements.size() - 1)
		{
			selectedIndex = elements.size() - 1;
		}
		render();
	}
	
	@Override
	public void renderMouseEnter(float x, float y)
	{
		render();
	}
	
	public void renderElementSelected(int index, float y)
	{
		Window.textDisplayer.renderMiddleSpaced(cutStringIfTooLong(elements.get(index).toString()), getX() + getWidth() / 2, y, elemSize, elemSize, RGB);
	}
	
	public void renderElement(int index, float y)
	{
		Window.textDisplayer.renderMiddleSpaced(cutStringIfTooLong(elements.get(index).toString()), getX() + getWidth() / 2, y, elemSize, elemSize);
	}
	
	private String cutStringIfTooLong(String s)
	{
		return (s.length() < maxLettersQuantity) ? s : s.substring(0, maxLettersQuantity - 2) + "..";
	}
	
	@Override
	public void onScrollDown()
	{
		goDown();
	}
	
	@Override
	public void onScrollUp()
	{
		goUp();
	}
	
	/** Moves one index up */
	public void goUp()
	{
		if (selectedIndex < end && selectedIndex < getSize() - 1)
		{
			selectedIndex++;
		}
		else
		{
			moveAndGoUp();
		}
	}
	
	/** Moves one index down */
	public void goDown()
	{
		if (selectedIndex > start)
		{
			selectedIndex--;
		}
		else
		{
			moveAndGoDown();
		}
	}
	
	/** Moves one level up */
	public void moveUp()
	{
		
		if (end < elements.size() - 1)
		{
			end++;
			start++;
		}
	}
	
	/** Moves one level down */
	public void moveDown()
	{
		
		if (start > 0)
		{
			end--;
			start--;
		}
	}
	
	/** Moves one level and index up */
	public void moveAndGoUp()
	{
		if (end < getSize() - 1)
		{
			if (selectedIndex <= end)
			{
				selectedIndex++;
			}
			end++;
			start++;
		}
	}
	
	/** Moves one level and index down */
	public void moveAndGoDown()
	{
		
		if (start > 0)
		{
			if (selectedIndex >= start)
			{
				selectedIndex--;
			}
			end--;
			start--;
		}
	}
	
	/**
	 * Sets level of displayed content which specifies what indexes are
	 * currently visible.
	 */
	public void setLevel(int level)
	{
		if (level > 0)
		{
			if (level <= getQuantityOflevels())
			{
				start = level - 1;
				end = elementsQuantity + level - 2;
			}
		}
	}
	
	/**
	 * If all elements can be displayed at once then quantity of levels is 1 .If
	 * there is one element than cannot be displayed then quantity is 2
	 */
	public int getQuantityOflevels()
	{
		return zeroIsMin(getSize() - getElementsQuantity()) + 1;
	}
	
	private int zeroIsMin(int x)
	{
		return x < 0 ? 0 : x;
	}
	
	public boolean isSelected()
	{
		return selectedIndex > -1;
	}
	
	@Override
	public typeOfValues getSelectedValue()
	{
		if (!isSelected())
		{
			return null;
		}
		return elements.get(selectedIndex);
	}
	
	public void setSelected(int i)
	{
		selectedIndex = i;
	}
	
	/** Clearing selection before setting new data in list is <b>highly recommended</b> */
	public void setList(typeOfValues[] e)
	{
		elements = new ArrayList<typeOfValues>(Arrays.asList(e));
	}
	
	@Override
	public void clearSelection()
	{
		selectedIndex = -1;
	}
	
	public void setSelectionColor(float r, float g, float b)
	{
		super.setColor(r, g, b);
	}
	
	public Color getSelectionColor()
	{
		return super.getColor();
	}
	
	@Override
	public void setWidth(float width)
	{
		super.setWidth(width);
		resetMaxLettersQuantity();
	}
	
	/**
	 * Calculates how many letters can be maximally displayed so that the text
	 * doesn't go out of list width. Notice that if the last letter is partially
	 * in list and partially outside the method will count it too
	 */
	private void resetMaxLettersQuantity()
	{
		maxLettersQuantity = (int) Math.ceil((getWidth() - elemSize / 2) / elemSize * 3);
	}
	
	/** Quantity of elements that are displayed at once */
	public int getElementsQuantity()
	{
		return elementsQuantity;
	}
	
}
