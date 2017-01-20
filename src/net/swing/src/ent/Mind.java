package net.swing.src.ent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Mind
{
	
	public String	startProgram;
	private int		link	= -1;
	
	public enum DefaultMind
	{
		REPTILE("HUNT"), PEACEFUL("WAIT"), MINDLESS("STAND");
		public String	p;
		
		private DefaultMind(String program)
		{
		}
	}
	
	public Mind(String program)
	{
		this.startProgram = program;
	}
	
	public Mind(int link)
	{
		this.link = link;
		startProgram = readLink(link);
	}
	
	public Mind(DefaultMind m)
	{
		startProgram = m.p;
	}
	
	/** reads program link */
	public String readLink(int linkNumber)
	{
		String data = "";
		try
		{
			Scanner sc = new Scanner(new File("AIprograms/LINK" + linkNumber + ".txt"));
			while (sc.hasNext())
			{
				data = data + sc.next();
			}
			sc.close();
		}
		catch (FileNotFoundException e)
		{
			System.err.println("AI program number" + linkNumber + " was not found!");
		}
		return data;
	}
	
	public int getLink()
	{
		return link;
	}
	
	public void setLink(int link)
	{
		this.link = link;
	}
}
