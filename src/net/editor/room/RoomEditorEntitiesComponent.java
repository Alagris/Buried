package net.editor.room;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import net.swing.engine.WPS;

public class RoomEditorEntitiesComponent extends JPanel implements ComponentListener, MouseListener
{
	/**
	 * 
	 */
	private static final long		serialVersionUID	= 1L;
	
	private ArrayList<JEntityLabel>	ents;
	
	public RoomEditorEntitiesComponent()
	{
		setLayout(null);
		ents = new ArrayList<JEntityLabel>();
	}
	
	public void setEntities(PrimitiveEntity[] entiesList)
	{
		ents.clear();
		removeAll();
		for (PrimitiveEntity primitiveEntity : entiesList)
		{
			ents.add(new JEntityLabel(primitiveEntity));
			add(ents.get(ents.size() - 1));
		}
		
	}
	
	/** Adds new entity label and returns its index */
	public int addEntity(WPS location, Entities ent)
	{
		JEntityLabel l = new JEntityLabel(location, ent);
		l.addMouseListener(this);
		ents.add(l);
		return ents.size() - 1;
	}
	
	/** Removes entity and at index i */
	public void removeEnt(int i)
	{
		remove(ents.get(i));
		ents.remove(i);
	}
	
	@Override
	public void componentResized(ComponentEvent e)
	{
		Dimension r = getSize();
		for (JEntityLabel entityLabel : ents)
		{
			entityLabel.setLocation((int) (r.width * entityLabel.getWPSlocation().a), (int) (r.height * entityLabel.getWPSlocation().b));
		}
	}
	
	@Override
	public void componentMoved(ComponentEvent e)
	{
	}
	
	@Override
	public void componentShown(ComponentEvent e)
	{
	}
	
	@Override
	public void componentHidden(ComponentEvent e)
	{
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		e.getComponent().setLocation(e.getPoint());
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
	}
	
	@Override
	public void mouseEntered(MouseEvent e)
	{
	}
	
	@Override
	public void mouseExited(MouseEvent e)
	{
	}
}
