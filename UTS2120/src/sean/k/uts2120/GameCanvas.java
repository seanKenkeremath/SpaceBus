package sean.k.uts2120;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameCanvas extends SurfaceView implements SurfaceHolder.Callback{
	

	
	GameThread thread;
	Game game;
	SharedPreferences data;

	/*
	 * this is the Android view that holds a canvas upon which the entire game is drawn
	 */
	public GameCanvas(Context context){
		super(context);
		getHolder().addCallback(this);
	
	}

	public GameCanvas(Context context, AttributeSet attr){
		super(context, attr);
		getHolder().addCallback(this);

	
	}
	
	
	@Override
	public void onDraw(Canvas canvas){
		
		if (game.getCurrentLevel()!=null){

			game.drawLevel(canvas);
		}
			//draw screen
			thread.getScreen().draw(canvas);
			
	}
	
	
	

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.setWillNotDraw(false);

		

	}
	
	
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		//pause();
	}
	
	public GameThread getGameThread(){
		return thread;
	}
	
	public void setGameThread(GameThread newThread){
		thread = newThread;
	}
	
	public void setGame(Game theGame){
		game = theGame;
	}
	
	public void setData(SharedPreferences theData){
		data = theData;
	}
	
	
	public void pause(){ //for activity onpause
		try {
			thread.save();
			thread.setRunning(false);
			thread.join();
			
		} catch (InterruptedException e){}
	}
	
	public void resume(){ //for activity onresume
		thread = new GameThread(game, data, getHolder(), this);
		thread.setRunning(true);
		thread.start();
	}
		
	}



