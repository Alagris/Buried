package net.swing.ground.GUI;

public interface GUIbutton
{
	
	/** Renders normal look of button */
	public void render();
	
	/** Renders button clicked */
	public void renderClick();
	
	/** Action to do on activation */
	public void onClick();
	
	/** Renders button while mouse is in button's area */
	public void renderMouseEnter();
}
