package net.swing.ground;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Controls
{
	
	private Controls()
	{
	}
	
	// Variables
	/** Mouse position */
	public static float		mouseX, mouseY;
	/** Mouse position from previous frame */
	public static float		lastMouseX, lastMouseY;
	/** I don't see any useful task this value could perform outside of this class , so it's private */
	private static boolean	wasClicked1		= false, wasClicked0 = false;
	
	public static int		lastTypedNumber	= 1;
	/** True if left mouse button is pressed */
	public static boolean	isMouse0pressed;
	/** True if right mouse button is pressed */
	public static boolean	isMouse1pressed;
	/**
	 * True if left mouse button is being released , which means that
	 * in previous frame mouse was pressed and now it's not
	 */
	public static boolean	isMouse0released;
	/**
	 * True if right mouse button is being released which means that
	 * in previous frame mouse was pressed and now it's not
	 */
	public static boolean	isMouse1released;
	/**
	 * True if left mouse button is pressed ( but this value is active only once per each click)
	 */
	public static boolean	isMouse0clicked;
	/**
	 * True if right mouse button is pressed ( but this value is active only once per each click)
	 */
	public static boolean	isMouse1clicked;
	/***/
	public static char		typedChar;
	/**
	 * This value is compatible with Keyboard.KEY_??? (e.g. Keyboard.KEY_A)
	 * provided by LWJGl.When nothing is typed it is -1 .
	 */
	public static int		typedKey;
	
	/** Scroll movement. dWhell < 0 means that scroll goes down */
	public static int		dWheel;
	
	public static void checkMouseWheel()
	{
		dWheel = Mouse.getDWheel();
	}
	
	/**
	 * Gets coordinates of mouse. Remember that in menu mouse shouldn't be
	 * grabbed
	 */
	public static void updateMouse()
	{
		if (Mouse.isButtonDown(0))
		{
			isMouse0released = false;
			isMouse0pressed = true;
		}
		else
		{
			isMouse0released = isMouse0pressed;
			isMouse0pressed = false;
		}
		
		if (Mouse.isButtonDown(1))
		{
			isMouse1released = false;
			isMouse1pressed = true;
		}
		else
		{
			isMouse1released = isMouse1pressed;
			isMouse1pressed = false;
		}
		
		lastMouseX = mouseX;
		lastMouseY = mouseY;
		
		mouseX = Mouse.getX();
		mouseY = Mouse.getY();
		
		isMouse0clicked = isMouse0Clicked();
		isMouse1clicked = isMouse1Clicked();
	}
	
	public static void updateKeyboard()
	{
		typedChar = '\u0000';
		typedKey = -1;
		while (Keyboard.next())
		{
			if (Keyboard.getEventKeyState())
			{
				typedChar = Keyboard.getEventCharacter();
				typedKey = Keyboard.getEventKey();
			}
		}
	}
	
	/** Returns true if mouse is clicked. It activates only once after a click. */
	private static boolean isMouse1Clicked()
	{
		if (isMouse1pressed)
		{
			if (!wasClicked1)
			{
				wasClicked1 = true;
				return true;
			}
		}
		else
		{
			wasClicked1 = false;
		}
		return false;
	}
	
	/** Returns true if mouse is clicked. It activates only once after a click. */
	private static boolean isMouse0Clicked()
	{
		if (isMouse0pressed)
		{
			if (!wasClicked0)
			{
				wasClicked0 = true;
				return true;
			}
		}
		else
		{
			wasClicked0 = false;
		}
		return false;
	}
	
	public static void resetMouse0Clicked()
	{
		isMouse0clicked = false;
	}
	
	public static void resetMouse1Clicked()
	{
		isMouse1clicked = false;
	}
	
	public static void resetMouse0Pressed()
	{
		isMouse0pressed = false;
	}
	
	public static void resetMouse1Pressed()
	{
		isMouse1pressed = false;
	}
	
	public static void resetMouse0()
	{
		resetMouse0Clicked();
		resetMouse0Pressed();
	}
	
	public static void resetMouse1()
	{
		resetMouse1Clicked();
		resetMouse1Pressed();
	}
	
	public static void resetMousePressed()
	{
		resetMouse1Pressed();
		resetMouse0Pressed();
	}
	
	public static void resetMouseClicked()
	{
		resetMouse1Clicked();
		resetMouse0Clicked();
	}
	
	public static void resetMouse()
	{
		resetMouse1();
		resetMouse0();
	}
	
	/** Used in inventory (checking which binded object is being selected) */
	public static void updateKeyboardNumbers()
	{
		int x = Keyboard.getEventKey();
		if (x >= 0 && x < 10) lastTypedNumber = x;
	}
	
	public static void checkForScreenshot()
	{
		// F2 key is used to capture screen shots
		if (Keyboard.isKeyDown(Keyboard.KEY_F2))
		{
			ScreenshotMaker.takePhoto();
		}
	}
}
