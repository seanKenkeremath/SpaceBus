package sean.k.uts2120;

abstract class GameAnimation {

	float duration;
	float age;
	boolean remove;
	boolean UIAnimation; //whether the animation is paused when game is paused
	Game game;
	
	/*
	 * animations all have an animate() method that is called during the animation phase
	 * of the GameThread.  An animate() method may include changing its target entity's paint
	 * or matrix to produce some graphical effect.
	 */
	public GameAnimation(Game theGame, float theDuration, boolean isUIAnimation){
		duration = theDuration;
		remove = false;
		age = 0;
		game = theGame;
		UIAnimation = isUIAnimation;
	}
	
	abstract void animate();
	
	abstract void end();
	
	public boolean remove(){
		return remove;
	}
}
