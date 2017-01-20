package net.twi.AI.main;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import net.math.src.func.Function;

public class FrameFuncDisplayer extends JFrame
{
	/**
		 * 
		 */
	private static final long	serialVersionUID	= 1L;
	// public Window window = new Window();
	public FuncDisplay			window;
	
	public FrameFuncDisplayer()
	{
		super("Function displayer");
		
		window = new FuncDisplay();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		add(window, BorderLayout.CENTER);
		
		setVisible(true);
		setDefaultBounds();
	}
	
	public void setDefaultBounds()
	{
		setBounds(1, 1, 500, 500);
	}
	
	public void requestFunction(int index, double x, double y, double z)
	{
		window.requestFunction(index, x, y, z);
	}
	
	public void requestCustomFunction(Function customFunc, double addedToX)
	{
		window.requestCustomFunction(customFunc, addedToX);
	}
}
