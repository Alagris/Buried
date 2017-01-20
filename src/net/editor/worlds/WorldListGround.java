package net.editor.worlds;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public final class WorldListGround extends JInternalFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long		serialVersionUID	= 1L;
	
	// menu bar items
	private final JMenuBar			menuBar				= new JMenuBar();
	
	private final JMenu				menu				= new JMenu("edit");
	private final JMenuItem			menuRefresh			= new JMenuItem("Refresh list");
	
	public final WorldListPanel		worldsList;
	public final WorldEditingPanel	worldsEditor;
	
	public WorldListGround()
	{
		
		super("Worlds editor", true, true, true, true);
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setJMenuBar(menuBar);
		menuBar.add(menu);
		menu.add(menuRefresh);
		menuRefresh.addActionListener(this);
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(layout);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(5, 5, 5, 5);
		c.ipadx = 5;
		c.ipady = 5;
		// 2 columns, 1 row
		
		c.weightx = 2;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 0;
		
		worldsEditor = new WorldEditingPanel();
		add(worldsEditor, c);
		
		c.weightx = 1;
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 0;
		
		worldsList = new WorldListPanel();
		add(worldsList, c);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == menuRefresh)
		{
			worldsList.refreshWorlds();
		}
	}
	
}
