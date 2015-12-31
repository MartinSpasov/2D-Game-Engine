package engine.sound;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALContext;
import org.lwjgl.openal.ALDevice;
import engine.resource.Resources;

public class SoundSystem {

	//private ALCapabilities capabilities;
	
	public SoundSystem() {
		//ALC.create();
		ALContext.create();
		//capabilities = AL.createCapabilities(arg0)
		Sound testSound1 = Resources.loadSound("title.ogg");
		Sound testSound2 = Resources.loadSound("wily.ogg");
		SoundSource testSource = new SoundSource();
		AL10.alSourceQueueBuffers(testSource.getSourceId(), testSound1.getBufferId());
		AL10.alSourceQueueBuffers(testSource.getSourceId(), testSound2.getBufferId());
		
		AL10.alSourcePlay(testSource.getSourceId());
		
		setGain(0.5f);
	}
	
	public void setGain(float gain) {
		AL10.alListenerf(AL10.AL_GAIN, gain);
	}
	
	public float getGain() {
		return AL10.alGetListenerf(AL10.AL_GAIN);
	}
	
	public void destroy() {
		ALDevice.getLastDevice().destroy();
		ALC.destroy();
	}
}
