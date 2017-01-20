package net.game.src.sta;

import javax.annotation.PreDestroy;

import net.LunarEngine2DMainClass;
import net.swing.ground.GUI.BarUsable;
import net.swing.ground.GUI.ButtonSquare;
import net.swing.ground.GUI.State;

public class StateVideoSettings extends State
{
	
	private ButtonSquare	back		= new ButtonSquare(0, 0, j * 3, i * 3, "back");
	private ButtonSquare	save		= new ButtonSquare(0, i * 4, j * 3, i * 3, "save");
	private BarUsable		brightness	= new BarUsable(j * 4, i, j * 5, i, 0, 1, 1, 1, -1, 0.5f);
	
	public StateVideoSettings()
	{
		brightness.setMode(BarUsable.MODE_ZIP);
	}
	
	@Override
	public void showState()
	{
		
		if (back.checkButton())
		{
			LunarEngine2DMainClass.getScreen().setState(Screen.STATE_OPTIONS);
		}
		if (save.checkButton())
		{
		}
		brightness.updateTextured();
	}
	
	public float getBrightness()
	{
		return brightness.getBarValue();
	}
	
	@PreDestroy
	@Override
	public void cleanUp()
	{
		back.removeButton();
		brightness.removeBar();
		save.removeButton();
	}
}
