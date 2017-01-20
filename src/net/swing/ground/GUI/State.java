package net.swing.ground.GUI;

import javax.annotation.PreDestroy;

import net.swing.ground.Controls;
import net.swing.ground.Delta;
import net.swing.ground.Window;

public abstract class State
{
	
	// The smallest things that are displayed in states should have this width
	// and height.
	/** minimum height (height/20) */
	public static final float	i				= Window.getHeight() / 20;
	/** minimum width (width/10) */
	public static final float	j				= Window.getWidth() / 10;
	private static boolean		isDeltaShown	= false;
	
	/**
	 * Renders all buttons background etc. Automatically updates mouse position,
	 * checks if should take a screen photo and calls showState() method.
	 */
	public void show()
	{
		Controls.checkForScreenshot();
		Controls.updateMouse();
		Controls.updateKeyboard();
		updateDelta();
		showState();
	}
	
	private void updateDelta()
	{
		if (Controls.typedKey == 61)
		{
			isDeltaShown = !isDeltaShown;
		}
		if (isDeltaShown)
		{
			Window.textDisplayer.render("D " + Delta.get() + " ," + Delta.updateFPS(), 0, i * 18);
		}
	}
	
	/**
	 * Put here everything that you want to be displayed when this state
	 * activate.Hint: all buttons and things like that should be initialized in
	 * the constructor
	 **/
	public abstract void showState();
	
	/** Remove everything like buttons or display lists */
	@PreDestroy
	public abstract void cleanUp();
}
