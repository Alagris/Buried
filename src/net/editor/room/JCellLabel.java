package net.editor.room;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

public class JCellLabel extends JLabel
{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	public JCellLabel()
	{
	}
	
	private boolean	outlineEnabled	= false;
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		if (outlineEnabled)
		{
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.GREEN);
			g2d.setStroke(new BasicStroke(getHeight() / 5));
			g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		}
	}
	
	public boolean isOutlineEnabled()
	{
		return outlineEnabled;
	}
	
	public void setOutlineEnabled(boolean outlineEnabled)
	{
		this.outlineEnabled = outlineEnabled;
	}
}
