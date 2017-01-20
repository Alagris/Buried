package net.game.src.sta;

import javax.annotation.PreDestroy;

import net.LunarEngine2DMainClass;
import net.swing.ground.Window;
import net.swing.ground.GUI.ButtonSquare;
import net.swing.ground.GUI.State;
import net.swing.ground.GUI.BarUsable;
import net.swing.src.env.WorldSettings;

public class StateOptions extends State
{
	
	private ButtonSquare	back, audio, video, controls, language;
	private byte			stateAfterExit	= Screen.STATE_LOBBY;
	private BarUsable		bar;
	
	public StateOptions()
	{
		language = new ButtonSquare(j, i * 16, j * 3, i * 4, "language");
		video = new ButtonSquare(j, i * 12, j * 3, i * 4, "video");
		controls = new ButtonSquare(j, i * 8, j * 3, i * 4, "controls");
		audio = new ButtonSquare(j, i * 4, j * 3, i * 4, "audio");
		back = new ButtonSquare(j, 0, j * 3, i * 4, "back");
		
		bar = new BarUsable(j * 5, i * 12, j * 4.5f, i, 1, 0, 0, 100, 0, 50);
	}
	
	@Override
	public void showState()
	{
		
		if (back.checkButton())
		{
			LunarEngine2DMainClass.getScreen().setState(stateAfterExit);
		}
		if (audio.checkButton())
		{
			LunarEngine2DMainClass.getScreen().setState(Screen.STATE_AUDIO);
		}
		if (video.checkButton())
		{
			LunarEngine2DMainClass.getScreen().setState(Screen.STATE_GRAPHICS);
		}
		if (controls.checkButton())
		{
			
		}
		if (language.checkButton())
		{
			
		}
		Window.textDisplayer.render("AI level:", j * 5, i * 13);
		bar.render();
		WorldSettings.entityObservationDelay = bar.getBarValue();
	}
	
	@PreDestroy
	@Override
	public void cleanUp()
	{
		language.removeButton();
		video.removeButton();
		audio.removeButton();
		controls.removeButton();
		back.removeButton();
		bar.removeBar();
		
	}
	
	public void setStateAfterExit(byte state)
	{
		stateAfterExit = state;
	}
	
}
