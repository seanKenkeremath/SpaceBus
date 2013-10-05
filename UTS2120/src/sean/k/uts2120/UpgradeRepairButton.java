package sean.k.uts2120;

import android.graphics.Canvas;
import android.graphics.Color;

public class UpgradeRepairButton extends UpgradeButton{
	
	final static int REPAIR_COST = 100;

	public UpgradeRepairButton(GameThread theThread, float xPos, float yPos) {
		super(theThread, xPos, yPos);

	}

	@Override
	void drawIcon(Canvas canvas) {
		paint.setColor(Color.WHITE);
		canvas.drawText("R",hitbox.centerX(),hitbox.centerY(),paint);
		
	}

	@Override
	void upgrade() {
		thread.getGame().getPlayer().repair();
		
	}

	@Override
	int getPrice() {
		return REPAIR_COST;
	}

	@Override
	String getMessage() {
		return "Repair The Bus";
	}

	@Override
	boolean available() {
		return (thread.getGame().getPlayer().getHealth()<thread.getGame().getPlayer().getMaxHealth());
	}

}
