package sean.k.uts2120;

public class SilverDrop extends MoneyDrop{
	
	final static int IMAGE_ID = R.drawable.silver;
	final static int AMOUNT = 25;
	
	public SilverDrop(Game theGame, float xPosition, float yPosition) {
		super(theGame, IMAGE_ID, xPosition, yPosition, AMOUNT);
	}


}
