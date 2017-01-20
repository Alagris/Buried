package net.swing.src.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import net.swing.src.data.Files;

import org.lwjgl.opengl.DisplayMode;

public class Launcher extends JFrame implements ActionListener, LauncherInterface
{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	// Initializations
	private JCheckBox			enableFullscreen	= new JCheckBox("Fullscreen");
	private Dimension			windowSize			= new Dimension(400, 200);
	private DisplayMode[]		displays;
	private JComboBox<String>	sizes				= new JComboBox<String>();
	public boolean				isReady				= false;
	private Image				IconImage;
	private JButton				start				= new JButton("Run game");
	private JButton				openEditor			= new JButton("Open editor");
	private Object				source;
	private int					sizeIndex			= 0;
	/** If true - game mode selected, false - editor mode selected */
	public boolean				isGame				= true;
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		source = e.getSource();
		if (source == start)
		{
			isReady = true;
		}
		else if (source == openEditor)
		{
			isReady = true;
			isGame = false;
		}
	}
	
	// Important getters and setters
	@Override
	public void setSizes(DisplayMode[] arg0)
	{
		displays = arg0;
		for (int i = 0; i != arg0.length; i++)
		{
			sizes.addItem("[" + i + "]: " + arg0[i].getWidth() + "x" + arg0[i].getHeight() + " (freq" + arg0[i].getFrequency() + " bpp" + arg0[i].getBitsPerPixel() + ")");
		}
		try
		{
			sizes.setSelectedIndex(sizeIndex);
		}
		catch (Exception e )
		{
			sizes.setSelectedIndex(0);
		}
	}
	
	@Override
	public boolean isFullscreenSelected()
	{
		return enableFullscreen.isSelected();
	}
	
	@Override
	public boolean isReady()
	{
		return isReady;
	}
	
	@Override
	public int getSelectedWidth()
	{
		if (displays == null) return 0;
		return displays[sizes.getSelectedIndex()].getWidth();
	}
	
	@Override
	public DisplayMode getSelectedDisplayMode()
	{
		return displays[sizes.getSelectedIndex()];
	}
	
	@Override
	public int getSelectedHeight()
	{
		if (displays == null) return 0;
		return displays[sizes.getSelectedIndex()].getHeight();
	}
	
	@Override
	public void shutdown()
	{
		dispose();
	}
	
	// JFrame initializing
	
	private void readProperties()
	{
		try
		{
			Scanner reading = new Scanner(Files.PROPERTIES_FILE.f);
			if (reading.nextLine().equals("fullscreen: enabled"))
			{
				enableFullscreen.setSelected(true);
			}
			sizeIndex = reading.nextInt();
			reading.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public Launcher()
	{
		super("LunarEngine2D launcher");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(true);
		GridLayout layout = new GridLayout(0, 2);
		layout.setHgap(10);
		layout.setVgap(10);
		setLayout(layout);
		setBackground(Color.BLACK);
		setAlwaysOnTop(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(windowSize);
		setLocation((int) (screenSize.getWidth() / 2 - windowSize.getWidth() / 2), (int) (screenSize.getHeight() / 2 - windowSize.getHeight() / 2));
		
		try
		{
			IconImage = ImageIO.read(new File("res/gui/logo128.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		setIconImage(IconImage);
		
		sizes.addActionListener(this);
		
		start.addActionListener(this);
		
		openEditor.addActionListener(this);
		
		add(sizes);
		add(enableFullscreen);
		add(start);
		add(openEditor);
		
		readProperties();
		
		System.out.println("Detected Java Version: " + System.getProperty("java.version"));
		System.out.println("Recommended Java Version: 1.6 or newer ");
		System.out.println();
	}
	
	public int getSelectedDisplay()
	{
		return sizes.getSelectedIndex();
	}
	
}
