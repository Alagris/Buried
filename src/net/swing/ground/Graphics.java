package net.swing.ground;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Color;

public class Graphics extends Rectanglef
{
	// ////////////////////////////////////////////
	// / variables
	// ////////////////////////////////////////////
	protected Color	RGB	= new Color(1f, 1f, 1f);
	
	// ////////////////////////////////////////////
	// / constructors
	// ////////////////////////////////////////////
	public Graphics()
	{
		super();
	}
	
	public Graphics(Rectanglef r)
	{
		super(r);
	}
	
	public Graphics(float x, float y, float width, float height)
	{
		super(x, y, width, height);
	}
	
	public Graphics(Pointf location, float width, float height)
	{
		super(location, width, height);
	}
	
	// ////////////////////////////////////////////
	// / setters and getters
	// ////////////////////////////////////////////
	public void setColor(float r, float g, float b)
	{
		RGB.r = r;
		RGB.g = g;
		RGB.b = b;
	}
	
	public void setColor(float r, float g, float b, float a)
	{
		RGB.r = r;
		RGB.g = g;
		RGB.b = b;
		RGB.a = a;
	}
	
	public Color getColor()
	{
		return RGB;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + " RGB{ red:" + RGB.r + " green:" + RGB.g + " blue:" + RGB.b + "}";
	}
	
	// ////////////////////////////////////////////
	// / color binding
	// ////////////////////////////////////////////
	public void bindColor()
	{
		RGB.bind();
	}
	
	// ////////////////////////////////////////////
	// / colored rendering of rectangle
	// ////////////////////////////////////////////
	
	protected void renderRectangle(Rectanglef area)
	{
		renderRectangle(area.getX(), area.getY(), area.getWidth(), area.getHeight());
	}
	
	protected void renderRectangle()
	{
		renderRectangle(getX(), getY(), width, height);
	}
	
	protected void renderRectangle(float x, float y, float width, float height)
	{
		renderQuad(x, y, x + width, y + height);
	}
	
	protected void renderQuad(float x, float y, float x2, float y2)
	{
		glColor3f(RGB.r, RGB.g, RGB.b);
		glBegin(GL_QUADS);
		
		glTexCoord2f(0, 0);
		glVertex2f(x, y2);
		
		glTexCoord2f(1, 0);
		glVertex2f(x2, y2);
		
		glTexCoord2f(1, 1);
		glVertex2f(x2, y);
		
		glTexCoord2f(0, 1);
		glVertex2f(x, y);
		
		glEnd();
	}
	
	protected void renderRectangle(float x, float y)
	{
		renderRectangle(x, y, width, height);
	}
	
	// ////////////////////////////////////////////
	// / rendering non-colored rectangle
	// ////////////////////////////////////////////
	
	/**
	 * Renders quad but LWJGL color is not changed to RGB value in this graphics
	 * (so you have to manipulate color on your own)
	 */
	public static void renderRectangleNonColored(Rectanglef area)
	{
		renderRectangleNonColored(area.getX(), area.getY(), area.getWidth(), area.getHeight());
	}
	
	/**
	 * Renders quad but LWJGL color is not changed to RGB value in this graphics
	 * (so you have to manipulate color on your own)
	 */
	protected void renderRectangleNonColored()
	{
		renderRectangleNonColored(getX(), getY(), width, height);
	}
	
	/**
	 * Renders quad but LWJGL color is not changed to RGB value in this graphics
	 * (so you have to manipulate color on your own)
	 */
	public static void renderRectangleNonColored(float x, float y, float width, float height)
	{
		renderQuadNonColored(x, y, x + width, y + height);
	}
	
	/** Generates display list and returns it's id */
	public static int generateRectangleNonColored(Rectanglef area)
	{
		return generateRectangleNonColored(area.getX(), area.getY(), area.getWidth(), area.getHeight());
	}
	
	/** Generates display list and returns it's id */
	public static int generateRectangleNonColored(float x, float y, float width, float height)
	{
		return generateQuadNonColored(x, y, x + width, y + height);
	}
	
	/**
	 * Renders quad but LWJGL color is not changed to RGB value in this graphics
	 * (so you have to manipulate color on your own)
	 */
	protected void renderRectangleNonColored(float x, float y)
	{
		renderRectangleNonColored(x, y, width, height);
	}
	
	public static void renderQuadNonColored(float x, float y, float x2, float y2)
	{
		glBegin(GL_QUADS);
		
		glTexCoord2f(0, 0);
		glVertex2f(x, y2);
		
		glTexCoord2f(1, 0);
		glVertex2f(x2, y2);
		
		glTexCoord2f(1, 1);
		glVertex2f(x2, y);
		
		glTexCoord2f(0, 1);
		glVertex2f(x, y);
		
		glEnd();
	}
	

	/**
	 * builds (like rendering but without calling glBegin and glEnd)
	 * quad but LWJGL color is not changed to RGB value in this graphics
	 * (so you have to manipulate color on your own)
	 */
	public static void buildRectangleNonColored(float x, float y, float width, float height)
	{
		buildQuadNonColored(x, y, x + width, y + height);
	}
	
