package net.editor.worlds;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import net.editor.blocksets.BlockSetsPane;
import net.editor.ents.EntsPane;
import net.editor.main.DerpyIsBestPony;
import net.editor.room.RoomsPane;
import net.swing.src.data.DataCoder;
import net.swing.src.data.Files;
import net.swing.src.data.QuickRoomsManager;
import net.swing.src.data.SaveOrigin;

public final class WorldEditingPanel extends JPanel implements ActionListener
{
	
	/**
	 * 
	 */
	private static final long					serialVersionUID	= 1L;
	
	private final JTextField					worldName;
	private final DefaultComboBoxModel<String>	boxModel;
	private final JComboBox<String>				startRoom;
	private final SpinnerNumberModel			spinerModel;
	private final JSpinner						gravity;
	private final DefaultListModel<String>		blockListModel, entsListModel;
	private final JList<String>					blockSets, entsSets;
	private final JScrollPane					blockListScroller, entsListScroller;
	private final JPanel						nameLabel, startLabel, gravityLabel, setsLabel, entsLabel;
	private final JButton						editSets, editRooms, saveChanges, editMap, editEnts;
	
	/** last time loaded values (used to detect changes) */
	private String								worldLastName		= "";
	/** last time loaded values (used to detect changes) */
	private String								startRoomLastName	= "";
	/** last time loaded values (used to detect changes) */
	private ArrayList<String>					blockSetsLastUsed	= new ArrayList<String>();
	/** last time loaded values (used to detect changes) */
	private ArrayList<String>					entsSetsLastUsed	= new ArrayList<String>();
	
	public WorldEditingPanel()
	{
		GridLayout layout = new GridLayout(0, 1);
		GridLayout layoutInternal = new GridLayout(1, 0);
		layout.setHgap(5);
		layout.setVgap(5);
		setLayout(layout);
		
		// world name
		nameLabel = new JPanel();
		nameLabel.setBorder(BorderFactory.createTitledBorder("World name:"));
		nameLabel.setLayout(layoutInternal);
		worldName = new JTextField();
		nameLabel.add(worldName);
		add(nameLabel);
		
		// StartRoom selection
		startLabel = new JPanel();
		startLabel.setBorder(BorderFactory.createTitledBorder("Start room:"));
		startLabel.setLayout(layoutInternal);
		boxModel = new DefaultComboBoxModel<String>();
		startRoom = new JComboBox<String>(boxModel);
		editRooms = new JButton("Manage rooms");
		editRooms.addActionListener(this);
		editRooms.setToolTipText("Delete/create rooms in this world");
		startLabel.add(editRooms);
		startLabel.add(startRoom);
		add(startLabel);
		
		// Gravity
		spinerModel = new SpinnerNumberModel(0, 0, 1, 0.05);
		gravity = new JSpinner(spinerModel);
		gravity.setEnabled(false);
		gravityLabel = new JPanel();
		gravityLabel.setBorder(BorderFactory.createTitledBorder("Gravity acceleration:"));
		gravityLabel.setLayout(layoutInternal);
		gravityLabel.add(gravity);
		add(gravityLabel);
		
		// BlockSets
		setsLabel = new JPanel();
		setsLabel.setBorder(BorderFactory.createTitledBorder("Block sets"));
		editSets = new JButton("Manage block sets");
		editSets.setToolTipText("Add/remove block sets used in this world");
		editSets.addActionListener(this);
		setsLabel.setLayout(layoutInternal);
		setsLabel.add(editSets);
		blockListModel = new DefaultListModel<String>();
		blockSets = new JList<String>(blockListModel);
		blockListScroller = new JScrollPane(blockSets);
		setsLabel.add(blockListScroller);
		add(setsLabel);
		
		// Entities list and managing
		entsLabel = new JPanel();
		entsLabel.setBorder(BorderFactory.createTitledBorder("Entities"));
		entsLabel.setLayout(layoutInternal);
		editEnts = new JButton("Manage entities");
		editEnts.addActionListener(this);
		editEnts.setToolTipText("Add/remove entities used in this world");
		entsLabel.add(editEnts);
		entsListModel = new DefaultListModel<String>();
		entsSets = new JList<String>(entsListModel);
		entsListScroller = new JScrollPane(entsSets);
		entsLabel.add(entsListScroller);
		add(entsLabel);
		
		// Saving
		saveChanges = new JButton("Save changes");
		saveChanges.addActionListener(this);
		add(saveChanges);
		
		// Editing rooms
		editMap = new JButton("Edit Map");
		editMap.addActionListener(this);
		editMap.setToolTipText("It's always better to save world changes before editing map");
		add(editMap);
		
		disableButtons();
	}
	
