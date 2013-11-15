package sean.k.uts2120;

import android.graphics.Canvas;
import android.graphics.Color;

public class UpgradeBoosterButton extends UpgradeButton{
	
	final static int LEVEL_TWO_PRICE = 250;
	final static int LEVEL_THREE_PRICE = 700;
	Booster booster;

	public UpgradeBoosterButton(UpgradeMenu menu, Game game) {
		super(menu, game);
		booster = game.getPlayer().getBooster();
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


}
