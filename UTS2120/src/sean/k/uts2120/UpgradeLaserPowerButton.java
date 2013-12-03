package sean.k.uts2120;

public class UpgradeLaserPowerButton extends UpgradeButton{

	
	final static int UPGRADE_ONE = 500;
	final static int UPGRADE_TWO = 1000;
	Laser laser;
	
	public UpgradeLaserPowerButton(UpgradeMenu menu, Game game) {
		super(menu, game,R.drawable.powericon);
		laser = game.getPlayer().getLaser();
	}

	@Override
	void upgrade() {
		
		laser.upgradePower();

		
	}

	@Override
	int getPrice() {
		if (laser.getPowerLevel() == 1){
			return UPGRADE_ONE;
		} else if (laser.getPowerLevel()==2){
			return UPGRADE_TWO;
		} else {
			return 0;
		}
	}

	@Override
	String getMessage() {
		return "Cannon Power Level " +(int)(laser.getPowerLevel()+1);
	}

	@Override
	boolean available() {
		if (laser.getPowerLevel()<Laser.MAX_POWER_LEVEL){
			return true;
		}
		return false;
	}


}
