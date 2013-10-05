package sean.k.uts2120;

import android.graphics.Color;

abstract class Enemy extends GameEntity{

	int damage; //damage dealth by touching
	int points; //bounty for killing
	
	
	/*
	 * this is an entity that damages the player to touch
	 * and can be destroyed by cannons.  Each must implement and ai() and createLoot() function
	 * to dictate behavior and what happens when the entity is destroyed.
	 */
	public Enemy(Game theGame, int theImageID, float xPosition, float yPosition, float theWidth, float theHeight, int theDamage, int theHealth, int thePoints){
		super(theGame,theImageID, xPosition,yPosition,theWidth,theHeight);
		enemy = true;
		damage = theDamage;
		maxHealth = theHealth;
		health = maxHealth;
		points = thePoints;
	}

	abstract void ai();
	
	abstract void createLoot();

	
	public float distance(){ //distance from player
		return Game.distance(this, game.getPlayer());
		//return (float) Math.sqrt(Math.pow((xPos-game.getPlayer().getX()),2)+Math.pow(yPos-game.getPlayer().getY(),2));
	}
	
	public int getDamage(){
		return damage;
	}
	
	@Override
	public void decHealth(int decAmount){
		super.decHealth(decAmount);
		if (health<=0){
			destroy(true);
		}
	}
	
	
	public void hit(int decHealthAmount){
		decHealth(decHealthAmount);
	}

	public void destroy(boolean createLoot){
		if (!remove){
		game.addEntity(new Explosion(game,xPos,yPos));
			if (createLoot) {
				
				if (points > 0) {
					int totalPoints = points;
					int scoreEffectColor = Color.WHITE;
					if (game.combo()){
						totalPoints = totalPoints*2;
						scoreEffectColor = Color.GREEN;
					}
					game.addEntity(new PointsEffect(game, totalPoints * game.getScoreMultiplier(), xPos, yPos, scoreEffectColor));
					game.incScore(totalPoints * game.getScoreMultiplier());
				}
				
				game.activateComboChain();
				createLoot();
			}
		remove = true;
		
		}
	}

	
}
