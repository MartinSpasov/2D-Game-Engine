package engine.resource;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;
import org.lwjgl.stb.STBVorbis;

import org.xml.sax.SAXException;

import engine.Tile;
import engine.Window;
import engine.graphics.Texture;
import engine.graphics.text.Font;
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
	
	public static Texture loadArrayTexture(String file, int rows, int columns, int filtering, int unit) {
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
		
		Texture texture = new Texture(Texture.TEXTURE_2D_ARRAY, unit);
		texture.bind();
		texture.bufferTexture3D(buffer, width, height, depth, filtering);
		texture.unbind();
		
		return texture;
	}
	
	public static Texture loadTexture(String fileName, int filtering, int unit) {
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		IntBuffer channels = BufferUtils.createIntBuffer(1);
		ByteBuffer data = STBImage.stbi_load(PATH + "texture/" + fileName, width, height, channels, 4);
		
		Texture texture = new Texture(Texture.TEXTURE_2D, unit);
		texture.bind();
		texture.bufferTexture2D(data, width.get(0), height.get(0), filtering);
		texture.unbind();
		
		return texture;
	}
	
	public static Sound loadSound(String fileName) {
		IntBuffer channels = BufferUtils.createIntBuffer(1);
		IntBuffer sampleRate = BufferUtils.createIntBuffer(1);
		ShortBuffer data = STBVorbis.stb_vorbis_decode_filename(PATH + "sound/" + fileName, channels, sampleRate);
		
		return new Sound(data, sampleRate.get(0));
	}
	
	public static Font loadFont(String fontAtlas, String fontDesc, int width, int height, Window window, int unit) {
		IntBuffer imageWidth = BufferUtils.createIntBuffer(1);
		IntBuffer imageHeight = BufferUtils.createIntBuffer(1);
		IntBuffer channels = BufferUtils.createIntBuffer(1);
		ByteBuffer data = STBImage.stbi_load(PATH + "font/" + fontAtlas, imageWidth, imageHeight, channels, 4);
		
		HashMap<Character, Integer> characterMap = new HashMap<Character, Integer>();
		int invalidCharacter = 0;
		int charCount = 0;
		ByteBuffer newData = null;
		
		try {
			Scanner input = new Scanner(new File(PATH + "font/" + fontDesc));
			
			// Ignore first 3 lines for now
			input.nextLine();
			input.nextLine();
			input.nextLine();
			
			String[] countLine = input.nextLine().split("\\s+");
			charCount = Integer.parseInt(countLine[1].substring(6));
			newData = BufferUtils.createByteBuffer(charCount * width * height * channels.get(0));
			
			int index = 0;
			while(input.hasNextLine()) {			
				String[] line = input.nextLine().split("\\s+");

				
				int asciiCode = Integer.parseInt(line[1].substring(3));
				
				if (asciiCode == -1) {
					invalidCharacter = index;
				}
				
				characterMap.put((char)asciiCode, index++);
				
				int x = Integer.parseInt(line[2].substring(2));
				int y = Integer.parseInt(line[3].substring(2));
				
				for (int y2 = 0; y2 < height; y2++) {
					for (int x2 = 0; x2 < width; x2++) {
						int offset = ((x + x2) * 4) + ((y + y2) * 4 * imageWidth.get(0));
						newData.put(data.get(offset));
						newData.put(data.get(offset + 1));
						newData.put(data.get(offset + 2));
						newData.put(data.get(offset + 3));
						
					}
				}

			}
			
			input.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		newData.flip();

		float glyphWidth = (2.0f / window.getWidth()) * width;
		float glyphHeight = (2.0f / window.getHeight()) * height;

		
		Texture texture = new Texture(Texture.TEXTURE_2D_ARRAY, unit);
		texture.bind();
		texture.bufferTexture3D(newData, width, height, charCount, Texture.LINEAR);
		texture.unbind();
		
		return new Font(texture, invalidCharacter, glyphWidth, glyphHeight, characterMap);
	}
	
	public static ArrayList<Tile> loadLevelTMX(String path) {
		ArrayList<Tile> tiles = null;
		
		try {
			TMXDecoder decoder = new TMXDecoder();
			tiles = decoder.parse(new FileInputStream(new File(PATH + "level/" + path)));
		} 
		catch (SAXException e) {
			e.printStackTrace();
		} 
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return tiles;
	}
	
	@Deprecated
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
