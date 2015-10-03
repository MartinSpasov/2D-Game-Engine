package engine.resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PNGDecoder {

	public static final int[] PNG_SIGNATURE = new int[]{137, 80, 78, 71, 13, 10, 26, 10};
	
	public PNGDecoder(String fileName) {
		File file = new File(fileName);
		
		if(!file.exists()) {
			// TODO throw exception
		}
		
		try {
			FileInputStream input = new FileInputStream(file);
			
			// Check signature
			for (int i = 0; i < PNG_SIGNATURE.length; i++) {
				if (input.read() != PNG_SIGNATURE[i]) {
					System.out.println("ERRRROORR");
				}
			}
			
			int data = 0;
			int length = 0;
			while (data != -1) {
				// Read chunk
				length = bytesToInt(new int[]{input.read(), input.read(), input.read(), input.read()});
				for (int i = 0; i < length; i++) {
					
				}
			}
			
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int bytesToInt(int[] bytes) {
		int i = (bytes[0]<<24)&0xff000000 | (bytes[1]<<16)&0x00ff0000 | (bytes[2]<< 8)&0x0000ff00 | (bytes[3]<< 0)&0x000000ff;
		return i;
	}
}
