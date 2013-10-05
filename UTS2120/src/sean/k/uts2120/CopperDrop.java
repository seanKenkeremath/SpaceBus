package sean.k.uts2120;

public class CopperDrop extends MoneyDrop{
	
	final static int IMAGE_ID = R.drawable.copper;
	final static int AMOUNT = 10;
	
	public CopperDrop(Game theGame, float xPosition, float yPosition) {
		super(theGame, IMAGE_ID, xPosition, yPosition, AMOUNT);
	}

	

}
