package net.editor.main;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import net.editor.blocksets.BlockSetsGround;
import net.editor.ents.EntsEditorGround;
import net.editor.room.RoomEditorGround;
import net.editor.worlds.WorldListGround;

public class GeneralWindow extends JFrame implements ActionListener
{
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	private JDesktopPane		internalFrames		= new JDesktopPane();
	// All internal frames possible to open
	public BlockSetsGround		blockSetsWindow;
	public WorldListGround		worldList;
	public RoomEditorGround		roomEditor;
	public EntsEditorGround		entsEditorGround;
	
	// menu bar items
	private JMenuBar			menuBar				= new JMenuBar();
	
	private JMenu				menu				= new JMenu("menu");
	private JMenuItem			menuExit			= new JMenuItem("exit");
	
	private JMenu				windows				= new JMenu("windows");
	private JMenuItem			openBlockSets		= new JMenuItem("Edit Block Sets");
	private JMenuItem			openWorldEditor		= new JMenuItem("Edit Worlds");
	private JMenuItem			openEntitiesEditor	= new JMenuItem("Edit Entities");
	private JMenuItem			openActionsEditor	= new JMenuItem("Edit Actions");
	
	public GeneralWindow()
	{
		super("Buried editor");
		
		setJMenuBar(menuBar);
		menuBar.add(menu);
		menuBar.add(windows);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(1, 0));
		
		add(internalFrames);
		
		menu.add(menuExit);
		menuExit.addActionListener(this);
		
		windows.add(openBlockSets);
		openBlockSets.addActionListener(this);
		
		windows.add(openWorldEditor);
		openWorldEditor.addActionListener(this);
		
		windows.add(openEntitiesEditor);
		openEntitiesEditor.addActionListener(this);
		
		windows.add(openActionsEditor);
		openActionsEditor.addActionListener(this);
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(d.width / 4, d.height / 4, d.width / 2, d.height / 2);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setResizable(true);
		
		setVisible(true);
	}
	
	public void openEntsEditor()
	{
		if (entsEditorGround == null)
		{
			entsEditorGround = new EntsEditorGround();
			internalFrames.add(entsEditorGround);
			resetInternal(entsEditorGround, 500, 600);
		}
		else
		{
			if (entsEditorGround.isClosed())
			{
				entsEditorGround = null;
				openEntsEditor();
				return;
			}
			else
			{
				entsEditorGround.show();
				resetInternal(entsEditorGround, 500, 600);
			}
		}
	}
	
	public void openRoomEditor()
	{
		if (roomEditor == null)
		{
			roomEditor = new RoomEditorGround();
			internalFrames.add(roomEditor);
			resetInternal(roomEditor, 500, 600);
		}
		else
		{
			if (roomEditor.isClosed())
			{
				roomEditor = null;
				openRoomEditor();
				return;
			}
			else
			{
				roomEditor.show();
				resetInternal(roomEditor, 500, 600);
			}
		}
	}
	
	public void openBlockSetsEditor()
	{
		if (blockSetsWindow == null)
		{
			blockSetsWindow = new BlockSetsGround();
			internalFrames.add(blockSetsWindow);
			resetInternal(blockSetsWindow, 500, 600);
			
		}
		else
		{
			if (blockSetsWindow.isClosed())
			{
				blockSetsWindow = null;
				openBlockSetsEditor();
				return;
			}
			else
			{
				blockSetsWindow.show();
				resetInternal(blockSetsWindow, 500, 600);
			}
		}
	}
	
	public void openWorldList()
	{
		if (worldList == null)
		{
			worldList = new WorldListGround();
			internalFrames.add(worldList);
			resetInternal(worldList, 600, 600);
			
		}
		else
		{
			if (worldList.isClosed())
			{
				worldList = null;
				openWorldList();
				return;
			}
			else
			{
				worldList.show();
				resetInternal(worldList, 600, 600);
			}
		}
	}
	
	private void resetInternal(JInternalFrame f, int w, int h)
	{
		if (!contains(f.getLocation()))
		{
			f.setLocation(0, 0);
		}
		if (f.getWidth() == 0 || f.getHeight() == 0)
		{
			f.setSize(w, h);
		}
		f.moveToFront();
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(openBlockSets))
		{
			openBlockSetsEditor();
		}
		else if (e.getSource().equals(menuExit))
		{
			System.exit(0);
		}
		else if (e.getSource().equals(openEntitiesEditor))
		{
			openEntsEditor();
		}
		else if (e.getSource().equals(openActionsEditor))
		{
			// TODO: actions editor
		}
		else if (e.getSource().equals(openWorldEditor))
		{
			openWorldList();
		}
	}
}
