package sean.k.uts2120;

abstract class MoneyDrop extends PickUp{

	
	final static float HEIGHT_PERCENT =.05f;
	final static float WIDTH_PERCENT =.05f;
	private int amount;
	
	/*
	 * a pickup representing any amount of money.
	 */
	public MoneyDrop(Game theGame, int theImageID, float xPosition, float yPosition, int theAmount) {
		super(theGame, theImageID, xPosition, yPosition, HEIGHT_PERCENT*Game.screenHeight, WIDTH_PERCENT*Game.screenHeight);
		amount = theAmount;
	}
	
	@Override
	void pickUp() {
		game.incGold(amount);
		remove = true;
	}
	
	public int getAmount(){
		return amount;
	}
}