	/**
	 * Erases all data from all components and variables and starts collecting
	 * this data once again for this (currently used) world.
	 */
	public void refresh()
	{
		refresh(worldName.getText());
	}
	
	/**
	 * Erases all data from all components and variables and starts collecting
	 * this data once again for selected world.
	 * 
	 * @param worldName
	 *            - selected world
	 */
	public void refresh(String worldName)
	{
		resetAll();
		if (worldName == null) return;
		setName(worldName);
		editSets.setEnabled(true);
		editRooms.setEnabled(true);
		editMap.setEnabled(true);
		editEnts.setEnabled(true);
		saveChanges.setEnabled(Files.validateName(worldName));
		for (String s : new SaveOrigin(worldName).getRoomsNames())
		{
			boxModel.addElement(s);
		}
		try
		{
			Scanner sc = new Scanner(new File("saves/" + worldName + "/info.txt"));
			String s;
			while (sc.hasNextLine())
			{
				s = sc.nextLine();
				if (s.startsWith(";SRm;"))
				{
					setStartRoom(s.replaceFirst(";SRm;", ""));
				}
				else if (s.startsWith(";TSet;"))
				{
					addBlockSet(s.replaceFirst(";TSet;", ""));
				}
				else if (s.startsWith(";Ent;"))
				{
					addEntityt(s.replaceFirst(";Ent;", ""));
				}
			}
			sc.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	/** Will return true if at least one of world parameters was changed */
	public boolean hasUnsavedChanges()
	{
		if (isWorldNameTheSame())
		{
			if (isStartRoomTheSame())
			{
				if (hasTheSameBlockSets())
				{
					if (hasTheSameEntities())
					{
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public boolean hasTheSameEntities()
	{
		if (entsListModel.size() != entsSetsLastUsed.size()) return false;
		for (int i = 0; i < entsListModel.size(); i++)
		{
			if (!entsSetsLastUsed.contains(entsListModel.getElementAt(i)))
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean hasTheSameBlockSets()
	{
		if (blockListModel.size() != blockSetsLastUsed.size()) return false;
		for (int i = 0; i < blockListModel.size(); i++)
		{
			if (!blockSetsLastUsed.contains(blockListModel.getElementAt(i)))
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean isStartRoomTheSame()
	{
		String s = startRoom.getItemAt(startRoom.getSelectedIndex());
		if (s == null) s = "";
		return startRoomLastName.equals(s);
	}
	
	public boolean isWorldNameTheSame()
	{
		String s = worldName.getText();
		if (s == null) s = "";
		return worldLastName.equals(s);
	}
	
	/**
	 * Sets value when new world data is loaded. If user change anything later
	 * old data will still be saved, so system know if any changes were made.
	 */
	public void addBlockSet(String set)
	{
		blockListModel.addElement(set);
		blockSetsLastUsed.add(set);
	}
	
	/**
	 * Sets value when new world data is loaded. If user change anything later
	 * old data will still be saved, so system know if any changes were made.
	 */
	public void addEntityt(String ent)
	{
		entsListModel.addElement(ent);
		entsSetsLastUsed.add(ent);
	}
	
	/**
	 * Sets value when new world data is loaded. If user change anything later
	 * old data will still be saved, so system know if any changes were made.
	 */
	public void setStartRoom(String room)
	{
		startRoom.setSelectedItem(room);
		if (boxModel.getSize() > 0)
		{
			startRoomLastName = startRoom.getItemAt(startRoom.getSelectedIndex());
		}
	}
	
	/**
	 * Sets value when new world data is loaded. If user change anything later
	 * old data will still be saved, so system know if any changes were made.
	 */
	@Override
	public void setName(String worldName)
	{
		worldLastName = worldName;
		this.worldName.setText(worldLastName);
	}
	
	/**
	 * Resets data in all components to null and also erases data from all those
	 * "last used" variables, so that system cannot detect whether any changes
	 * were made.
	 */
	public void resetAll()
	{
		worldName.setText("");
		boxModel.removeAllElements();
		blockListModel.removeAllElements();
		entsListModel.removeAllElements();
		
		worldLastName = "";
		startRoomLastName = "";
		blockSetsLastUsed.clear();
		entsSetsLastUsed.clear();
		
		disableButtons();
	}
	
	private void disableButtons()
	{
		
		editRooms.setEnabled(false);
		editSets.setEnabled(false);
		saveChanges.setEnabled(false);
		editMap.setEnabled(false);
		editEnts.setEnabled(false);
	}
	
	public void save()
	{
		File f = new File("saves/" + worldLastName);// world folder
		if (!isWorldNameTheSame())
		{// renaming
			if (!Files.validateName(worldName.getText()))
			{
				JOptionPane.showMessageDialog(this, "World name is invalid!");
				return;
			}
			if (f.renameTo(new File("saves/" + worldName.getText())))
			{
				DerpyIsBestPony.general.worldList.worldsList.renameWorld(worldLastName, worldName.getText());
				worldLastName = worldName.getText();
			}
		}
		
		/* Detecting changes in start room and block sets */
		boolean saveBlockSets = !hasTheSameBlockSets();
		boolean saveStartRoom = !isStartRoomTheSame();
		boolean saveEntities = !hasTheSameEntities();
		/* Checking if there is any reason for messing with info.txt */
		if (saveBlockSets || saveStartRoom || saveEntities)
		{
			forceInfoSaving(saveBlockSets, saveStartRoom, saveEntities);
		}
		
		// TODO: save gravity changes
		
	}
	
	private void forceInfoSaving(boolean saveBlockSets, boolean saveStartRoom, boolean saveEntities)
	{
		/* Creating (empty) string of data that will be saved in files */
		String dataToSaveInInfo = "";
		
		/* If sets were changed... */
		if (saveBlockSets)
		{
			/* Removing old data */
			blockSetsLastUsed.clear();
			for (int i = 0; i < blockListModel.size(); i++)
			{/* ...it will add new data to string */
				dataToSaveInInfo = dataToSaveInInfo + ";TSet;" + blockListModel.getElementAt(i) + "\n";
				/*
				 * and replace old data in array with new sets, so next time it
				 * won't detect any changes
				 */
				blockSetsLastUsed.add(blockListModel.getElementAt(i));
			}
		}
		else
		{/* ..otherwise it will add the data anyway (but it's not changed) */
			for (int i = 0; i < blockListModel.size(); i++)
			{
				dataToSaveInInfo = dataToSaveInInfo + ";TSet;" + blockSetsLastUsed.get(i) + "\n";
			}
		}
		
		/* If start room was changed... */
		if (saveStartRoom)
		{
			/* ...Changing info about start room to the new one */
			startRoomLastName = startRoom.getSelectedItem().toString();
		}
		
		if (saveEntities)
		{
			/* Removing old data */
			entsSetsLastUsed.clear();
			for (int i = 0; i < entsListModel.size(); i++)
			{/* ...it will add new data to string */
				dataToSaveInInfo = dataToSaveInInfo + ";Ent;" + entsListModel.getElementAt(i) + "\n";
				/*
				 * and replace old data in array with new sets, so next time it
				 * won't detect any changes
				 */
				entsSetsLastUsed.add(entsListModel.getElementAt(i));
			}
		}
		else
		{/* ..otherwise it will add the data anyway (but it's not changed) */
			for (int i = 0; i < entsListModel.size(); i++)
			{
				dataToSaveInInfo = dataToSaveInInfo + ";Ent;" + entsSetsLastUsed.get(i) + "\n";
			}
		}
		
		/* Adding to string info about start room */
		dataToSaveInInfo = dataToSaveInInfo + ";SRm;" + startRoomLastName;
		
		/* Writing data */
		DataCoder.write(new File("saves/" + worldLastName + "/info.txt"), dataToSaveInInfo);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == saveChanges)
		{
			save();
		}
		else if (e.getSource() == editSets)
		{
			/* Shows block set selection pane */
			BlockSetsPane pane = new BlockSetsPane(DerpyIsBestPony.general, blockListModel);
			/* Validates data */
			if (pane.getResult() == null) return;
			if (pane.isAddSet())
			{
				blockListModel.addElement(pane.getResult());
			}
			else
			{
				blockListModel.removeElement(pane.getResult());
			}
		}
		else if (e.getSource() == editRooms)
		{
			/* Shows block set selection pane */
			RoomsPane pane = new RoomsPane(DerpyIsBestPony.general, new SaveOrigin(worldLastName), "Remove");
			/* Validates data */
			if (pane.getResult() == null) return;
			if (pane.isAddRoom())
			{
				QuickRoomsManager.createDefaultRoom(pane.getResult(), new SaveOrigin(worldLastName));
				boxModel.addElement(pane.getResult());
			}
			else
			{
				if (!QuickRoomsManager.deleteRoom(pane.getResult(), new SaveOrigin(worldLastName)))
				{
					JOptionPane.showMessageDialog(this, "An error occured while removing: " + pane.getResult(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				boxModel.removeElement(pane.getResult());
			}
		}
		else if (e.getSource() == editMap)
		{
			String roomNameToEdit = "";
			
			if (startRoom.getSelectedIndex() == -1)
			{
				roomNameToEdit = JOptionPane.showInputDialog(this, "This world (" + worldLastName + ") has no rooms. Select name of\n new room that is going to be created now.", "No start room", JOptionPane.QUESTION_MESSAGE);
				/*
				 * Checking if cancel option was selected
				 */
				if (roomNameToEdit == null) return;
				if (!Files.validateName(roomNameToEdit))
				{
					JOptionPane.showMessageDialog(this, roomNameToEdit + " is invalid name for room!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				QuickRoomsManager.createDefaultRoom(roomNameToEdit, new SaveOrigin(worldLastName));
				startRoom.addItem(roomNameToEdit);
				forceInfoSaving(false, true, false);
			}
			else
			{
				roomNameToEdit = startRoom.getSelectedItem().toString();
			}
			if (blockSetsLastUsed.size() == 0)
			{
				if (blockSets.getModel().getSize() > 0)
				{
					JOptionPane.showMessageDialog(this, "Make sure that added block sets are saved!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(this, "This world has no block sets!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				return;
			}
			
			DerpyIsBestPony.general.openRoomEditor();
			DerpyIsBestPony.general.roomEditor.loadRoom(new SaveOrigin(worldLastName), roomNameToEdit);
		}
		else if (e.getSource() == editEnts)
		{
			/* Shows ents selection pane */
			EntsPane pane = new EntsPane(DerpyIsBestPony.general, entsListModel);
			/* Validates data */
			if (pane.getResult() == null) return;
			if (pane.isAddEnt())
			{
				entsListModel.addElement(pane.getResult());
			}
			else
			{
				entsListModel.removeElement(pane.getResult());
			}
		}
	}
	
	/**
	 * Will returns true if all parameters are valid and saving them won't cause
	 * any errors AND if at least one of those parameters was changed.
	 */
	public boolean isSaveEnabled()
	{
		if (hasUnsavedChanges())
		{
			return isSavePossible();
		}
		return false;
	}
	
	/**
	 * Returns true is all parameters are valid and saving them won't cause any
	 * errors
	 */
	public boolean isSavePossible()
	{
		return Files.validateName(worldName.getText());
	}
}
