package net.swing.ground.GUI;

public interface GUIlist<typeOfValues>
{
	
	/** Renders normal look of list */
	public void render();
	
	/** What to do on click */
	public void onClick(float x, float y);
	
	/** What to do if is scrolled */
	public void onScrollDown();
	
	public void onScrollUp();
	
	public void renderMouseEnter(float x, float y);
	
	public typeOfValues getSelectedValue();
}
