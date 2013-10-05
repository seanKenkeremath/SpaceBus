package sean.k.uts2120;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;

public class Booster {
	
	
	final static float SPEED_PERCENT_LEVEL_ONE = Game.GAME_SPEED_NORMAL_PERCENT*2;
	final static int BOOST_RECHARGE_ONE  = (int) (50*Game.THREAD_WAIT_TIME);
	final static float BOOST_DURATION_PERCENT_ONE = .15f;
	final static int BOOST_COLOR_ONE = 0xff0000dd;
	
	final static float SPEED_PERCENT_LEVEL_TWO = SPEED_PERCENT_LEVEL_ONE*4/3;
	final static int BOOST_RECHARGE_TWO  = BOOST_RECHARGE_ONE*3/4;
	final static float BOOST_DURATION_PERCENT_TWO = .15f;
	final static int BOOST_COLOR_TWO = 0xff9944cc;
	
	final static float SPEED_PERCENT_LEVEL_THREE = SPEED_PERCENT_LEVEL_TWO*4/3;
	final static int BOOST_RECHARGE_THREE = BOOST_RECHARGE_TWO*3/4;
	final static float BOOST_DURATION_PERCENT_THREE = .15f;
	final static int BOOST_COLOR_THREE = 0xFF33FFEE;
	
	final static float BOOSTER_RADIUS_PERCENT = .02f;
	final static float JET_HEIGHT_PERCENT = .02f;
	final static float JET_WIDTH_PERCENT = .03f;
	
	final static int MAX_LEVEL = 3;

	
	public int boostTotalRechargeTime;
	public int boostRechargeTime;
	
	public float boostTimeFraction; //out of recharge time
	public int boostTime;
	
	public Game game;
	public Player player;

	Paint boosterPaint;
	Paint jetPaint;
	
	private int boosterColor;
	
	private float boosterRadius;
	
	private float bottomPlayerYPosition;

	
	private float leftJetXPosition;
	private float rightJetXPosition;
	
	
	private RectF leftJet;
	private RectF rightJet;
	
	private float jetWidth;
	private float jetHeight;
	
	boolean active;
	
	int boosterLevel;
	float boosterSpeed;
	
	
	/*
	 * this class represents the booster on the bus.  It holds an Upgrade Level
	 * which dictates how strong the boost is and how fast it recharges.
	 * It also dicatates what color the booster jet is.
	 */
	public Booster(Game theGame, Player thePlayer, int theLevel){
		game = theGame;
		player = thePlayer;
		boosterSpeed  = Game.calcSpeed(SPEED_PERCENT_LEVEL_ONE);
		boostTotalRechargeTime = BOOST_RECHARGE_ONE;
		boostRechargeTime = 0;
		boostTimeFraction = BOOST_DURATION_PERCENT_ONE;
		boostTime = 0;
		
		boosterLevel = 1;
		boosterColor = BOOST_COLOR_ONE;
		while(boosterLevel<theLevel){
			upgrade();
		}
		boosterLevel = theLevel;
		
		
		boosterPaint = new Paint();
		boosterPaint.setStyle(Style.FILL);
		boosterPaint.setColor(boosterColor);
		
		jetPaint = new Paint();
		jetPaint.setStyle(Style.FILL);
		jetPaint.setColor(Color.BLACK);
		
		boosterRadius = Game.screenHeight*BOOSTER_RADIUS_PERCENT;

		
		bottomPlayerYPosition = player.getY()+player.getHeight()/2;
		jetWidth = Game.screenHeight*JET_WIDTH_PERCENT;
		jetHeight = Game.screenHeight*JET_HEIGHT_PERCENT;
		
		leftJet = new RectF();
		rightJet = new RectF();
		

	}
	
	public void draw(Canvas canvas){
		
		leftJetXPosition = player.getX()-player.getWidth()/4;
		rightJetXPosition = player.getX()+player.getWidth()/4;
		

		leftJet.set(leftJetXPosition-jetWidth/2,bottomPlayerYPosition-jetHeight/2,leftJetXPosition+jetWidth/2,bottomPlayerYPosition+jetHeight/2);
		rightJet.set(rightJetXPosition-jetWidth/2,bottomPlayerYPosition-jetHeight/2,rightJetXPosition+jetWidth/2,bottomPlayerYPosition+jetHeight/2);
		
		canvas.drawRect(leftJet,jetPaint);
		canvas.drawRect(rightJet,jetPaint);
		
		if (active){
			canvas.drawCircle(rightJetXPosition, rightJet.bottom+boosterRadius, boosterRadius, boosterPaint);
			canvas.drawCircle(leftJetXPosition, leftJet.bottom+boosterRadius, boosterRadius, boosterPaint);
		} else{
			canvas.drawCircle(rightJetXPosition, rightJet.bottom+boosterRadius, boosterRadius*(game.getUpTime()%10)/10, boosterPaint);
			canvas.drawCircle(leftJetXPosition, leftJet.bottom+boosterRadius, boosterRadius*(game.getUpTime()%10)/10, boosterPaint);
		}
	}
	
	
	public void boost(){
		if (boostRechargeTime>0){
			return;
		}
		game.setSpeed(boosterSpeed);
		active = true;
		boostTime = (int) (boostTotalRechargeTime*boostTimeFraction);
		boostRechargeTime = boostTotalRechargeTime;
	}
	
	
	public void reset(){
		boostTime = 0;
		boostRechargeTime = 0;
		game.setSpeed(Game.gameSpeedNormal);
		active = false;
	}
	
	public void upgrade(){
		if (boosterLevel==1){
			boosterSpeed = Game.calcSpeed(SPEED_PERCENT_LEVEL_TWO);
			boostTotalRechargeTime = BOOST_RECHARGE_TWO;
			boostTimeFraction = BOOST_DURATION_PERCENT_TWO;
			boosterColor = BOOST_COLOR_TWO;
			boosterPaint.setColor(boosterColor);
			boosterLevel++;
			
		} else if (boosterLevel==2){
			boosterSpeed = Game.calcSpeed(SPEED_PERCENT_LEVEL_THREE);
			boostTotalRechargeTime = BOOST_RECHARGE_THREE;
			boostTimeFraction = BOOST_DURATION_PERCENT_THREE;
			boosterColor = BOOST_COLOR_THREE;
			boosterPaint.setColor(boosterColor);
			boosterLevel++;
		}
	}
	
	public void update(){
		if (boostTime>0){
			boostTime--;
			if (boostTime==0){
				game.setSpeed(Game.gameSpeedNormal);
				active = false;
			}
		}

		if (boostRechargeTime>0){
			boostRechargeTime--;
		}
	}
	
	public int getLevel(){
		return boosterLevel;
	}
	
	public double getBoostRechargeRatio(){
		return (double)(boostTotalRechargeTime-boostRechargeTime)/(double)boostTotalRechargeTime;
	}
	
}
