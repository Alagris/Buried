package net.editor.room;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import net.editor.main.DerpyIsBestPony;
import net.swing.src.data.PrimitiveRoom;
import net.swing.src.data.SaveOrigin;
import net.swing.src.env.Matrix;
import net.swing.src.env.WorldSettings;

public class RoomEditorMapComponent extends JPanel implements MouseInputListener, ComponentListener
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	// ==================variables=========
	
	private SaveOrigin				saveOrigin;
	/**
	 * Size of map is NOT size of component. Map is a square so width = height =
	 * mapSize
	 */
	public PrimitiveRoom		primitive			= new PrimitiveRoom();
	// private Stroke selectionStroke;
	private int					selectedCellX, selectedCellY;
	private boolean				hasErrors;
	private int					usedBlock			= 0;
	
	private JCellLabel[]		cells;
	
	// ==================Constructors=========
	
	public RoomEditorMapComponent()
	{
		super.addComponentListener(this);
		/*
		 * It's set to true by default because world is not loaded in
		 * constructor. This value will change into false when any world loads
		 */
		hasErrors = true;
		
		GridLayout l = new GridLayout(WorldSettings.rows, WorldSettings.columns);
		super.setLayout(l);
		cells = new JCellLabel[WorldSettings.floorSize()];
		
		for (int i = 0, x = 1, y = WorldSettings.rows; i < cells.length; i++)
		{
			cells[i] = new JCellLabel();
			super.add(cells[i], l);
			
			/* Name of cells is it's: "X Y block_ID" */
			cells[i].setName(x + "," + y + ",0");
			cells[i].addMouseListener(this);
			cells[i].addMouseMotionListener(this);
			
			if (x < WorldSettings.columns)
			{
				x++;
			}
			else
			{
				y--;
				x = 1;
			}
			
		}
		
	}
	
	// ================Saving data==============
	
	public void saveFloorMatrix()
	{
		primitive.saveBlockMatrix();
	}
	
	public void saveAreaMatrix()
	{
		primitive.saveAreaMatrix();
	}
	
	public void saveEntites()
	{
		primitive.saveEntities();
	}
	
	public void saveAll()
	{
		saveFloorMatrix();
		saveAreaMatrix();
		saveEntites();
	}
	
	// ==================Graphics=========
	
	/**
	 * Loads all icons once more. If any block ID was changed it's icon will be
	 * refreshed after calling this method. It doesn't edit blocks matrix
	 */
	private void resetBlockIcons()
	{
		if (cells[0].getWidth() > 0)
		{
			if (cells[0].getHeight() > 0)
			{
				for (int i = 0; i < cells.length; i++)
				{
					setBlockAt(cells[i], parseInt(cells[i].getName().split(",")[2]));
				}
			}
		}
	}
	
	public void repaint(boolean putBlock)
	{
		Point point = getMousePosition();
		if (point != null)
		{
			Component p = getComponentAt(point);
			if (p instanceof JCellLabel)
			{
				String[] s = p.getName().split(",");
				JCellLabel e = (JCellLabel) p;
				setSelectedCellX(parseInt(s[0]));
				setSelectedCellY(parseInt(s[1]));
				if (putBlock)
				{
					if (!hasErrors)
					{
						setBlockAt(e);
						p.setName(getSelectedCellX() + "," + getSelectedCellY() + "," + usedBlock);
						setBlockInMatrix(getSelectedCellX(), getSelectedCellY());
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
	
	// ==================getters=========
	
	public int getUsedBlock()
	{
		return usedBlock;
	}
	
	public SaveOrigin getSave()
	{
		return saveOrigin;
	}
	
	public int getSelectedCellX()
	{
		return selectedCellX;
	}
	
	public int getSelectedCellY()
	{
		return selectedCellY;
	}
	
	// ==================setters=========
	
	public void setUsedBlock(int usedBlock)
	{
		this.usedBlock = usedBlock;
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
	
	public void setRoomBuilder(String roomName)
	{
		if (saveOrigin == null) return;
		/* setSave(save); is not necessary */
		loadRoomBuilder(roomName);
	}
	
	public void setRoomBuilder(SaveOrigin saveOrigin, String roomName)
	{
		setSave(saveOrigin);
		loadRoomBuilder(roomName);
	}
	
	/** l - JLabel where Icon of usedBlock will be set */
	private void setBlockAt(JLabel l, int idOfBlock)
	{
		if (idOfBlock == 0)
		{
			l.setIcon(null);
		}
		else
		{
			Image icon = primitive.blockImages[idOfBlock - 1].getImage();
			BufferedImage b = new BufferedImage(l.getWidth(), l.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
			Graphics g = b.getGraphics();
			g.drawImage(icon, 0, 0, l.getWidth(), l.getHeight(), null);
			ImageIcon iIcon = new ImageIcon(b);
			l.setIcon(iIcon);
		}
	}
	
	/** l - JLabel where Icon of usedBlock will be set. Sets usedBlock as icon */
	private void setBlockAt(JLabel l)
	{
		setBlockAt(l, usedBlock);
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
	
	/** x and y - coordinates of cell on matrix */
	private void setBlockInMatrix(int x, int y)
	{
		primitive.blockMatrix.putBlockHere(x, y, usedBlock);
	}
	
	/** Loads the same room in the same save once again */
	public void refresh()
	{
		loadRoomBuilder(PrimitiveRoom.currentRoom);
	}
	
	/**
	 * Pay attention that this room is always loaded from currently used save.
	 * To load from another save you need to call setSave() at first.
	 */
	private void loadRoomBuilder(String roomName)
	{
		/*
		 * if (save == null) return;
		 * 
		 * it SHOULD not be necessary since there are only 2 methods that call
		 * this method. Look at those methods and you will see that save will
		 * never be null here.
		 */
		if (roomName != null)
		{
			hasErrors = primitive.load(saveOrigin, roomName);
			if (hasErrors)
			{
				JOptionPane.showMessageDialog(this, "This world has no block sets!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				if (primitive.wereSetsChanged)
				{
					DerpyIsBestPony.general.roomEditor.editorPanel.blocks.resetTable(primitive.blockNames);
				}
			}
			
			if (primitive.blockMatrix == null)
			{
				clearCellsData();
			}
			else
			{
				fillCellsWithData(primitive.blockMatrix);
			}
			
			resetBlockIcons();
		}
		
	}
	
	private void clearCellsData()
	{
		for (int i = 0, x = 1, y = WorldSettings.rows; i < cells.length; i++)
		{
			/* Name of cells is it's: "X Y block_ID" */
			cells[i].setName(x + "," + y + ",0");
			
			if (x < WorldSettings.columns)
			{
				x++;
			}
			else
			{
				y--;
				x = 1;
			}
			
		}
	}
	
	private void fillCellsWithData(Matrix m)
	{
		for (int i = 0, x = 1, y = WorldSettings.rows; i < cells.length; i++)
		{
			/* Name of cells is it's: "X Y block_ID" */
			cells[i].setName(x + "," + y + "," + m.getID(x, y));
			
			if (x < WorldSettings.columns)
			{
				x++;
			}
			else
			{
				y--;
				x = 1;
			}
			
		}
	}
	
	private void setSave(SaveOrigin saveOrigin)
	{
		this.saveOrigin = saveOrigin;
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
		((JCellLabel) e.getComponent()).setOutlineEnabled(true);
	}
	
	@Override
	public void mouseExited(MouseEvent e)
	{
		((JCellLabel) e.getComponent()).setOutlineEnabled(false);
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
		resetBlockIcons();
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
