package net.swing.ground.GUI;

public interface GUIbar
{
	
	public static final byte	ORIENTATION_HORIZONTAL	= 0;
	public static final byte	ORIENTATION_VERTICAL	= 1;
	
	public void setMaxValue(float max);
	
	public void setBarValue(float arg0);
	
	public float getBarValue();
	
	public void render();
	
}
