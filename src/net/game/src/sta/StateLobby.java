package net.game.src.sta;

import static org.lwjgl.opengl.GL11.*;

import javax.annotation.PreDestroy;

import net.LunarEngine2DMainClass;
import net.game.src.main.LunarEngine2D;
import net.swing.engine.graph.TexturesBase;
import net.swing.ground.Controls;
import net.swing.ground.Graphics;
import net.swing.ground.Window;
import net.swing.ground.GUI.BarUsable;
import net.swing.ground.GUI.ButtonSquare;
import net.swing.ground.GUI.GUIbar;
import net.swing.ground.GUI.ListVertical;
import net.swing.ground.GUI.State;
import net.swing.src.data.SaveOrigin;
import net.swing.src.data.SavePlayable;

public final class StateLobby extends State
{
	// TODO: add multiplayer
	private ButtonSquare			exit, select, /* multi, */options, remove, prompt_yes, prompt_no;
	private BarUsable				sideBar;
	private ListVertical<String>	maps;
	private boolean					isRemovingRequested	= false;
	private boolean					isExitRequested		= false;
	
	public static final int			DISPLAY_LIST_BACKGROUND;
	public final int				DISPLAY_LIST_BUTTONS_TOP;
	public static final int			DISPLAY_LIST_BUTTONS_BOTTOM;
	public static final int			DISPLAY_LIST_GAMES_LIST;
	public static final int			DISPLAY_LIST_EXTRA_BACKGROUND;
	
	static
	{
		
		DISPLAY_LIST_BACKGROUND = Graphics.generateRectangleNonColored(0, 0, Window.getWidth(), Window.getHeight());
		DISPLAY_LIST_GAMES_LIST = Graphics.generateRectangleNonColored(0, 0, Window.getWidth() * 0.2f, Window.getHeight());
		DISPLAY_LIST_BUTTONS_BOTTOM = Graphics.generateQuadNonColored(Window.getWidth() - i * 10, i, Window.getWidth(), i * 4);
		DISPLAY_LIST_EXTRA_BACKGROUND = Graphics.generateRectangleNonColored(0, i * 8, Window.getWidth(), i * 5);
	}
	
	public StateLobby()
	{
		sideBar = new BarUsable(Window.getWidth() * 0.2f, 0, Window.getWidth() * 0.02f, Window.getHeight(), 1, 1, 1, 1, 0, 0, TexturesBase.BAR_WHITE, GUIbar.ORIENTATION_VERTICAL);
		sideBar.setMode(BarUsable.MODE_ZIP);
		
		DISPLAY_LIST_BUTTONS_TOP = Graphics.generateRectangleNonColored(sideBar.getAbsoluteWidth(), Window.getHeight() * 0.7f, Window.getWidth() - sideBar.getAbsoluteWidth(), Window.getHeight() * 0.2f);
		
		select = new ButtonSquare(sideBar.getAbsoluteWidth(), Window.getHeight() * 0.7f + j / 2, j * 2, j / 2, "select");
		remove = new ButtonSquare(Window.getWidth() - i * 9, i * 1.5f, i * 2, i * 2, TexturesBase.REMOVE);
		options = new ButtonSquare(Window.getWidth() - i * 6, i * 1.5f, i * 2, i * 2, TexturesBase.SETTINGS);
		exit = new ButtonSquare(Window.getWidth() - i * 3, i * 1.5f, i * 2, i * 2, TexturesBase.EXIT);
		exit.setColor(1, 0, 0);
		remove.setColor(1, 0, 0);
		
		prompt_yes = new ButtonSquare(j * 2.5f, i * 9, j * 2, i * 2, "YES");
		prompt_no = new ButtonSquare(j * 5.5f, i * 9, j * 2, i * 2, "NO");
		
		maps = new ListVertical<String>(0, 0, Window.getWidth() * 0.2f, Window.getHeight(), 10);
		maps.setList(SaveOrigin.getSavesNames());
		maps.setSelectionColor(1, 0, 0);
		
	}
	
	@Override
	public void showState()
	{
		renderBackground();
		checkButtons();
		renderText();
	}
	
	private void renderText()
	{
		Window.textDisplayer.render(maps.getSelectedValue(), sideBar.getAbsoluteWidth(), Window.getHeight() * 0.7f);
	}
	
