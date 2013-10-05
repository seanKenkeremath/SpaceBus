package sean.k.uts2120;

import android.graphics.Canvas;
import android.graphics.Color;

public class NewGameButton extends GameButton{

	final static float WIDTH_PERCENT_WIDTH = .5f;
	final static float HEIGHT_PERCENT = .2f;
	final static String TEXT = "New Game";

	/*
	 * implementation of GameButton that resets or starts the game.
	 */
	public NewGameButton(GameThread theThread, float yPos) {
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
		thread.getGame().gameReset();
		thread.getScreen().exit(); // exit an additional time so bitmap of main menu does not overlap with level bitmaps
		thread.startLevel(new LevelDebug(thread.getGame()));
		thread.unpause();

	}
}
