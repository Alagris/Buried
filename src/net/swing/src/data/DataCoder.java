package net.swing.src.data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public final class DataCoder
{
	private DataCoder()
	{
	}
	
	/**
	 * List of markers: R - room floor data C - room collision areas data
	 */
	
	/**
	 * @param marker
	 *            - find list of markers in DataCoder class
	 * @throws IllegalArgumentException
	 *             when marker is invalid (must be digit or letter)
	 */
	public static String codeData(int[] data, char marker) throws IllegalArgumentException
	{
		if (!Character.isLetterOrDigit(marker)) throw new IllegalArgumentException();
		String s = marker + "=";
		int lastID = data[0];
		int tick = 1;
		for (int i = tick; i < data.length; i++)
		{
			if (data[i] != lastID)
			{
				if (tick == 1)
				{
					s = s + lastID + "=";
				}
				else
				{
					s = s + lastID + "m" + tick + "=";
				}
				tick = 1;
			}
			else
			{
				tick++;
			}
			lastID = data[i];
		}
		if (tick == 1)
		{
			s = s + data[data.length - 1] + "=E";
		}
		else
		{
			s = s + data[data.length - 1] + "m" + tick + "=E";
		}
		return s;
	}
	
	public static char detectMarker(String data)
	{
		return data.charAt(0);
	}
	
	/**
	 * Parses matrix data that was coded using codeData() method
	 * 
	 * @param matrixSize
	 *            - size of matrix that this data represent
	 */
	public static int[] parseData(String data, int matrixSize)
	{
		int[] matrix = new int[matrixSize];
		// Reading data
		// Parsing
		data = data.replace(detectMarker(data) + "=", "");
		data = data.replace("=E", "");
		String[] parts = data.toString().split("=");
		int id, quantity, matrixIndex = 0;
		for (String part : parts)
		{
			if (part.contains("m"))
			{
				id = Integer.parseInt(part.split("m")[0]);
				quantity = Integer.parseInt(part.split("m")[1]);
				for (int i = 0; i < quantity; i++)
				{
					matrix[matrixIndex] = id;
					matrixIndex++;
				}
			}
			else
			{
				matrix[matrixIndex] = Integer.parseInt(part);
				matrixIndex++;
			}
			
		}
		return matrix;
	}
	
	/**
	 * Parses matrix data that was coded using codeData() method
	 * 
	 * @param matrixSize
	 *            - size of matrix that this data represent
	 * @param maxID
	 *            - makes that only ID between 0 -maxID are accepted and id
	 *            there is maxDI+1 it will be changed to 0
	 */
	public static int[] parseData(String data, int matrixSize, int maxID)
	{
		int[] matrix = new int[matrixSize];
		// Reading data
		// Parsing
		data = data.replace(detectMarker(data) + "=", "");
		data = data.replace("=E", "");
		String[] parts = data.toString().split("=");
		int id, quantity, matrixIndex = 0;
		for (String part : parts)
		{
			if (part.contains("m"))
			{
				id = Integer.parseInt(part.split("m")[0]);
				quantity = Integer.parseInt(part.split("m")[1]);
				for (int i = 0; i < quantity; i++)
				{
					matrix[matrixIndex] = id > maxID || id < 0 ? 0 : id;
					matrixIndex++;
				}
			}
			else
			{
				id = Integer.parseInt(part);
				matrix[matrixIndex] = id > maxID || id < 0 ? 0 : id;
				matrixIndex++;
			}
			
		}
		return matrix;
	}
	
	/** lines start with 1 */
	public static String read(File roomFile, int line)
	{
		String s;
		try
		{
			Scanner sc = new Scanner(roomFile);
			int i = 1;
			while (sc.hasNextLine())
			{
				s = sc.nextLine();
				if (i == line)
				{
					sc.close();
					return s;
				}
				i++;
			}
			sc.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			
		}
		
		return null;
	}
	
	public static String read(File roomFile)
	{
		String data = "";
		try
		{
			Scanner sc = new Scanner(roomFile);
			while (sc.hasNext())
			{
				data = data + sc.next();
			}
			sc.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return data;
	}
	
	/** Reads everything including next line characters (\n and other like this) */
	public static String readAll(File file)
	{
		String data = "";
		Scanner sc;
		try
		{
			sc = new Scanner(file);
			if (sc.hasNext())
			{
				sc.useDelimiter("\\Z");
				data = sc.next();
			}
			sc.close();
			
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		return data;
	}
	
	public static void write(File file, String data) throws SecurityException
	{
		try
		{
			PrintWriter pw = new PrintWriter(file);
			pw.print(data);
			pw.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public static String[] readLinesArray(File file, int linesQuantity)
	{
		String[] data = new String[linesQuantity];
		try
		{
			Scanner sc = new Scanner(file);
			for (int i = 0; sc.hasNext() && i < linesQuantity; i++)
			{
				data[i] = sc.nextLine();
			}
			sc.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * It's much more recommended to use readLinesArray(File file,<b>int
	 * linesQuantity</b>)
	 */
	public static String[] readLinesArray(File file)
	{
		return readLinesArrayList(file).toArray(new String[0]);
	}
	
	public static ArrayList<String> readLinesArrayList(File file)
	{
		ArrayList<String> data = new ArrayList<>();
		try
		{
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine())
			{
				data.add(sc.nextLine());
			}
			sc.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return data;
	}
	
	/** Array will contain only those lines that start with filter string. */
	public static String[] readLinesArray(File file, String filter)
	{
		return readLinesArrayList(file, filter).toArray(new String[0]);
	}
	
	/** Array will contain only those lines that start with filter string */
	public static ArrayList<String> readLinesArrayList(File file, String filter)
	{
		ArrayList<String> data = new ArrayList<>();
		try
		{
			String s;
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine())
			{
				s = sc.nextLine();
				if (s.startsWith(filter))
				{
					data.add(s.replaceFirst(filter, ""));
				}
			}
			sc.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return data;
	}
	
	public static void write(File file, String[] lines) throws SecurityException
	{
		try
		{
			PrintWriter pw = new PrintWriter(file);
			for (String line : lines)
			{
				pw.print(line + "\n");
			}
			pw.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
	}
	
	/** prefix is added at the beginning of each line */
	public static void write(File file, String[] lines, String prefix) throws SecurityException
	{
		try
		{
			PrintWriter pw = new PrintWriter(file);
			for (String line : lines)
			{
				pw.print(prefix + line + "\n");
			}
			pw.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public static String readFirstLineWith(File file, String lineBeginning)
	{
		try
		{
			String s;
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine())
			{
				s = sc.nextLine();
				if (s.startsWith(lineBeginning))
				{
					sc.close();
					return s;
				}
			}
			sc.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static String readFirstLine_CutPrefix(File file, String lineBeginning)
	{
		return readFirstLineWith(file, lineBeginning).replaceFirst(lineBeginning, "");
	}
	
	/**
	 * Copies data from one file and pastes it to another. If destination file
	 * doesn't exists it will be created. If source file doesn't exist method
	 * will stop.
	 * 
	 * @param from
	 *            - source of data
	 * @param to
	 *            - place where data will be copied to
	 * @throws IOException
	 */
	public static void copyDataFromTo(File from, File to) throws IOException
	{
		if (from.exists())
		{
			if (!to.exists())
			{
				to.createNewFile();
			}
			write(to, readAll(from));
		}
		
	}
	
	/**
	 * textures source is name of set (not of PNG file).Splits an image
	 * vertically into array of smaller square images. Width of source image
	 * must be divisible by its height
	 * 
	 * @throws IOException
	 */
	public static BufferedImage[] getBufferedImgsOfBlocks(String texturesSource) throws IOException
	{
		return splitToSquares(readBlocksSprite(texturesSource));
	}
	
	public static BufferedImage readBlocksSprite(String blockSet) throws IOException
	{
		return ImageIO.read(new File(Files.BLOCK_TEX_FOLDER.f + "/" + blockSet + ".png"));
	}
	
	/**
	 * Splits an image vertically into array of smaller square images. Width of
	 * source image must be divisible by its height
	 */
	public static BufferedImage[] splitToSquares(BufferedImage source)
	{
		return splitBufferedImage(findSquaresQuantity(source), source);
	}
	
	/**
	 * Calculates how many smaller square images would be returned if source
	 * image was split vertically
	 */
	public static int findSquaresQuantity(BufferedImage source)
	{
		return (source.getWidth() / source.getHeight());
	}
	
	/**
	 * Splits buffered image into array of its parts. Source image is divided
	 * vertically.
	 */
	public static BufferedImage[] splitBufferedImage(int partsNumber, BufferedImage source)
	{
		BufferedImage[] buffers = new BufferedImage[partsNumber];
		int partWidth = source.getWidth() / partsNumber;
		for (int i = 0; i < partsNumber; i++)
		{
			buffers[i] = source.getSubimage(i * partWidth, 0, partWidth, source.getHeight());
		}
		return buffers;
	}
	
	/**
	 * @param texturesSources
	 *            Names of set block sets (not of PNG files).
	 * @throws IOException
	 */
	public static BufferedImage[] getBufferedImgsOfBlocks(String[] texturesSources) throws IOException
	{
		/*
		 * Creates an array with default capacity texturesSources.length*10
		 * because 10 is the estimated minimal quantity that block sets could
		 * have
		 */
		ArrayList<BufferedImage> collectedBlocks = new ArrayList<BufferedImage>(texturesSources.length * 10);
		for (String set : texturesSources)
		{
			for (BufferedImage img : getBufferedImgsOfBlocks(set))
			{
				collectedBlocks.add(img);
			}
		}
		
		return collectedBlocks.toArray(new BufferedImage[0]);
	}
	
	/**
	 * @param texturesSources
	 *            Names of set block sets (not of PNG files).
	 * @throws IOException
	 */
	public static ImageIcon[] getIconsOfBlocks(String[] texturesSources) throws IOException
	{
		ArrayList<ImageIcon> collectedBlocks = new ArrayList<ImageIcon>();
		for (String set : texturesSources)
		{
			for (BufferedImage img : getBufferedImgsOfBlocks(set))
			{
				collectedBlocks.add(new ImageIcon(img));
			}
		}
		
		return collectedBlocks.toArray(new ImageIcon[0]);
	}
	
	public static boolean eraseFolder(File folder)
	{
		
		for (File f : folder.listFiles())
		{
			if (f.isDirectory())
			{
				eraseFolder(f);
			}
			else
			{
				f.delete();
			}
		}
		return folder.delete();
	}
	
	public static void removeEmptyLines(File f, int minLinelength)
	{
		try
		{
			ArrayList<String> data = new ArrayList<>();
			
			Scanner sc = new Scanner(f);
			
			while (sc.hasNextLine())
			{
				data.add(sc.nextLine());
			}
			sc.close();
			
			PrintWriter pw = new PrintWriter(f);
			for (String line : data)
			{
				if (line.length() >= minLinelength) pw.print(line + "\n");
			}
			pw.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
