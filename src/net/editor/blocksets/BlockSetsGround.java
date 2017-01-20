package net.editor.blocksets;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class BlockSetsGround extends JInternalFrame implements ActionListener
{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	public static final String	path				= "res/objects/blocks";
	
	public BlockSetsPanel		blockSetsPanel;
	
	// menu bar items
	private JMenuBar			menuBar;
	
	private JMenu				sets;
	private JMenuItem			menuNewSet;
	private JMenuItem			menuRemoveSet;
	private JMenuItem			menuAdd;
	private JMenuItem			menuRemoveBlock;
	
	private JMenu				menu;
	private JMenuItem			refreshSetsList;
	private JMenuItem			overwrite;
	private JMenuItem			menuTestSet;
	
	public BlockSetsGround()
	{
		super("Blocks sets editor", true, true, true, true);
		
		setLayout(new BorderLayout());
		blockSetsPanel = new BlockSetsPanel();
		
		menuBar = new JMenuBar();
		
		sets = new JMenu("block sets");
		menuNewSet = new JMenuItem("new set");
		menuRemoveSet = new JMenuItem("remove edited set");
		menuAdd = new JMenuItem("add block");
		menuRemoveBlock = new JMenuItem("remove selected block");
		
		menu = new JMenu("menu");
		refreshSetsList = new JMenuItem("refresh list");
		overwrite = new JMenuItem("overwrite this set");
		menuTestSet = new JMenuItem("test this set");
		
		// menu bar
		menuBar.add(menu);
		menu.add(refreshSetsList);
		refreshSetsList.addActionListener(this);
		menu.add(overwrite);
		overwrite.addActionListener(this);
		menu.add(menuTestSet);
		menuTestSet.addActionListener(this);
		
		menuBar.add(sets);
		sets.add(menuNewSet);
		menuNewSet.addActionListener(this);
		sets.add(menuAdd);
		menuAdd.addActionListener(this);
		sets.add(menuRemoveSet);
		menuRemoveSet.addActionListener(this);
		sets.add(menuRemoveBlock);
		menuRemoveBlock.addActionListener(this);
		
		add(blockSetsPanel, BorderLayout.CENTER);
		
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setJMenuBar(menuBar);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == refreshSetsList)
		{
			blockSetsPanel.refreshBlockSetsComboBox();
		}
		else if (e.getSource() == menuNewSet)
		{
			blockSetsPanel.createNewSet();
		}
		else if (e.getSource() == menuAdd)
		{
			blockSetsPanel.addBlock();
		}
		else if (e.getSource() == menuRemoveSet)
		{
			blockSetsPanel.removeSet(blockSetsPanel.getSelectedSetIndex());
		}
		else if (e.getSource() == menuRemoveBlock)
		{
			blockSetsPanel.removeBlock(blockSetsPanel.getSelectedBlockIndex());
		}
		else if (e.getSource() == overwrite)
		{
			blockSetsPanel.saveImageData();
			blockSetsPanel.saveTextData();
		}
		else if (e.getSource() == menuTestSet)
		{
			blockSetsPanel.testSetSource(blockSetsPanel.getSelectedSetIndex());
		}
		
	}
	
}
