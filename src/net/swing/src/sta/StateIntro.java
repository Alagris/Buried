package net.swing.src.sta;

import static org.lwjgl.opengl.GL11.*;

import javax.annotation.PreDestroy;

import net.LunarEngine2DMainClass;
import net.game.src.sta.Screen;
import net.swing.engine.graph.TexturesBase;
import net.swing.ground.Camera;
import net.swing.ground.Delta;
import net.swing.ground.Graphics;
import net.swing.ground.Window;
import net.swing.ground.GUI.State;
import net.swing.src.env.WorldSettings;

public class StateIntro extends State
{
	
	private int	logo	= Graphics.generateRectangleNonColored(Window.getWidth() / 4, Window.getHeight() / 4, Window.getWidth() / 2, Window.getHeight() / 2);
	
	/**
	 * @param nextState
	 *            - do never set it as this state (INTRO) unless you want to see
	 *            it again and again...
	 */
	public StateIntro(byte nextStateID)
	{
		this.nextState = nextStateID;
	}
	
	private byte	nextState;
	private float	time	= 0;
	private float	speed	= 0;
	
	/**
	 * This method starts timer. When time reaches 1 INTRO will automatically
	 * switch to next state
	 */
	public void start(float startValue, float countingSpeed)
	{
		Camera.setBackgroundColor(0, 0, 0, 0);
		time = startValue;
		speed = countingSpeed;
		LunarEngine2DMainClass.getScreen().setState(Screen.STATE_INTRO);
	}
	
	@Override
	public void showState()
	{
		Window.textDisplayer.renderMiddleSpaced("powered by Lunar Engine 2D", Window.getWidth() / 2, Window.getHeight() * 0.15f);
		TexturesBase.LOGO.texture.bind();
		glEnable(GL_BLEND);
		time += speed * Delta.get() * WorldSettings.timelapse;
		if (time > 1)
		{
			LunarEngine2DMainClass.getScreen().setState(nextState);
		}
		glColor4f(1, 1, 1, time);
		// NOTE: rendering semi-transparency must be done with DisplayLists.
		glCallList(logo);
		glDisable(GL_BLEND);
		
	}
	
	@PreDestroy
	@Override
	public void cleanUp()
	{
		glDeleteLists(logo, 1);
	}
	
}
