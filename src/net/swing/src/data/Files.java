package net.swing.src.data;

import java.io.File;
import java.io.IOException;

public enum Files
{
	FONT_FILE(new File("res/font/font.png"), false),
	/** Folder with origin */
	SAVES_FOLDER(new File("saves"), true),
	/** Folder with playable saves */
	PLAYABLE_SAVES_FOLDER(new File("playable"), true),
	/** Folder with blocks textures */
	BLOCK_TEX_FOLDER(new File("res/objects/blocks"), true),
	/** Folder with entities */
	ENTITIES_FOLDER(new File("res/objects/entities"), true),
	/** Folder with temporary entities */
	TEMP_ENTITIES_FOLDER(new File("res/objects/tempEntities"), true),
	/** Folder with sound files */
	SOUNDS_FILE(new File("res/sounds"), true),
	/** Folder with music files */
	MUSIC_FILE(new File("res/music"), true),
	/** Folder with voice folders */
	VOICE_FILE(new File("res/texts"), false),
	/** File with saved settings etc. */
	PROPERTIES_FILE(new File("properties.txt"), false),
	/** Folder where all screen shots are stored */
	SCREENSHOTS_FOLDER(new File("screenshots"), true);
	
	/** returns file in selected element in enumeration */
	public File		f;
	public boolean	isDirectory;
	
	public String getPath()
	{
		return f.getPath();
	}
	
	private Files(File f, boolean isDirectory)
	{
		this.f = f;
		this.isDirectory = isDirectory;
	}
	
	/** Creates all needed files in case any of them did not exist */
	public static void checkFiles()
	{
		for (Files f : values())
		{// check every file in this enumeration
			if (f.isDirectory)
			{// if it's a directory create a directory (with all parent directories)
				f.f.mkdirs();
			}
			else
			{
				try
				{// if it's a file create a file
					f.f.createNewFile();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			
		}
	}
	
	/** returns true when name is valid */
	public static boolean validateName(String name, int minLength, int maxLength)
	{
		if (name.length() < minLength) return false;
		if (name.length() > maxLength) return false;
		return validateName(name);
	}
	
	/** Validates name and check if its length is not shorter than minLength */
	public static boolean validateNameMin(String name, int minLength)
	{
		if (name.length() < minLength) return false;
		return validateName(name);
	}
	
	/** Validates name and check if its length is not longer than maxLength */
	public static boolean validateNameMax(String name, int maxLength)
	{
		if (name.length() > maxLength) return false;
		return validateName(name);
	}
	
	/**
	 * returns true when name is valid .
	 */
	public static boolean validateName(String name)
	{
		if (name == null) return false;
		if (name.length() == 0) return false;
		
		for (char c : name.toCharArray())
		{
			if (isIllegalCharacterForFileName(c))
			{
				return false;
			}
		}
		return true;
	}
	
	private static boolean isIllegalCharacterForFileName(char c)
	{
		return Character.isWhitespace(c) || c == '?' || c == '/' || c == '<' || c == '>' || c == '\\' || c == ':' || c == '.' || c == '|' || c == '^' || c == '*' || c == '%';
	}
	
	public static boolean validateName_allowSpaces(String name)
	{
		if (name == null) return false;
		if (name.length() == 0) return false;
		
		for (char c : name.toCharArray())
		{
			if (c != ' ' && isIllegalCharacterForFileName(c))
			{
				return false;
			}
		}
		return true;
	}
	
}
