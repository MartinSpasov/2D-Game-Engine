package engine.graphics.animation;

public class Animation {

	private int[] frames;
	private float[] delays;
	
	private int currentFrameIndex;
	private int currentFrame;
	private float timeElapsed;
	
	private boolean paused;
	
	public Animation(int[] frames, float[] delays) {
		this.frames = frames;
		this.delays = delays;
		currentFrame = frames[0];
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
				
				currentFrame = frames[currentFrameIndex];
			} 
		}
	}
	
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
	public void reset() {
		currentFrameIndex = 0;
		currentFrame = frames[0];
		timeElapsed = 0;
	}
	
	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}
	
	public int getCurrentFrame() {
		return currentFrame;
	}
}
