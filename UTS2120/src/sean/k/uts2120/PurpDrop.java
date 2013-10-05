package sean.k.uts2120;

public class PurpDrop extends MoneyDrop{

	final static int IMAGE_ID = R.drawable.purp;
	final static int AMOUNT = 500;
	
	public PurpDrop(Game theGame, float xPosition, float yPosition) {
		super(theGame, IMAGE_ID, xPosition, yPosition, AMOUNT);
	}

}
