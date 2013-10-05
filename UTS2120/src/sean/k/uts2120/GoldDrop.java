package sean.k.uts2120;

public class GoldDrop extends MoneyDrop{
	
	final static int IMAGE_ID = R.drawable.gold;
	final static int AMOUNT = 50;
	
	/*
	 * this class is a pickUp that represents various
	 * amounts of money
	 */
	public GoldDrop(Game theGame, float xPosition, float yPosition) {
		super(theGame, IMAGE_ID, xPosition, yPosition, AMOUNT);
	}

	

}
