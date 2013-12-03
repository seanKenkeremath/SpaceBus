package sean.k.uts2120;

public class AOEEnemy extends Enemy{

	final static float HEIGHT_PERCENT = 0.1f;
	final static float WIDTH_PERCENT = 0.1f;

	//final static int IMAGE_ID = R.drawable.megilloutrage;
	final static int IMAGE_ID2 = R.drawable.megillhappy;
	final static int HEALTH = SmallAsteroid.HEALTH*2;
	final static int DAMAGE = Player.STARTING_HEALTH/15+1;
	final static float SPEEDX_PERCENT = 0f;
	final static float SPEEDY_PERCENT = Game.GAME_SPEED_NORMAL_PERCENT/2;
	final static int RECHARGE_TIME = 240;
	final static int STOP_TIME = 60;
	final static int NUMBER_SHOTS  = 8;
	final static float ARC_PERCENT = 1f;

	final static int POINTS = 40;
	
	int recharge;
	int stopped;

	
	public AOEEnemy(Game theGame, float xPosition, float yPosition) {
		super(theGame, IMAGE_ID2, xPosition, yPosition, Game.screenHeight*HEIGHT_PERCENT, Game.screenHeight*HEIGHT_PERCENT,DAMAGE,HEALTH, POINTS);
		childEntities.add(new AOEEnemyShot(game,0f,0f));
		recharge = 0;
		stopped = 0;
		velocityY = Game.calcSpeed(SPEEDY_PERCENT);
		velocityX = Game.calcSpeed(SPEEDX_PERCENT);
	}
	
	@Override
	public void update(){
		super.update();

		if (stopped>0){
			stopped--;
		} else{
			if (recharge>0){
				recharge--;
			}
		}
	}

	@Override
	void ai() {
		// Log.d("DISTANCE",""+distance());


		if (stopped==0){
			velocityX = Game.calcSpeed(SPEEDX_PERCENT);
			velocityY = Game.calcSpeed(SPEEDY_PERCENT);
			//bitmap = game.getBitmap(IMAGE_ID);
		}
		
		if (recharge > 0 || yPos<Game.screenHeight/2) {

		} else {
			
			//stop flight
			stopped = STOP_TIME;
			velocityX= 0f;
			velocityY = 0f;
			//bitmap = game.getBitmap(IMAGE_ID2);
			
			
			recharge = RECHARGE_TIME;
			
			boolean odd = false;
			int numShots = NUMBER_SHOTS;
			
			if (NUMBER_SHOTS%2==1){
				odd = true;
				numShots = numShots-1;
			}
			
			for (int i =0; i<numShots/2; i++){
				
			AOEEnemyShot shot1 = new AOEEnemyShot(game, xPos, yPos);
			
			double angle1 = (Math.PI*3/2 +(2*Math.PI*ARC_PERCENT/2))-(double)i/(double)numShots*(2*Math.PI*ARC_PERCENT);

			shot1.setVelocityY((float) (shot1.getSpeed() * Math
					.sin(angle1)));
			shot1.setVelocityX((float) (shot1.getSpeed()* Math
					.cos(angle1)));
			
			AOEEnemyShot shot2 = new AOEEnemyShot(game, xPos, yPos);
			
			double angle2 = (Math.PI*3/2 -(2*Math.PI*ARC_PERCENT/2))+(double)i/(double)numShots*(2*Math.PI*ARC_PERCENT);

			shot2.setVelocityY((float) (shot2.getSpeed() * Math
					.sin(angle2)));
			shot2.setVelocityX((float) (shot2.getSpeed() * Math
					.cos(angle2)));

			game.addEntity(shot1);
			game.addEntity(shot2);
			

			}
			
			if (odd){
				AOEEnemyShot shot3 = new AOEEnemyShot(game, xPos, yPos);
				
				double angle3 = Math.PI*3/2;

				shot3.setVelocityY((float) (shot3.getSpeed()* Math
						.sin(angle3)));
				shot3.setVelocityX((float) (shot3.getSpeed()* Math
						.cos(angle3)));

				game.addEntity(shot3);
			}
			
		}
	}

	@Override
	void createLoot() {
		game.addEntity(new GoldDrop(game,xPos,yPos));
	}
	

	class AOEEnemyShot extends EnemyShot{
		
		final static int DAMAGE = Player.STARTING_HEALTH/15+1;
		final static float SPEED_PERCENT = Game.VELOCITY_MEDIUM_PERCENT/2;

		public AOEEnemyShot(Game theGame, float xPosition, float yPosition) {
			super(theGame, AOEEnemy.IMAGE_ID2, xPosition, yPosition, 20, 20, Game.calcSpeed(SPEED_PERCENT),DAMAGE);
			
		}
		
		
	}

}
