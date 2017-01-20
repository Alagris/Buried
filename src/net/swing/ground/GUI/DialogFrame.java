package net.swing.ground.GUI;

import java.io.File;

import javax.annotation.PreDestroy;

import net.LunarEngine2DMainClass;
import net.swing.ground.BitmapFont;
import net.swing.src.data.DataCoder;
import net.swing.src.data.Files;
import net.swing.src.sound.SoundSource;

public class DialogFrame extends TextDisplayer
{
	/////////////////////////////////////////
	// FIXME: This class is not ready to use!
	/////////////////////////////////////////
	
	private String			text		= "";
	private File			nextVoiceFolder;
	private boolean			enableNext	= false;
	private String			lastVoice	= "";
	
	/** If text starts with #next_voice_folder_name this button will appear **/
	private ButtonSquare	next;
	
	public DialogFrame(float x, float y, float width, float height, BitmapFont font)
	{
		super(font);
		setBounds(x, y, width, height);
		
		next = new ButtonSquare(x + width - (width / 6), 0, width / 6, height / 5, "next");
		next.setTextDisplayer(font);
	}
	
	@PreDestroy
	public void cleanUp()
	{
		next.removeButton();
	}
	
	/** @param - folder with text.txt and voice.wav */
	public void loadText()
	{
	}
	
	/**
	 * @param folder
	 *            - the one containing text.txt and voice.wav
	 */
	public void loadText(String textName)
	{
		if (lastVoice.equals(textName)) return;
		
		if (LunarEngine2DMainClass.getAudioManager().getVoiceManager().load(textName))
		{
			LunarEngine2DMainClass.getAudioManager().getVoiceManager().stopVoice();
			LunarEngine2DMainClass.getAudioManager().playVoice();
			lastVoice = textName;
		}
		
		File f = new File(folder + "/text.txt");
		if (f.exists())
		{
			// finding text data
			text = DataCoder.read(f);
			if (text.startsWith("#"))
			{
				int hastags = 0;
				String fName = "";
				for (char c : text.toCharArray())
				{
					if (c == '#')
					{
						hastags++;
						if (hastags == 2)
						{
							break;
						}
					}
					else
					{
						fName = fName + c;
					}
				}
				if (hastags == 2)
				{
					text.replaceFirst("#" + fName + "#", "");
				}
				nextVoiceFolder = new File(folder.getParentFile() + "/" + fName);
				enableNext = nextVoiceFolder.exists();
				
				if (enableNext)
				{
					next.setText("next");
				}
			}
			next.setText("ok");
		}
	}
	
	public void eraseText()
	{
		enableNext = false;
		lastVoice = "";
		text = "";
	}
	
	public void update()
	{
		renderStringInBounds(text);
		if (!text.equals("") && next.checkButton())
		{
			if (enableNext)
			{
				loadText(nextVoiceFolder);
			}
			else
			{
				stop();
				eraseText();
			}
		}
	}
	
}
