package net.swing.ground.GUI;

import net.swing.ground.BitmapFont;

public interface GUItextArea
{
	public BitmapFont getFont();
	
	public void setText(String text);
	
	public void update();
	
	public void render();
	
}
