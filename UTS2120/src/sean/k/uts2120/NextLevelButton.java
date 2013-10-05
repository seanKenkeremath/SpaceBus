package sean.k.uts2120;

import android.graphics.Canvas;
import android.graphics.Color;

public class NextLevelButton extends GameButton{
	
	final static float WIDTH_PERCENT_WIDTH = .5f;
	final static float HEIGHT_PERCENT = .2f;
	final static String TEXT = "Next Level";

	/*
	 * implementation of GameButton that starts the next Level
	 */
	public NextLevelButton(GameThread theThread, float yPos) {
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
		thread.startLevel(thread.getGame().getCurrentLevel().nextLevel());
		thread.unpause();
		
	}

}
