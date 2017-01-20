package net.swing.ground;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Fullscreen
{
	
	/**
	 * Set the display mode to be used
	 * 
	 * @param width
	 *            The width of the display required
	 * @param height
	 *            The height of the display required
	 * @param fullscreen
	 *            True if we want fullscreen mode
	 */
	public void setDisplayMode(int width, int height, boolean fullscreen)
	{
		
		// return if requested DisplayMode is already set
		if ((Display.getDisplayMode().getWidth() == width) && (Display.getDisplayMode().getHeight() == height) && (Display.isFullscreen() == fullscreen))
		{
			return;
		}
		
		try
		{
			DisplayMode targetDisplayMode = null;
			
			if (fullscreen)
			{
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;
				
				for (int i = 0; i < modes.length; i++)
				{
					
					if ((modes[i].getWidth() == width) && (modes[i].getHeight() == height))
					{
						
						if ((targetDisplayMode == null) || (modes[i].getFrequency() >= freq))
						{
							
							if ((targetDisplayMode == null) || (modes[i].getBitsPerPixel() > targetDisplayMode.getBitsPerPixel()))
							{
								
								targetDisplayMode = modes[i];
								freq = targetDisplayMode.getFrequency();
							}
						}
						
						// if we've found a match for bpp and frequence against
						// the
						// original display mode then it's probably best to go
						// for this one
						// since it's most likely compatible with the monitor
						if ((modes[i].getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) && (modes[i].getFrequency() == Display.getDesktopDisplayMode().getFrequency()))
						{
							targetDisplayMode = modes[i];
							break;
						}
					}
				}
			}
			else
			{
				targetDisplayMode = new DisplayMode(width, height);
			}
			
			if (targetDisplayMode == null)
			{
				System.out.println("Failed to find value mode: " + width + "x" + height + " fs=" + fullscreen);
				return;
			}
			
			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(fullscreen);
		}
		catch (LWJGLException e)
		{
			System.out.println("Unable to setup mode " + width + "x" + height + " fullscreen=" + fullscreen + e);
		}
	}
	
	public boolean isFullscreen()
	{
		return Display.isFullscreen();
	}
}
