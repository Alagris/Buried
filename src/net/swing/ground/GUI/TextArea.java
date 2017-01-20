package net.swing.ground.GUI;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import net.swing.ground.BitmapFont;
import net.swing.ground.Controls;
import net.swing.ground.Rectanglef;

public class TextArea extends TextDisplayerAbstract
{
	// TODO: simplify this class as much as possible
	
	// ////////////////////////////////////////////
	// / variables
	// ////////////////////////////////////////////
	private int					caretPositionX		= -1;
	private int					caretPositionY		= -1;
	private int					lettersInOneLine	= -1;
	/**
	 * Contains information of number of characters in each line
	 * and which of them contain line separator \n (which is
	 * placed at the end of line containing it)
	 * Lines with \n have the same number value but negative sign.
	 * This way allows program to use only one array instead of two
	 * for the same information (chars and separators) but stored separately.
	 */
	private ArrayList<Integer>	listOfLines			= new ArrayList<Integer>();
	
	// ////////////////////////////////////////////
	// / constructors
	// ////////////////////////////////////////////
	public TextArea(BitmapFont font, float x, float y, float width, float height, boolean isEditable)
	{
		super(font, x, y, width, height, isEditable);
		setLettersInOneLine(font.getCharacterIndex(getWidth() - font.getCharacherRealWidth(), 0));
	}
	
	public TextArea(BitmapFont font, float x, float y, float width, float height)
	{
		super(font, x, y, width, height);
		setLettersInOneLine(font.getCharacterIndex(getWidth() - font.getCharacherRealWidth(), 0));
	}
	
	public TextArea(BitmapFont font, Rectanglef area, boolean isEditable)
	{
		super(font, area, isEditable);
		setLettersInOneLine(font.getCharacterIndex(getWidth() - font.getCharacherRealWidth(), 0));
	}
	
	public TextArea(BitmapFont font, Rectanglef area)
	{
		super(font, area);
		setLettersInOneLine(font.getCharacterIndex(getWidth() - font.getCharacherRealWidth(), 0));
	}
	
	public TextArea(BitmapFont font, String text, float x, float y, float width, float height, boolean isEditable)
	{
		super(font, text, x, y, width, height, isEditable);
		setLettersInOneLine(font.getCharacterIndex(getWidth() - font.getCharacherRealWidth(), 0));
	}
	
	public TextArea(BitmapFont font, String text, float x, float y, float width, float height)
	{
		super(font, text, x, y, width, height);
		setLettersInOneLine(font.getCharacterIndex(getWidth() - font.getCharacherRealWidth(), 0));
	}
	
	public TextArea(BitmapFont font, String text, Rectanglef area, boolean isEditable)
	{
		super(font, text, area, isEditable);
		setLettersInOneLine(font.getCharacterIndex(getWidth() - font.getCharacherRealWidth(), 0));
	}
	
	public TextArea(BitmapFont font, String text, Rectanglef area)
	{
		super(font, text, area);
		setLettersInOneLine(font.getCharacterIndex(getWidth() - font.getCharacherRealWidth(), 0));
	}
	
	// ////////////////////////////////////////////
	// / getters and setters of variables
	// ////////////////////////////////////////////
	
	public ArrayList<Integer> getListOfLines()
	{
		return listOfLines;
	}
	
	@Override
	public void setWidth(float width)
	{
		super.setWidth(width);
		setLettersInOneLine(font.getCharacterIndex(getWidth() - font.getCharacherRealWidth(), 0));
	}
	
	public int getLettersInOneLine()
	{
		return lettersInOneLine;
	}
	
	private final void setLettersInOneLine(int lettersInOneLine)
	{
		this.lettersInOneLine = lettersInOneLine;
	}
	
	public int getCaretPositionY()
	{
		return caretPositionY;
	}
	
	public void setCaretPositionY(int caretPositionY)
	{
		this.caretPositionY = caretPositionY;
	}
	
	public int getCaretPositionX()
	{
		return caretPositionX;
	}
	
