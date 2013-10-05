package sean.k.uts2120;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class MainMenuScreen extends GameScreen{

	int backgroundColor;
	private final static float TEXT_SIZE_PERCENT = .05f;
	final static int BUTTON_WAIT_TIME = (int) (1000f/Game.THREAD_WAIT_TIME);
	private Paint menuPanelPaint;
	private RectF menuPanel;
	
	private final static float MENU_PANEL_HEIGHT_PERCENT_TOTAL = .6f;
	private final static float MENU_PANEL_WIDTH_PERCENT_WIDTH = .8f;
/*
 * the screen and splash screen when the game is first opened or when gameplay
 * is quit.  It contains a bitmap background image.
 */
	public MainMenuScreen(GameThread theThread) {
		super(theThread, R.drawable.menuscreen, BUTTON_WAIT_TIME);
		buttons.add(new NewGameButton(thread, Game.totalHeight/3));
		backgroundColor = Color.parseColor("#ff000000");
		paint.setTextSize(TEXT_SIZE_PERCENT * Game.screenHeight);
		paint.setColor(Color.WHITE);
		menuPanelPaint = new Paint();
		menuPanelPaint.setColor(Color.BLACK);
		menuPanel = new RectF();
		/*
		menuPanel.set(Game.screenWidth*.5f - MENU_PANEL_WIDTH_PERCENT_WIDTH*Game.screenWidth*.5f,
				Game.totalHeight*.5f - MENU_PANEL_HEIGHT_PERCENT_TOTAL*Game.totalHeight*.5f, 
				Game.screenWidth*.5f + MENU_PANEL_WIDTH_PERCENT_WIDTH*Game.screenWidth*.5f,
				Game.totalHeight*.5f + MENU_PANEL_HEIGHT_PERCENT_TOTAL*Game.totalHeight*.5f);
				*/
		
		menuPanel.set(Game.screenWidth*(.5f-MENU_PANEL_WIDTH_PERCENT_WIDTH*.5f), Game.totalHeight*(.5f-MENU_PANEL_HEIGHT_PERCENT_TOTAL*.5f), Game.screenWidth*(.5f+MENU_PANEL_WIDTH_PERCENT_WIDTH*.5f), Game.totalHeight*(.5f+MENU_PANEL_HEIGHT_PERCENT_TOTAL*.5f));
				
				
	}
	
	@Override
	public void drawPanel(Canvas canvas){
		canvas.drawRect(menuPanel, menuPanelPaint);
		canvas.drawText("High Score:", Game.screenWidth*.5f, Game.totalHeight*2/3, paint);
		canvas.drawText("" +thread.getGame().getHighScore() + " DPM", Game.screenWidth*.5f, Game.totalHeight*2/3 + paint.getTextSize(), paint);

	}
	


}
