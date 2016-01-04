package engine.resource;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;
import org.lwjgl.stb.STBVorbis;

import engine.Tile;
import engine.graphics.ArrayTexture;
import engine.graphics.Texture;
import engine.sound.Sound;

public class Resources {

	public static final String PATH = "res/";
	
	// TODO handle errors for all methods
	public static String loadText(String file) {
		StringBuilder string = new StringBuilder();
		try {
			Scanner input = new Scanner(new File(PATH + file));
			while (input.hasNextLine()) {
				string.append(input.nextLine() + "\n");
			}
			
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return string.toString();
	}
	
	public static  ArrayTexture loadArrayTexture(String file, int rows, int columns) {
		ByteBuffer buffer = null;
		int width = 0;
		int height = 0;
		int depth = 0;
		try {
			BufferedImage image = ImageIO.read(new File(PATH + "texture/" + file));
			width = image.getWidth() / columns;
			height = image.getHeight() / rows;
			ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
			buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
			for (int j = 0; j < rows; j++) {
				for (int i = 0; i < columns; i++) {
					images.add(image.getSubimage(i * width, j * height, width, height));
				}
			}
			for (BufferedImage image1 : images) {
				for (int y = 0; y < image1.getHeight(); y++) {
					for (int x = 0; x < image1.getWidth(); x++) {
						Color color = new Color(image1.getRGB(x, y), true);
						buffer.put((byte)color.getRed());
						buffer.put((byte)color.getGreen());
						buffer.put((byte)color.getBlue());
						buffer.put((byte)color.getAlpha());
					}
				}
				depth++;
			}
			buffer.flip();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ArrayTexture(buffer, width, height, depth);
	}
	
	public static Texture loadTexture(String fileName) {
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		IntBuffer channels = BufferUtils.createIntBuffer(1);
		ByteBuffer data = STBImage.stbi_load(PATH + "texture/" + fileName, width, height, channels, 4);
		
		return new Texture(data, width.get(0), height.get(0));
	}
	
	public static Sound loadSound(String fileName) {
		IntBuffer channels = BufferUtils.createIntBuffer(1);
		IntBuffer sampleRate = BufferUtils.createIntBuffer(1);
		ShortBuffer data = STBVorbis.stb_vorbis_decode_filename(PATH + "sound/" + fileName, channels, sampleRate);
		
		return new Sound(data, sampleRate.get(0));
	}
	
	public static ArrayList<Tile> loadLevel(String map, String keys) {
		HashMap<Color, Integer> keyMap = new HashMap<Color, Integer>();
		
		try {
			Scanner input = new Scanner(new File(PATH + "level/" + keys));
			while (input.hasNextLine()) {
				String[] line = input.nextLine().split("\\s+");
				int r = Integer.parseInt(line[1]);
				int g = Integer.parseInt(line[2]);
				int b = Integer.parseInt(line[3]);
				int a = Integer.parseInt(line[4]);
				keyMap.put(new Color(r,g,b,a), Integer.parseInt(line[0]));
			}
			input.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		
		try {
			BufferedImage image = ImageIO.read(new File(PATH + "level/" + map));
			
			for (int y = 0; y < image.getHeight(); y++) {
				for (int x = 0; x < image.getWidth(); x++) {
					Color color = new Color(image.getRGB(x, y));
					Integer index = keyMap.get(color);
					
					if (index != null) {
						tiles.add(new Tile(x,-y,index));
					}
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return tiles;
	}
}
