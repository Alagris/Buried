package net.swing.ground;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class BitmapFont
{
	// ////////////////////////////////////////////
	// / variables
	// ////////////////////////////////////////////
	
	/** The texture object for the bitmap font. */
	protected Texture	fontTexture;
	/** default values of variables: width,height,red,green,blue */
	protected float		w, h;
	protected Color		RGB	= new Color(0, 0, 0);
	
	// ////////////////////////////////////////////
	// / constructors
	// ////////////////////////////////////////////
	
	public BitmapFont(BitmapFont font)
	{
		w = font.w;
		h = font.h;
		RGB.r = font.RGB.r;
		RGB.g = font.RGB.g;
		RGB.b = font.RGB.b;
		this.fontTexture = font.fontTexture;
	}
	
	public BitmapFont(File fontFile, float width, float height)
	{
		w = width;
		h = height;
		try
		{
			setTextures(fontFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public BitmapFont(BitmapFont font, float width, float height)
	{
		w = width;
		h = height;
		RGB.r = font.RGB.r;
		RGB.g = font.RGB.g;
		RGB.b = font.RGB.b;
		this.fontTexture = font.fontTexture;
	}
	
	// ////////////////////////////////////////////
	// / getters and setters
	// ////////////////////////////////////////////
	
	public void setColor(float r, float g, float b)
	{
		RGB.r = r;
		RGB.g = g;
		RGB.b = b;
	}
	
	public float getLetterWidth()
	{
		return w;
	}
	
	public float getLetterHeight()
	{
		return h;
	}
	
	private void setTextures(File file) throws IOException
	{
		fontTexture = TextureLoader.getTexture("PNG", new FileInputStream(file));
	}
	
	// ////////////////////////////////////////////
	// / calculating height
	// ////////////////////////////////////////////
	/**
	 * two values are returned by this method, but calling it is very expensive
	 * (it has loop in it) so its better when you call it once and save those
	 * values in variables
	 * 
	 * @return two values:
	 * @first - quantity of characters in longest line (use getStringLength to
	 *        get width)
	 * @second - quantity of lines (multiply by getLetterHeight() to get height)
	 * */
	public int[] getTextHeight(float x, String string)
	{
		return getTextHeight(x, string, w);
	}
	
	/**
	 * two values are returned by this method, but calling it is very expensive
	 * (it has loop in it) so its better when you call it once and save those
	 * values in variables
	 * 
	 * @return two values:
	 * @first - quantity of characters in longest line (use getStringLength to
	 *        get width)
	 * @second - quantity of lines (multiply by getLetterHeight() to get height)
	 * */
	public int[] getTextHeight(float x, String string, float characterWidth)
	{
		int variableToSaveLengthOfLongestLine = 0;
		int variableToSaveQuantityOfLines = 1;
		for (int i = 0, n = 0; i < string.length(); i++)
		{
			
			if (string.charAt(i) == '\n')
			{
				n = 0;
				variableToSaveQuantityOfLines++;
				continue;
			}
			else if (x + getCharacterWidthPlusX(n, characterWidth) > Window.getWidth())
			{
				n = 0;
				variableToSaveQuantityOfLines++;
			}
			n++;
			if (variableToSaveLengthOfLongestLine < n)
			{
				variableToSaveLengthOfLongestLine = n;
			}
		}
		return new int[] { variableToSaveLengthOfLongestLine, variableToSaveQuantityOfLines };
	}
	
	/**
	 * Calculates which line would be selected by point located at y if there
	 * was a text that starts at height: textTopCoordinateY .
	 * 
	 * @param y
	 *            - line selected by mouse (WARNING: this method doesn't check
	 *            if mouse/anything else actually selected that line, because it
	 *            doens't check X value)
	 * @param characterHeight
	 * @return integer that can be positive or negative. It is caused because
	 *         method doesn't check if Y value is inside area of text. If it's
	 *         out of bounds weird things can happen (like negative number of line).
	 *         Returned line index increases as the pointed y coordinate is lower:
	 * 
	 *         <pre>
	 *         y = textTopCoordinateY -> index 0 (no particular line selected)
	 *         y < textTopCoordinateY -> index > 0
	 *         y > textTopCoordinateY -> index < 0
	 * </pre>
	 **/
	public int getSelectedLine(float y, float textTopCoordinateY, float characterHeight)
	{
		return (int) Math.ceil((double) (textTopCoordinateY - y) / characterHeight);
	}
	
	/**
	 * Calculates which line would be selected by point located at y if there
	 * was a text that starts at height: textTopCoordinateY .
	 * 
	 * @param y
	 *            - line selected by mouse (WARNING: this method doesn't check
	 *            if mouse/anything else actually selected that line, because it
	 *            doens't check X value)
	 * @return integer that can be positive or negative. It is caused because
	 *         method doesn't check if Y value is inside area of text. If it's
	 *         out of bounds weird things can happen (like negative number of line).
	 *         Returned line index increases as the pointed y coordinate is lower:
	 * 
	 *         <pre>
	 *         y = textTopCoordinateY -> index 0 (no particular line selected)
	 *         y < textTopCoordinateY -> index > 0
	 *         y > textTopCoordinateY -> index < 0
	 * </pre>
	 **/
	public int getSelectedLine(float y, float textY)
	{
		return getSelectedLine(y, textY, h);
	}
	
	// ////////////////////////////////////////////
	// / calculating width
	// ////////////////////////////////////////////
	public float getCharacherRealHalfWidth()
	{
		return getCharacherRealHalfWidth(getLetterWidth());
	}
	
	public float getCharacherRealHalfWidth(float characterWidth)
	{
		return characterWidth / 8;
	}
	
	public float getCharacherRealWidth()
	{
		return getCharacherRealWidth(getLetterWidth());
	}
	
	public float getCharacherRealWidth(float characterWidth)
	{
		return characterWidth / 4;
	}
	
	/**
	 * Calculates X coordinate of character at specified index in
	 * displayed string.Also useful to
	 * calculate width of such long string (just put the last
	 * possible index as placeInTextLine).
	 */
	public float getCharacterWidthPlusX(int placeInTextLine)
	{
		return getCharacterWidthPlusX(placeInTextLine, getLetterWidth());
	}
	
	public float getCharacterX(int placeInTextLine)
	{
		return getCharacterX(placeInTextLine, getLetterWidth());
	}
	
	/**
	 * characterX = placeInTextLine * characterWidth / 4
	 */
	public float getCharacterX(int placeInTextLine, float characterWidth)
	{
		return placeInTextLine * getCharacherRealWidth(characterWidth);
	}
	
	public float getCharacterWidthPlusX(int placeInTextLine, float characterWidth)
	{
		return getCharacterX(placeInTextLine, characterWidth) + characterWidth / 2;
	}
	
	/**
	 * Try to keep values of parameters to be: characterX >= textStartXPosition.
	 * Otherwise it doesn't make sense.
	 * placeInTextLine = characterX / characterWidth * 4
	 */
	public int getCharacterIndex(float characterX, float textStartXPosition)
	{
		return (int) ((characterX - textStartXPosition) / getLetterWidth() * 4);
	}
	
	public int getCharacterIndex(float characterX)
	{
		return (int) (characterX / getLetterWidth() * 4);
	}
	
	// ////////////////////////////////////////////
	// / rendering normally
	// ////////////////////////////////////////////
	
	public void render(String text, float x, float y, float maxWidth)
	{
		renderString(text, 16, x, y, getLetterWidth(), getLetterHeight(), maxWidth);
	}
	
	public void render(float x, String text, float y, float width, float height, float maxWidth)
	{
		renderString(text, 16, x, y, width, height, maxWidth);
	}
	
	public void render(String text, float x, float y, float red, float green, float blue, float maxWidth)
	{
		renderString(text, 16, x, y, getLetterWidth(), getLetterHeight(), red, green, blue, maxWidth);
	}
	
	public void render(String text, float x, float y, float characterWidth, float characterHeight, float red, float green, float blue, float maxWidth)
	{
		renderString(text, 16, x, y, characterWidth, characterHeight, red, green, blue, maxWidth);
	}
	
	private void renderString(String string, int gridSize, float x, float y, float characterWidth, float characterHeight, float maxWidth)
	{
		renderString(string, gridSize, x, y, characterWidth, characterHeight, RGB.r, RGB.g, RGB.b, maxWidth);
	}
	
	public void render(String text, float x, float y)
	{
		renderString(text, 16, x, y, getLetterWidth(), getLetterHeight());
	}
	
	public void render(String text, float x, float y, float width, float height)
	{
		renderString(text, 16, x, y, width, height, Window.getWidth() - x);
	}
	
	public void render(String text, float x, float y, float red, float green, float blue)
	{
		renderString(text, 16, x, y, getLetterWidth(), getLetterHeight(), red, green, blue, Window.getWidth() - x);
	}
	
	public void render(String text, float x, float y, float characterWidth, float characterHeight, float red, float green, float blue)
	{
		renderString(text, 16, x, y, characterWidth, characterHeight, red, green, blue, Window.getWidth() - x);
	}
	
	private void renderString(String string, int gridSize, float x, float y, float characterWidth, float characterHeight)
	{
		renderString(string, gridSize, x, y, characterWidth, characterHeight, RGB.r, RGB.g, RGB.b);
	}
	
	private void renderString(String string, int gridSize, float x, float y, float characterWidth, float characterHeight, float red, float green, float blue)
	{
		renderString(string, gridSize, x, y, characterWidth, characterHeight, red, green, blue, Window.getWidth() - x);
	}
	
	private void renderString(String string, int gridSize, float x, float y, float characterWidth, float characterHeight, float red, float green, float blue, float maxWidth)
	{
		if (string == null) return;
		fontTexture.bind();
		
		glEnable(GL_BLEND);
		glColor3f(red, green, blue);
		
		glBegin(GL_QUADS);
		for (int i = 0, n = 0; i < string.length(); i++)
		{
			
			if (string.charAt(i) == '\n')
			{
				y -= characterHeight;
				n = 0;
				continue;// loop continues because there is no need to display \n
			}
			else if (getCharacterWidthPlusX(n, characterWidth) > maxWidth)
			{
				y -= characterHeight;
				n = 0;
			}
			int asciiCode = string.charAt(i);
			float cellSize = 1.0f / gridSize;
			float cellX = (asciiCode % gridSize) * cellSize;
			float cellY = (asciiCode / gridSize) * cellSize;
			
			glTexCoord2f(cellX, cellY + cellSize);
			glVertex2f(x + getCharacterX(n, characterWidth), y);
			glTexCoord2f(cellX + cellSize, cellY + cellSize);
			glVertex2f(x + getCharacterWidthPlusX(n, characterWidth), y);
			glTexCoord2f(cellX + cellSize, cellY);
			glVertex2f(x + getCharacterWidthPlusX(n, characterWidth), y + characterHeight);
			glTexCoord2f(cellX, cellY);
			glVertex2f(x + getCharacterX(n, characterWidth), y + characterHeight);
			
			n++;
		}
		glEnd();
		
		glDisable(GL_BLEND);
		glColor3f(1, 1, 1);
	}
	
	// ////////////////////////////////////////////
	// /rendering text in one line
	// ////////////////////////////////////////////
	
	public void renderIgnoringNewLine(String text, float x, float y)
	{
		renderStringIgnoringNewLine(text, 16, x, y, getLetterWidth(), getLetterHeight());
	}
	
	public void renderIgnoringNewLine(String text, float x, float y, float width, float height)
	{
		renderStringIgnoringNewLine(text, 16, x, y, width, height);
	}
	
	public void renderIgnoringNewLine(String text, float x, float y, float red, float green, float blue)
	{
		renderStringIgnoringNewLine(text, 16, x, y, getLetterWidth(), getLetterHeight(), red, green, blue);
	}
	
	public void renderIgnoringNewLine(String text, float x, float y, float characterWidth, float characterHeight, float red, float green, float blue)
	{
		renderStringIgnoringNewLine(text, 16, x, y, characterWidth, characterHeight, red, green, blue);
	}
	
	private void renderStringIgnoringNewLine(String string, int gridSize, float x, float y, float characterWidth, float characterHeight)
	{
		renderStringIgnoringNewLine(string, gridSize, x, y, characterWidth, characterHeight, RGB.r, RGB.g, RGB.b);
	}
	
	protected void renderStringIgnoringNewLine(String string, int gridSize, float x, float y, float characterWidth, float characterHeight, float red, float green, float blue)
	{
		if (string == null) return;
		fontTexture.bind();
		
		glEnable(GL_BLEND);
		glColor3f(red, green, blue);
		
		glBegin(GL_QUADS);
		for (int i = 0, n = 0; i < string.length(); i++)
		{
			int asciiCode = string.charAt(i);
			if (asciiCode == '\n')
			{
				continue;// loop continues because there is no need to display \n
			}
			float cellSize = 1.0f / gridSize;
			float cellX = (asciiCode % gridSize) * cellSize;
			float cellY = (asciiCode / gridSize) * cellSize;
			
			glTexCoord2f(cellX, cellY + cellSize);
			glVertex2f(x + getCharacterX(n, characterWidth), y);
			glTexCoord2f(cellX + cellSize, cellY + cellSize);
			glVertex2f(x + getCharacterWidthPlusX(n, characterWidth), y);
			glTexCoord2f(cellX + cellSize, cellY);
			glVertex2f(x + getCharacterWidthPlusX(n, characterWidth), y + characterHeight);
			glTexCoord2f(cellX, cellY);
			glVertex2f(x + getCharacterX(n, characterWidth), y + characterHeight);
			
			n++;
		}
		glEnd();
		
		glDisable(GL_BLEND);
		glColor3f(1, 1, 1);
	}
	
	// ////////////////////////////////////////////
	// /rendering middle spaced
	// ////////////////////////////////////////////
	
	public void renderMiddleSpaced(String text, float x, float y)
	{
		renderStringMiddleSpaced(text, 16, x, y, getLetterWidth(), getLetterHeight());
	}
	
	public void renderMiddleSpaced(String text, float x, float y, float width, float height)
	{
		renderStringMiddleSpaced(text, 16, x, y, width, height);
	}
	
	public void renderMiddleSpaced(String text, float x, float y, float red, float green, float blue)
	{
		renderMiddleSpaced(text, 16, x, y, getLetterWidth(), getLetterHeight(), red, green, blue);
	}
	
	public void renderMiddleSpaced(String text, float x, float y, Color c)
	{
		renderMiddleSpaced(text, 16, x, y, getLetterWidth(), getLetterHeight(), c.r, c.g, c.b);
	}
	
	private void renderStringMiddleSpaced(String string, int gridSize, float x, float y, float characterWidth, float characterHeight)
	{
		renderMiddleSpaced(string, gridSize, x, y, characterWidth, characterHeight, RGB.r, RGB.g, RGB.b);
	}
	
	public void renderMiddleSpaced(String string, float x, float y, float characterWidth, float characterHeight, float red, float green, float blue)
	{
		
		renderString(string, 16, x - getCharacterWidthPlusX(string.length(), characterWidth) / 2, y - characterHeight / 2, characterWidth, characterHeight, red, green, blue);
		
	}
	
	public void renderMiddleSpaced(String string, float x, float y, float characterWidth, float characterHeight, Color c)
	{
		
		renderString(string, 16, x - getCharacterWidthPlusX(string.length(), characterWidth) / 2, y - characterHeight / 2, characterWidth, characterHeight, c.r, c.g, c.b);
		
	}
	
	public void renderMiddleSpaced(String string, int gridSize, float x, float y, float characterWidth, float characterHeight, float red, float green, float blue)
	{
		
		renderString(string, gridSize, x - getCharacterWidthPlusX(string.length(), characterWidth) / 2, y - characterHeight / 2, characterWidth, characterHeight, red, green, blue);
		
	}
	
	// ////////////////////////////////////////////
	// / rendering highlighted
	// ////////////////////////////////////////////
	
	public void renderHighlighted(String text, float x, float y, float maxWidth, int hightlightStart, int hightlightEnd)
	{
		renderHighlightedString(text, 16, x, y, getLetterWidth(), getLetterHeight(), maxWidth, hightlightStart, hightlightEnd);
	}
	
	public void renderHighlighted(String text, int hightlightStart, int hightlightEnd, float x, float y, float width, float height, float maxWidth)
	{
		renderHighlightedString(text, 16, x, y, width, height, maxWidth, hightlightStart, hightlightEnd);
	}
	
	public void renderHighlighted(String text, float x, float y, float red, float green, float blue, float maxWidth, int hightlightStart, int hightlightEnd)
	{
		renderHighlightedString(text, 16, x, y, getLetterWidth(), getLetterHeight(), red, green, blue, maxWidth, hightlightStart, hightlightEnd);
	}
	
	public void renderHighlighted(String text, float x, float y, float characterWidth, float characterHeight, float red, float green, float blue, float maxWidth, int hightlightStart, int hightlightEnd)
	{
		renderHighlightedString(text, 16, x, y, characterWidth, characterHeight, red, green, blue, maxWidth, hightlightStart, hightlightEnd);
	}
	
	private void renderHighlightedString(String string, int gridSize, float x, float y, float characterWidth, float characterHeight, float maxWidth, int hightlightStart, int hightlightEnd)
	{
		renderHighlightedString(string, gridSize, x, y, characterWidth, characterHeight, RGB.r, RGB.g, RGB.b, maxWidth, hightlightStart, hightlightEnd);
	}
	
	public void renderHighlighted(String text, float x, float y, int hightlightStart, int hightlightEnd)
	{
		renderHighlightedString(text, 16, x, y, getLetterWidth(), getLetterHeight(), hightlightStart, hightlightEnd);
	}
	
	public void renderHighlighted(String text, float x, float y, float width, float height, int hightlightStart, int hightlightEnd)
	{
		renderHighlightedString(text, 16, x, y, width, height, Window.getWidth() - x, hightlightStart, hightlightEnd);
	}
	
	public void renderHighlighted(String text, float x, float y, float red, float green, float blue, int hightlightStart, int hightlightEnd)
	{
		renderHighlightedString(text, 16, x, y, getLetterWidth(), getLetterHeight(), red, green, blue, Window.getWidth() - x, hightlightStart, hightlightEnd);
	}
	
	public void renderHighlighted(String text, float x, float y, float characterWidth, float characterHeight, float red, float green, float blue, int hightlightStart, int hightlightEnd)
	{
		renderHighlightedString(text, 16, x, y, characterWidth, characterHeight, red, green, blue, Window.getWidth() - x, hightlightStart, hightlightEnd);
	}
	
	private void renderHighlightedString(String string, int gridSize, float x, float y, float characterWidth, float characterHeight, int hightlightStart, int hightlightEnd)
	{
		renderHighlightedString(string, gridSize, x, y, characterWidth, characterHeight, RGB.r, RGB.g, RGB.b, Window.getWidth() - x, hightlightStart, hightlightEnd);
	}
	
	/**
	 * @param hightlightStart
	 *            - starts from index 0. Includes the char at specified index
	 * @param hightlightEnd
	 *            - starts from index 0.Includes the char at specified index
	 */
	protected void renderHighlightedString(String string, int gridSize, float x, float y, float characterWidth, float characterHeight, float red, float green, float blue, float maxWidth, int hightlightStart, int hightlightEnd)
	{
		// ATTENTION: this method doesn't render text. It only renders highlight of selected part of text
		if (string == null) return;
		if (hightlightStart > hightlightEnd)
		{
			int d = hightlightEnd;
			hightlightEnd = hightlightStart;
			hightlightStart = d;
		}
		boolean isHighlightEnabled = false;
		glEnable(GL_BLEND);
		glColor4f(0.5f, 0.5f, 0.5f, 0.5f);
		glDisable(GL_TEXTURE_2D);
		int n = 0, hightlightInThisLineStartIndex = 0;
		for (int i = 0; i < string.length(); i++, n++)
		{
			
			if (i == hightlightStart)
			{
				isHighlightEnabled = true;
				hightlightInThisLineStartIndex = n;
			}
			else if (i == hightlightEnd)
			{
				// last line is painted outside of loop
				break;
			}
			
			if (string.charAt(i) == '\n')
			{
				if (isHighlightEnabled) Graphics.renderRectangleNonColored(x + getCharacherRealHalfWidth() + getCharacterX(hightlightInThisLineStartIndex), y, getCharacterX(n - hightlightInThisLineStartIndex), characterHeight);
				y -= characterHeight;
				n = -1;
				hightlightInThisLineStartIndex = 0;
			}
			else if (getCharacterWidthPlusX(n, characterWidth) > maxWidth)
			{
				if (isHighlightEnabled) Graphics.renderRectangleNonColored(x + getCharacherRealHalfWidth() + getCharacterX(hightlightInThisLineStartIndex), y, getCharacterX(n - hightlightInThisLineStartIndex), characterHeight);
				y -= characterHeight;
				n = hightlightInThisLineStartIndex = 0;
			}
		}
		Graphics.renderRectangleNonColored(x + getCharacherRealHalfWidth() + getCharacterX(hightlightInThisLineStartIndex), y, getCharacterX(n - hightlightInThisLineStartIndex), characterHeight);
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glColor3f(1, 1, 1);
	}
}
