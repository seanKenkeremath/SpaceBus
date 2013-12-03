package sean.k.uts2120;

public class UpgradeArmorButton extends UpgradeButton{
	
	final static int LEVEL_TWO_PRICE = 300;
	final static int LEVEL_THREE_PRICE = 600;
	Armor armor;

	public UpgradeArmorButton(UpgradeMenu menu, Game game) {
		super(menu, game, R.drawable.armoricon);
		armor = game.getPlayer().getArmor();
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

		return "Armor Level " + (int)(armor.getLevel()+1);
	}

	@Override
	boolean available() {

		return armor.getLevel()<Armor.MAX_LEVEL;
	}


}
