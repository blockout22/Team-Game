package game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import core.management.Direction;

public class Input {

	public static void update(int delta, Game game) {
		keyboard(delta, game);
		mouse(game);
	}

	private static void keyboard(int delta, Game game) {
		float moveAmt = (1.01f * delta);
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			game.getCamera().move(Direction.FORWARD, moveAmt);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			game.getCamera().move(Direction.LEFT, moveAmt);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			game.getCamera().move(Direction.BACK, moveAmt);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			game.getCamera().move(Direction.RIGHT, moveAmt);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			game.getCamera().move(Direction.UP, moveAmt);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			game.getCamera().move(Direction.DOWN, moveAmt);
		}

		if (Keyboard.next()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
				game.generateClearAllColors();
			}
		}
	}

	private static void mouse(Game game) {
		if (Mouse.isButtonDown(2)) {
			game.getCamera().rotate();
			Mouse.setGrabbed(true);
		} else {
			Mouse.setGrabbed(false);
		}
	}

}