	private void renderBackground()
	{
		renderTexturedBackground();
		
		glDisable(GL_TEXTURE_2D);
		renderNonTexturedBackground();
		glEnable(GL_TEXTURE_2D);
	}
	
	private void renderTexturedBackground()
	{
		glColor4f(1, 1, 1, 1);
		TexturesBase.LOGO.texture.bind();
		glCallList(DISPLAY_LIST_BACKGROUND);
	}
	
	private void renderNonTexturedBackground()
	{
		renderNonTransparentBackground();
		
		glEnable(GL_BLEND);
		renderTransparentBackground();
		glDisable(GL_BLEND);
	}
	
	private void renderNonTransparentBackground()
	{
		glColor4f(0, 0.3f, 0.5f, 1);
		glCallList(DISPLAY_LIST_GAMES_LIST);
	}
	
	private void renderTransparentBackground()
	{
		glColor4f(0, 0.3f, 0.5f, 0.8f);
		glCallList(DISPLAY_LIST_BUTTONS_TOP);
		glCallList(DISPLAY_LIST_BUTTONS_BOTTOM);
	}
	
	private void checkButtons()
	{
		
		if (isExitRequested)
		{
			render();
			
			if (showPrompt("Are you sure you want to leave?"))
			{
				LunarEngine2DMainClass.shutdown();
			}
		}
		else if (isRemovingRequested)
		{
			/*
			 * list of maps and some buttons cannot be checked while removing
			 * prompt is active.
			 */
			render();
			String selectedOriginName = maps.getSelectedValue();
			if (showPrompt("Are you sure you want to delete " + selectedOriginName + "?"))
			{
				SaveOrigin originToDelete = new SaveOrigin(selectedOriginName);
				originToDelete.deleteWholeSaveHard();
				//delete All Playable Saves Referenced To This Origin 
				for (SavePlayable s : SavePlayable.getSavePlayables(selectedOriginName))
				{
					if(!s.deleteWholeSaveHard()){
						LunarEngine2DMainClass.getScreen().setErrorStateAndBlock("Could not remove "+s.getPath());
						break;
					}
				}
				maps.clearSelection();
				maps.setList(SaveOrigin.getSavesNames());
			}
			
		}
		else
		{
			update();
		}
	}
	
	/** Will return true if user click "YES". If user click "NO" or "YES" prompt will close */
	private boolean showPrompt(String text)
	{
		
		renderPromptBackground();
		
		Window.textDisplayer.renderMiddleSpaced(text, Window.getWidth() / 2, i * 12, i * 1.5f, i);
		
		if (prompt_no.checkButton())
		{
			isRemovingRequested = false;
			isExitRequested = false;
		}
		if (prompt_yes.checkButton())
		{
			isRemovingRequested = false;
			isExitRequested = false;
			return true;
		}
		return false;
	}
	
	private void renderPromptBackground()
	{
		glColor4f(0.3f, 0.3f, 0.3f, 0.8f);
		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glCallList(DISPLAY_LIST_EXTRA_BACKGROUND);
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
	}
	
	private void update()
	{
		if (exit.checkButton())
		{
			isExitRequested = true;
		}
		if (select.checkButton())
		{
			if (maps.isSelected())
			{
				LunarEngine2D.screen.singleplayer.refreshSaves(maps.getSelectedValue());
				LunarEngine2DMainClass.getScreen().setState(Screen.STATE_SINGLEPLAYER);
			}
		}
		if (options.checkButton())
		{
			LunarEngine2DMainClass.getScreen().setState(Screen.STATE_OPTIONS);
		}
		if (remove.checkButton())
		{
			if (maps.getSelectedValue() != null) isRemovingRequested = true;
		}
		
		Controls.checkMouseWheel();
		
		sideBar.updateTextured();
		
		if (sideBar.isBarInUse())
		{
			maps.setLevel((int) Math.ceil(Controls.mouseY / sideBar.getHeight() * maps.getQuantityOflevels()));
		}
		else
		{
			maps.checkArrowKeys();
		}
		
		maps.checkList();
	}
	
	private void render()
	{
		exit.render();
		select.render();
		options.render();
		remove.render();
		sideBar.renderTextured();
		maps.render();
	}
	
	@PreDestroy
	@Override
	public void cleanUp()
	{
		exit.removeButton();
		select.removeButton();
		options.removeButton();
	}
	
}