	public void setCaretPositionX(int caretPositionX)
	{
		this.caretPositionX = caretPositionX;
	}
	
	/**
	 * This method must be called right after text change if
	 * you want to move caret later
	 */
	@Override
	protected void setTextValueChangedToTrue()
	{
		super.setTextValueChangedToTrue();
		setListOfLines(text);
	}
	
	// ////////////////////////////////////////////
	// / getters and setters of characters and lines
	// ////////////////////////////////////////////
	
	public int getLinesNumber()
	{
		return listOfLines.size();
	}
	
	private void setListOfLines(StringBuilder stringBuilder)
	{
		/*
		 * In this method line index starts
		 * exceptionally from 0 instead of 1
		 */
		int line = 0, charsInThisLine = 1;
		for (int i = 0; i < stringBuilder.length();)
		{
			if (stringBuilder.charAt(i) == '\n')
			{
				addLineToList(line++, -charsInThisLine);
				charsInThisLine = 0;
			}
			else
			{
				if (i < stringBuilder.length() - 1 && stringBuilder.charAt(i + 1) == '\n')
				{
					addLineToList(line++, -charsInThisLine - 1);
					// add 1 so that we already count in this line \n which is next character
					charsInThisLine = 0;
					i++;// skip \n
				}
				else if (charsInThisLine >= getLettersInOneLine())
				{
					addLineToList(line++, charsInThisLine);
					charsInThisLine = 0;
				}
			}
			i++;
			charsInThisLine++;
		}
		charsInThisLine--;
		if (charsInThisLine > 0)
		{
			addLineToList(line++, charsInThisLine);
		}
		// if line separator is at the end of text
		if (stringBuilder.length() > 0 && stringBuilder.charAt(stringBuilder.length() - 1) == '\n')
		{
			// then add an extra empty line
			addLineToList(line++, 0);
		}
		if (listOfLines.size() > line)
		{
			listOfLines.subList(line, listOfLines.size()).clear();
		}
	}
	
	/**
	 * Checks if list is long enough to add
	 * integer at this index and resizes list when needed.
	 * ATTENTION: if index is negative it will throw exception
	 */
	private void addLineToList(int line, int charsInThisLine)
	{
		if (listOfLines.size() <= line)
		{
			listOfLines.add(charsInThisLine);
		}
		else
		{
			listOfLines.set(line, charsInThisLine);
		}
	}
	
	/**
	 * lines start from 1 (which is the line at the top).
	 * Returns number of all the chars that are visible to user
	 */
	public int getLettersInLine(int line)
	{
		return getLettersInLineIndex(line - 1);
	}
	
	/**
	 * line index starts from 0 (which is the line at the top).
	 * Returns number of all the chars that are visible to user
	 */
	public int getLettersInLineIndex(int index)
	{
		int i = listOfLines.get(index);
		return i < 0 ? -i - 1 : i;
	}
	
	/** lines start from 1 (which is the line at the top) */
	public int getCharsInLine(int line)
	{
		return getCharsInLineIndex(line - 1);
	}
	
	/** line index starts from 0 (which is the line at the top) */
	public int getCharsInLineIndex(int index)
	{
		return Math.abs(listOfLines.get(index));
	}
	
	// ////////////////////////////////////////////
	// / getters and setters of indexes and positions
	// ////////////////////////////////////////////
	
	/**
	 * Returns correct position - if position points to
	 * an existing in that place character that returns just the
	 * position. Otherwise it returns the greatest possible
	 * character position in that line
	 */
	private int correctCaretPositionX(int position, int line)
	{
		return Math.min(position, getLettersInLine(line));
	}
	
