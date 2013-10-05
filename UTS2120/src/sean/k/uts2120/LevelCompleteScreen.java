package sean.k.uts2120;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;

public class LevelCompleteScreen extends GameScreen {

	int backgroundColor;
	final static float TEXT_HEIGHT_PERCENT_WIDTH = .08f;
	final static float BONUS_TEXT_HEIGHT_PERCENT_WIDTH = .04f;
	Paint bonusPaint;
	final static int BUTTON_WAIT_TIME = (int) (1000f/Game.THREAD_WAIT_TIME);


	/*
	 * the screen displayed when a Level is complete
	 */
	public LevelCompleteScreen(GameThread theThread, int buttonWaitTime) {
		super(theThread, GameScreen.NO_BACKGROUND_IMAGE, buttonWaitTime);
		buttons.add(new NextLevelButton(thread, Game.screenHeight / 2));
		
		buttons.add(new ShopButton(thread, Game.screenHeight / 2
				+ NextLevelButton.HEIGHT_PERCENT * Game.screenHeight
				+ GameScreen.BUTTON_PADDING));
		
		buttons.add(new QuitGameButton(thread, Game.screenHeight / 2
				+ NextLevelButton.HEIGHT_PERCENT * Game.screenHeight
				+ GameScreen.BUTTON_PADDING + ShopButton.HEIGHT_PERCENT
				* Game.screenHeight + GameScreen.BUTTON_PADDING));

		backgroundColor = Color.parseColor("#90000000");
		paint.setTextSize(TEXT_HEIGHT_PERCENT_WIDTH*Game.screenWidth);
		paint.setColor(Color.WHITE);
		
		bonusPaint = new Paint();
		bonusPaint.setColor(Color.WHITE);
		bonusPaint.setTextSize(BONUS_TEXT_HEIGHT_PERCENT_WIDTH *Game.screenWidth);
		bonusPaint.setTextAlign(Align.CENTER);
		bonusPaint.setStyle(Style.FILL);
	}

	@Override
	public void drawBackground(Canvas canvas) {

		canvas.drawColor(backgroundColor);
		thread.getGame().drawScore(canvas);
		canvas.drawText("LEVEL COMPLETE", Game.screenWidth / 2,Game.topMarginHeight+paint.getTextSize(),
				paint);
		if (thread.getGame().getCurrentLevel().allPassengers()){
		canvas.drawText("Bonus: All Passengers (" + Game.ALL_PASSENGER_BONUS +" DPM)", Game.screenWidth*.5f, Game.topMarginHeight+paint.getTextSize()+bonusPaint.getTextSize(),bonusPaint);
		}

	}

}
