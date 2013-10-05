package sean.k.uts2120;

public class Fuel extends PickUp{

	final static int IMAGE_ID = R.drawable.antimatter;
	final static float HEIGHT_PERCENT =.05f;
	final static float WIDTH_PERCENT =.05f;
	final static int HEAL_AMOUNT = Player.STARTING_HEALTH/2;

	/*
	 * a pick up that heals the player a certain amount
	 */
	public Fuel(Game theGame, float xPosition, float yPosition) {
		super(theGame, IMAGE_ID, xPosition, yPosition, WIDTH_PERCENT*Game.screenHeight, HEIGHT_PERCENT*Game.screenHeight);
		enemy = false;
		//velocityX = (float) (Math.cos(Math.random()*Math.PI*2)*Game.SPEED);
	}


	@Override
	void pickUp() {
		game.getPlayer().incHealth(HEAL_AMOUNT);

	}
}
