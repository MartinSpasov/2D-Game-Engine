package engine.graphics.animation;

public class Animation {

	private int[] frames;
	private float[] delays;
	
	private int currentFrameIndex;
	private float timeElapsed;
	
	private boolean paused;
	
	public Animation(int[] frames, float[] delays) {
		this.frames = frames;
		this.delays = delays;
	}
	
	public void tick(float delta) {
		if (!paused) {
			timeElapsed += delta;
			if (timeElapsed >= delays[currentFrameIndex]) {
				timeElapsed -= delays[currentFrameIndex];

				currentFrameIndex++;
				if (currentFrameIndex >= frames.length) {
					currentFrameIndex = 0;
				}
			} 
		}
	}
	
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
	public void reset() {
		currentFrameIndex = 0;
		timeElapsed = 0;
	}
	
	public int getCurrentFrame() {
		return frames[currentFrameIndex];
	}
}
