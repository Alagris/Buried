package net.game.src.sta;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import net.LunarEngine2DMainClass;
import net.swing.ground.Window;
import net.swing.ground.GUI.ButtonSquare;
import net.swing.ground.GUI.State;

public class StateInfo extends State
{
	
	private ButtonSquare	back, openWebsite, readTutorial;
	
	public StateInfo()
	{
		back = new ButtonSquare(j * 4, i * 4, j * 2, i * 2, "back");
		openWebsite = new ButtonSquare(j * 3, 0, j * 4, i * 2, "go to website");
		readTutorial = new ButtonSquare(j * 3, i * 2, j * 4, i * 2, "design levels");
	}
	
	@Override
	public void showState()
	{
		if (back.checkButton())
		{
			LunarEngine2DMainClass.getScreen().setState(Screen.STATE_LOBBY);
		}
		if (readTutorial.checkButton())
		{
			if (Window.isFullscreen()) Window.setFullscreen(false);
			try
			{
				Desktop.getDesktop().open(new File("saves/HowToScript.txt"));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		if (openWebsite.checkButton())
		{
			if (Window.isFullscreen()) Window.setFullscreen(false);
			try
			{
				Desktop.getDesktop().browse(new URL("http://alagris123582.wix.com/firstprince").toURI());
			}
			catch (MalformedURLException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (URISyntaxException e)
			{
				e.printStackTrace();
			}
		}
		// Text to be rendered
		// Window.textDisplayer.render("", 10, HEIGHT-i,j/2,i);
	}
	
	@Override
	public void cleanUp()
	{
		back.removeButton();
	}
	
}