	public void setCaretIndexAndPositionByPoint(float x, float y)
	{
		if (listOfLines.isEmpty())
		{
			setCaretPositionY(1);
			setCaretPositionX(0);
			setCaretIndex(0);
		}
		else
		{
			// caret location is found
			int x1 = font.getCharacterIndex(x);
			int y1 = font.getSelectedLine(y, getHeight());
			y1 = y1 > listOfLines.size() ? listOfLines.size() : y1;
			x1 = correctCaretPositionX(x1, y1);
			// caret index is calculated
			setCaretIndex(getIndexByPosition(x1, y1));
			// caret location is set
			setCaretPositionY(y1);
			setCaretPositionX(x1);
		}
	}
	
	private int getIndexByPosition(int positionX, int positionY)
	{
		if (listOfLines.isEmpty()) return 0;
		int index = 0;
		
		/*
		 * positionY starts from 1 (first line is 1)
		 * listOfLines.size() also starts from 1 ( but first line is 0)
		 * and that's why it has to use statement
		 * ; i < positionY-1;
		 * instead of
		 * ; i < positionY;
		 */
		int i = 0;
		for (; i < positionY - 1 && i < listOfLines.size() - 1; i++)
		{
			index += getCharsInLineIndex(i);
		}
		/*
		 * loop ends on i = positionY-2
		 */
		i++;
		/*
		 * so it's time to check positionY-1
		 */
		return index + correctCaretPositionX(positionX, i);
	}
	
	@Override
	public void moveCaretLeft()
	{
		if (getCaretIndex() > 0)
		{
			
			if (getCaretPositionX() == 0)
			{
				setCaretPositionY(getCaretPositionY() - 1);
				setCaretPositionX(getLettersInLine(getCaretPositionY()));
			}
			else
			{
				super.moveCaretLeft();
				setCaretPositionX(getCaretPositionX() - 1);
			}
			
		}
	}
	
	@Override
	public void moveCaretRigth()
	{
		if (getCaretIndex() < text.length())
		{
			
			if (getCaretPositionX() == getLettersInLine(getCaretPositionY()))
			{
				setCaretPositionY(getCaretPositionY() + 1);
				setCaretPositionX(0);
			}
			else
			{
				super.moveCaretRigth();
				setCaretPositionX(getCaretPositionX() + 1);
			}
		}
	}
	
	/**
	 * If when user by moving caret one index forward reaches border of text
	 * the caret will jump to the next line but instead of being placed at
	 * first index in line it's going to be placed at second .
	 * Use this method when text is being modified and character before
	 * caret is being added.
	 */
	private void moveCaretRigth_repeatIfNewLine()
	{
		if (getCaretIndex() < text.length())
		{
			super.moveCaretRigth();
			if (getCaretPositionX() == getLettersInLine(getCaretPositionY()))
			{
				setCaretPositionY(getCaretPositionY() + 1);
				setCaretPositionX(1);
			}
			else
			{
				setCaretPositionX(getCaretPositionX() + 1);
			}
		}
	}
	
	/**
	 * If when user by moving caret one index backward reaches border of text
	 * the caret will jump to the previous line but instead of being placed at
	 * last index in line it's going to be placed at last but one .
	 * Use this method when text is being modified and character before
	 * caret is being removed.
	 */
	public void moveCaretLeft_repeatIfNewLine()
	{
		if (getCaretIndex() > 0)
		{
			super.moveCaretLeft();
			if (getCaretPositionX() == 0)
			{
				setCaretPositionY(getCaretPositionY() - 1);
				setCaretPositionX(getLettersInLine(getCaretPositionY()));
			}
			else
			{
				setCaretPositionX(getCaretPositionX() - 1);
			}
			
		}
	}
	
	public void moveCaretDown()
	{
		if (getCaretPositionY() < listOfLines.size())
		{
			setCaretPositionY(getCaretPositionY() + 1);
			setCaretPositionX(correctCaretPositionX(getCaretPositionX(), getCaretPositionY()));
			setCaretIndex(getIndexByPosition(getCaretPositionX(), getCaretPositionY()));
		}
	}
	
