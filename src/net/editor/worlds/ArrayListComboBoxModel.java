package net.editor.worlds;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.MutableComboBoxModel;

public class ArrayListComboBoxModel<E> extends AbstractListModel<E> implements ComboBoxModel<E>, MutableComboBoxModel<E>
{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	private ArrayList<E>		list				= new ArrayList<E>();
	private Object				selectedObject;
	
	public ArrayList<E> getList()
	{
		return list;
	}
	
	public void setList(ArrayList<E> list)
	{
		this.list = list;
	}
	
	/**
	 * Set the value of the selected item. The selected item may be null.
	 * <p>
	 * 
	 * @param anObject
	 *            The combo box value or null for no selection.
	 */
	@Override
	public void setSelectedItem(Object anObject)
	{
		if ((selectedObject != null && !selectedObject.equals(anObject)) || selectedObject == null && anObject != null)
		{
			selectedObject = anObject;
			fireContentsChanged(this, -1, -1);
		}
	}
	
	@Override
	public Object getSelectedItem()
	{
		return selectedObject;
	}
	
	@Override
	public int getSize()
	{
		return list.size();
	}
	
	@Override
	public E getElementAt(int index)
	{
		if (index >= 0 && index < getSize())
			return list.get(index);
		else
			return null;
	}
	
	/**
	 * Returns the index-position of the specified object in the list.
	 * 
	 * @param anObject
	 * @return an int representing the index position, where 0 is the first
	 *         position
	 */
	public int getIndexOf(Object anObject)
	{
		return list.indexOf(anObject);
	}
	
	// implements javax.swing.MutableComboBoxModel
	@Override
	public void addElement(E anObject)
	{
		list.add(anObject);
		fireIntervalAdded(this, list.size() - 1, list.size() - 1);
		if (list.size() == 1 && selectedObject == null && anObject != null)
		{
			setSelectedItem(anObject);
		}
	}
	
	// implements javax.swing.MutableComboBoxModel
	@Override
	public void insertElementAt(E anObject, int index)
	{
		list.add(index, anObject);
		fireIntervalAdded(this, index, index);
	}
	
	// implements javax.swing.MutableComboBoxModel
	@Override
	public void removeElementAt(int index)
	{
		if (getElementAt(index) == selectedObject)
		{
			if (index == 0)
			{
				setSelectedItem(getSize() == 1 ? null : getElementAt(index + 1));
			}
			else
			{
				setSelectedItem(getElementAt(index - 1));
			}
		}
		
		list.remove(index);
		
		fireIntervalRemoved(this, index, index);
	}
	
	// implements javax.swing.MutableComboBoxModel
	@Override
	public void removeElement(Object anObject)
	{
		int index = list.indexOf(anObject);
		if (index != -1)
		{
			removeElementAt(index);
		}
	}
	
	/**
	 * Empties the list.
	 */
	public void removeAllElements()
	{
		if (list.size() > 0)
		{
			int firstIndex = 0;
			int lastIndex = list.size() - 1;
			list.clear();
			selectedObject = null;
			fireIntervalRemoved(this, firstIndex, lastIndex);
		}
		else
		{
			selectedObject = null;
		}
	}
	
}
