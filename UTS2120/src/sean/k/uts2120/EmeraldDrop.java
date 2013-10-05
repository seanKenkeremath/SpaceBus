package sean.k.uts2120;

public class EmeraldDrop extends MoneyDrop{

	
	final static int IMAGE_ID = R.drawable.emerald;
	final static int AMOUNT = 100;
	
	public EmeraldDrop(Game theGame, float xPosition, float yPosition) {
		super(theGame, IMAGE_ID, xPosition, yPosition, AMOUNT);
	}

	
}
