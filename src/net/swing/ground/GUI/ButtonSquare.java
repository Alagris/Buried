package net.swing.ground.GUI;

import static org.lwjgl.opengl.GL11.*;

import javax.annotation.PreDestroy;

import net.swing.engine.graph.TexturesBase;
import net.swing.ground.BitmapFont;
import net.swing.ground.Controls;

public class ButtonSquare extends Button
{
	
	private int				list	= glGenLists(1);
	private TexturesBase	tex		= TexturesBase.BUTTON_DARK;
	
	public ButtonSquare(float x, float y, float width, float height, String text)
	{
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		setText(text);
		createList();
		
	}
	
	public ButtonSquare(float x, float y, float width, float height, TexturesBase id)
	{
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		tex = id;
		setText(null);
		createList();
	}
	
	private void createList()
	{
		glNewList(list, GL_COMPILE);
		glBegin(GL_QUADS);
		
		glTexCoord2f(0, 1);
		glVertex2f(getX(), getY());
		
		glTexCoord2f(0, 0);
		glVertex2f(getX(), getAbsoluteHeight());
		
		glTexCoord2f(1, 0);
		glVertex2f(getAbsoluteWidth(), getAbsoluteHeight());
		
		glTexCoord2f(1, 1);
		glVertex2f(getAbsoluteWidth(), getY());
		
		glEnd();
		glEndList();
	}
	
	public void setText(String text)
	{
		name = text;
		if (name == null) return;
		textX = getX() + width / 2 - textDisplayer.getCharacterWidthPlusX(name.length()) / 2;
		textY = getY() + height / 2 - textDisplayer.getLetterHeight() / 2;
		if (textX < getX())
		{
			textX = getX();
		}
		if (textY < getY())
		{
			textY = getY();
		}
	}
	
	@Override
	public void render()
	{
		bindColor();
		renderButton();
	}
	
	@Override
	public void renderClick()
	{
		glColor3f(0.4f, 0.7f, 1f);
		renderButton();
	}
	
	@Override
	public void onClick()
	{
		renderClick();
		System.out.println("Button clicked: " + name);
	}
	
	@Override
	public void renderMouseEnter()
	{
		glColor3f(0.4f, 1f, 1f);
		renderButton();
	}
	
	private void renderButton()
	{
		tex.getTexture().bind();
		glCallList(list);
		textDisplayer.render(name, textX, textY);
	}
	
	@Override
	@PreDestroy
	public void removeButton()
	{
		glDeleteLists(list, 1);
	}
	
	public boolean checkButton()
	{
		return super.checkButton(Controls.mouseX, Controls.mouseY, Controls.isMouse0clicked);
	}
	
	public BitmapFont getTextDisplayer()
	{
		return textDisplayer;
	}
	
	public void setTextDisplayer(BitmapFont textDisplayer)
	{
		this.textDisplayer = textDisplayer;
		setText(name);
	}
	
	public String getText()
	{
		return name;
	}
	
}
