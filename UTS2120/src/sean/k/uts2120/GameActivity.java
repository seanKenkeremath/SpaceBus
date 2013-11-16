package sean.k.uts2120;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ViewFlipper;

public class GameActivity extends Activity {

	private GameThread thread;
	GameCanvas panel;
	private ViewFlipper menuFlipper;
	private Game game;
	final static String HIGH_SCORE_KEY = "HighScore";
	final static String DEBUG = "SPACEBUS_DEBUG";
	final static int MENU_PAUSE = 0;
	final static int MENU_GAME_OVER = 1;
	final static int MENU_LEVEL_COMPLETE = 2;
	final static int MENU_UPGRADES = 3;

	


	public void initialize() {

		game = new Game();
		Log.d(DEBUG, "Creating new Game Object");
		SharedPreferences data = getSharedPreferences(
				MainMenu.PREFERENCES_LABEL, MODE_PRIVATE);
		int highScore = data.getInt(HIGH_SCORE_KEY, 0);
		game.setHighScore(highScore);
		Log.d(DEBUG, "Loaded high score:" + highScore);


		panel.setGame(game);
		panel.setData(data);

		game.initialize();
		
		menuFlipper = (ViewFlipper) findViewById(R.id.game_menu);
		menuFlipper.setVisibility(View.INVISIBLE);
			
	}

	public ViewFlipper getMenuFlipper(){
		return menuFlipper;
	}
	public GameCanvas getPanel() {
		return panel;
	}

	public GameThread getThread(){
		return thread;
	}
	
	public Game getGame(){
		return game;
	}
	/*
	 * the purpose of this class is to determine the screen dimensions, set up
	 * the drawing panel and initializes the game thread.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d(DEBUG, "OnCreate");
		setContentView(R.layout.game);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		Game.screenWidth = displaymetrics.widthPixels;
		// Game.screenWidth = 600;
		Game.screenHeight = displaymetrics.heightPixels
				* (1f - Game.BOTTOM_MARGIN_PERCENT - Game.TOP_MARGIN_PERCENT);
		// Game.screenHeight =
		// 600*(1f-Game.BOTTOM_MARGIN_PERCENT-Game.TOP_MARGIN_PERCENT);
		Game.bottomMarginHeight = displaymetrics.heightPixels
				* Game.BOTTOM_MARGIN_PERCENT;
		Game.topMarginHeight = displaymetrics.heightPixels
				* Game.TOP_MARGIN_PERCENT;
		Game.totalHeight = Game.screenHeight + Game.bottomMarginHeight
				+ Game.topMarginHeight;
		Game.velocitySlow = Game.VELOCITY_SLOW_PERCENT * Game.screenHeight;
		Game.velocityMed = Game.VELOCITY_MEDIUM_PERCENT * Game.screenHeight;
		Game.velocityFast = Game.VELOCITY_FAST_PERCENT * Game.screenHeight;
		Game.tiltSensitivity = Game.TILT_SENSITIVITY_PERCENT_WIDTH_PER_G
				* Game.screenWidth;
		Game.gameSpeedNormal = Game.GAME_SPEED_NORMAL_PERCENT
				* Game.screenHeight;
		panel = (GameCanvas) findViewById(R.id.game_canvas);
		initialize();

	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(DEBUG, "OnResume");
		panel.resume(); // creates new thread
		thread = panel.getGameThread();
		thread.setAccelerometer((SensorManager) getSystemService(Context.SENSOR_SERVICE));
		panel.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent mEvent) {
				if (mEvent.getAction() != MotionEvent.ACTION_UP) {
					thread.setTouchInput(mEvent);
					return true;
				}
				return true;
			}

		});

		if (thread != null) {
			thread.onResume();
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(DEBUG, "OnPause");
		thread.onPause();
		panel.pause(); // destroys old thread
		Log.d(DEBUG,"Old thread destroyed");

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(DEBUG,"On Destroy, nulling Game");

		game = null;
	}

}
