package net.swing.ground.GUI;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import net.swing.ground.BitmapFont;
import net.swing.ground.Graphics;
import net.swing.ground.Rectanglef;

public abstract class TextDisplayerAbstract extends Graphics
{
	// ////////////////////////////////////////////
	// / variables
	// ////////////////////////////////////////////
	private boolean			editable			= false;
	protected StringBuilder	text;
	protected BitmapFont	font;
	private boolean			decoration			= false;
	private boolean			textValueChanged	= false;
	private boolean			isEnabled			= true;
	/** caret is rendered before the character of the same index as caret */
	protected int			caretIndex			= -1;
	private int				selectionStartIndex	= -1;
	private int				selectionEndIndex	= -1;
	
	// ////////////////////////////////////////////
	// / constructors
	// ////////////////////////////////////////////
	
	public TextDisplayerAbstract(BitmapFont font, Rectanglef area)
	{
		super(area);
		this.font = new BitmapFont(font);// creating new object allows program to modify some values
		// only in this text displayer and the original font will stay the same
		this.text = new StringBuilder();
		
	}
	
	public TextDisplayerAbstract(BitmapFont font, Rectanglef area, boolean isEditable)
	{
		this(font, area);
		setEditable(isEditable);
	}
	
	public TextDisplayerAbstract(BitmapFont font, float x, float y, float width, float height)
	{
		super(x, y, width, height);
		this.font = new BitmapFont(font);// creating new object allows program to modify some values
		// only in this text displayer and the original font will stay the same
		this.text = new StringBuilder(text);
	}
	
	public TextDisplayerAbstract(BitmapFont font, float x, float y, float width, float height, boolean isEditable)
	{
		this(font, x, y, width, height);
		setEditable(isEditable);
	}
	
	public TextDisplayerAbstract(BitmapFont font, String text, Rectanglef area)
	{
		super(area);
		this.font = new BitmapFont(font);// creating new object allows program to modify some values
		// only in this text displayer and the original font will stay the same
		this.text = new StringBuilder(text);
	}
	
	public TextDisplayerAbstract(BitmapFont font, String text, Rectanglef area, boolean isEditable)
	{
		this(font, text, area);
		setEditable(isEditable);
	}
	
	public TextDisplayerAbstract(BitmapFont font, String text, float x, float y, float width, float height)
	{
		super(x, y, width, height);
		this.font = new BitmapFont(font);
		this.text = new StringBuilder(text);
	}
	
	public TextDisplayerAbstract(BitmapFont font, String text, float x, float y, float width, float height, boolean isEditable)
	{
		this(font, text, x, y, width, height);
		setEditable(isEditable);
	}
	
	// ////////////////////////////////////////////
	// / getters and setters
	// ////////////////////////////////////////////
	
	public boolean isEnabled()
	{
		return isEnabled;
	}
	
	public void setEnabled(boolean isEnabled)
	{
		this.isEnabled = isEnabled;
	}
	
	public BitmapFont getFont()
	{
		return font;
	}
	
	public void setText(String text)
	{
		this.text.replace(0, this.text.length(), text);
		setTextValueChangedToTrue();
	}
	
	public String getText()
	{
		return text.toString();
	}
	
	public void setFontColor(float r, float g, float b)
	{
		font.setColor(r, g, b);
	}
	
	public boolean isEditable()
	{
		return editable;
	}
	
	public final void setEditable(boolean editable)
	{
		this.editable = editable;
	}
	
	public void setFont(BitmapFont font)
	{
		this.font = font;
	}
	
	public boolean isDecoration()
	{
		return decoration;
	}
	
	public void setDecoration(boolean decoration)
	{
		this.decoration = decoration;
	}
	
	/**
	 * Returns is value of displayed
	 * text has changed since the last
	 * time this method was called.
	 */
	public boolean wasTextValueChanged()
	{
		/* if was changed */
		if (textValueChanged)
		{/* return true and reset value back to false */
			textValueChanged = false;
			return true;
		}
		/* otherwise return false and do nothing */
		return false;
	}
	
	protected void setTextValueChangedToTrue()
	{
		textValueChanged = true;
	}
	
	public int getCaretIndex()
	{
		return caretIndex;
	}
	
	public void setCaretIndex(int caretIndex)
	{
		this.caretIndex = caretIndex;
	}
	
	/**
	 * Even though it's called selection end it not necessarily has to be placed after selection start.
	 * Sometimes (when user moves mouse from latter text index to earlier index) end of
	 * selection will be nearer to index 0 than start selection index
	 */
	protected int getSelectionEndIndex()
	{
		return selectionEndIndex;
	}
	
