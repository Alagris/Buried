package net.game.src.main;

import javax.annotation.PreDestroy;

import net.swing.ground.BitmapFont;
import net.swing.ground.Graphics;
import net.swing.ground.Window;
import net.swing.ground.GUI.ButtonSquare;

public class TopInventory extends Graphics
{
	private final ButtonSquare	reImport, refreshProps, pause, back;
	private final BitmapFont	font;
	
	public TopInventory(float x, float y, float width, float height)
	{
		super(x, y, width, height);
		
		float buttonWidth = getWidth() / /* quantity of buttons = */4;
		refreshProps = new ButtonSquare(getX(), getY(), buttonWidth, getHeight(), "refresh");
		reImport = new ButtonSquare(getX() + buttonWidth, getY(), buttonWidth, getHeight(), "reload");
		pause = new ButtonSquare(getX() + buttonWidth * 2, getY(), buttonWidth, getHeight(), "pause");
		back = new ButtonSquare(getX() + buttonWidth * 3, getY(), buttonWidth, getHeight(), "exit");
		
		font = new BitmapFont(Window.textDisplayer);
		
		refreshProps.setTextDisplayer(font);
		reImport.setTextDisplayer(font);
		pause.setTextDisplayer(font);
		back.setTextDisplayer(font);
	}
	
	public void update()
	{
		if (pause.checkButton())
		{
			LunarEngine2D.screen.game.switchGame(!LunarEngine2D.screen.game.isAction());
		}
		if (back.checkButton())
		{
			LunarEngine2D.screen.game.exit();
		}
		if (reImport.checkButton())
		{
			LunarEngine2D.screen.game.view.importDataToThisRoom();
		}
		if (refreshProps.checkButton())
		{
			LunarEngine2D.screen.game.view.refreshProps();
		}
	}
	
	/** Only renders buttons and doesn't check if they are clicked */
	public void render()
	{
		pause.render();
		back.render();
		refreshProps.render();
		reImport.render();
	}
	
	@PreDestroy
	public void cleanUp()
	{
		pause.removeButton();
		back.removeButton();
		refreshProps.removeButton();
		reImport.removeButton();
	}
}
