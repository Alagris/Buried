package net.editor.room;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import net.swing.engine.WPS;

public class JEntityLabel extends JLabel
{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	private WPS					WPSlocation;
	
	public JEntityLabel(WPS location, String marker)
	{
		setName(marker);
		setText(getName());
		setWPSlocation(location);
		setBackground(Color.WHITE);
		resetSize();
	}
	
	public JEntityLabel(WPS location, Entities ent)
	{
		this(location, ent.getMarker());
	}
	
	public JEntityLabel(PrimitiveEntity ent)
	{
		this(ent.getLocation(), ent.getName());
	}
	
	public WPS getWPSlocation()
	{
		return WPSlocation;
	}
	
	public void setWPSlocation(WPS wPSlocation)
	{
		WPSlocation = wPSlocation;
	}
	
	public void resetSize()
	{
		setSize(getFontMetrics(getFont()).getHeight(), getFontMetrics(getFont()).stringWidth(getName()));
	}
	
	public void resetSize(int fontSize)
	{
		setFont(new Font(getFont().getName(), getFont().getStyle(), fontSize));
		resetSize();
	}
}
