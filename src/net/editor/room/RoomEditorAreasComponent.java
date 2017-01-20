package net.editor.room;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import net.editor.main.DerpyIsBestPony;
import net.swing.src.env.Matrix;
import net.swing.src.env.WorldSettings;

public class RoomEditorAreasComponent extends JPanel implements MouseInputListener, ComponentListener
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	// ==================variables=========
	
	private int					selectedCellX, selectedCellY;
	/**
	 * Prevents editor from inserting areas on mouse click. It's set to true by
	 * default because world is not loaded in constructor. This value will
	 * change into false when any world loads
	 */
	private boolean				hasErrors;
	private int					usedArea			= 0;
	private boolean				isMarkerEnabled		= false;
	
	private JAreaLabel[]		cells;
	
	// ==================Constructors=========
	
	public RoomEditorAreasComponent()
	{
		super.addComponentListener(this);
		
		GridLayout l = new GridLayout(WorldSettings.cRows, WorldSettings.cColumns);
		super.setLayout(l);
		cells = new JAreaLabel[WorldSettings.collAreaSize()];
		
		for (int i = 0, x = 1, y = WorldSettings.cRows; i < cells.length; i++)
		{
			cells[i] = new JAreaLabel();
			super.add(cells[i], l);
			
			/* Name of cells is it's: "X Y area_ID" */
			cells[i].setName(x + "," + y);
			cells[i].addMouseListener(this);
			cells[i].addMouseMotionListener(this);
			
			if (x < WorldSettings.cColumns)
			{
				x++;
			}
			else
			{
				y--;
				x = 1;
			}
		}
		clearCellsData();
		setMarkerColor(Color.MAGENTA);
	}
	
	// ==================Graphics=========
	
	public void repaint(boolean putBlock)
	{
		Point point = getMousePosition();
		if (point != null)
		{
			Component p = getComponentAt(point);
			if (p instanceof JAreaLabel)
			{
				String[] s = p.getName().split(",");
				JAreaLabel e = (JAreaLabel) p;
				setSelectedCellX(parseInt(s[0]));
				setSelectedCellY(parseInt(s[1]));
				if (putBlock)
				{
					if (!hasErrors)
					{
						setBlockAt(e);
					}
				}
				
				super.repaint();
				return;
			}
			else
			{
				setSelectedCell(0, 0);
			}
		}
		else
		{
			setSelectedCell(0, 0);
		}
		
		super.repaint();
	}
	
	private int parseInt(String string)
	{
		int n = 0;
		char[] c = string.toCharArray();
		for (int i = c.length - 1, decimalShift = 1; i > -1; i--)
		{
			n += Character.getNumericValue(c[i]) * decimalShift;
			decimalShift *= 10;
		}
		return n;
	}
	
	private void updateAreaMarker()
	{
		if (isMarkerEnabled())
		{
			for (JAreaLabel areaLabel : cells)
			{
				if (parseInt(areaLabel.getText()) == usedArea)
				{
					areaLabel.setOpaque(true);
				}
				else
				{
					areaLabel.setOpaque(false);
				}
			}
		}
		else
		{
			for (JAreaLabel areaLabel : cells)
			{
				areaLabel.setOpaque(false);
			}
		}
		for (JAreaLabel areaLabel : cells)
		{
			areaLabel.repaint();
		}
	}
	
	// ==================getters=========
	
	public int getUsedBlock()
	{
		return usedArea;
	}
	
	public int getSelectedCellX()
	{
		return selectedCellX;
	}
	
	public int getSelectedCellY()
	{
		return selectedCellY;
	}
	
	public boolean isMarkerEnabled()
	{
		return isMarkerEnabled;
	}
	
	// ==================setters=========
	
	public void setMarkerColor(Color markerColor)
	{
		for (JAreaLabel areaLabel : cells)
		{
			areaLabel.setBackground(markerColor);
		}
	}
	
	public void enableMarker(boolean markeArea)
	{
		this.isMarkerEnabled = markeArea;
		updateAreaMarker();
	}
	
	public void setUsedArea(int usedArea)
	{
		this.usedArea = usedArea;
		updateAreaMarker();
	}
	
	public void setSelectedCellX(int selectedCellX)
	{
		this.selectedCellX = selectedCellX;
	}
	
	public void setSelectedCell(int selectedCellX, int selectedCellY)
	{
		setSelectedCellX(selectedCellX);
		setSelectedCellY(selectedCellY);
	}
	
	public void setSelectedCellY(int selectedCellY)
	{
		this.selectedCellY = selectedCellY;
	}
	
	/** l - JLabel where Icon of usedBlock will be set */
	private void setAreaAt(JAreaLabel l, int idOfBlock)
	{
		l.setText("" + idOfBlock);
		if (isMarkerEnabled())
		{
			if (idOfBlock == usedArea)
			{
				l.setOpaque(true);
			}
			else
			{
				l.setOpaque(false);
			}
			
		}
		else
		{
			l.setOpaque(false);
		}
		
		l.repaint();
		
		setBlockInMatrix(getSelectedCellX(), getSelectedCellY());
	}
	
	/** l - JLabel where Icon of usedBlock will be set. Sets usedBlock as icon */
	private void setBlockAt(JAreaLabel l)
	{
		setAreaAt(l, usedArea);
	}
	
	/** x and y - coordinates of cell on matrix */
	private void setBlockInMatrix(int x, int y)
	{
		DerpyIsBestPony.general.roomEditor.map.mapComp.primitive.areaMatrix.putBlockHere(x, y, usedArea);
	}
	
	public void clearCellsData()
	{
		for (JAreaLabel JAreaLabel : cells)
		{
			/*
			 * Name of cells is it's: "X,Y" Text of cells is it's area_ID
			 */
			JAreaLabel.setText("0");
		}
		hasErrors = true;
	}
	
	public void fillCellsWithData(Matrix m)
	{
		if (m.getColumns() == WorldSettings.cColumns)
		{
			if (m.getRows() == WorldSettings.cRows)
			{
				String[] s;
				for (int i = 0; i < cells.length; i++)
				{
					/*
					 * Name of cells is it's: "X,Y" Text of cells is it's
					 * area_ID
					 */
					s = cells[i].getName().split(",");
					cells[i].setText("" + m.getID(parseInt(s[0]), parseInt(s[1])));
				}
				hasErrors = false;
				return;
			}
		}
		hasErrors = true;
		System.err.println("Matrix has invalid size " + m);
	}
	
	// ==================overrides=========
	
	@Override
	public Component add(Component comp)
	{
		return comp;
	}
	
	@Override
	public void remove(int index)
	{
	}
	
	@Override
	public synchronized void addMouseListener(MouseListener l)
	{
	}
	
	@Override
	public synchronized void addMouseMotionListener(MouseMotionListener l)
	{
	}
	
	@Override
	public synchronized void addComponentListener(ComponentListener l)
	{
	}
	
	@Override
	public void setLayout(LayoutManager mgr)
	{
	}
	
	@Override
	public void repaint()
	{
	}
	
	// ==================listeners=========
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		repaint(true);
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
	}
	
	@Override
	public void mouseEntered(MouseEvent e)
	{
		((JAreaLabel) e.getComponent()).setOutlineEnabled(true);
	}
	
	@Override
	public void mouseExited(MouseEvent e)
	{
		((JAreaLabel) e.getComponent()).setOutlineEnabled(false);
		repaint(false);
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		repaint(true);
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		repaint(false);
	}
	
	@Override
	public void componentResized(ComponentEvent e)
	{
		// Font f = new Font("",0,cells[0].getWidth()/2);
		// for (JAreaLabel cellLabel : cells)
		// {
		// cellLabel.setFont(f);
		// }
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
	
}
