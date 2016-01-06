package engine.sound;

import java.nio.ShortBuffer;

import org.lwjgl.openal.AL10;

public class Sound {

	private int bufferId;
	
	public Sound(ShortBuffer data, int sampleRate) {
		bufferId = AL10.alGenBuffers();
		AL10.alBufferData(bufferId, AL10.AL_FORMAT_MONO16, data, sampleRate);
	}
	
	public int getBufferId() {
		return bufferId;
	}
	
	public void destroy() {
		AL10.alDeleteBuffers(bufferId);
	}
}
