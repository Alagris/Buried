package net.twi.AI.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.JPanel;

public class StatisticsPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private final Color[]		colors				= { Color.RED, Color.CYAN, Color.GREEN, Color.BLUE, Color.MAGENTA };
	private int					lastMouseLocationX, lastMouseLocationY;
	private int					mouseX;
	int							statisticsDataVector;
	LinkedList<int[]>			list				= new LinkedList<int[]>();
	
	public StatisticsPanel()
	{
		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e)
			{
				
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				lastMouseLocationX = e.getX();
				lastMouseLocationY = e.getY();
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
			}
		});
		
		addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e)
			{
				mouseX = e.getX();
				repaint();
			}
			
			@Override
			public void mouseDragged(MouseEvent e)
			{
				
				viewLocation.setLocation(viewLocation.x + e.getX() - lastMouseLocationX, viewLocation.y + e.getY() - lastMouseLocationY);
				lastMouseLocationX = e.getX();
				lastMouseLocationY = e.getY();
				repaint();
			}
		});
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		g.clearRect(0, 0, getWidth(), getHeight());
		int x = 1;
		int[] lastEntries = new int[statisticsDataVector];
		for (int[] data : list)
		{
			drawPixels(x, data, g, lastEntries);
			x++;
		}
		g.setColor(Color.BLACK);
		g.drawLine(mouseX, 0, mouseX, getHeight());
		g.drawString("" + (mouseX - viewLocation.x), mouseX, getHeight());
		g.drawLine(viewLocation.x, 0, viewLocation.x, getHeight());
		g.drawLine(0, viewLocation.y + getHeight(), getWidth(), viewLocation.y + getHeight());
	}
	
	private Point	viewLocation	= new Point();
	
	void drawPixels(int x, int[] data, Graphics g, int[] lastEntries)
	{
		
		for (int i = 0; i < data.length; i++)
		{
			g.setColor(colors[i]);
			g.drawLine(x + viewLocation.x, getHeight() - data[i] + viewLocation.y, x + viewLocation.x, getHeight() - lastEntries[i] + viewLocation.y);
			lastEntries[i] = data[i];
		}
	}
	
}