package sean.k.uts2120;

abstract class Asteroid extends Enemy{
	
	
	//Matrix spinMatrix;
	//float rotationSpeed;
	final static int IMAGE_ID = R.drawable.asteroid;
	final static float MAX_ROTATION_SPEED =(float) (2*Math.PI*Game.THREAD_WAIT_TIME/20);
	private boolean asteroidBeltMember;
	private float asteroidBeltSpan;
	
	public Asteroid(Game theGame, float xPosition, float yPosition, float theWidth, float theHeight, int theDamage, int theHealth, int thePoints) {
		super(theGame, IMAGE_ID, xPosition, yPosition, theWidth, theHeight, theDamage, theHealth, thePoints);
		//velocityX = (float) (Math.cos(Math.random()*Math.PI*2)*Game.SPEED);
		//spinMatrix = new Matrix();
		//rotationSpeed = (float) ((Math.random()-1)*MAX_ROTATION_SPEED);
		asteroidBeltSpan = 0f;
		asteroidBeltMember = false;
		
	}
	

	
	abstract Asteroid copy();
	
	public void setAsteroidBeltMember(boolean partOfBelt, float spanOfBelt){
		asteroidBeltMember = partOfBelt;
		if (partOfBelt){
		asteroidBeltSpan = spanOfBelt;
		} else{
			asteroidBeltSpan = 0f;
		}
	}
	
	@Override
	public void ai(){
		
	}
	
	@Override 
	public void checkBounds(){
		if (asteroidBeltMember){
			if ((xPos-width/2)>Game.screenWidth || (xPos+width/2)<0 || (yPos-height/2)>(Game.screenHeight+Game.bottomMarginHeight) || (yPos+height/2)<Game.topMarginHeight-asteroidBeltSpan){
			remove = true;
			}
		} else{
			super.checkBounds();
		}
	}
	
	/*
	@Override
	public void draw(Canvas c){


		if (game.getBitmap(name)!=null){
			//spinMatrix.setTranslate(xPos-width/2, yPos-height/2);
			//spinMatrix.preRotate(rotationSpeed*age,width/(float)2,height/(float)2);
			//c.drawBitmap(bitmap, spinMatrix, paint);
			c.drawBitmap(game.getBitmap(name), xPos-width/2, yPos-height/2, paint);
		}
	}
	*/

	//special draw?
	


}
