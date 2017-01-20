package net.editor.room;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

public class JAreaLabel extends JLabel
{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	public JAreaLabel()
	{
		setHorizontalTextPosition(CENTER);
		setVerticalTextPosition(CENTER);
		setVerticalAlignment(CENTER);
		setHorizontalAlignment(CENTER);
		setOpaque(false);
	}
	
	private boolean	outlineEnabled	= false;
	
	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		
		if (isOutlineEnabled())
		{
			g2d.clearRect(0, 0, getWidth(), getHeight());
		}
		super.paint(g);
		
		g2d.setStroke(new BasicStroke(1));
		g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
	}
	
	public boolean isOutlineEnabled()
	{
		return outlineEnabled;
	}
	
	@Override
	public void setName(String name)
	{
		super.setName(name);
		setToolTipText(name);
	}
	
	public void setOutlineEnabled(boolean outlineEnabled)
	{
		this.outlineEnabled = outlineEnabled;
	}
}
