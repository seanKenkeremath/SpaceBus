package sean.k.uts2120;

import android.graphics.Canvas;
import android.graphics.Color;

public class QuitGameButton extends GameButton{
	
	//goes to main menu
	
	final static float WIDTH_PERCENT_WIDTH = .5f;
	final static float HEIGHT_PERCENT = .2f;
	final static String TEXT = "Quit";

	/*
	 * implementation of GameButton that returns to the main menu.
	 */
	public QuitGameButton(GameThread theThread, float yPos) {
		super(theThread, Game.screenWidth*WIDTH_PERCENT_WIDTH, Game.screenHeight*HEIGHT_PERCENT, Game.screenWidth/2, yPos);
		paint.setTextSize(Game.screenWidth*WIDTH_PERCENT_WIDTH/(float)TEXT.length());
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);

		paint.setColor(Color.WHITE);
		canvas.drawText(TEXT, hitbox.centerX(), hitbox.centerY(), paint);
		
		
	}

	@Override
	void click() {
		
		thread.exitLevel(thread.getGame().getCurrentLevel());
		thread.getGame().quit();
		thread.pause(new MainMenuScreen(thread));
		
	}

}
