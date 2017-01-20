package net.swing.ground;

import java.io.File;

public class Window
{
	/** Window width */
	private static int				Width		= 540;
	/** Window height */
	private static int				Height		= 480;
	/***/
	private static Fullscreen		fullscreen	= new Fullscreen();
	
	private static String			gameName;
	
	public static BitmapFont		textDisplayer;
	
	private Window()
	{
	}
	
	public static void initialize(int width, int height, String gameName)
	{
		Width = width;
		Height = height;
		Window.gameName = gameName;
	}
	
	public static void setTextDisplayer(String fontFile)
	{
		textDisplayer = new BitmapFont(new File(fontFile), Width / 12, Height / 12);
	}
	
	public static void setTextDisplayer(File fontFile)
	{
		textDisplayer = new BitmapFont(fontFile, Width / 12, Height / 12);
	}
	
	/** Changes full screen mode */
	public static void setFullscreen(boolean arg0)
	{
		fullscreen.setDisplayMode(Width, Height, arg0);
	}
	
	/** Returns full screen mode status */
	public static boolean isFullscreen()
	{
		return fullscreen.isFullscreen();
	}
	
	/** Display width */
	public static int getWidth()
	{
		return Width;
	}
	
	/** Display height */
	public static int getHeight()
	{
		return Height;
	}
	
	public static String getGameName()
	{
		return gameName;
	}
	
}
