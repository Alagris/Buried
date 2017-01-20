package net.editor.blocksets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

public final class BlockSetsPanel extends JPanel implements ActionListener, ListSelectionListener, ItemListener, KeyListener
{
	
	/**
	 * 
	 */
	private static final long				serialVersionUID	= 1L;
	
	public final JComboBox<String>			blockSets;
	private final JFileChooser				chooser;
	/** Currently loaded texture (with all sprites) */
	private BufferedImage					blocksTexturesArray;
	/** array of all detected blocks (editable model) */
	private final DefaultListModel<String>	listModel;
	/** list of all detected blocks (component) */
	private final JList<String>				list;
	private final JScrollPane				listScroller;
	/**
	 * Area where name of currently selected block is displayed (and its
	 * possible to rename it)
	 */
	private final JTextField				nameField;
	
	/** Here is displayed texture of selected block */
	private final JLabel					display;
	
	// buttons ;
	private final JButton					add;
	private final JButton					remove;
	private final JButton					rename;
	
	public BlockSetsPanel()
	{
		
		blockSets = new JComboBox<String>();
		chooser = new JFileChooser();
		blocksTexturesArray = null;
		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		listScroller = new JScrollPane(list);
		nameField = new JTextField("no selection");
		display = new JLabel("no selection");
		add = new JButton("add block");
		remove = new JButton("remove block");
		rename = new JButton("rename");
		
		if (!new File(BlockSetsPane.path).exists())
		{
			JOptionPane.showMessageDialog(this, "Resources folder not found!\nMake sure that this program is in the folder with all Buried files!", "Bad location", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		
		// file chooser
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setMultiSelectionEnabled(true);
		chooser.setFileHidingEnabled(true);
		chooser.setFileFilter(new FileFilter() {
			@Override
			public String getDescription()
			{
				return "images filter";
			}
			
			@Override
			public boolean accept(File f)
			{
				if (f.isFile())
				{
					switch (f.getName().toLowerCase().split("\\.")[1])
					{
						case "jpg":
							return true;
						case "jpeg":
							return true;
						case "png":
							return true;
						case "bmp":
							return true;
					}
					return false;
				}
				return true;
			}
		});
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(listModel);
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		list.setVisibleRowCount(-1);
		list.addListSelectionListener(this);
		
		blockSets.addItemListener(this);
		refreshBlockSetsComboBox();
		
		nameField.setEditable(true);
		nameField.setEnabled(false);
		nameField.addKeyListener(this);
		
		// buttons
		display.setEnabled(false);
		remove.setEnabled(false);
		add.addActionListener(this);
		remove.addActionListener(this);
		rename.addActionListener(this);
		rename.setEnabled(false);
		
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(layout);
		
		c.fill = GridBagConstraints.BOTH;
		// 2 columns, 3 rows
		
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 0;
		add(blockSets, c);
		
		c.weightx = 2;
		c.weighty = 2;
		c.gridwidth = 1;
		c.gridheight = 5;
		c.gridx = 0;
		c.gridy = 1;
		add(listScroller, c);
		
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 6;
		add(nameField, c);
		
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 6;
		add(rename, c);
		
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 1;
		c.gridy = 1;
		add(add, c);
		c.gridy = 2;
		add(remove, c);
		c.gridheight = 2;
		c.gridy = 3;
		add(display, c);
		
		if (blockSets.getModel().getSize() > 0)
		{
			fillListWidthBlocks();
		}
		
	}
	
	/**
	 * Clears previous data and loads all detected sets of blocks to JComboBox
	 * "blockSets".
	 */
	public void refreshBlockSetsComboBox()
	{
		DefaultComboBoxModel<String> m = new DefaultComboBoxModel<String>();
		for (String s : BlockSetsPane.scanForSets())
		{
			m.addElement(s);
		}
		blockSets.setModel(m);
	}
	
	/** Call this method every time user selects another source do edit */
	public void fillListWidthBlocks()
	{
		// checking if there is anything to fill array with
		if (blockSets.getModel().getSize() != 0)
		{
			try
			{// reading image data
				blocksTexturesArray = ImageIO.read(new File(BlockSetsPane.path + blockSets.getItemAt(blockSets.getSelectedIndex()) + ".png"));
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			// preparing list
			listModel.clear();
			Scanner sc;
			try
			{
				sc = new Scanner(new File(BlockSetsPane.path + blockSets.getItemAt(blockSets.getSelectedIndex()) + ".txt"));
				while (sc.hasNextLine())
				{
					listModel.addElement(sc.nextLine());
				}
				sc.close();
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == rename)
		{
			renameBlock(list.getSelectedIndex(), nameField.getText());
			rename.setEnabled(false);
			rename.setBackground(Color.GREEN);
		}
		else if (e.getSource() == add)
		{
			addBlock();
		}
		else if (e.getSource() == remove)
		{
			removeBlock(list.getSelectedIndex());
		}
		
	}
	
	public void createNewSet()
	{
		createNewSet(JOptionPane.showInputDialog(this, "New set name:"));
	}
	
	public void createNewSet(String setName)
	{
		if (setName == null) return;
		if (setName.length() < 1) return;
		File f = new File(BlockSetsPane.path + "/" + setName + ".txt");
		if (f.exists())
		{
			JOptionPane.showMessageDialog(this, "this set already exists!");
			return;
		}
		try
		{
			f.createNewFile();
			File ff = new File(BlockSetsPane.path + "/" + setName + ".png");
			ff.createNewFile();
			blockSets.addItem(ff.getName().split("\\.")[0]);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void removeSet(int setIndex)
	{
		String setName = getSetName(setIndex);
		if (JOptionPane.showConfirmDialog(this, "Do you want to remove " + setName + " ?", "Remove set", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)
		{
			File f = new File(BlockSetsPane.path + "/" + setName + ".txt");
			if (f.exists())
			{
				f.delete();
			}
			f = new File(BlockSetsPane.path + "/" + setName + ".png");
			if (f.exists())
			{
				f.delete();
			}
			listModel.clear();
			blockSets.removeItemAt(setIndex);
		}
	}
	
	public void clearSet(int setIndex)
	{
		String setName = getSetName(setIndex);
		File f = new File(BlockSetsPane.path + "/" + setName + ".txt");
		PrintWriter pw;
		if (f.exists())
		{
			try
			{
				pw = new PrintWriter(f);
				pw.print("");
				pw.close();
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		f = new File(BlockSetsPane.path + "/" + setName + ".png");
		if (f.exists())
		{
			try
			{
				pw = new PrintWriter(f);
				pw.print("");
				pw.close();
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		listModel.clear();
		blocksTexturesArray = null;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		if (list.getSelectedValue() == null)
		{
			nameField.setEnabled(false);
			display.setEnabled(false);
			remove.setEnabled(false);
			nameField.setText("no selection");
		}
		else
		{
			nameField.setEnabled(true);
			display.setEnabled(true);
			remove.setEnabled(true);
			nameField.setText(list.getSelectedValue());
			displayBlock(list.getSelectedIndex());
		}
		rename.setEnabled(false);
		rename.setBackground(null);
	}
	
	/**
	 * adds block to currently edited set (opens file chooser where user can
	 * select texture)
	 */
	public void addBlock()
	{
		if (blockSets.getModel().getSize() == 0)
		{
			JOptionPane.showMessageDialog(this, "Ooops. There is no set to add block to.\nCreate new set at first.");
			createNewSet();
		}
		else
		{
			chooser.showOpenDialog(this);
			for (File f : chooser.getSelectedFiles())
			{
				addBlock(f);
			}
		}
	}
	
	/** adds block to currently edited set */
	private void addBlock(File textureFile)
	{
		if (textureFile == null) return;
		if (blocksTexturesArray == null)
		{
			try
			{
				blocksTexturesArray = ImageIO.read(textureFile);
				if (blocksTexturesArray.getWidth() != blocksTexturesArray.getHeight())
				{
					JOptionPane.showMessageDialog(this, "Width and height of the image must be the same!");
					return;
				}
				double d = Math.log(blocksTexturesArray.getWidth()) / Math.log(2);
				if (d != (int) d)
				{
					JOptionPane.showMessageDialog(this, "Width and height of the image must be\n power of 2 (like 2,4,8,16,32...)! \n Unsupported Width=" + blocksTexturesArray.getWidth());
					blocksTexturesArray = null;
					return;
				}
				
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			saveImageData();
		}
		else
		{
			BufferedImage img = null, resized;
			try
			{
				img = ImageIO.read(textureFile);
				if (img.getHeight() != blocksTexturesArray.getHeight() || img.getWidth() != blocksTexturesArray.getHeight())
				{
					if (JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(this, "Textures of blocks added to this set\nmust have width and height equal to\n" + blocksTexturesArray.getHeight() + "\n" + textureFile.getName() + " is "
							+ img.getWidth() + "x" + img.getHeight() + "\n Do you want to resize this image?", "Incorrect size", JOptionPane.YES_NO_OPTION))
					{
						return;
					}
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			resized = new BufferedImage(blocksTexturesArray.getWidth() + blocksTexturesArray.getHeight(), blocksTexturesArray.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g = resized.createGraphics();
			g.drawImage(blocksTexturesArray, 0, 0, blocksTexturesArray.getWidth(), blocksTexturesArray.getHeight(), null);
			g.drawImage(img, blocksTexturesArray.getWidth(), 0, blocksTexturesArray.getHeight(), blocksTexturesArray.getHeight(), null);
			g.dispose();
			
			blocksTexturesArray = resized;
			saveImageData();
		}
		listModel.addElement(textureFile.getName().split("\\.")[0]);
		saveTextData();
	}
	
	public void renameBlock(int index, String newName)
	{
		listModel.set(index, newName);
		saveTextData();
	}
	
	public void saveTextData()
	{
		try
		{
			PrintWriter pw = new PrintWriter(new File(BlockSetsPane.path + blockSets.getItemAt(blockSets.getSelectedIndex()) + ".txt"));
			for (Object s : listModel.toArray())
			{
				pw.println(s);
			}
			pw.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public void saveImageData()
	{
		if (blocksTexturesArray == null) return;
		try
		{
			ImageIO.write(blocksTexturesArray, "PNG", new File(BlockSetsPane.path + blockSets.getItemAt(blockSets.getSelectedIndex()) + ".png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent e)
	{
		if (e.getStateChange() != ItemEvent.DESELECTED)
		{
			fillListWidthBlocks();
		}
	}
	
	public void displayBlock(int index)
	{
		if (index < 0) return;
		Graphics2D g = (Graphics2D) display.getGraphics();
		g.drawImage(blocksTexturesArray, 0, 0, display.getWidth(), display.getHeight(), index * blocksTexturesArray.getHeight(), 0, (index + 1) * blocksTexturesArray.getHeight(), blocksTexturesArray.getHeight(), null);
		g.dispose();
	}
	
	public String getSelectedSetName()
	{
		return blockSets.getItemAt(blockSets.getSelectedIndex());
	}
	
	public String getSetName(int index)
	{
		return blockSets.getItemAt(index);
	}
	
	public int getSelectedSetIndex()
	{
		return blockSets.getSelectedIndex();
	}
	
	public int getSelectedBlockIndex()
	{
		return list.getSelectedIndex();
	}
	
	public void removeBlock(int selectedBlockIndex)
	{
		if (selectedBlockIndex < 0) return;
		BufferedImage cropped = null, subImage;
		Graphics2D g;
		if (selectedBlockIndex == 0)
		{
			if (listModel.getSize() == 1)
			{
				clearSet(getSelectedSetIndex());
				return;
			}
			else
			{
				cropped = new BufferedImage(blocksTexturesArray.getHeight() * (listModel.size() - 1), blocksTexturesArray.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
				g = cropped.createGraphics();
				subImage = blocksTexturesArray.getSubimage(blocksTexturesArray.getHeight(),// x
						0,// y
						blocksTexturesArray.getWidth() - cropped.getHeight(), // width
						blocksTexturesArray.getHeight());// height
				g.drawImage(subImage, 0,// x
						0,// y
						cropped.getWidth(), // width
						cropped.getHeight(), // height
						null);
				g.dispose();
			}
		}
		else
		{
			cropped = new BufferedImage(blocksTexturesArray.getHeight() * (listModel.size() - 1), blocksTexturesArray.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
			g = cropped.createGraphics();
			// drawing first part
			subImage = blocksTexturesArray.getSubimage(0,// x
					0,// y
					selectedBlockIndex * blocksTexturesArray.getHeight(), // width
					cropped.getHeight());// height
			g.drawImage(subImage, 0,// x
					0,// y
					selectedBlockIndex * blocksTexturesArray.getHeight(), // width
					cropped.getHeight(), // height
					null);
			if (selectedBlockIndex != listModel.getSize() - 1)
			{// if it is the
				// last block
				// there won't
				// be any second
				// part
				// drawing second part
				subImage = blocksTexturesArray.getSubimage((selectedBlockIndex + 1) * blocksTexturesArray.getHeight(),// x
						0,// y
						blocksTexturesArray.getWidth() - (selectedBlockIndex + 1) * blocksTexturesArray.getHeight(), // width
						blocksTexturesArray.getHeight());// height
				g.drawImage(subImage, selectedBlockIndex * blocksTexturesArray.getHeight(), // x
						0, // y
						cropped.getWidth() - selectedBlockIndex * blocksTexturesArray.getHeight(), // width
						cropped.getHeight(), // height
						null);
			}
			g.dispose();
		}
		
		blocksTexturesArray = cropped;
		saveImageData();
		listModel.removeElementAt(selectedBlockIndex);
		saveTextData();
	}
	
	@Override
	public void keyTyped(KeyEvent e)
	{
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		if (nameField.getText().equals(list.getSelectedValue()))
		{
			rename.setEnabled(false);
			rename.setBackground(null);
		}
		else
		{
			rename.setEnabled(true);
			rename.setBackground(Color.GREEN);
		}
	}
	
	public void testSetSource(String name)
	{
		int n;
		try
		{
			LineNumberReader l = new LineNumberReader(new FileReader(new File(BlockSetsPane.path + "/" + name + ".txt")));
			l.skip(Long.MAX_VALUE);
			n = l.getLineNumber();
			l.close();// counting lines
			
			BufferedImage img = ImageIO.read(new File(BlockSetsPane.path + "/" + name + ".png"));
			double d = Math.log(img.getHeight()) / Math.log(2);
			if (d == (int) d)
			{// checking if d is power of 2
				d = img.getWidth() / img.getHeight();
				if (d == (int) d)
				{// checking if all sprite sheets are square
					if (d == n)
					{// checking if all blocks have their textures
						JOptionPane.showMessageDialog(this, "Set " + name + " has no errors.\nIt's ready to be used in game.", "Correct data", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					else
					{
						JOptionPane.showMessageDialog(this, "Set " + name + " has errors!\nNumber of blocks is NOT equal to quantity of textures.", "Damaged data", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				else
				{
					JOptionPane.showMessageDialog(this, "Set " + name + " has errors!\nTextures are not square!", "Damaged data", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Set " + name + " has errors!\nHeight of textures is not power of two!", "Damaged data", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(this, "Set " + name + " has errors!\nSystem couldn't find files!", "Damaged data", JOptionPane.ERROR_MESSAGE);
	}
	
	public void testSetSource(int setIndex)
	{
		testSetSource(getSetName(setIndex));
	}
	
}
