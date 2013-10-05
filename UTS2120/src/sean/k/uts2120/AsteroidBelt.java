package sean.k.uts2120;

import java.util.ArrayList;

public class AsteroidBelt extends GameEntity{

	
	final static float THIN_DENSITY = .005f*Game.THREAD_WAIT_TIME;
	final static float MEDIUM_DENSITY = THIN_DENSITY*4/3;
	final static float THICK_DENSITY = THIN_DENSITY*2;
	
	final static float VELOCITY_SLOW_PERCENT = Game.VELOCITY_SLOW_PERCENT;
	final static float VELOCITY_MEDIUM_PERCENT = VELOCITY_SLOW_PERCENT * 3/2;
	
	private float asteroidsPerFrame;
	private float asteroidSpeed;
	
	private double astChance;
	private double astTypeChance;
	private double spanMultiplier;

	private ArrayList<Asteroid> astArray;
	private boolean left;
	
	public AsteroidBelt(Game theGame, boolean leftOfScreen,  float theSpan, float theAsteroidDensity, float theAsteroidSpeed, ArrayList<Asteroid> asteroidTypes) {
		super(theGame, GameEntity.NO_IMAGE, 0, Game.topMarginHeight-theSpan/2, 5,theSpan);
		childEntities.addAll(asteroidTypes);
		astArray = asteroidTypes;
		left = leftOfScreen;
		if (!left){
			setX(Game.screenWidth);
		}
		
		asteroidsPerFrame = theAsteroidDensity;
		asteroidSpeed = theAsteroidSpeed;


	}
	
	/*
	 * NOTE:the average number of asteroids on screen will be
	 * screenWidth*Frame/Distance*asteroidRate
	 */
	
	@Override
	public void spawn(){



		int initialAsteroidsOnScreen = (int)(Game.screenWidth/(asteroidSpeed)*asteroidsPerFrame);
		//Log.d("AOS",""+initialAsteroidsOnScreen);
		double screenWidthMultiplier;
		for (int i =0; i<initialAsteroidsOnScreen; i++){
			screenWidthMultiplier = Math.random();
			createAsteroid((float) (screenWidthMultiplier*Game.screenWidth));
		}
	}
	
/*
 * the bounds are slightly different because it spawns entities that would normally
 * be out of bounds.
 */
	@Override 
	public void checkBounds(){

			if ((xPos-width/2)>Game.screenWidth || (xPos+width/2)<0 || (yPos-height/2)>(Game.screenHeight+Game.bottomMarginHeight) || (yPos+height/2)<Game.topMarginHeight-height/2){
			remove = true;
			}

	}
	
	/*
	 * every frame there is a certain chance of creating a new asteroid based
	 * on the density specified in construction.
	 */
	@Override
	public void update(){
		super.update();
		astChance = Math.random();
		if (astChance<asteroidsPerFrame){	
			createAsteroid(xPos);
		}
	}

	/*
	 * the asteroid created is randomly chosen from the arraylist of
	 * asteroids passed during creation
	 */
	public void createAsteroid(float xPosition){
		
			astTypeChance = Math.random();
			
			Asteroid newAst = astArray.get((int) Math.floor(astTypeChance*astArray.size())).copy();
			spanMultiplier = Math.random();
			newAst.setX(xPosition);
			newAst.setY((float) (yPos-height/2+height*spanMultiplier));
			float asteroidVelocity = asteroidSpeed;
			if (!left){
				asteroidVelocity = asteroidVelocity*-1;
			}
			newAst.setVelocityX(asteroidVelocity);
			newAst.setAsteroidBeltMember(true, height);
			game.addEntity(newAst);
	}
}
