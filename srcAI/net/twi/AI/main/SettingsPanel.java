package net.twi.AI.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import static net.twi.AI.main.R.*;

public class SettingsPanel extends JPanel
{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	private JPanel initPanel(String title, LayoutManager layout, Component... children)
	{
		JPanel panel = new JPanel();
		panel.setLayout(layout);
		panel.setBorder(BorderFactory.createTitledBorder(title));
		for (Component component : children)
		{
			panel.add(component);
		}
		this.add(panel);
		return panel;
	}
	
	private JPanel initPanel(String title, Component... children)
	{
		return initPanel(title, new BorderLayout(), children);
	}
	
	void setup()
	{
		setLayout(new GridLayout(0, 1));
		
		final JSpinner foodJSpinner = new JSpinner(foodModel);
		foodJSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e)
			{
				foodRestorationSpeed = (int) foodJSpinner.getValue();
			}
		});
		initPanel("Food restoration speed", foodJSpinner);
		
		JSpinner hungerJSpinner = new JSpinner(hungerModel);
		hungerJSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e)
			{
				hungerTime = (int) hungerModel.getValue();
			}
		});
		initPanel("Hunger time to die (in frames)", hungerJSpinner);
		
		JSpinner sleepJSpinner = new JSpinner(sleepTimeModel);
		sleepJSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e)
			{
				sleepTime = (int) sleepTimeModel.getValue();
			}
		});
		initPanel("Simulation slowdown", sleepJSpinner);
		
		JSpinner childCostJSpinner = new JSpinner(childCostModel);
		childCostJSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e)
			{
				childCost = (int) childCostModel.getValue();
			}
		});
		initPanel("Child cost", childCostJSpinner);
		
		final JCheckBox vizCheckBox = new JCheckBox("Vizualization enabled");
		vizCheckBox.setSelected(true);
		add(vizCheckBox);
		vizCheckBox.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e)
			{
				FrameNeuralPixelTest.setVisualizationEnabled(vizCheckBox.isSelected());
			}
		});
		
		final JButton pauseOrResume = new JButton(isSimulationPaused ? "Resume" : "Pause");
		pauseOrResume.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				isSimulationPaused = !isSimulationPaused;
				pauseOrResume.setText(isSimulationPaused ? "Resume" : "Pause");
			}
		});
		JButton nextSimulationFrame = new JButton("Next frame");
		nextSimulationFrame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (isSimulationPaused) R.doJustOneNextFrame = true;
			}
		});
		initPanel("Pausing simulation", new GridLayout(1, -1), pauseOrResume, nextSimulationFrame);
	}
}
