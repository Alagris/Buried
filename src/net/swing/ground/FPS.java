package net.swing.ground;

import org.lwjgl.Sys;

public class FPS
{
	
	private int		delta	= 0;
	/** time at last frame */
	private long	lastFrame;
	
	/**
	 * Calculate how many milliseconds have passed since last frame.
	 * 
	 * @return milliseconds passed since last frame
	 */
	public int getDelta()
	{
		long time = getTime();
		delta = (int) (time - lastFrame);
		lastFrame = time;
		
		return delta;
	}
	
	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	public long getTime()
	{
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	private String	fpsString;
	/** last fps time */
	private long	lastFPS	= getTime();
	/** frames per second */
	private int		fps;
	
	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public String updateFPS()
	{
		
		if (getTime() - lastFPS > 1000)
		{
			fpsString = "FPS " + fps;
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
		return fpsString;
	}
}
