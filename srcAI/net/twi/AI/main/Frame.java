package net.twi.AI.main;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Frame extends JFrame
{
	/**
		 * 
		 */
	private static final long	serialVersionUID	= 1L;
	// public Window window = new Window();
	public FuncDisplay			window;
	
	public Frame()
	{
		super("AI-Labs");
	
		window = new FuncDisplay();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(window, BorderLayout.CENTER);
		
		setVisible(true);
		setBounds(1, 1, 500, 500);
	}
}
