package net.editor.main;

import java.io.File;
import java.io.IOException;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import net.swing.src.data.DataCoder;

public class DerpyIsBestPony
{
	
	public static GeneralWindow	general;
	
	/**
	 * Notice that editor has it's own main method. At the moment it is executed
	 * from BuriedMain class but in the future it will be possible to execute
	 * program directly from here so that user can quickly open editor without
	 * running rest of LunarEngine2D.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		File properties = new File("editor.txt");
		try
		{
			if (properties.createNewFile())
			{
				if (setUI("Nimbus"))
				{
					DataCoder.write(properties, "Nimbus");
				}
				else
				{
					DataCoder.write(properties, UIManager.getLookAndFeel().getName());
				}
			}
			else
			{
				if (!setUI(DataCoder.read(properties, 1)))
				{/*
				 * An unexpected error occurred which could mean that data in
				 * file is modified
				 */
					if (setUI("Nimbus"))
					{
						DataCoder.write(properties, "Nimbus");
					}
					else
					{
						DataCoder.write(properties, UIManager.getLookAndFeel().getName());
					}
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		general = new GeneralWindow();
	}
	
	/** Returns true if UI was successfully found and loaded */
	private static boolean setUI(String uiName)
	{
		try
		{
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
			{
				if (uiName.equals(info.getName()))
				{
					UIManager.setLookAndFeel(info.getClassName());
					return true;
				}
			}
		}
		catch (Exception e)
		{
		}
		return false;
	}
	
}
