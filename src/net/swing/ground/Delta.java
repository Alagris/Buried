package net.swing.ground;

public class Delta
{
	/***/
	private static FPS	fps	= new FPS();
	
	private static int	delta;
	
	public static void update()
	{
		delta = fps.getDelta();
	}
	
	public static int get()
	{
		return delta;
	}
	
	public static String updateFPS()
	{
		return fps.updateFPS();
	}
	
}
