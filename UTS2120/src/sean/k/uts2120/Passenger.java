package sean.k.uts2120;

public class Passenger extends PickUp{

	final static int IMAGE_ID=R.drawable.seanjetpack_megoggle;
	final static float HEIGHT_PERCENT = .1f;
	final static float WIDTH_PERCENT = .05f;
	final static int POINTS = 20;
	
	/*
	 * PickUp that is spawned by a bus stop.
	 * Contains a board() method that rewards Player for picking him up.
	 */
	public Passenger(Game theGame, float xPosition,	float yPosition) {
		
		super(theGame, IMAGE_ID, xPosition, yPosition, Game.screenHeight*WIDTH_PERCENT, Game.screenHeight*HEIGHT_PERCENT);
	}


	public void board(){
		SilverDrop fare = new SilverDrop(game,xPos,yPos);
		game.addEntity(new MoneyEffect(game,xPos,yPos,fare));
		//game.incScore(POINTS);
		game.incGold(fare.getAmount());
		game.incPassengerCount(1);
	}


	@Override
	void pickUp() {
		board();
		remove = true;
	}

}
