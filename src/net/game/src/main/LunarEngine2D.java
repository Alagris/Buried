package net.game.src.main;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import net.LunarEngine2DMainClass;
import net.game.src.sta.Screen;
import net.swing.ground.Camera;
import net.swing.ground.Delta;
import net.swing.ground.ScreenInterface;
import net.swing.ground.Window;
import net.swing.src.data.Files;
import net.swing.src.main.MainClassInterface;
import net.swing.src.obj.CollAreas;
import net.swing.src.sound.AudioManager;

import org.lwjgl.opengl.Display;

public final class LunarEngine2D implements MainClassInterface
{
	
	private static AudioManager	audioManager;
	
	/**
	 * It is static because then all classes (in srcGame) can use it directly
	 * from Buried (Buried.screen) instead of getting it from BuriedMain
	 * (BuriedMain.getScreen() ). This way allows game classes to use all
	 * methods from screen, not only those from ScreenInterface. It if final
	 * because it we don't want to change it.
	 */
	public static final Screen	screen	= new Screen();
	
	@Override
	public AudioManager getAudioManager()
	{
		return audioManager;
	}
	
	public static void applyVolumes()
	{
		audioManager.getMusicStore().setGlobalVolume(LunarEngine2DMainClass.getMusicVol());
		audioManager.getSoundStore().setVolume(LunarEngine2DMainClass.getSoundVol());
		audioManager.getVoiceManager().setVolume(LunarEngine2DMainClass.getVoiceVol());
	}
	
	@Override
	public void whenStops()
	{
		System.out.println("saving properties");
		LunarEngine2DMainClass.saveProperties();
		System.out.println("Destroying OpenGL");
		Display.destroy();
		System.out.println("Destroying OpenAL");
		audioManager.destroy();
	}
	
	@Override
	public void loop()
	{
		// erasing last view
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		// updating fps
		Delta.update();
		// updating game and creating new view
		screen.update();
		audioManager.update();
		// repaint
		Display.update();
		Display.sync(60);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void setup()
	{
		Window.setTextDisplayer(Files.FONT_FILE.f);
		Window.textDisplayer.setColor(1, 1, 1);
		
		// initializations
		Camera.initializeView(Window.getWidth(), Window.getHeight());
		screen.initializeGraph();
		screen.initializeStates();
		// music and sounds
		audioManager = new AudioManager();
		audioManager.create(Files.SOUNDS_FILE.f, Files.MUSIC_FILE.f);
		
		screen.intro.start(0.001f, 0.005f);
		audioManager.getMusicStore().playMusic(0, 0);
		applyVolumes();
		Delta.update();
	}
	
	@Override
	public void declare(int id, String instructions)
	{
		System.out.println("Declaration [number: " + id + "] execulted!");
		switch (id)
		{
			case 50:
				parse50(instructions);
				break;
			case 51:
				parse51(instructions);
				break;
		}
	}
	
	/** Parses declaration of actions */
	private void parse51(String instructions)
	{
		CollAreas.getNewInstance(instructions).run(null, true);
	}
	
	/** Parses declaration of music sound tracks */
	private void parse50(String instructions)
	{
		int i = 0;
		for (String s : instructions.split(","))
		{
			audioManager.getMusicStore().loadSoundtrack(i, Integer.parseInt(s));
			i++;
		}
	}
	
	@Override
	public ScreenInterface getScreen()
	{
		return screen;
	}
}
