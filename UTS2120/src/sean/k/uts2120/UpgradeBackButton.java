package sean.k.uts2120;

import android.graphics.Canvas;
import android.graphics.Color;

public class UpgradeBackButton extends GameButton{

	
	final static float WIDTH_PERCENT_WIDTH = .5f;
	final static float HEIGHT_PERCENT_TOTAL = .12f;
	final static String TEXT = "Back";

	public UpgradeBackButton(GameThread theThread, float yPos) {
		super(theThread, Game.screenWidth*WIDTH_PERCENT_WIDTH, Game.totalHeight*HEIGHT_PERCENT_TOTAL, Game.screenWidth/2, yPos);
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
		thread.pause(new LevelCompleteScreen(thread, 0));
		
	}
	
}
