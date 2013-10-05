package sean.k.uts2120;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;




public class GameActivity extends Activity{

	GameThread thread;
	GameCanvas panel;
	Game game;
	final static String HIGH_SCORE_KEY = "HighScore";

	
	




	public void initialize(){
		
		game = new Game();
		panel.setGame(game);
		SharedPreferences data = getPreferences(MODE_PRIVATE);
		data.getInt(HIGH_SCORE_KEY, 0);
		thread.setData(data);
		

		game.initialize();
	}
	




	
	public GameCanvas getPanel(){
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
		panel = (GameCanvas) findViewById(R.id.game_canvas);
		initialize();
		
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		
		panel.resume(); //creates new thread
		thread = panel.getGameThread();
		thread.setAccelerometer((SensorManager) getSystemService(Context.SENSOR_SERVICE));
		panel.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent mEvent) {
				if (mEvent.getAction() != MotionEvent.ACTION_UP){
					thread.setTouchInput(mEvent);
					return true;
				}
				return true;
			}
			
		});

		
		if (thread!=null){
		thread.onResume();
		}
		
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		
		thread.onPause();
		panel.pause(); //destroys old thread
		
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		game = null;
	}



	



	
}
