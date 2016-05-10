package engine.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import engine.Tile;

public class TMXDecoder extends DefaultHandler {
	
	private static final int CSV = 0;
	
	private ArrayList<Tile> tiles;
	private XMLReader parser;
	
	private boolean parseData;
	private int dataEncoding;
	
	private StringBuilder sb;

	private int width;
	private int height;

	public TMXDecoder() throws SAXException, ParserConfigurationException {
		tiles = new ArrayList<Tile>();
		dataEncoding = -1;
		sb = new StringBuilder();
		parser = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
		parser.setContentHandler(this);
		//parser.parse(new InputSource(new FileInputStream(new File(PATH + "level/" + path))));
	}
	
	public ArrayList<Tile> parse(InputStream input) throws IOException, SAXException {
		parser.parse(new InputSource(input));
		
		String[] indices = sb.toString().split(",");
		
		int x = 0;
		int y = 0;
		
		for (int i = 0; i < indices.length; i++) {
			int index = Integer.parseInt(indices[i]);
			index--;
			
			if (index > -1) {
				tiles.add(new Tile(x, -y, index));
			}
			
			x++;
			if (x >= width) {
				x = 0;
				y++;
			}
		}
		
		return tiles;
	}

	@Override
	public void setDocumentLocator(Locator locator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startPrefixMapping(String prefix, String uri) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		if (qName.equals("data")) {
			parseData = true;
			if (atts.getValue("encoding").equals("csv")) {
				dataEncoding = CSV;
			}
		}
		else if (qName.equals("map")) {
			width = Integer.parseInt(atts.getValue("width"));
			height = Integer.parseInt(atts.getValue("height"));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("data")) {
			parseData = false;
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (parseData) {
			if (dataEncoding == CSV) {

				for (int i = start; i < start + length; i++) {
					if (!Character.isWhitespace(ch[i])) {
						sb.append(ch[i]);
					}
				}

			}
		}
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processingInstruction(String target, String data) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
		// TODO Auto-generated method stub
		
	}
	
}
