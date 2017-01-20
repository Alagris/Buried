package net.game.src.sta;

import javax.annotation.PreDestroy;

import org.lwjgl.input.Keyboard;

import net.LunarEngine2DMainClass;
import net.game.src.main.GameView;
import net.swing.ground.Controls;
import net.swing.ground.GameInterface;
import net.swing.ground.GameViewInterface;
import net.swing.ground.Window;
import net.swing.ground.GUI.State;

public final class StateGame extends State implements GameInterface
{
	public GameView	view		= new GameView();
	/** If set to false then game is paused */
	private boolean	isAction	= false;
	
	@Override
	public void showState()
	{
		if (Controls.typedKey == Keyboard.KEY_F5)
		{
			view.saveRoomOrShowError();
			System.out.println("Room quick save done (F5)");
		}
		if (isAction)
		{
			// updating logic and rendering graphics
			view.update();
		}
		else
		{
			// logic is frozen but graphics is still being rendered
			view.renderRoom();
			// user still can edit inventory
			view.updateInventory();
			// huge text "PAUSED" is displayed in the center of game screen
			Window.textDisplayer.renderMiddleSpaced("PAUSED", Window.getWidth() / 2, Window.getHeight() / 2, Window.getWidth() / 5, Window.getHeight() / 5);
		}
	}
	
	@PreDestroy
	@Override
	public void cleanUp()
	{
		view.cleanUp();
	}
	
	@Override
	public void switchGame(boolean arg0)
	{
		System.out.println("game mode active: " + arg0);
		isAction = arg0;
	}
	
	/** Saves game and exits to lobby */
	public void exit()
	{
		LunarEngine2DMainClass.getAudioManager().getMusicStore().stopMusic();// stops game music
		LunarEngine2DMainClass.getAudioManager().getSoundStore().stopSounds();// stops currently played sounds
		LunarEngine2DMainClass.getAudioManager().getVoiceManager().stopVoice();// stops currently played voice
		LunarEngine2DMainClass.getAudioManager().getMusicStore().playMusic(0, 0);// plays main theme
		
		view.saveRoomOrShowError();
		view.saveLastRoomInfo();
		switchGame(false);
		LunarEngine2DMainClass.getScreen().setState(Screen.STATE_LOBBY);
	}
	
	@Override
	public boolean isAction()
	{
		return isAction;
	}
	
	@Override
	public GameViewInterface getGameView()
	{
		return view;
	}
	
}
