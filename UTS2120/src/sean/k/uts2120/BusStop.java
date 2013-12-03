package sean.k.uts2120;

import android.util.Log;

public class BusStop extends GameEntity{

	final static int IMAGE_ID = R.drawable.bus_stop;
	final static float HEIGHT_PERCENT = .2f;
	final static float WIDTH_PERCENT = .08f;
	final static float SCATTER_RADIUS_PERCENT = .05f;
	
	private int scatterRadius;
	private int numberPassengers;
	
	
	/*
	 * this object spawns passengers that need to be picked up. Pick up passengers by moving onto them.
	 */
	public BusStop(Game theGame, float xPosition, float yPosition, int numPassengers) {
		super(theGame, IMAGE_ID, xPosition, yPosition, Game.screenHeight*WIDTH_PERCENT, Game.screenHeight*HEIGHT_PERCENT);
		childEntities.add(new Passenger(game, 0f,0f));
		numberPassengers = numPassengers;
		scatterRadius = (int) (Game.screenHeight*SCATTER_RADIUS_PERCENT);
		
		
		
	}
	
	@Override
	public void spawn(){
		super.spawn();
		double randomX;
		double randomY;
		Log.d(GameActivity.DEBUG, "Bus Stop Created. Passengers = "+numberPassengers);
		for (int i =0 ; i<numberPassengers; i++){
			
			randomX = 2*(.5-Math.random());
			randomY = .5 *(Math.random());
			
			Passenger newPassenger = new Passenger(game, (float)(xPos+randomX*scatterRadius),(float) (yPos+randomY*scatterRadius));
			game.getCurrentLevel().incTotalPassengers(1);
			game.addEntity(newPassenger);
			

		}
	}
	
	@Override
	public void update(){
		super.update();
	}

	


}
