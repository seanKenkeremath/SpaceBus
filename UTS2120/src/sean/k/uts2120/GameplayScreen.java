package sean.k.uts2120;

import android.graphics.Canvas;
import android.view.MotionEvent;

public class GameplayScreen extends GameScreen{

	public GameplayScreen(GameThread theThread) {
		super(theThread, GameScreen.NO_BACKGROUND_IMAGE, 0);
		buttons.add(new PauseButton(thread));
		buttons.add(new OpenDoorsButton(thread));
	}

	
	/*This is the normal gameplay screen that registers touch input and
	 * draws the pause button and doors button.
	 */
	@Override
	public void draw(Canvas canvas){
		super.draw(canvas);
	}

	
	@Override
	public void touch(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) { 
			//single click activates buttons and also fires
			float xCoor = event.getX();
			float yCoor = event.getY();
			for (GameButton button : buttons) {
				if (button.contains(xCoor, yCoor)) {
					button.click();
				}
			}

			if (yCoor < (thread.getGame().getPlayer().getY() - thread.getGame()
					.getPlayer().getHeight() / 2)) {

				thread.getGame().getPlayer().getLaser().shoot(xCoor, yCoor);

			}

		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			//drag only fires, does not activate buttons
			float xCoor = event.getX();
			float yCoor = event.getY();

			if (yCoor < (thread.getGame().getPlayer().getY() - thread.getGame()
					.getPlayer().getHeight() / 2)) {

				thread.getGame().getPlayer().getLaser().shoot(xCoor, yCoor);

			}

		}

	}

}
