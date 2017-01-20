package net.swing.engine.graph;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glDeleteLists;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;

import java.util.BitSet;

import javax.annotation.PreDestroy;

import net.swing.ground.Graphics;
import net.swing.ground.Pointf;
import net.swing.src.env.Matrix;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class DisplayMatrix
{
	
	// ==================
	// Initialization methods & constructors
	// ==================
	
	protected int[]	lists;
	
	public DisplayMatrix(int rows, int columns, float blockWidth, float blockHeight, Pointf location)
	{
		this(rows, columns, blockWidth, blockHeight, location.getX(), location.getY());
	}
	
	public DisplayMatrix(int rows, int columns, float blockWidth, float blockHeight, float x, float y)
	{
		buildMatrix(rows, columns, blockWidth, blockHeight, x, y);
	}
	
	/**
	 * Creates new (and removes previous) matrix
	 * 
	 * @param y
	 * @param x
	 */
	public void buildMatrix(int rows, int columns, float blockWidth, float blockHeight, float x, float y)
	{
		removeMatrix();
		produceLists(rows, columns, blockWidth, blockHeight, x, y);
	}
	
	// ==================
	// List removing methods
	// ==================
	@PreDestroy
	public void removeMatrix()
	{
		if (lists == null) return;
		for (int index = 0; index < lists.length; index++)
		{
			glDeleteLists(lists[index], 1);
		}
	}
	
	// ==================
	// List producing methods
	// ==================
	
	private void produceLists(int rows, int columns, float blockWidth, float blockHeight, float shiftX, float shiftY)
	{
		lists = new int[rows * columns];
		
		for (int index = 0, x = 1, y = 1; index < lists.length; index++)
		{
			produceList(index, x, y, blockWidth, blockHeight, shiftX, shiftY);
			if (x < columns)
			{
				x++;
			}
			else
			{
				y++;
				x = 1;
			}
		}
	}
	
	private void produceList(int index, float x, float y, float width, float height, float shiftX, float shiftY)
	{
		lists[index] = glGenLists(1);
		glNewList(lists[index], GL_COMPILE);
		renderQuad(shiftX + (x - 1) * width, shiftY + (y - 1) * height, width, height);
		glEndList();
	}
	
	private void renderQuad(float x, float y, float width, float height)
	{
		Graphics.renderRectangleNonColored(x, y, width, height);
	}
	
	// ==================
	// Rendering methods (without specified location)
	// ==================
	
	public void renderDisplay_BooleanColor(BitSet map, Color activeColor) throws IndexOutOfBoundsException
	{
		activeColor.bind();
		for (int index = 0; index < lists.length && index < map.size(); index++)
		{
			if (map.get(index))
			{
				// renders display list
				glCallList(lists[index]);
			}
		}
	}
	
	public void renderDisplay_BooleanColor(Matrix m, Color activeColor) throws IndexOutOfBoundsException
	{
		activeColor.bind();
		for (int index = 0; index < lists.length && index < m.size(); index++)
		{
			if (m.getID(index) == 1)
			{
				// renders display list
				glCallList(lists[index]);
			}
			
		}
	}
	
	/**
	 * Binds color to each value in matrix (when cell in matrix has value 0 then
	 * it will be filled with color at index 0)
	 * 
	 * @param colors
	 *            - array of colors. Index indicates color of cell value
	 */
	public void renderDisplay_MultiColor(Matrix m, Color... colors) throws IndexOutOfBoundsException
	{
		for (int index = 0; index < lists.length && index < m.size(); index++)
		{
			colors[m.getID(index)].bind();
			// renders display list
			glCallList(lists[index]);
		}
	}
	
	public void renderDisplay_Textured(Matrix m, Color activeColor) throws IndexOutOfBoundsException
	{
		// TODO: render display with textures
	}
	
	// ==================
	// Rendering methods (with specified location)
	// ==================
	
	/**
	 * Location of of bottom left corner is changed. This method is slower than
	 * other methods that use default location (specified in constructor).
	 * 
	 * @param x
	 *            - amount of pixels that is going to be added to default x
	 *            location
	 * @param y
	 *            - amount of pixels that is going to be added to default y
	 *            location
	 */
	public void renderDisplay_BooleanColor(BitSet map, Color activeColor, float x, float y) throws IndexOutOfBoundsException
	{
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		renderDisplay_BooleanColor(map, activeColor);
		GL11.glPopMatrix();
	}
	
	/**
	 * Location of of bottom left corner is changed. This method is slower than
	 * other methods that use default location (specified in constructor).
	 * 
	 * @param x
	 *            - amount of pixels that is going to be added to default x
	 *            location
	 * @param y
	 *            - amount of pixels that is going to be added to default y
	 *            location
	 */
	public void renderDisplay_BooleanColor(Matrix m, Color activeColor, float x, float y) throws IndexOutOfBoundsException
	{
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		renderDisplay_BooleanColor(m, activeColor);
		GL11.glPopMatrix();
	}
	
	/**
	 * Binds color to each value in matrix (when cell in matrix has value 0 then
	 * it will be filled with color at index 0). Location of of bottom left
	 * corner is changed. This method is slower than other methods that use
	 * default location (specified in constructor).
	 * 
	 * @param x
	 *            - amount of pixels that is going to be added to default x
	 *            location
	 * @param y
	 *            - amount of pixels that is going to be added to default y
	 *            location
	 * @param colors
	 *            - array of colors. Index indicates color of cell value
	 */
	public void renderDisplay_MultiColor(Matrix m, Color[] colors, float x, float y) throws IndexOutOfBoundsException
	{
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		renderDisplay_MultiColor(m, colors);
		GL11.glPopMatrix();
	}
	
	/**
	 * Location of of bottom left corner is changed. This method is slower than
	 * other methods that use default location (specified in constructor).
	 * 
	 * @param x
	 *            - amount of pixels that is going to be added to default x
	 *            location
	 * @param y
	 *            - amount of pixels that is going to be added to default y
	 *            location
	 */
	public void renderDisplay_Textured(Matrix m, Color activeColor, float x, float y) throws IndexOutOfBoundsException
	{
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		renderDisplay_Textured(m, activeColor);
		GL11.glPopMatrix();
	}
}