	protected void setSelectionStartIndex(int selectionStartIndex)
	{
		this.selectionStartIndex = selectionStartIndex;
	}
	
	/**
	 * Even though it's called selection start it not necessarily has to be placed before selection end.
	 * Sometimes (when user moves mouse from latter text index to earlier index) end of
	 * selection will be nearer to index 0 than start selection index.
	 */
	protected int getSelectionStartIndex()
	{
		return selectionStartIndex;
	}
	
	protected void setSelectionEndIndex(int selectionEndIndex)
	{
		this.selectionEndIndex = selectionEndIndex;
	}
	
	public void moveCaretRigth()
	{
		this.caretIndex++;
	}
	
	public void moveCaretLeft()
	{
		this.caretIndex--;
	}
	
	/** Return false if part or whole text is selected */
	public boolean isTextSelectionEmpty()
	{
		return selectionEndIndex == selectionStartIndex;
	}
	
	// ////////////////////////////////////////////
	// / index correction methods
	// ////////////////////////////////////////////
	
	protected void correctEndIndexIfToBig()
	{
		if (getSelectionEndIndex() > text.length()) setSelectionEndIndex(text.length());
	}
	
	/**
	 * Sets index to 0 if it's index is negative.
	 * This method prevents caret from disappearing.
	 */
	protected void correctEndIndexIfToSmall()
	{
		if (getSelectionEndIndex() < 0) setSelectionEndIndex(0);
	}
	
	protected void correctCaretIfToBig()
	{
		if (getCaretIndex() > text.length()) setCaretIndex(text.length());
	}
	
	/**
	 * Sets caret to 0 if it's index is negative.
	 * This method prevents caret from disappearing.
	 */
	protected void correctCaretIfToSmall()
	{
		if (getCaretIndex() < 0) setCaretIndex(0);
	}
	
	// ////////////////////////////////////////////
	// / extra text editing methods
	// ////////////////////////////////////////////
	
	public void selectAll()
	{
		selectionStartIndex = 0;
		selectionEndIndex = text.length();
	}
	
	public void copySelectionToClipboard()
	{
		if (isTextSelectionEmpty()) return;
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(getSelectedText()), null);
	}
	
	public String getSelectedText()
	{
		if (isTextSelectionEmpty()) return "";
		if (selectionStartIndex > selectionEndIndex)
		{
			return text.substring(selectionEndIndex, selectionStartIndex);
		}
		else
		{
			return text.substring(selectionStartIndex, selectionEndIndex);
		}
	}
	
	public void cutSelectedText()
	{
		copySelectionToClipboard();
		eraseSelection();
	}
	
	public void pasteTextFromClipboard(int index)
	{
		try
		{
			text.insert(index, Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor).toString());
			setTextValueChangedToTrue();
		}
		catch (HeadlessException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedFlavorException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	protected void pasteTextFromClipboard()
	{
		if (isTextSelectionEmpty())
		{
			pasteTextFromClipboard(getCaretIndex());
		}
		else
		{
			if (selectionStartIndex > selectionEndIndex)
			{
				pasteTextFromClipboard(selectionEndIndex, selectionStartIndex);
			}
			else
			{
				pasteTextFromClipboard(selectionStartIndex, selectionEndIndex);
			}
		}
		
	}
	
	private void pasteTextFromClipboard(int selectionStartIndex, int selectionEndIndex)
	{
		try
		{
			text.replace(selectionStartIndex, selectionEndIndex, Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor).toString());
			setTextValueChangedToTrue();
		}
		catch (HeadlessException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedFlavorException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		setCaretIndex(selectionStartIndex);
		resetSelection();
		
	}
	
	public void eraseSelection()
	{
		if (isTextSelectionEmpty()) return;
		if (selectionStartIndex > selectionEndIndex)
		{
			text = text.delete(selectionEndIndex, selectionStartIndex);
			setTextValueChangedToTrue();
			setCaretIndex(selectionEndIndex);
			resetSelection();
		}
		else
		{
			text = text.delete(selectionStartIndex, selectionEndIndex);
			setTextValueChangedToTrue();
			setCaretIndex(selectionStartIndex);
			resetSelection();
		}
	}
	
	/** clears selection ( text stays the same) */
	public void resetSelection()
	{
		resetSelection(-1);
	}
	
	/** clears selection ( text stays the same) */
	public void resetSelection(int indexForBoth)
	{
		setSelectionEndIndex(indexForBoth);
		setSelectionStartIndex(indexForBoth);
	}
	
	// ////////////////////////////////////////////
	// / abstract methods
	// ////////////////////////////////////////////
	public abstract void update();
	
	public abstract void render();
	
}
