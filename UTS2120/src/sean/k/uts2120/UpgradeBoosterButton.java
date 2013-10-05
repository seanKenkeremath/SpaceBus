package sean.k.uts2120;

import android.graphics.Canvas;
import android.graphics.Color;

public class UpgradeBoosterButton extends UpgradeButton{
	
	final static int LEVEL_TWO_PRICE = 250;
	final static int LEVEL_THREE_PRICE = 700;
	Booster booster;

	public UpgradeBoosterButton(GameThread theThread, float xPos, float yPos) {
		super(theThread, xPos, yPos);
		booster = thread.getGame().getPlayer().getBooster();
	}

	@Override
	void upgrade() {
		booster.upgrade();
		
	}

	@Override
	int getPrice() {
		if (booster.getLevel()==1){
			return LEVEL_TWO_PRICE;
		}
		if (booster.getLevel()==2){
			return LEVEL_THREE_PRICE;
		}
		return 0;
	}

	@Override
	String getMessage() {
		return "Upgrade Booster";
	}

	@Override
	boolean available() {

		return booster.getLevel()<Booster.MAX_LEVEL;
	}

	@Override
	void drawIcon(Canvas canvas) {
		paint.setColor(Color.WHITE);
		canvas.drawText("B",hitbox.centerX(),hitbox.centerY(),paint);
		
	}

}
