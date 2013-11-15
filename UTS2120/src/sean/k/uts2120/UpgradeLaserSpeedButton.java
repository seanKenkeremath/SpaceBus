package sean.k.uts2120;

public class UpgradeLaserSpeedButton extends UpgradeButton{

	final static int UPGRADE_ONE = 150;
	final static int UPGRADE_TWO = 300;
	Laser laser;
	
	public UpgradeLaserSpeedButton(UpgradeMenu menu, Game game) {
		super(menu, game);
		laser = game.getPlayer().getLaser();
	}

	@Override
	void upgrade() {
		laser.upgradeSpeed();
		
	}

	@Override
	int getPrice() {
		if (laser.getSpeedLevel()==1){
			return UPGRADE_ONE;
		} else if (laser.getSpeedLevel()==2){
			return UPGRADE_TWO;
		} else{
			return 0;
		}
	}

	@Override
	String getMessage() {
		return "Upgrade Cannon Speed" +" (level "+(int)(laser.getSpeedLevel()+1)+")";
	}

	@Override
	boolean available() {
		if (laser.getSpeedLevel()<Laser.MAX_SPEED_LEVEL){
			return true;
		}
		return false;
	}


}
