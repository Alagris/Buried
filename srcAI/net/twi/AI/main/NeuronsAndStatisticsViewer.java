package net.twi.AI.main;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import net.math.src.neurons.NeurogliaRecursive;

public class NeuronsAndStatisticsViewer extends JFrame implements Runnable, WindowListener
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	private JTabbedPane			tabs				= new JTabbedPane();
	
	// private NeuronsPanel neuronsPanel = new NeuronsPanel();
	public NeuronsCirclePanel	neuronsCirclePanel	= new NeuronsCirclePanel();
	public StatisticsPanel		statisticsPanel		= new StatisticsPanel();
	public SettingsPanel		settingsPanel		= new SettingsPanel();
	
	public NeuronsAndStatisticsViewer(int statisticsDataVector)
	{
		super("Neuroscientist toolkit");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLayout(new BorderLayout());
		addWindowListener(this);
		// tabs.addTab("Neurons", neuronsPanel);
		tabs.addTab("NeuronsCircle", neuronsCirclePanel);
		tabs.addTab("Statistics", statisticsPanel);
		tabs.addTab("Settings", settingsPanel);
		settingsPanel.setup();
		add(tabs);
		
		pack();
		setVisible(true);
		
		statisticsPanel.statisticsDataVector = statisticsDataVector;
	}
	
	public void pushStatisticsData(int... data)
	{
		if (data.length == statisticsPanel.statisticsDataVector)
		{
			statisticsPanel.list.add(data);
		}
		if (tabs.getSelectedIndex() == 1)
		{
			statisticsPanel.repaint();
		}
	}
	
	public void viewNeuralNet(NeurogliaRecursive n, String extraComment)
	{
		setVisible(true);
		// neuronsPanel.testedBrain = nl;
		neuronsCirclePanel.setBrain(n);
		neuronsCirclePanel.setText(extraComment);
		tabs.setSelectedIndex(0);
		neuronsCirclePanel.repaint();
		// neuronsPanel.repaint();
		toFront();
	}
	
	@Override
	public void run()
	{
	}
	
	@Override
	public void windowOpened(WindowEvent e)
	{
	}
	
	@Override
	public void windowClosing(WindowEvent e)
	{
		setVisible(false);
	}
	
	@Override
	public void windowClosed(WindowEvent e)
	{
	}
	
	@Override
	public void windowIconified(WindowEvent e)
	{
	}
	
	@Override
	public void windowDeiconified(WindowEvent e)
	{
	}
	
	@Override
	public void windowActivated(WindowEvent e)
	{
	}
	
	@Override
	public void windowDeactivated(WindowEvent e)
	{
	}
	
}
