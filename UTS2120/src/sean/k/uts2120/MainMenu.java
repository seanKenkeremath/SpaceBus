package sean.k.uts2120;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends Activity {
	
	
	final static long MAIN_MENU_DELAY = 2500L;
	final static String PREFERENCES_LABEL = "GamePreferences";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		
		final View mainPanel = findViewById(R.id.main_menu_panel);
		mainPanel.setVisibility(View.INVISIBLE);
		

		Button newGameButton = (Button) findViewById(R.id.main_menu_startButton);
		newGameButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent gameIntent = new Intent(getApplicationContext(), GameActivity.class);
				startActivity(gameIntent);
				finish();
			}
			
		});
		
		TextView highScore = (TextView) findViewById(R.id.main_menu_high_score);
		SharedPreferences data = getSharedPreferences(PREFERENCES_LABEL,MODE_PRIVATE);
		highScore.setText(""+data.getInt(GameActivity.HIGH_SCORE_KEY, 0));
		
		mainPanel.postDelayed(new Runnable(){

			@Override
			public void run() {
				mainPanel.setVisibility(View.VISIBLE);
			}
			
		}, MAIN_MENU_DELAY);
		
	}
}