	public void moveCaretUp()
	{
		if (getCaretPositionY() > 1)
		{
			setCaretPositionY(getCaretPositionY() - 1);
			setCaretPositionX(correctCaretPositionX(getCaretPositionX(), getCaretPositionY()));
			setCaretIndex(getIndexByPosition(getCaretPositionX(), getCaretPositionY()));
		}
	}
	
	@Override
	public void eraseSelection()
	{
		if (isTextSelectionEmpty()) return;
		if (getSelectionStartIndex() > getSelectionEndIndex())
		{
			text = text.delete(getSelectionEndIndex(), getSelectionStartIndex());
			setCaretIndex(getSelectionEndIndex());
		}
		else
		{
			text = text.delete(getSelectionStartIndex(), getSelectionEndIndex());
			setCaretIndex(getSelectionStartIndex());
		}
		resetSelection();
		setTextValueChangedToTrue();
	}
	
	// ////////////////////////////////////////////
	// / updating
	// ////////////////////////////////////////////
	
	/**
	 * checks if mouse (button 0) clicked inside and automatically switches to
	 * editable. Then checks if user is typing and finally renders
	 */
	@Override
	public void update()
	{
		if (!isEnabled())
		{
			// if this component is not enabled then it couldn't be edited
			// and won't react to user input
			if (isDecoration()) renderDecor();
			font.render(text.toString(), getX(), getAbsoluteHeight() - font.getLetterHeight(), getWidth());
			return;
		}
		//
		//
		//
		// Checking mouse input to determine whether this component is currently being in use
		if (Controls.isMouse0clicked)
		{
			if (isPointInside(Controls.mouseX, Controls.mouseY))
			{
				// text area is focused so editing mode is enabled
				setEditable(true);
				setCaretIndexAndPositionByPoint(Controls.mouseX, Controls.mouseY);
				// selection is reset
				resetSelection(getCaretIndex());
			}
			else
			{
				setEditable(false);
				setCaretIndex(-1);
				// If caret is -1 then selection is not displayed anyway
				// so there is no need to reset it You shouldn't even worry
				// what will happen when textArea get focused again
				// (selection is reset when mouse clicks inside textArea)
			}
		}
		else if (Controls.isMouse0pressed)
		{
			/* Caret location stays the same when user selects text */
			setSelectionEndIndex(getIndexByPosition(font.getCharacterIndex(Controls.mouseX), font.getSelectedLine(Controls.mouseY, getHeight())));
		}
		//
		//
		//
		// if component is editable then the whole logic of text area is executed
		if (isEditable())
		{
			/* ignoring characters that cannot be displayed (enter,backspace etc.) */
			if (Character.getType(Controls.typedChar) != Character.CONTROL)
			{
				if (isTextSelectionEmpty())
				{// just insert char when there is no selection
					text.insert(getCaretIndex(), Controls.typedChar);
					setTextValueChangedToTrue();
					moveCaretRigth_repeatIfNewLine();
				}
				else
				{// if there is selection check which is first: start or end
					if (getSelectionStartIndex() < getSelectionEndIndex())
					{
						// and the replace selection with typed character
						setCaretIndex(getSelectionStartIndex());
						moveCaretRigth_repeatIfNewLine();
						text.replace(getSelectionStartIndex(), getSelectionEndIndex(), "" + Controls.typedChar);
					}
					else
					{
						// and the replace selection with typed character
						setCaretIndex(getSelectionEndIndex());
						moveCaretRigth_repeatIfNewLine();
						text.replace(getSelectionEndIndex(), getSelectionStartIndex(), "" + Controls.typedChar);
					}
					// typing any key resets selection
					setTextValueChangedToTrue();
					resetSelection();
				}
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
			{
				// Checking text editing functions
				// triggered by pressing CTRL+typedKey
				// ==========================
				switch (Controls.typedKey)
				{
					case Keyboard.KEY_X:
						/*
						 * caret will be placed at the same
						 * index and position as one of selection ends (depending on whether start of selection
						 * has index nearer to zero or end of selection has index nearer to zero)
						 */
						if (getSelectionStartIndex() == getSelectionEndIndex()) return;
						setCaretIndex(getSelectionStartIndex() < getSelectionEndIndex() ? getSelectionStartIndex() : getSelectionEndIndex());
						cutSelectedText();
						break;
					case Keyboard.KEY_V:
						pasteTextFromClipboard();
						break;
					case Keyboard.KEY_A:
						selectAll();
						break;
					case Keyboard.KEY_C:
						copySelectionToClipboard();
						break;
				}
				// ==============================
			}
			else
			{
				// Checking text editing and caret navigation functions
				// triggered by pressing any key without holding CTRL
				// ==============================
				switch (Controls.typedKey)
				{
					case Keyboard.KEY_RETURN:
						
						if (getSelectionEndIndex() != getSelectionStartIndex())
						{
							eraseSelection();
						}
						text.insert(getCaretIndex(), '\n');
						setTextValueChangedToTrue();
						setCaretPositionX(0);
						moveCaretDown();
						break;
					case Keyboard.KEY_BACK:
						if (getSelectionEndIndex() != getSelectionStartIndex())
						{
							/*
							 * caret will be placed at the same
							 * index and position as one of selection ends (depending on whether start of selection
							 * has index nearer to zero or end of selection has index nearer to zero)
							 */
							setCaretIndex(getSelectionStartIndex() < getSelectionEndIndex() ? getSelectionStartIndex() : getSelectionEndIndex());
							eraseSelection();
							setTextValueChangedToTrue();
						}
						else if (getCaretIndex() > 0)
						{
							moveCaretLeft_repeatIfNewLine();
							text.deleteCharAt(getCaretIndex());
							setTextValueChangedToTrue();
						}
						break;
					case Keyboard.KEY_DELETE:
						if (getSelectionEndIndex() != getSelectionStartIndex())
						{
							/*
							 * caret will be placed at the same
							 * index and position as one of selection ends (depending on whether start of selection
							 * has index nearer to zero or end of selection has index nearer to zero)
							 */
							setCaretIndex(getSelectionStartIndex() < getSelectionEndIndex() ? getSelectionStartIndex() : getSelectionEndIndex());
							eraseSelection();
							setTextValueChangedToTrue();
						}
						else if (text.length() > 0 && getCaretIndex() < text.length())
						{
							text.deleteCharAt(getCaretIndex());
							setTextValueChangedToTrue();
						}
						break;
					case Keyboard.KEY_LEFT:
						moveCaretLeft();
						resetSelection();
						break;
					case Keyboard.KEY_RIGHT:
						moveCaretRigth();
						resetSelection();
						break;
					case Keyboard.KEY_UP:
						moveCaretUp();
						resetSelection();
						break;
					case Keyboard.KEY_DOWN:
						moveCaretDown();
						resetSelection();
						break;
				}
				// ==============================
			}
		}
		render();
		
	}
	
	// ////////////////////////////////////////////
	// / rendering
	// ////////////////////////////////////////////
	@Override
	public void render()
	{
		if (isDecoration()) renderDecor();
		font.render(text.toString(), getX(), getAbsoluteHeight() - font.getLetterHeight(), getWidth());
		if (getCaretIndex() > -1)
		{
			if (getSelectionStartIndex() == getSelectionEndIndex())
			{
				// renders caret
				float y = getAbsoluteHeight() - font.getLetterHeight() * getCaretPositionY();
				renderVerticalLine(getX() + font.getCharacherRealHalfWidth() + font.getCharacterX(getCaretPositionX()), y, y + font.getLetterHeight(), 2, 0, 0, 0);
				
			}
			else
			{
				// renders selection
				font.renderHighlighted(text.toString(), getX(), getAbsoluteHeight() - font.getLetterHeight(), getWidth(), getSelectionStartIndex(), getSelectionEndIndex());
			}
		}
	}
	
	private void renderDecor()
	{
		glDisable(GL_TEXTURE_2D);
		renderRectangleNoTexture();
		glEnable(GL_TEXTURE_2D);
	}
	
}
