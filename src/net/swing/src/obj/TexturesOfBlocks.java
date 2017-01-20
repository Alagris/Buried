package net.swing.src.obj;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.LunarEngine2DMainClass;
import net.swing.engine.graph.TexturesBase;
import net.swing.src.data.DataCoder;
import net.swing.src.data.Files;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;

public final class TexturesOfBlocks
{
	
	/** names of blocks */
	public final String[]	blocks;
	/** Textures of blocks **/
	public final Texture[]	blockTextures;
	
	/**
	 * WARNING screen.initializeStates() is required here
	 * 
	 * @param texturesSource
	 *            - name of set with all textures and names of blocks to load
	 */
	public TexturesOfBlocks(String texturesSource)
	{
		
		File texturesFile = new File(Files.BLOCK_TEX_FOLDER.f + "/" + texturesSource + ".png");
		File namesFile = new File(Files.BLOCK_TEX_FOLDER.f + "/" + texturesSource + ".txt");
		
		if (!texturesFile.exists() || !namesFile.exists())
		{
			LunarEngine2DMainClass.getScreen().setErrorStateAndBlock("Textures data doesn't exist!");
			blockTextures = new Texture[0];
			blocks = new String[0];
			return;
		}
		
		BufferedImage sprites = null;
		try
		{
			sprites = ImageIO.read(texturesFile);
		}
		catch (IOException e)
		{
			LunarEngine2DMainClass.getScreen().setErrorStateAndBlock(e.getMessage());
			e.printStackTrace();
		}
		/** width and height of block texture are equal */
		int blocksize = sprites.getHeight();
		/** Total quantity of blocks */
		int texturesQuantity = (sprites.getWidth() / blocksize);
		
		blockTextures = new Texture[texturesQuantity];
		blocks = DataCoder.readLinesArray(namesFile, texturesQuantity);
		try
		{
			for (int i = 0; i < texturesQuantity; i++)
			{
				// TODO: set GL_NEAREST (import static org.lwjgl.opengl.GL11.*;)
				blockTextures[i] = BufferedImageUtil.getTexture("", sprites.getSubimage(i * blocksize, 0, blocksize, blocksize));
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	// ========OLD VERSION OF THIS CONSTRUCTOR==========
	// If something is not working look here because this constructor always
	// worked fine
	// /**
	// * WARNING screen.initializeStates() is required here
	// *
	// * @param texturesSource
	// * - name of set with all textures and names of blocks to load
	// */
	// public TexturesOfBlocks(String texturesSource)
	// {
	//
	// File texturesFile = new File(Files.blockTexFolder.f + "/" +
	// texturesSource + ".png");
	// File namesFile = new File(Files.blockTexFolder.f + "/" + texturesSource +
	// ".txt");
	//
	// if (!texturesFile.exists() || !namesFile.exists())
	// {
	// BuriedMain.getScreen().setErrorStateAndBlock("Textures data doesn't exist!");
	// blockTextures = new Texture[0];
	// blocks = new String[0];
	// return;
	// }
	//
	// BufferedImage sprites = null;
	// Scanner sc = null;
	// try
	// {
	// sprites = ImageIO.read(texturesFile);
	// }
	// catch (IOException e)
	// {
	// BuriedMain.getScreen().setErrorStateAndBlock(e.getMessage());
	// e.printStackTrace();
	// }
	// try
	// {
	// sc = new Scanner(namesFile);
	// }
	// catch (FileNotFoundException e)
	// {
	// BuriedMain.getScreen().setErrorStateAndBlock(e.getMessage());
	// e.printStackTrace();
	// }
	// /** width and height of block texture are equal */
	// int blocksize = sprites.getHeight();
	// /** Total quantity of blocks */
	// int texturesQuantity = (int) (sprites.getWidth() / blocksize);
	//
	// blockTextures = new Texture[texturesQuantity];
	// blocks = new String[texturesQuantity];
	// try
	// {
	// for (int i = 0; sc.hasNext() && i < texturesQuantity; i++)
	// {
	// blocks[i] = sc.nextLine();
	// blockTextures[i] = BufferedImageUtil.getTexture("", sprites.getSubimage(i
	// * blocksize, 0, blocksize, blocksize));
	// }
	// sc.close();
	// }
	//
	// catch (IOException e)
	// {
	// e.printStackTrace();
	// }
	// }
	
	public Texture getTexture(String blockName)
	{
		for (int i = 0; i < blocks.length; i++)
		{
			if (blocks[i].equals(blockName))
			{
				return blockTextures[i];
			}
		}
		return TexturesBase.EMERGENCY.texture;
	}
	
	/**
	 * builds new block (ent.swing.src.obj.Block) using data previously
	 * collected
	 * 
	 * @param index
	 *            - specifies which texture and name to take from array
	 * @param id
	 *            - created block will have this id
	 */
	public Block buildBlock(int index, int id)
	{
		if (index <= blocks.length) if (index >= 0) if (id >= 0) return new Block(id, blocks[index], blockTextures[index]);
		return Things.air;
	}
	
	public int getSize()
	{
		return blocks.length;
	}
	
	public void cleanUp()
	{
		for (Texture t : blockTextures)
		{
			t.release();
		}
	}
}
