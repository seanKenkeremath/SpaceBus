package sean.k.uts2120;

import android.graphics.Canvas;
import android.graphics.Color;

public class UpgradeScoreMultiplierButton extends UpgradeButton{
	
	final static int LEVEL_ONE = 1;
	final static int LEVEL_TWO = 2;
	final static int LEVEL_TWO_PRICE = 500;
	final static int LEVEL_THREE = 3;
	final static int LEVEL_THREE_PRICE = 1500;
	final static int LEVEL_FOUR = 4;
	final static int LEVEL_FOUR_PRICE = 3000;

	public UpgradeScoreMultiplierButton(GameThread theThread, float xPos, float yPos) {
		super(theThread, xPos, yPos);
		// TODO Auto-generated constructor stub
	}

	@Override
	void upgrade() {
		if (thread.getGame().getScoreMultiplier()==LEVEL_ONE){
			thread.getGame().setScoreMultiplier(LEVEL_TWO);
		} else if (thread.getGame().getScoreMultiplier()==LEVEL_TWO){
			thread.getGame().setScoreMultiplier(LEVEL_THREE);
		} else if (thread.getGame().getScoreMultiplier()==LEVEL_THREE){
			thread.getGame().setScoreMultiplier(LEVEL_FOUR);
		}
		
	}

	@Override
	int getPrice() {
		if (thread.getGame().getScoreMultiplier()==LEVEL_ONE){
			return LEVEL_TWO_PRICE;
		} else if (thread.getGame().getScoreMultiplier()==LEVEL_TWO){
			return LEVEL_THREE_PRICE;
		} else if (thread.getGame().getScoreMultiplier()==LEVEL_THREE){
			return LEVEL_FOUR_PRICE;
		} else
		return 0;
	}

	@Override
	String getMessage() {
		if (thread.getGame().getScoreMultiplier()==LEVEL_ONE){
			return "Upgrade Score Multiplier to X" + LEVEL_TWO;
		} else if (thread.getGame().getScoreMultiplier()==LEVEL_TWO){
			return "Upgrade Score Multiplier to X" + LEVEL_THREE;
		} else if (thread.getGame().getScoreMultiplier()==LEVEL_THREE){
			return "Upgrade Score Multiplier to X" + LEVEL_FOUR;
		} else {
			return "";
		}
		
	}

	@Override
	boolean available() {
		return thread.getGame().getScoreMultiplier()<LEVEL_FOUR;
	}

	@Override
	void drawIcon(Canvas canvas) {
		paint.setColor(Color.WHITE);
		canvas.drawText("X",hitbox.centerX(),hitbox.centerY(),paint);
		
	}

}
