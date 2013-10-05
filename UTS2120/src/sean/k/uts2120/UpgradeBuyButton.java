package sean.k.uts2120;

import android.graphics.Canvas;
import android.graphics.Color;

public class UpgradeBuyButton extends GameButton{
	
	final static float WIDTH_PERCENT_WIDTH = .5f;
	final static float HEIGHT_PERCENT_TOTAL = .12f;
	final static String TEXT = "Buy";

	/*
	 * buys the selected upgrade in the Upgrade Screen.
	 */
	public UpgradeBuyButton(GameThread theThread, float yPos) {
		super(theThread, Game.screenWidth*WIDTH_PERCENT_WIDTH, Game.totalHeight*HEIGHT_PERCENT_TOTAL, Game.screenWidth/2, yPos);
		paint.setTextSize(Game.totalHeight*HEIGHT_PERCENT_TOTAL/2);
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		paint.setColor(Color.WHITE);
		canvas.drawText(TEXT, hitbox.centerX(), hitbox.centerY(), paint);
		
		
	}

	@Override
	void click() {
		((UpgradeScreen) thread.getScreen()).buyUpgrades();
		
	}
	
}
