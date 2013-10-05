package sean.k.uts2120;

public class Effect extends GameEntity{

	int lifeTime;
	
	/*
	 * this is an entity that does not interact with other objects.  It has a specified 
	 * lifetime after which it will disappear.
	 */
	public Effect(Game theGame, int theImageID, float xPosition, float yPosition,
			float theWidth, float theHeight, int theLifeTime) {
		super(theGame, theImageID, xPosition, yPosition, theWidth, theHeight);
		lifeTime = theLifeTime;
	}

	
	@Override
	protected void updateAge(){
		super.updateAge();
		if (age>lifeTime){
			remove = true;
		}
	}
}
