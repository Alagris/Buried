package net.editor.room;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;

import net.swing.src.data.SaveOrigin;

public class RoomEditorGround extends JInternalFrame implements ActionListener
{
	
	/**
	 * 
	 */
	private static final long		serialVersionUID	= 1L;
	
	public RoomEditorPanel			editorPanel;
	public Map						map;
	private JSplitPane				splitPane;
	
	// menu bar items
	private JMenuBar				menuBar;
	
	private JMenu					menu;
	private JMenuItem				save;
	private JMenuItem				refresh;
	
	/***/
	public static final Dimension	minDim				= new Dimension(100, 1);
	
	public RoomEditorGround()
	{
		super("Room editor", true, true, true, true);
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		menuBar = new JMenuBar();
		
		menu = new JMenu("Menu");
		save = new JMenuItem("Save");
		save.addActionListener(this);
		refresh = new JMenuItem("Refresh");
		refresh.addActionListener(this);
		
		menu.add(save);
		menu.add(refresh);
		menuBar.add(menu);
		setJMenuBar(menuBar);
		
		map = new Map();
		editorPanel = new RoomEditorPanel();
		
		map.setPreferredSize(minDim);
		map.setMinimumSize(minDim);
		
		editorPanel.setPreferredSize(minDim);
		editorPanel.setMinimumSize(minDim);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, editorPanel, map);
		
		add(splitPane, BorderLayout.CENTER);
	}
	
	public void loadRoom(SaveOrigin saveOrigin, String room)
	{
		map.loadRoom(saveOrigin, room);//
		editorPanel.load(saveOrigin, room);
		
	}
	
	public void save()
	{
		map.saveAll();
		editorPanel.properties.save();
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == save)
		{
			save();
		}
		else if (e.getSource() == refresh)
		{
			map.refresh();
		}
	}
	
}
