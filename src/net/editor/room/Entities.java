package net.editor.room;

import javax.swing.ImageIcon;

public enum Entities
{
	PLAYER("player", null);
	
	private String		marker;
	private ImageIcon	icon;
	
	private Entities(String marker, ImageIcon icon)
	{
	}
	
	public ImageIcon getIcon()
	{
		return icon;
	}
	
	public String getMarker()
	{
		return marker;
	}
}
