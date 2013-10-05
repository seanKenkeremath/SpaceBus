package sean.k.uts2120;

import android.graphics.Canvas;
import android.graphics.Color;

public class PausedScreen extends GameScreen{
	
	int backgroundColor;
	final static int TEXT_SIZE = 70;
	

	/*
	 * the screen created by pausing.
	 */
	public PausedScreen(GameThread theThread) {
		super(theThread, GameScreen.NO_BACKGROUND_IMAGE, 0);
		buttons.add(new UnpauseButton(thread, Game.screenHeight/2));
		buttons.add(new QuitGameButton(thread, Game.screenHeight/2+UnpauseButton.HEIGHT_PERCENT*Game.screenHeight+GameScreen.BUTTON_PADDING));
		backgroundColor = Color.parseColor("#90000000");
		paint.setTextSize(TEXT_SIZE);
		paint.setColor(Color.WHITE);
	}

	@Override
	public void drawBackground(Canvas canvas) {
		
		
		canvas.drawColor(backgroundColor);
		//canvas.drawText("PAUSED", Game.screenWidth/2, TEXT_SIZE, paint);
		
	}

}
