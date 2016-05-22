package engine.resource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import engine.Tile;

public class TMXDecoder {
	
	public enum Encoding {
		CSV,
		BASE64,
		XML;
	}

	public static final String TMX_FORMAT_VERSION = "1.0";
	
	private ArrayList<Tile> tiles;
	
	private int levelWidth;
	private int levelHeight;
	
	private int tileWidth;
	private int tileHeight;
	
	private int nextObjectId;
	
	private Encoding encoding;
	
	private StringBuilder buffer;
	
	public TMXDecoder(InputStream input) {
		tiles = new ArrayList<Tile>();
		encoding = Encoding.XML;
		buffer = new StringBuilder();
		
		try {
			XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(input);
			
			while (reader.hasNext()) {
				XMLEvent event = reader.nextEvent();
				
				switch(event.getEventType()) {
				case XMLEvent.START_ELEMENT:
					parseTag(event.asStartElement());
					break;
				case XMLEvent.CHARACTERS:
					Characters chars = event.asCharacters();
					if (!chars.isWhiteSpace()) {
						String data = chars.getData();
						for (int i = 0; i < data.length(); i++) {
							char character = data.charAt(i);
							if (!Character.isWhitespace(character))
								buffer.append(data.charAt(i));
						}
					}
					break;
				case XMLEvent.END_ELEMENT:
					if (event.asEndElement().getName().toString().equals("data")) {
						parseData();
					}
				}
			}
		} 
		catch (XMLStreamException e) {
			e.printStackTrace();
		} 
		catch (FactoryConfigurationError e) {
			e.printStackTrace();
		}
		catch (NumberFormatException e) {
			e.printStackTrace();
		} 
		catch (InvalidValueException e) {
			e.printStackTrace();
		}

	}
	
	private void parseTag(StartElement event) throws InvalidValueException {
		
		@SuppressWarnings("unchecked")
		Iterator<Attribute> iter = event.getAttributes();
		
		switch(event.getName().toString()) {
		case "map":
			while (iter.hasNext()) {
				parseMapAttribute(iter.next());
			}
			break;
		case "data":
			while (iter.hasNext()) {
				parseDataAttribute(iter.next());
			}
			break;
		}
		
	}
	
	private void parseData()  {
		
		
		switch (encoding) {
		case CSV:
			parseDataCSV();
			break;
		default:
			// TODO throw unsupported encoding exception
			break;
		}
	}
	
	private void parseDataCSV() {
		
		String[] indices = buffer.toString().split(",");

		int x = 0;
		int y = 0;
		
		for (int i = 0; i < indices.length; i++) {
			int index = Integer.parseInt(indices[i]);
			index--;
			
			if (index > -1) {
				tiles.add(new Tile(x, -y, index));
			}
			
			x++;
			if (x >= levelWidth) {
				x = 0;
				y++;
			}
		}
	}
	
	private void parseMapAttribute(Attribute attrib) throws InvalidValueException {
		/*
		 * Unimplemented attributes:
		 * 
		 * hexsidelength
		 * staggeraxis
		 * staggerindex
		 * backgroundcolor
		 * 
		 * 
		 * */
		
		switch(attrib.getName().toString()) {
		case "version":
			if (!attrib.getValue().equals(TMX_FORMAT_VERSION)) {
				// TODO log warning message
			}
			break;
		case "orientation":
			if (!attrib.getValue().equals("orthogonal")) {
				throw new InvalidValueException("orientation", attrib.getValue());
			}
			break;
		case "renderorder":
			// This does not seem to matter until layers are implemented
			if (!attrib.getValue().equals("right-down")) {
				throw new InvalidValueException("renderorder", attrib.getValue());
			}
			break;
		case "width":
			levelWidth = Integer.parseInt(attrib.getValue());
			break;
		case "height":
			levelHeight = Integer.parseInt(attrib.getValue());
			break;
		case "tilewidth":
			tileWidth = Integer.parseInt(attrib.getValue());
			break;
		case "tileHeight":
			tileHeight = Integer.parseInt(attrib.getValue());
			break;
		case "nextobjectid":
			nextObjectId = Integer.parseInt(attrib.getValue());
			break;
		default:
			// TODO log warning message
			break;
		}
	}
	
	private void parseDataAttribute(Attribute attrib) throws InvalidValueException {
		/*
		 * Unimplemented attributes:
		 * 
		 * compression
		 * 
		 * */
		
		switch(attrib.getName().toString()) {
		case "encoding":
			if (attrib.getValue().equals("csv")) {
				encoding = Encoding.CSV;
			}
			else {
				throw new InvalidValueException("encoding", attrib.getValue());
			}
			break;
		default:
			// TODO log warning message
			break;
		}
	}
	
	public ArrayList<Tile> getTiles() {
		return tiles;
	}
	
	public int getLevelWidth() {
		return levelWidth;
	}

	public int getLevelHeight() {
		return levelHeight;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public int getNextObjectId() {
		return nextObjectId;
	}

	public Encoding getEncoding() {
		return encoding;
	}



}
