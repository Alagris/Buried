package net.swing.ground.GUI;

import java.util.ArrayList;

public class ListVerticalFancy<typeOfValues> extends ListVertical<typeOfValues>
{
	
	private boolean		multipleSelectionEnabled;
	private boolean[]	selectedElements;
	
	public ListVerticalFancy(float x, float y, float width, float height, int elementsQunatity, typeOfValues defaultElement, boolean multiSelection)
	{
		super(x, y, width, height, elementsQunatity, defaultElement);
		setMultipleSelectionEnabled(multiSelection);
		selectedElements = new boolean[elements.size()];
	}
	
	public ListVerticalFancy(float x, float y, float width, float height, int elementsQunatity, typeOfValues[] defaultElements, boolean multiSelection)
	{
		super(x, y, width, height, elementsQunatity, defaultElements);
		setMultipleSelectionEnabled(multiSelection);
		selectedElements = new boolean[elements.size()];
	}
	
	public ListVerticalFancy(float x, float y, float width, float height, int elementsQunatity, boolean multiSelection)
	{
		super(x, y, width, height, elementsQunatity);
		setMultipleSelectionEnabled(multiSelection);
		selectedElements = new boolean[elements.size()];
	}
	
	public boolean isMultipleSelectionEnabled()
	{
		return multipleSelectionEnabled;
	}
	
	public void setMultipleSelectionEnabled(boolean multipleSelectionEnabled)
	{
		this.multipleSelectionEnabled = multipleSelectionEnabled;
	}
	
	@Override
	public void render()
	{
		if (isMultipleSelectionEnabled())
		{
			for (int i = start; i <= end && i < elements.size(); i++)
			{
				if (selectedElements[i])
				{
					renderElementSelected(i, getY() + (i - start) * elemSize + elemSize / 2);
				}
				else
				{
					renderElement(i, getY() + (i - start) * elemSize + elemSize / 2);
				}
			}
		}
		else
		{
			super.render();
		}
	}
	
	@Override
	public void onClick(float x, float y)
	{
		selectedIndex = (int) Math.ceil((y - getY()) / elemSize) + start - 1;
		if (selectedIndex > elements.size() - 1)
		{
			// this occurs when list has not enough elements
			selectedIndex = elements.size() - 1;
		}
		if (isMultipleSelectionEnabled())
		{
			selectedElements[selectedIndex] = !selectedElements[selectedIndex];
		}
		render();
	}
	
	public <T> T[] getSeletedElements(T[] list)
	{
		ArrayList<typeOfValues> elements1 = new ArrayList<typeOfValues>();
		for (int i = 0; i < selectedElements.length; i++)
		{
			if (selectedElements[i])
			{
				elements1.add(elements.get(i));
			}
		}
		return elements1.toArray(list);
	}
	
	public boolean[] getSeletedIndexes()
	{
		return selectedElements;
	}
	
	public boolean isSelected(int index)
	{
		return selectedElements[index];
	}
	
	@Override
	public void onScrollDown()
	{
		this.goDown();
	}
	
	@Override
	public void onScrollUp()
	{
		this.goUp();
	}
	
	@Override
	public void goDown()
	{
		if (start > 0)
		{
			end--;
			start--;
		}
	}
	
	@Override
	public void goUp()
	{
		if (end < elements.size() - 1)
		{
			end++;
			start++;
		}
	}
	
	@Override
	public boolean isSelected()
	{
		for (boolean b : selectedElements)
		{
			if (b) return true;
		}
		return false;
	}
	
	@Override
	public void clearSelection()
	{
		super.clearSelection();
		for (int i = 0; i < selectedElements.length; i++)
		{
			selectedElements[i] = false;
		}
	}
	
}
