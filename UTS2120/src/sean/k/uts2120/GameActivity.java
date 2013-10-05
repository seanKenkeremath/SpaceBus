package sean.k.uts2120;
import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;





public class GameActivity extends Activity{

	GameThread _thread;
	DrawingPanel panel;
	Game game;
	
	




	public void initialize(){
		
		game = new Game();
		panel.setGame(game);
		
		
		//initialize bitmaps
		/*
		Bitmap busStopImage = createImage(R.drawable.bus_stop, (int)(Game.screenHeight*BusStop.WIDTH_PERCENT), (int)(Game.screenHeight*BusStop.HEIGHT_PERCENT));
		game.cacheImage(BusStop.NAME,busStopImage);
		

		

		
		Bitmap earthImage = createImage(R.drawable.earth, (int)Game.screenHeight/2, (int)Game.screenHeight/2);
		game.cacheImage(LevelOne.NAME_EARTH, earthImage);
		
		Bitmap levelOneImage = createImage(R.drawable.spacebackground2, (int)Game.screenWidth, (int)Game.screenHeight);
		game.cacheImage(LevelOne.NAME, levelOneImage);
		
		

		
		Bitmap astSImage = createImage(R.drawable.asteroid, (int)(MediumAsteroid.WIDTH_PERCENT*Game.screenHeight), (int)(MediumAsteroid.HEIGHT_PERCENT*Game.screenHeight));
		game.cacheImage(Asteroid.NAME, astSImage);	

		Bitmap astIceSImage = createImage(R.drawable.ice_chunk_small, (int)(SmallIceAsteroid.WIDTH_PERCENT*Game.screenHeight), (int)(SmallIceAsteroid.HEIGHT_PERCENT*Game.screenHeight));
		game.cacheImage(SmallIceAsteroid.NAME, astIceSImage);
		
		
		Bitmap fuelImage = createImage(R.drawable.antimatter, (int)(Fuel.WIDTH_PERCENT*Game.screenHeight), (int)(Fuel.HEIGHT_PERCENT*Game.screenHeight));
		game.cacheImage(Fuel.NAME, fuelImage);
		
		Bitmap enemyImage = createImage(R.drawable.ic_launcher, (int)(BasicEnemy.HEIGHT_PERCENT*Game.screenHeight), (int)(BasicEnemy.HEIGHT_PERCENT*Game.screenHeight));
		game.cacheImage(BasicEnemy.NAME, enemyImage);
		
		Bitmap aoeEnemyImage = createImage(R.drawable.megilloutrage, (int)(AOEEnemy.HEIGHT_PERCENT*Game.screenHeight), (int)(AOEEnemy.HEIGHT_PERCENT*Game.screenHeight));
		game.cacheImage(AOEEnemy.NAME, aoeEnemyImage);
		
		Bitmap aoeEnemyImage2 = createImage(R.drawable.megillhappy, (int)(AOEEnemy.WIDTH_PERCENT*Game.screenHeight), (int)(AOEEnemy.HEIGHT_PERCENT*Game.screenHeight));
		game.cacheImage(AOEEnemy.NAME2, aoeEnemyImage2);
		*/

		game.initialize();
	}
	




	
	public DrawingPanel getPanel(){
		return panel;
	}
	
	
	/*
	 * the purpose of this class is to determine the screen dimensions,
	 * set up the drawing panel and initializes the game thread.
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.game);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		Game.screenWidth = displaymetrics.widthPixels;
		//Game.screenWidth = 600;
		Game.screenHeight = displaymetrics.heightPixels*(1f-Game.BOTTOM_MARGIN_PERCENT-Game.TOP_MARGIN_PERCENT);
		//Game.screenHeight = 600*(1f-Game.BOTTOM_MARGIN_PERCENT-Game.TOP_MARGIN_PERCENT);
		Game.bottomMarginHeight = displaymetrics.heightPixels*Game.BOTTOM_MARGIN_PERCENT;
		Game.topMarginHeight = displaymetrics.heightPixels*Game.TOP_MARGIN_PERCENT;
		Game.totalHeight = Game.screenHeight+ Game.bottomMarginHeight+Game.topMarginHeight;
		Game.velocitySlow = Game.VELOCITY_SLOW_PERCENT*Game.screenHeight;
		Game.velocityMed = Game.VELOCITY_MEDIUM_PERCENT*Game.screenHeight;
		Game.velocityFast = Game.VELOCITY_FAST_PERCENT*Game.screenHeight;
		Game.tiltSensitivity = Game.TILT_SENSITIVITY_PERCENT_WIDTH_PER_G*Game.screenWidth;
		Game.gameSpeedNormal = Game.GAME_SPEED_NORMAL_PERCENT*Game.screenHeight;
		panel = (DrawingPanel) findViewById(R.id.drawing_panel);
		initialize();
		
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		
		panel.resume(); //creates new thread
		_thread = panel.getGameThread();
		_thread.setAccelerometer((SensorManager) getSystemService(Context.SENSOR_SERVICE));
		panel.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent mEvent) {
				if (mEvent.getAction() != MotionEvent.ACTION_UP){
					_thread.setTouchInput(mEvent);
					return true;
				}
				return true;
			}
			
		});

		
		if (_thread!=null){
		_thread.onResume();
		}
		
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		
		_thread.onPause();
		panel.pause(); //destroys old thread
		
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		game = null;
	}



	



	
}
