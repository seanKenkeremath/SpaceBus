package sean.k.uts2120;

abstract class PickUp extends GameEntity{

	/*
	 * an entity that represents an object that Player can obtain.  
	 * the pickUp method is called when Player collides with this object.
	 */
	public PickUp(Game theGame, int theImageID,  float xPosition, float yPosition,
			float theWidth, float theHeight) {
		super(theGame, theImageID, xPosition, yPosition, theWidth, theHeight);
	}
	
	
	abstract void pickUp();
	
	

}
