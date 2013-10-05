package sean.k.uts2120;

import android.graphics.Color;
import android.graphics.LightingColorFilter;

public class BasicEnemy extends Enemy{
	
	final static int IMAGE_ID = R.drawable.spaceship2;
	

	final static float HEIGHT_PERCENT = 0.14f;
	final static float WIDTH_PERCENT = 0.1f;
	final static int HEALTH = SmallAsteroid.HEALTH*2;
	final static int DAMAGE = Player.STARTING_HEALTH/15+1;
	final static float SPEED_PERCENT = Game.VELOCITY_SLOW_PERCENT/6;
	final static int RECHARGE_TIME = 200;
	final static int BURST_INTERVAL = 20;
	final static int POINTS = 10;
	
	int recharge = 0;
	int shotsFired = 0;
	
	boolean spotted;
	
	
	/*
	 * this enemy moves at the same speed as the rest of the game. 
	 * If it gets close to you it "sees" you and moves at your speed.
	 * It fires bursts of three shots and can be destroyed with one shot.
	 * If you use your booster you can get past it.
	 */
	public BasicEnemy(Game theGame, float xPosition, float yPosition) {
		super(theGame,IMAGE_ID, xPosition, yPosition, Game.screenHeight*WIDTH_PERCENT, Game.screenHeight*HEIGHT_PERCENT,DAMAGE,HEALTH, POINTS);
		childEntities.add(new BasicEnemyShot(game,0f,0f));
		recharge = 0;
		shotsFired = 0;
		spotted = false;

	}
	
	@Override
	public void update(){
		super.update();
		if (recharge>0){
			recharge--;
		}
	}

	@Override
	void ai() {
		// Log.d("DISTANCE",""+distance());
		

		float deltaY = yPos - game.getPlayer().getY();
		float deltaX = xPos - game.getPlayer().getX();

		double angle = Math.acos(deltaX
				/ (Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2))));

		if (!spotted && distance() > Game.screenHeight / 3) {

			setVelocityX((float) (Game.calcSpeed(SPEED_PERCENT) * Math.cos(angle) * -1));

		} else if (deltaY*-1>Game.screenHeight/4 && distance() < Game.screenHeight/3){
			spotted = true;
			setVelocityY(Game.calcSpeed(Game.GAME_SPEED_NORMAL_PERCENT)*-1);
			setVelocityX((float) (Game.calcSpeed(SPEED_PERCENT)*2 * Math.cos(angle) * -1));			
			if (recharge > 0) {

			} else {
				EnemyShot shot = new BasicEnemyShot(game, xPos, yPos);
				shot.setVelocityY((float) (shot.getSpeed()*Math.sin(angle)));
				shot.setVelocityX((float) (shot.getSpeed()*Math.cos(angle)*-1));
				game.addEntity(shot);
				shotsFired++;
				if (shotsFired<3){
					recharge = BURST_INTERVAL;
				} else{
					recharge = RECHARGE_TIME;
					shotsFired = 0;
				}
				
			}
		} else if (deltaY>0){
			spotted = false;
			setVelocityY(0f);

		}

	}

	@Override
	void createLoot() {
		double lootChance = Math.random();
		if (lootChance<.3){
			game.addEntity(new SilverDrop(game,xPos,yPos));
		} else if (lootChance<.7){
			game.addEntity(new CopperDrop(game,xPos,yPos));
		}
		
		
	}
	



}


class BasicEnemyShot extends EnemyShot{
	
	final static int DAMAGE = Player.STARTING_HEALTH/15+1;
	final static float SPEED_PERCENT = Game.VELOCITY_SLOW_PERCENT;
	
	LightingColorFilter filter;
	
	/*
	 * the shot fired by this enemy
	 */
	public BasicEnemyShot(Game theGame, float xPosition, float yPosition) {
		super(theGame, Laser.IMAGE_ID, xPosition, yPosition, Laser.WIDTH_PERCENT*Game.screenHeight, (int)(Laser.HEIGHT_PERCENT*Game.screenHeight),Game.calcSpeed(SPEED_PERCENT),DAMAGE);
		filter = new LightingColorFilter(Color.RED, 100);
		paint.setColorFilter(filter);
		
	}
	
	
}
