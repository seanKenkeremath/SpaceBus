package sean.k.uts2120;

public class UpgradeScoreMultiplierButton extends UpgradeButton{
	
	final static int LEVEL_ONE = 1;
	final static int LEVEL_TWO = 2;
	final static int LEVEL_TWO_PRICE = 500;
	final static int LEVEL_THREE = 3;
	final static int LEVEL_THREE_PRICE = 1500;
	final static int LEVEL_FOUR = 4;
	final static int LEVEL_FOUR_PRICE = 3000;

	public UpgradeScoreMultiplierButton(UpgradeMenu menu, Game game) {
		super(menu, game);
		// TODO Auto-generated constructor stub
	}

	@Override
	void upgrade() {
		if (game.getScoreMultiplier()==LEVEL_ONE){
			game.setScoreMultiplier(LEVEL_TWO);
		} else if (game.getScoreMultiplier()==LEVEL_TWO){
			game.setScoreMultiplier(LEVEL_THREE);
		} else if (game.getScoreMultiplier()==LEVEL_THREE){
			game.setScoreMultiplier(LEVEL_FOUR);
		}
		
	}

	@Override
	int getPrice() {
		if (game.getScoreMultiplier()==LEVEL_ONE){
			return LEVEL_TWO_PRICE;
		} else if (game.getScoreMultiplier()==LEVEL_TWO){
			return LEVEL_THREE_PRICE;
		} else if (game.getScoreMultiplier()==LEVEL_THREE){
			return LEVEL_FOUR_PRICE;
		} else
		return 0;
	}

	@Override
	String getMessage() {
		if (game.getScoreMultiplier()==LEVEL_ONE){
			return "Upgrade Score Multiplier to X" + LEVEL_TWO;
		} else if (game.getScoreMultiplier()==LEVEL_TWO){
			return "Upgrade Score Multiplier to X" + LEVEL_THREE;
		} else if (game.getScoreMultiplier()==LEVEL_THREE){
			return "Upgrade Score Multiplier to X" + LEVEL_FOUR;
		} else {
			return "";
		}
		
	}

	@Override
	boolean available() {
		return game.getScoreMultiplier()<LEVEL_FOUR;
	}


}
