package sean.k.uts2120;

public class UpgradeRepairButton extends UpgradeButton{
	
	final static int REPAIR_COST = 100;

	public UpgradeRepairButton(UpgradeMenu menu, Game game) {
		super(menu, game,R.drawable.repairicon);

	}


	@Override
	void upgrade() {
		game.getPlayer().repair();
		
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
		return (game.getPlayer().getHealth()<game.getPlayer().getMaxHealth());
	}

}
