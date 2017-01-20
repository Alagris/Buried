package net.swing.ground;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;

import javax.imageio.ImageIO;

import net.swing.src.data.Files;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class ScreenshotMaker
{
	
	private static final int			bpp			= 3;
	private static final BufferedImage	captured	= new BufferedImage(Window.getWidth(), Window.getHeight(), BufferedImage.TYPE_INT_RGB);
	
	private ScreenshotMaker()
	{
	}
	
	/** Captures screen as image and saves it to screenshots/... */
	public static void takePhoto()
	{
		try
		{
			Thread.sleep(100);
		}
		catch (InterruptedException e2)
		{
			e2.printStackTrace();
		}
		GL11.glReadBuffer(GL11.GL_FRONT);
		ByteBuffer buffer = BufferUtils.createByteBuffer(Window.getWidth() * Window.getHeight() * bpp);
		GL11.glReadPixels(0, 0, Window.getWidth(), Window.getHeight(), GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, buffer);
		for (int x = 0; x < Window.getWidth(); x++)
		{
			for (int y = 0; y < Window.getHeight(); y++)
			{
				int i = (x + (Window.getWidth() * y)) * bpp;
				int r = buffer.get(i) & 0xFF;
				int g = buffer.get(i + 1) & 0xFF;
				int b = buffer.get(i + 2) & 0xFF;
				captured.setRGB(x, Window.getHeight() - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
			}
		}
		
		File file = new File(Files.SCREENSHOTS_FOLDER.f.getPath() + "/" + new Date().getTime() + ".png");
		try
		{
			file.createNewFile();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		try
		{
			ImageIO.write(captured, "png", file);
			System.out.println("Screenshot taken");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
