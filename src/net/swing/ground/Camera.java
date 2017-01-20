package net.swing.ground;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL12;
import org.newdawn.slick.Color;

public class Camera
{
	
	// View
	
	/** Sets color of background */
	public static void setBackgroundColor(Color c)
	{
		setBackgroundColor(c.r, c.g, c.b, c.a);
	}
	
	/** Sets color of background */
	public static void setBackgroundColor(float r, float g, float b, float a)
	{
		Display.setInitialBackground(r, g, b);
		glClearColor(r, g, b, a);
	}
	
	/**It's Deprecated because it should be used only once when the
	 * game starts.*/
	@Deprecated
	public static void initializeView(double width, double height)
	{
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, 0, height, 1, -1);
		
		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER, 0.1f);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		glEnable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glHint(GL_LINE_SMOOTH_HINT, GL_FASTEST);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_FASTEST);
		glHint(GL_POINT_SMOOTH_HINT, GL_FASTEST);
		glHint(GL_POLYGON_SMOOTH_HINT, GL_FASTEST);
		glCullFace(GL_BACK_RIGHT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
	    
		setBackgroundColor(0, 0, 0, 0);
		
	}
	
	public static void viewResize(double width, double height)
	{
		glViewport(0, 0, (int) width, (int) height);
		glLoadIdentity();
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0.0f, width, 0.0f, height, 1.0f, -1.0f);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
	
}
