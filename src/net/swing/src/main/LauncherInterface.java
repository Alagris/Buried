package net.swing.src.main;

import org.lwjgl.opengl.DisplayMode;

public interface LauncherInterface
{
	
	public void setSizes(DisplayMode[] arg0);
	
	public boolean isFullscreenSelected();
	
	public boolean isReady();
	
	public int getSelectedWidth();
	
	public DisplayMode getSelectedDisplayMode();
	
	public int getSelectedHeight();
	
	public void shutdown();
	
}
