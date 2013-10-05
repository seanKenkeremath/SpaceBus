package sean.k.uts2120;

import java.util.ArrayList;

public class BusStop extends GameEntity{

	final static int IMAGE_ID = R.drawable.bus_stop;
	final static float HEIGHT_PERCENT = .2f;
	final static float WIDTH_PERCENT = .08f;
	final static float SCATTER_RADIUS_PERCENT = .05f;
	final static float PROXIMITY_RADIUS_PERCENT = .15f;
	
	ArrayList<Passenger> passengers;
	private int scatterRadius;
	private int proximityRadius;
	private int numberPassengers;
	
	
	/*
	 * this object spawns passengers that need to be picked up.
	 * When you are in proximity of this object, the game will register that you are
	 * near by the and the Open Doors button will become visible.
	 */
	public BusStop(Game theGame, float xPosition, float yPosition, int numPassengers) {
		super(theGame, IMAGE_ID, xPosition, yPosition, Game.screenHeight*WIDTH_PERCENT, Game.screenHeight*HEIGHT_PERCENT);
		childEntities.add(new Passenger(game, 0f,0f));
		passengers = new ArrayList<Passenger>();
		numberPassengers = numPassengers;
		proximityRadius = (int) (Game.screenHeight*PROXIMITY_RADIUS_PERCENT);
		scatterRadius = (int) (Game.screenHeight*SCATTER_RADIUS_PERCENT);
		
		
		
	}
	
	@Override
	public void spawn(){
		super.spawn();
		double randomX;
		double randomY;
		//Log.d("BUS STOP CREATED", ""+numberPassengers);
		for (int i =0 ; i<numberPassengers; i++){
			
			randomX = 2*(.5-Math.random());
			randomY = .5 *(Math.random());
			
			Passenger newPassenger = new Passenger(game, (float)(xPos+randomX*scatterRadius),(float) (yPos+randomY*scatterRadius));
			passengers.add(newPassenger);
			game.getCurrentLevel().incTotalPassengers(1);
			game.addEntity(newPassenger);
			

		}
	}
	
	@Override
	public void update(){
		super.update();
		if (Game.distance(game.getPlayer(), this)<proximityRadius && passengers.size()>0){
			game.getPlayer().seeBusStop();
			if (game.getPlayer().doorsOpen){
				board();
			}
		}

	}
	
	public void board(){
		for (Passenger passenger: passengers){
			passenger.board();
		}
		
		passengers.clear();
	}
	


}
