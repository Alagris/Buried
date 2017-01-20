package net.game.src.sta;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import net.LunarEngine2DMainClass;
import net.game.src.main.LunarEngine2D;
import net.swing.ground.BitmapFont;
import net.swing.ground.Controls;
import net.swing.ground.Graphics;
import net.swing.ground.Window;
import net.swing.ground.GUI.ButtonSquare;
import net.swing.ground.GUI.ListVertical;
import net.swing.ground.GUI.State;
import net.swing.ground.GUI.TextField;
import net.swing.src.data.Files;
import net.swing.src.data.SavePlayable;

public class StateSingleplayer extends State
{
	
	private ListVertical<String>	saveNumber;
	private ButtonSquare			up, down, back, join, newWorld, delete, createNew, cancelNew;
	private boolean					isNewSaveBeingCreated			= false;
	/**
	 * indicates whether name of new world is valid and world is ready to be
	 * created
	 */
	private boolean					ready							= false;
	private TextField				newWorldName;
	public static final int			DISPLAY_LIST_EXTRA_BACKGROUND	= Graphics.generateRectangleNonColored(0, i * 7, Window.getWidth(), i * 6);
	/**
	 * Indicates which map all the saves are related to and when new saves are
	 * created they will automatically be referenced to that map as it's origin
	 */
	private String					mapName;
	
	public StateSingleplayer()
	{
		down = new ButtonSquare(j * 9, i * 2, j, i * 5, "V");
		up = new ButtonSquare(j * 9, i * 7, j, i * 5, "^");
		delete = new ButtonSquare(j, i * 15, j * 3, i * 4, "delete");
		back = new ButtonSquare(j, 0, j * 3, i * 4, "back");
		join = new ButtonSquare(j, i * 5, j * 3, i * 4, "join");
		newWorld = new ButtonSquare(j, i * 10, j * 3, i * 4, "new");
		saveNumber = new ListVertical<String>(j * 6, i * 2, j * 3, i * 10, 7, SavePlayable.getSavesNames());
		
		createNew = new ButtonSquare(j * 2.5f, i * 8, j * 2, i, "Create");
		cancelNew = new ButtonSquare(j * 5.5f, i * 8, j * 2, i, "Cancel");
		
		createNew.setTextDisplayer(new BitmapFont(Window.textDisplayer, i * 1.5f, i));
		cancelNew.setTextDisplayer(createNew.getTextDisplayer());
		
		newWorldName = new TextField(Window.textDisplayer, "new world", j, i * 9.5f, Window.getWidth() - j * 2, i * 2);
		onTextChange();
		newWorldName.setColor(0, 0.3f, 0.5f);
		newWorldName.setDecoration(true);
		newWorldName.setMaxCharNumber(20);
	}
	
	private void onTextChange()
	{
		onTextChange(newWorldName.getText());
	}
	
	private void onTextChange(String newText)
	{
		if (Files.validateName_allowSpaces(newText))
		{
			if (ready = !new SavePlayable(newText).exists())
			{
				createNew.setColor(1, 1, 1);
				return;
			}
		}
		createNew.setColor(1, 0, 0);
		ready = false;
	}
	
	public void refreshSaves()
	{
		saveNumber.setList(SavePlayable.getSavesNames(getMapName()));
	}
	
	public void refreshSaves(String originFilter)
	{
		saveNumber.setList(SavePlayable.getSavesNames(originFilter));
		setMapName(originFilter);
	}
	
	@Override
	public void showState()
	{
		
		if (isNewSaveBeingCreated)
		{
			down.render();
			up.render();
			newWorld.render();
			back.render();
			join.render();
			delete.render();
			saveNumber.render();
			
			glColor4f(0.3f, 0.3f, 0.3f, 0.8f);
			glEnable(GL_BLEND);
			glDisable(GL_TEXTURE_2D);
			glCallList(StateLobby.DISPLAY_LIST_EXTRA_BACKGROUND);
			glEnable(GL_TEXTURE_2D);
			glDisable(GL_BLEND);
			
			Window.textDisplayer.renderMiddleSpaced("Choose name of the new save", Window.getWidth() / 2, i * 12, i * 1.5f, i);
			
			if (cancelNew.checkButton())
			{
				closePrompt();
			}
			
			if (ready)
			{
				if (createNew.checkButton())
				{
					/* If coping save and loading it finish correcty.. */
					if (LunarEngine2D.screen.game.view.createSaveAndLoad(newWorldName.getText(), getMapName()))
					{
						/* ..music will stop.. */
						LunarEngine2DMainClass.getAudioManager().getMusicStore().stopMusic();
						/* ..and state will be set as game */
						LunarEngine2DMainClass.getScreen().setState(Screen.STATE_GAME);
						/* And of course prompt has to be closed */
						closePrompt();
					}
					else
					{
						LunarEngine2DMainClass.getScreen().setErrorStateAndBlock("Something went wrong while creating new save!");
					}
				}
			}
			else
			{
				createNew.render();
				Window.textDisplayer.render("name not valid", j * 7.5f, i * 8, i, i, 1, 0, 0);
			}
			newWorldName.update();
			if (newWorldName.wasTextValueChanged()) onTextChange();
		}
		else
		{
			if (down.checkButton())
			{
				saveNumber.goDown();
			}
			
			if (up.checkButton())
			{
				saveNumber.goUp();
			}
			
			if (newWorld.checkButton())
			{
				isNewSaveBeingCreated = true;
			}
			
			if (back.checkButton())
			{
				LunarEngine2DMainClass.getScreen().setState(Screen.STATE_LOBBY);
			}
			
			if (join.checkButton())
			{
				if (saveNumber.isSelected())
				{
					LunarEngine2DMainClass.getGameView().loadMap(saveNumber.getSelectedValue(), false);
					LunarEngine2DMainClass.getAudioManager().getMusicStore().stopMusic();
					LunarEngine2DMainClass.getScreen().setState(Screen.STATE_GAME);
				}
			}
			if (delete.checkButton())
			{
				if (saveNumber.isSelected())
				{
					SavePlayable s = new SavePlayable(saveNumber.getSelectedValue());
					if (!s.deleteWholeSaveHard())
					{
						if (Window.isFullscreen()) Window.setFullscreen(false);
						LunarEngine2DMainClass.getScreen().setErrorState(
								"Something went wrong! Close game and remove this save manually (delete folder that is named as the save that you wanted to remove) here:\n" + Files.PLAYABLE_SAVES_FOLDER.f.getAbsolutePath());
						s.debug();
					}
					refreshSaves();
				}
			}
			Controls.checkMouseWheel();
			saveNumber.checkList();
		}
	}
	
	private void closePrompt()
	{
		isNewSaveBeingCreated = false;
		newWorldName.setText("new world");
	}
	
	@Override
	public void cleanUp()
	{
		back.removeButton();
		join.removeButton();
		
	}
	
	/**
	 * Indicates which map all the saves are related to and when new saves are
	 * created they will automatically be referenced to that map as it's origin
	 */
	public String getMapName()
	{
		return mapName;
	}
	
	/**
	 * Indicates which map all the saves are related to and when new saves are
	 * created they will automatically be referenced to that map as it's origin
	 */
	public void setMapName(String mapName)
	{
		this.mapName = mapName;
	}
}
