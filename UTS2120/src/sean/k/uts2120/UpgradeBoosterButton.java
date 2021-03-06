package sean.k.uts2120;

public class UpgradeBoosterButton extends UpgradeButton{
	
	final static int LEVEL_TWO_PRICE = 250;
	final static int LEVEL_THREE_PRICE = 700;
	Booster booster;

	public UpgradeBoosterButton(UpgradeMenu menu, Game game) {
		super(menu, game,R.drawable.boostericon);
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
		return "Booster Level " + (int)(booster.getLevel()+1);	
		}

	@Override
	boolean available() {

		return booster.getLevel()<Booster.MAX_LEVEL;
	}


}