	/** Like rendering but without calling glBegin and glEnd */
	public static void buildQuadNonColored(float x, float y, float x2, float y2)
	{
		glTexCoord2f(0, 0);
		glVertex2f(x, y2);
		
		glTexCoord2f(1, 0);
		glVertex2f(x2, y2);
		
		glTexCoord2f(1, 1);
		glVertex2f(x2, y);
		
		glTexCoord2f(0, 1);
		glVertex2f(x, y);
	}
	
	/** Generates display list and returns it's id */
	public static int generateQuadNonColored(float x, float y, float x2, float y2)
	{
		int listID = glGenLists(1);
		glNewList(listID, GL_COMPILE);
		renderQuadNonColored(x, y, x2, y2);
		glEndList();
		return listID;
	}
	
	// ////////////////////////////////////////////
	// / rendering rectangle without textures
	// ////////////////////////////////////////////
	
	/**
	 * Coordinations of texture corners are not specified
	 * but textures itself are not disabled. To do that use
	 * glDiable(GL_TEXTURE_2D) .
	 */
	protected void renderRectangleNoTexture(Rectanglef area)
	{
		renderRectangleNoTexture(area.getX(), area.getY(), area.getWidth(), area.getHeight());
	}
	
	/**
	 * Coordinations of texture corners are not specified
	 * but textures itself are not disabled. To do that use
	 * glDisable(GL_TEXTURE_2D) .
	 */
	protected void renderRectangleNoTexture()
	{
		renderRectangleNoTexture(getX(), getY(), width, height);
	}
	
	/**
	 * Coordinations of texture corners are not specified
	 * but textures itself are not disabled. To do that use
	 * glDisable(GL_TEXTURE_2D) .
	 */
	protected void renderRectangleNoTexture(float x, float y, float width, float height)
	{
		renderQuadNoTexture(x, y, x + width, y + height);
	}
	
	/**
	 * Coordinations of texture corners are not specified
	 * but textures itself are not disabled. To do that use
	 * glDisable(GL_TEXTURE_2D) .
	 */
	protected void renderQuadNoTexture(float x, float y, float x2, float y2)
	{
		glColor3f(RGB.r, RGB.g, RGB.b);
		glBegin(GL_QUADS);
		
		glVertex2f(x, y2);
		
		glVertex2f(x2, y2);
		
		glVertex2f(x2, y);
		
		glVertex2f(x, y);
		
		glEnd();
	}
	
	/**
	 * Coordinations of texture corners are not specified (better performance)
	 * but textures itself are not disabled. To do that use
	 * glDisable(GL_TEXTURE_2D) .
	 */
	protected void renderRectangleNoTexture(float x, float y)
	{
		renderRectangleNoTexture(x, y, width, height);
	}
	
	// ////////////////////////////////////////////
	// / rendering filled circles
	// ////////////////////////////////////////////
	/**
	 * precision = 0.2f is enough. Pay attention that those circles don't have
	 * texture coordinates but it's possible to color them
	 */
	public static void renderFilledCircle(float x, float y, float radius, float precision)
	{
		glBegin(GL_TRIANGLE_FAN);
		
		glVertex2f(x, y);
		
		for (float angle = 0; angle < Math.PI * 2 + precision; angle += precision)
		{
			glVertex2f(x + (float) Math.cos(angle) * radius, y + (float) Math.sin(angle) * radius);
		}
		
		glEnd();
	}
	
	/**
	 * precision = 0.2f is enough. Pay attention that those circles don't have
	 * texture coordinates but it's possible to color them
	 */
	public static int generateFilledCircle(float x, float y, float radius, float precision)
	{
		
		int listID = glGenLists(1);
		glNewList(listID, GL_COMPILE);
		renderFilledCircle(x, y, radius, precision);
		glEndList();
		return listID;
	}
	
	// ////////////////////////////////////////////
	// / rendering outline of circles
	// ////////////////////////////////////////////
	
	/**
	 * precision = 0.2f is enough. Pay attention that those circles don't have
	 * texture coordinates but it's possible to color them
	 */
	public static void renderOutlineCircle(float x, float y, float radius, float precision)
	{
		glBegin(GL_LINE_LOOP);
		for (float angle = 0; angle < Math.PI * 2; angle += precision)
		{
			glVertex2f(x + (float) Math.cos(angle) * radius, y + (float) Math.sin(angle) * radius);
		}
		
		glEnd();
	}
	
	/**
	 * precision = 0.2f is enough. Pay attention that those circles don't have
	 * texture coordinates but it's possible to color them
	 */
	public static int generateOutlineCircle(float x, float y, float radius, float precision)
	{
		int listID = glGenLists(1);
		glNewList(listID, GL_COMPILE);
		renderOutlineCircle(x, y, radius, precision);
		glEndList();
		return listID;
	}
	
	// ////////////////////////////////////////////
	// / rendering vertical lines
	// ////////////////////////////////////////////
	
	protected void renderVerticalLine(float x, float y, float absoluteHeight, float lineWidth, float r, float g, float b)
	{
		glDisable(GL_TEXTURE_2D);
		glColor3f(r, g, b);
		glLineWidth(lineWidth);
		glBegin(GL_LINES);
		glVertex2f(x, y);
		glVertex2f(x, absoluteHeight);
		glEnd();
		glEnable(GL_TEXTURE_2D);
	}
	
	protected void renderVerticalLine(float x, float y, float absoluteHeight, float lineWidth)
	{
		renderVerticalLine(x, y, absoluteHeight, lineWidth, RGB.r, RGB.g, RGB.b);
	}
}
