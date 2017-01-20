package net.swing.ground.GUI;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;

import net.swing.ground.BitmapFont;
import net.swing.ground.Controls;
import net.swing.ground.Rectanglef;

public final class TextField extends TextDisplayerAbstract
{
	
	// ////////////////////////////////////////////
	// / variables
	// ////////////////////////////////////////////
	
	private int	maxCharNumber	= -1;
	
	// ////////////////////////////////////////////
	// / constructors
	// ////////////////////////////////////////////
	
	public TextField(BitmapFont font, float x, float y, float width, float height, boolean isEditable)
	{
		super(font, x, y, width, height, isEditable);
	}
	
	public TextField(BitmapFont font, float x, float y, float width, float height)
	{
		super(font, x, y, width, height);
		
	}
	
	public TextField(BitmapFont font, Rectanglef area, boolean isEditable)
	{
		super(font, area, isEditable);
		
	}
	
	public TextField(BitmapFont font, Rectanglef area)
	{
		super(font, area);
		
	}
	
	public TextField(BitmapFont font, String text, float x, float y, float width, float height, boolean isEditable)
	{
		super(font, text, x, y, width, height, isEditable);
		
	}
	
	public TextField(BitmapFont font, String text, float x, float y, float width, float height)
	{
		super(font, text, x, y, width, height);
		
	}
	
	public TextField(BitmapFont font, String text, Rectanglef area, boolean isEditable)
	{
		super(font, text, area, isEditable);
		
	}
	
	public TextField(BitmapFont font, String text, Rectanglef area)
	{
		super(font, text, area);
	}
	
	// ////////////////////////////////////////////
	// / getters and setters
	// ////////////////////////////////////////////
	public int getMaxCharNumber()
	{
		return maxCharNumber;
	}
	
	/** unlimited if lower than 1 */
	public void setMaxCharNumber(int maxCharNumber)
	{
		this.maxCharNumber = maxCharNumber;
	}
	
	// ////////////////////////////////////////////
	// / extra text editing methods
	// ////////////////////////////////////////////
	
	private void cutTextIfTooLong()
	{
		if (text.length() > getMaxCharNumber())
		{
			text.delete(getMaxCharNumber(), text.length() - 1);
		}
	}
	
	@Override
	public void pasteTextFromClipboard(int index)
	{
		super.pasteTextFromClipboard(index);
		cutTextIfTooLong();
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
		if (isEnabled())
		{
			if (Controls.isMouse0clicked)
			{
				if (isPointInside(Controls.mouseX, Controls.mouseY))
				{
					setEditable(true);
					setCaretIndex(font.getCharacterIndex(Controls.mouseX, getX()));
					correctCaretIfToBig();
					resetSelection(caretIndex);
				}
				else
				{
					setEditable(false);
					setCaretIndex(-1);
				}
			}
			else if (Controls.isMouse0pressed)
			{
				/* Caret location stays the same when user selects text */
				setSelectionEndIndex(font.getCharacterIndex(Controls.mouseX, getX()));
				correctEndIndexIfToSmall();
				correctEndIndexIfToBig();
			}
			if (isEditable())
			{
				updateEditing();
			}
			render();
		}
		else
		{
			renderDecorAndFont();
			// caret is not rendered
		}
		
	}
	
	/** checks if user is typing */
	private void updateEditing()
	{
		/* ignoring characters that cannot be displayed (enter,backspace etc.) */
		if (Character.getType(Controls.typedChar) != Character.CONTROL)
		{
			if (isTextSelectionEmpty())
			{
				/* Checking if text length hasn't reached limit yet */
				if (text.length() < maxCharNumber || maxCharNumber < 1)
				{
					text.insert(getCaretIndex(), Controls.typedChar);
					moveCaretRigth();
				}
			}
			else
			{
				if (getSelectionStartIndex() > getSelectionEndIndex())
				{
					setCaretIndex(getSelectionEndIndex() + 1);
					text.replace(getSelectionEndIndex(), getSelectionStartIndex(), "" + Controls.typedChar);
				}
				else
				{
					setCaretIndex(getSelectionStartIndex() + 1);
					text.replace(getSelectionStartIndex(), getSelectionEndIndex(), "" + Controls.typedChar);
				}
				resetSelection();
			}
			setTextValueChangedToTrue();
		}
		else
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
			{
				switch (Controls.typedKey)
				{
					case Keyboard.KEY_X:
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
			}
			else
			{
				switch (Controls.typedKey)
				{
					case Keyboard.KEY_BACK:
						if (getSelectionEndIndex() != getSelectionStartIndex())
						{
							eraseSelection();
							resetSelection();
						}
						else if (getCaretIndex() > 0)
						{
							text.deleteCharAt(getCaretIndex() - 1);
							moveCaretLeft();
							setTextValueChangedToTrue();
						}
						break;
					case Keyboard.KEY_DELETE:
						if (getSelectionEndIndex() != getSelectionStartIndex())
						{
							eraseSelection();
							resetSelection();
						}
						else if (text.length() > 0 && getCaretIndex()<text.length())
						{
							text.deleteCharAt(getCaretIndex());
						}
						break;
					case Keyboard.KEY_LEFT:
						moveCaretLeft();
						correctCaretIfToSmall();
						resetSelection();
						break;
					case Keyboard.KEY_RIGHT:
						moveCaretRigth();
						correctCaretIfToBig();
						resetSelection();
						break;
				}
			}
		}
	}
	
	// ////////////////////////////////////////////
	// / rendering
	// ////////////////////////////////////////////
	@Override
	public void render()
	{
		renderDecorAndFont();
		if (getCaretIndex() > -1)
		{
			if (getSelectionStartIndex() == getSelectionEndIndex())
			{
				renderCaret(getCaretIndex());
			}
			else
			{
				renderSelection();
			}
		}
	}
	
	private void renderDecorAndFont()
	{
		if (isDecoration()) renderDecor();
		font.renderIgnoringNewLine(text.toString(), getX(), getAbsoluteHeight()-font.getLetterHeight());
	}
	
	private void renderSelection()
	{
		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glColor4f(0.5f, 0.5f, 0.5f, 0.5f);
		renderRectangleNonColored(getX() + font.getCharacherRealHalfWidth() + font.getCharacterX(getSelectionStartIndex()), getY(), font.getCharacterX(getSelectionEndIndex() - getSelectionStartIndex()), getHeight());
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
	}
	
	private void renderCaret(int caret)
	{
		
		renderVerticalLine(getX() + font.getCharacherRealHalfWidth() + font.getCharacterX(caret),getAbsoluteHeight()-font.getLetterHeight(), getAbsoluteHeight(), 2, 0, 0, 0);
	}
	
	private void renderDecor()
	{
		glDisable(GL_TEXTURE_2D);
		renderRectangleNoTexture();
		glEnable(GL_TEXTURE_2D);
	}
	
}
