package net.game.src.sta;

import javax.annotation.PreDestroy;

import net.LunarEngine2DMainClass;
import net.swing.ground.Window;
import net.swing.ground.GUI.ButtonSquare;
import net.swing.ground.GUI.State;

public class StateError extends State
{
	
	private ButtonSquare	back;
	
	public StateError()
	{
		back = new ButtonSquare(j * 4, i * 2, j * 2, i * 2, "back");
	}
	
	/**Error message*/
	private String	em	= "";
	
	public void setError(String errormessage)
	{
		if (em == null)
		{
			em = errormessage;
		}
		else
		{
			em = em + "\n" + errormessage;
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void showState()
	{
		
		Window.textDisplayer.render(em, 10, i * 15);
		
		if (back.checkButton())
		{
			em = "";
			LunarEngine2DMainClass.getScreen().unblock();
			LunarEngine2DMainClass.getScreen().setState(Screen.STATE_LOBBY);
		}
	}
	
	@PreDestroy
	@Override
	public void cleanUp()
	{
		back.removeButton();
	}
}
