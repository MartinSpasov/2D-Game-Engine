package engine.sound;

import org.lwjgl.openal.AL10;

public class SoundSource {

	private int sourceId;
	
	public SoundSource() {
		sourceId = AL10.alGenSources();
	}
	
	public int getSourceId() {
		return sourceId;
	}
	
	public void destroy() {
		AL10.alDeleteSources(sourceId);
	}
}
