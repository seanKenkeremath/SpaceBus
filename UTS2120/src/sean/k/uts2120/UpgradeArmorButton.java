package sean.k.uts2120;

import android.graphics.Canvas;
import android.graphics.Color;

public class UpgradeArmorButton extends UpgradeButton{
	
	final static int LEVEL_TWO_PRICE = 300;
	final static int LEVEL_THREE_PRICE = 600;
	Armor armor;

	public UpgradeArmorButton(GameThread theThread, float xPos, float yPos) {
		super(theThread, xPos, yPos);
		armor = thread.getGame().getPlayer().getArmor();
	}

	@Override
	void upgrade() {
		armor.upgrade();
		
	}

	@Override
	int getPrice() {
		if (armor.getLevel()==1){
			return LEVEL_TWO_PRICE;
		}
		if (armor.getLevel()==2){
			return LEVEL_THREE_PRICE;
		}
		return 0;
	}

	@Override
	String getMessage() {
		return "Upgrade Armor";
	}

	@Override
	boolean available() {

		return armor.getLevel()<Armor.MAX_LEVEL;
	}

	@Override
	void drawIcon(Canvas canvas) {
		paint.setColor(Color.WHITE);
		canvas.drawText("A",hitbox.centerX(),hitbox.centerY(),paint);
		
	}

}
