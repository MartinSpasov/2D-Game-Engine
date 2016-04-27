package engine.graphics.ui;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import engine.Game;
import engine.graphics.RenderSystem;
import engine.graphics.ui.widget.Widget;
import engine.input.MouseButtonListener;
import engine.input.MouseMovementListener;

public class UserInterface implements MouseButtonListener, MouseMovementListener {

	private ArrayList<Widget> widgets;
	
	private float cursorX;
	private float cursorY;
	
	private Game game;
	
	public UserInterface(Game game) {
		this.game = game;
		widgets = new ArrayList<Widget>();
	}
	
	public void addUserInterfaceComponent(Widget component) {
		widgets.add(component);
	}
	
	public void render(RenderSystem renderer) {
		for (Widget widget : widgets) {
			widget.render(renderer);
		}
	}

	@Override
	public void onMouseButton(long window, int button, int action, int mods) {
		for (Widget widget : widgets) {
			if (widget.getBounds().contains(cursorX, cursorY) && button == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_PRESS) {
				widget.onClick();
			}
		}
	}

	@Override
	public void onMouseMove(long window, double xPos, double yPos) {
		// TODO this is probably not the  best way to do this
		cursorX = (float) (((xPos * 2) / game.getWindow().getWidth()) - 1);
		cursorY = (float) (((yPos * -2) / game.getWindow().getHeight()) + 1);
		
		//System.out.println("X: " + cursorX);
		//System.out.println("Y: " + cursorY);
	}
}
