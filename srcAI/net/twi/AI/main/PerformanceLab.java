package net.twi.AI.main;

import net.swing.ground.Window;
import net.swing.src.data.Test;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class PerformanceLab
{
	
	public static void main(String[] args)
	{
		double x = 1;
		double constant = 0;
		
		while (true)
		{
			constant += x;
			x /= -2;
			System.out.println(constant+" "+x);
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void checkLoopInitializations()
	{
		// While program doesn't run CPU 3% (sometimes drops to 20%), Memory 2,8
		// GB
		//
		// CPU 80% , Memory 2,8 GB
		// while (true)
		// {
		// long[] a = new long[999999];
		// System.gc();
		// }
		//
		// CPU 20% (sometimes drops to 50%) , Memory 3,5 GB
		// while (true)
		// {
		// long[] a = new long[999999];
		// }
		//
		// CPU 30% (sometimes drops to 70%) , Memory 3,5 GB
		// long[] a = new long[999999];
		// while (true)
		// {
		// a = new long[999999];
		// }
		//
		// CPU 30% (sometimes drops to 70%) , Memory 3,5 GB
		// long[] a;
		// while (true)
		// {
		// a = new long[999999];
		// }
		//
		// CPU 80%, Memory 2,8 GB
		// long[] a;
		// while (true)
		// {
		// a = new long[999999];
		// System.gc();
		// }
	}
	
	/*
	 * public static void check() { Test.start();
	 * 
	 * Test.end(); }
	 */
	public static void checkLWJGL()
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(100, 100));
			Display.setTitle(Window.getGameName());
			Display.setResizable(false);
			Display.setVSyncEnabled(true);
			Display.setFullscreen(false);
			Display.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
		while (true)
		{
			Test.start();
			
			Test.end();
		}
	}
	
	public static void checkDataFlow()
	{
		int[] x = { 1, 2, 3, 4, 5, 6 };
		a(x);
		System.out.println(x[2]);
	}
	
	private static void a(int[] i)
	{
		i[2] = 11;
	}
	
	public static void checkArrayReinitialization()
	{
		int[] x = new int[99999999];
		System.out.println("Filling array with values");
		Test.start();
		for (int i = 0; i < x.length; i++)
		{
			x[i] = 1;
		}
		Test.end();
		
		System.out.println("Implementing new array ; lenght = " + x.length);
		Test.start();
		@SuppressWarnings("unused")
		int[] y = new int[x.length];
		Test.end();
		System.out.println("passing data from pointer x to y");
		Test.start();
		y = x;
		Test.end();
	}
	
	public static void checkGarbageCollector()
	{
		Test.start();
		System.gc();
		Test.end();
	}
}
