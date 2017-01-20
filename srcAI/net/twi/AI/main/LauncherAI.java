package net.twi.AI.main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class LauncherAI extends JFrame implements ActionListener, Runnable
{
	/**
	 * 
	 */
	private static final long		serialVersionUID	= 1L;
	
	public static final int			default_rows		= 200;
	public static final int			default_columns		= 200;
	public static final double		default_beta		= 4;
	public static final int			default_agents		= 40;
	
	JButton							ready				= new JButton("start");
	
	SpinnerNumberModel				betaModel			= new SpinnerNumberModel(default_beta, -100, 100, 0.1);
	SpinnerNumberModel				agentsModel			= new SpinnerNumberModel(default_agents, 1, 100, 1);
	SpinnerNumberModel				foodModel			= new SpinnerNumberModel(R.foodRestorationSpeed, 0, 1, 1);
	SpinnerNumberModel				hungerModel			= new SpinnerNumberModel(R.hungerTime, 100, 100000, 1);
	SpinnerNumberModel				rowsModel			= new SpinnerNumberModel(default_rows, 10, 1000, 1);
	SpinnerNumberModel				columnsModel		= new SpinnerNumberModel(default_columns, 10, 1000, 1);
	JTextField						startDNA			= new JTextField("");
	
	Thread							t;
	public static final File		agentsFolder		= new File("AgentsData");
	static{
		if(!agentsFolder.exists()){
			agentsFolder.mkdirs();
		}
	}
	DefaultComboBoxModel<String>	boxModel			= new DefaultComboBoxModel<String>(agentsFolder.list());
	
	public LauncherAI(Thread thread)
	{
		super("Initial settings");
		t = thread;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(2, 2));
		
		JPanel hungerPanel = new JPanel();
		hungerPanel.setLayout(new BorderLayout());
		hungerPanel.setBorder(BorderFactory.createTitledBorder("Hunger time to die (in frames)"));
		JSpinner hungerJSpinner = new JSpinner(hungerModel);
		hungerPanel.add(hungerJSpinner);
		add(hungerPanel);
		
		JPanel betaPanel = new JPanel();
		betaPanel.setLayout(new BorderLayout());
		betaPanel.setBorder(BorderFactory.createTitledBorder("Beta constant (neurons sensitivity)"));
		JSpinner betaJSpinner = new JSpinner(betaModel);
		betaPanel.add(betaJSpinner);
		add(betaPanel);
		
		JPanel agentsPanel = new JPanel();
		agentsPanel.setLayout(new BorderLayout());
		agentsPanel.setBorder(BorderFactory.createTitledBorder("Agents quantity"));
		JSpinner agentsJSpinner = new JSpinner(agentsModel);
		agentsJSpinner.setEnabled(false);
		agentsPanel.add(agentsJSpinner);
		add(agentsPanel);
		
		JPanel foodPanel = new JPanel();
		foodPanel.setLayout(new BorderLayout());
		foodPanel.setBorder(BorderFactory.createTitledBorder("Food restoration speed"));
		JSpinner foodJSpinner = new JSpinner(foodModel);
		foodPanel.add(foodJSpinner);
		add(foodPanel);
		
		JPanel DNAPanel = new JPanel();
		DNAPanel.setLayout(new BorderLayout());
		DNAPanel.setBorder(BorderFactory.createTitledBorder("start DNA"));
		DNAPanel.add(startDNA);
		add(DNAPanel);
		
		JPanel rowsPanel = new JPanel();
		rowsPanel.setLayout(new BorderLayout());
		rowsPanel.setBorder(BorderFactory.createTitledBorder("Matrix rows"));
		JSpinner rowsSpinner = new JSpinner(rowsModel);
		rowsPanel.add(rowsSpinner);
		add(rowsPanel);
		
		JPanel columnsPanel = new JPanel();
		columnsPanel.setLayout(new BorderLayout());
		columnsPanel.setBorder(BorderFactory.createTitledBorder("Matrix columns"));
		JSpinner columnsSpinner = new JSpinner(columnsModel);
		columnsPanel.add(columnsSpinner);
		add(columnsPanel);
		
		JPanel filesPanel = new JPanel();
		filesPanel.setLayout(new BorderLayout());
		filesPanel.setBorder(BorderFactory.createTitledBorder("File with agents data"));
		JComboBox<String> filesBox = new JComboBox<String>(boxModel);
		filesPanel.add(filesBox);
		filesBox.setEnabled(false);
		add(filesPanel);
		
		add(ready);
		ready.addActionListener(this);
		
		pack();
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		dispose();
		synchronized (t)
		{
			t.notifyAll();
		}
	}
	
	@Override
	public void run()
	{
	}
}
