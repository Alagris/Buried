package net.editor.worlds;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.editor.main.DerpyIsBestPony;
import net.swing.src.data.Files;
import net.swing.src.data.SaveOrigin;

public final class WorldListPanel extends JPanel implements ActionListener
{
	
	/**
	 * 
	 */
	private static final long				serialVersionUID		= 1L;
	
	public final DefaultListModel<String>	listModel;
	private final JList<String>				worlds;
	private final JScrollPane				listScroller;
	private final JLabel					text;
	private final JPanel					buttonsLabel;
	private final JButton					add, remove;
	private JTextField						newWorldName;
	/** Index of last selected world on the list */
	private int								lastSelectedValueIndex	= -1;
	
	public WorldListPanel()
	{
		listModel = new DefaultListModel<String>();
		worlds = new JList<String>(listModel);
		listScroller = new JScrollPane(worlds);
		worlds.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		text = new JLabel("List of worlds:");
		newWorldName = new JTextField();
		buttonsLabel = new JPanel();
		add = new JButton("add");
		remove = new JButton("remove");
		add.setEnabled(false);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		buttonsLabel.setLayout(layout);
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(3, 3, 3, 3);
		c.ipadx = 5;
		c.ipady = 5;
		c.weightx = 1;
		c.weighty = 1;
		
		c.gridx = 0;
		c.gridy = 0;
		buttonsLabel.add(add, c);
		add.addActionListener(this);
		c.gridx = 1;
		c.gridy = 0;
		buttonsLabel.add(remove, c);
		remove.addActionListener(this);
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		buttonsLabel.add(newWorldName, c);
		newWorldName.setToolTipText("type here name of the new world");
		newWorldName.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e)
			{
			}
			
			@Override
			public void keyTyped(KeyEvent e)
			{
			}
			
			@Override
			public void keyReleased(KeyEvent e)
			{
				checkAddFiled();
			}
			
		});
		
		setLayout(new BorderLayout());
		
		add(text, BorderLayout.PAGE_START);
		add(listScroller, BorderLayout.CENTER);
		add(buttonsLabel, BorderLayout.PAGE_END);
		
		worlds.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				if (worlds.isSelectionEmpty()) return;
				if (lastSelectedValueIndex == worlds.getSelectedIndex()) return;
				if (DerpyIsBestPony.general.worldList.worldsEditor.isSaveEnabled())
				{
					int response = JOptionPane.showConfirmDialog(getParent(), "This world has unsaved changes.\nDo you want do save them?", "Unsaved data", JOptionPane.YES_NO_CANCEL_OPTION);
					
					if (response == JOptionPane.YES_OPTION)
					{
						DerpyIsBestPony.general.worldList.worldsEditor.save();
					}
					else if (response == JOptionPane.CANCEL_OPTION)
					{
						worlds.setSelectedIndex(lastSelectedValueIndex);
						return;
					}
				}
				lastSelectedValueIndex = worlds.getSelectedIndex();
				DerpyIsBestPony.general.worldList.worldsEditor.refresh(worlds.getSelectedValue());
			}
		});
		
		refreshWorlds();
	}
	
	private void checkAddFiled()
	{
		if (Files.validateName(newWorldName.getText()))
		{
			add.setEnabled(true);
			return;
		}
		add.setEnabled(false);
	}
	
	public void refreshWorlds()
	{
		listModel.clear();
		for (File f : new File("saves").listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name)
			{
				return new File(dir.getName() + "/" + name + "/info.txt").exists();
			}
		}))
		{
			listModel.addElement(f.getName());
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		
		if (e.getSource() == add)
		{
			String t = newWorldName.getText();
			for (int i = 0; i < worlds.getModel().getSize(); i++)
			{
				if (worlds.getModel().getElementAt(i).equals(t))
				{
					JOptionPane.showMessageDialog(this, "This world already exists!", "Error", JOptionPane.OK_OPTION);
					return;
				}
			}
			SaveOrigin saveOrigin = new SaveOrigin(t);
			saveOrigin.createSave();
			refreshWorlds();
		}
		else if (e.getSource() == remove)
		{
			String selectedWorld = worlds.getSelectedValue();
			if (selectedWorld != null)
			{
				if (JOptionPane.showConfirmDialog(this, "Are you sure you want to delete: " + selectedWorld, "Remove world", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				{
					SaveOrigin saveOrigin = new SaveOrigin(selectedWorld);
					saveOrigin.deleteWholeSave();
					clearSelection();
					listModel.removeElement(selectedWorld);
				}
			}
		}
	}
	
	public void renameWorld(String oldWorld, String newWorld)
	{
		try
		{
			listModel.setElementAt(newWorld, listModel.indexOf(oldWorld));
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			e.printStackTrace();
			System.err.println("Refreshing started!");
			refreshWorlds();
		}
	}
	
	public void clearSelection()
	{
		DerpyIsBestPony.general.worldList.worldsEditor.resetAll();
		worlds.clearSelection();
		lastSelectedValueIndex = -1;
	}
	
}
