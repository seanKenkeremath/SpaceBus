package sean.k.uts2120;

public class BugEnemy extends Enemy{

	
	final static int DAMAGE = SmallAsteroid.DAMAGE;
	final static int HEALTH = SmallAsteroid.HEALTH;
	final static float HEIGHT_PERCENT = .1f;
	final static float WIDTH_PERCENT = .1f;
	final static float SPEED_PERCENT = Game.VELOCITY_SLOW_PERCENT/2;
	final static float SPEED_PERCENT_FAST = Game.VELOCITY_SLOW_PERCENT*2;
	final static int POINTS = 5;
	
	final static int WANDER_DURATION = (int) (100f/Game.THREAD_WAIT_TIME);
	final double WANDER_CHANCE = .05f;
	
	int wander;

	
	/*
	 * this enemy moves around until it gets close enough to you and leaps at you.
	 * It can be destroyed with one shot.
	 */
	public BugEnemy(Game theGame, float xPosition, float yPosition) {
		super(theGame, R.drawable.bug, xPosition, yPosition, Game.screenHeight*WIDTH_PERCENT, Game.screenHeight*HEIGHT_PERCENT,
				DAMAGE, HEALTH, POINTS);
		wander = 0;
	}

	@Override
	void ai() {

		float deltaY = game.getPlayer().getY() - yPos;
		float deltaX = game.getPlayer().getX() - xPos;
		
		double angle = Math.acos(deltaX
				/ (Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2))));
		
		if (wander>0){
			wander--;
		} else{
			
			setVelocityX((float) (Game.calcSpeed(SPEED_PERCENT) * Math.cos(angle) * 1));
			setVelocityY((float) (Game.calcSpeed(SPEED_PERCENT) * Math.sin(angle) * -1));
			
			if (Math.random()<WANDER_CHANCE){
				wander();
			}
		}
		
		if (distance()<Game.screenHeight/3){
			lunge(angle);
			//backUp(angle);
		}

	}
	
	private void lunge(double angle){
		setVelocityX((float) (Game.calcSpeed(SPEED_PERCENT_FAST) * Math.cos(angle)));
		setVelocityY((float) (Game.calcSpeed(SPEED_PERCENT_FAST) * Math.sin(angle)));
	}
	
	private void backUp(double angle){
		setVelocityX((float) (Game.calcSpeed(SPEED_PERCENT_FAST) * Math.cos(angle) * -1));
		setVelocityY((float) (Game.calcSpeed(SPEED_PERCENT_FAST) * Math.sin(angle) * -1));
	}
	
	private void wander(){
		setVelocityX((float) (Game.calcSpeed(SPEED_PERCENT) * Math.cos(Math.PI*2* Math.random())));
		setVelocityY(0);
		wander = WANDER_DURATION;
	}

	@Override
	void createLoot() {

			game.addEntity(new CopperDrop(game,xPos,yPos));
			
	}

}
