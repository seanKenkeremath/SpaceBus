package sean.k.uts2120;

import android.graphics.Canvas;
import android.graphics.Color;

public class MissionFailedScreen extends GameScreen{
	
	int backgroundColor;
	final static int TEXT_SIZE = 70;
	final static int BUTTON_WAIT_TIME = (int) (1000f/Game.THREAD_WAIT_TIME);

	/*
	 * the screen displayed when a Player loses
	 */
	public MissionFailedScreen(GameThread theThread) {
		super(theThread, GameScreen.NO_BACKGROUND_IMAGE, BUTTON_WAIT_TIME);
		//buttons.add(new ResetLevelButton(thread, Game.screenHeight/2));
		buttons.add(new QuitGameButton(thread, Game.screenHeight/2+UnpauseButton.HEIGHT_PERCENT*Game.screenHeight+GameScreen.BUTTON_PADDING));
		backgroundColor = Color.parseColor("#90000000");
		paint.setTextSize(TEXT_SIZE);
		paint.setColor(Color.WHITE);
	}

	@Override
	public void drawBackground(Canvas canvas) {
		
		
		canvas.drawColor(backgroundColor);

		canvas.drawText("Game Over", Game.screenWidth / 2,Game.topMarginHeight+paint.getTextSize(),
				paint);
		
	}

}
