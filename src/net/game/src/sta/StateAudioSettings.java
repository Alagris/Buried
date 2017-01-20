package net.game.src.sta;

import javax.annotation.PreDestroy;

import net.LunarEngine2DMainClass;
import net.game.src.main.LunarEngine2D;
import net.swing.ground.Window;
import net.swing.ground.GUI.BarUsable;
import net.swing.ground.GUI.ButtonSquare;
import net.swing.ground.GUI.State;

public final class StateAudioSettings extends State
{
	
	BarUsable	voiceVolumeBar;
	BarUsable	musicVolumeBar;
	BarUsable	soundVolumeBar;
	ButtonSquare	back, save;
	
	public StateAudioSettings()
	{
		back = new ButtonSquare(0, 0, j * 3, i * 3, "back");
		save = new ButtonSquare(0, i * 4, j * 3, i * 3, "save");
		voiceVolumeBar = new BarUsable(j * 4, i * 4, j * 5, i, 0, 1, 1,/* max */1,/* min */0,/* start */LunarEngine2DMainClass.getVoiceVol());
		musicVolumeBar = new BarUsable(j * 4, i * 7, j * 5, i, 0, 1, 1, 1, 0, LunarEngine2DMainClass.getMusicVol());
		soundVolumeBar = new BarUsable(j * 4, i, j * 5, i, 0, 1, 1, 1, 0, LunarEngine2DMainClass.getSoundVol());
		voiceVolumeBar.setMode(BarUsable.MODE_ZIP);
		musicVolumeBar.setMode(BarUsable.MODE_ZIP);
		soundVolumeBar.setMode(BarUsable.MODE_ZIP);
	}
	
	@Override
	public void showState()
	{
		Window.textDisplayer.render("voice volume:", j * 4, i * 5);
		voiceVolumeBar.updateTextured();
		Window.textDisplayer.render("music volume:", j * 4, i * 8);
		musicVolumeBar.updateTextured();
		Window.textDisplayer.render("sound volume:", j * 4, i * 2);
		soundVolumeBar.updateTextured();
		if (save.checkButton())
		{
			LunarEngine2DMainClass.setVoiceVol(voiceVolumeBar.getBarValue());
			LunarEngine2DMainClass.setMusicVol(musicVolumeBar.getBarValue());
			LunarEngine2DMainClass.setSoundVol(soundVolumeBar.getBarValue());
			LunarEngine2D.applyVolumes();
		}
		if (back.checkButton())
		{
			LunarEngine2DMainClass.getScreen().setState(Screen.STATE_OPTIONS);
		}
	}
	
	@PreDestroy
	@Override
	public void cleanUp()
	{
		back.removeButton();
		musicVolumeBar.removeBar();
		soundVolumeBar.removeBar();
		voiceVolumeBar.removeBar();
	}
	
}
