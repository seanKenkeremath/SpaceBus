package sean.k.uts2120;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.RectF;

public class UpgradeScreen extends GameScreen{

	int backgroundColor;
	
	
	
	final static int UPGRADES_PER_ROW = 3;
	final static int UPGRADES = 6;
	
	final static float TITLE_HEIGHT_PERCENT_TOTAL = .08f;
	final static float TITLE_MARGIN_HEIGHT_PERCENT_TOTAL = .10f;
	final static float UPGRADE_BUTTON_MARGIN_HEIGHT_PERCENT_TOTAL  = .03f;
	final static float UPGRADE_MESSAGE_HEIGHT_PERCENT_TOTAL = .07f;
	final static float UPGRADE_COST_HEIGHT_PERCENT_TOTAL = .05f;
	final static float TOTAL_GOLD_HEIGHT_PERCENT_TOTAL = .05f;
	
	final static float UPGRADE_BUTTON_MARGIN_WIDTH_PERCENT_WIDTH = .03f;
	
	int titleHeight;
	int titleMarginHeight;
	int weaponUpgradeHeight;
	int upgradeButtonMargin;
	int upgradeMessageHeight;
	int upgradeCostHeight;
	int buyButtonHeight;
	int totalGoldHeight;
	
	int backButtonHeight;
	
	int upgradeBackgroundHeight;
	int upgradeBackgroundWidth;
	
	
	int upgradeButtonMarginWidth;
	
	UpgradeButton selected;
	
	RectF upgradeBackground;
	
	/*
	 * the GameScreen where you can upgrade various components of your bus.
	 * It holds UpgradeButton objects which have a price and an upgrade() method.
	 */

	public UpgradeScreen(GameThread theThread) {
		super(theThread, GameScreen.NO_BACKGROUND_IMAGE, 0);
		

		titleHeight = (int) (TITLE_HEIGHT_PERCENT_TOTAL*Game.totalHeight);
		titleMarginHeight = (int) (TITLE_MARGIN_HEIGHT_PERCENT_TOTAL*Game.totalHeight);
		weaponUpgradeHeight = (int) (UpgradeButton.HEIGHT_PERCENT_WIDTH*Game.screenWidth);
		upgradeButtonMargin = (int) (UPGRADE_BUTTON_MARGIN_HEIGHT_PERCENT_TOTAL*Game.totalHeight);
		upgradeMessageHeight = (int) (UPGRADE_MESSAGE_HEIGHT_PERCENT_TOTAL*Game.totalHeight);
		upgradeCostHeight = (int) (UPGRADE_COST_HEIGHT_PERCENT_TOTAL*Game.totalHeight);
		buyButtonHeight = (int) (UpgradeBuyButton.HEIGHT_PERCENT_TOTAL*Game.totalHeight);
		totalGoldHeight = (int) (TOTAL_GOLD_HEIGHT_PERCENT_TOTAL*Game.totalHeight);
		
		upgradeButtonMarginWidth = (int)(UPGRADE_BUTTON_MARGIN_WIDTH_PERCENT_WIDTH*Game.screenWidth);

		
		upgradeBackgroundHeight = (int) ((weaponUpgradeHeight)
				* Math.ceil((double) UPGRADES / (double) UPGRADES_PER_ROW) + upgradeButtonMargin
				* (Math.ceil((double) UPGRADES / (double) UPGRADES_PER_ROW)- 1) );

		upgradeBackgroundWidth = weaponUpgradeHeight*UPGRADES_PER_ROW+upgradeButtonMarginWidth*(UPGRADES_PER_ROW-1);
		upgradeBackground = new RectF(Game.screenWidth/2-upgradeBackgroundWidth/2,titleHeight+titleMarginHeight,Game.screenWidth/2+upgradeBackgroundWidth/2,titleHeight+titleMarginHeight+upgradeBackgroundHeight);
		backButtonHeight = (int) (UpgradeBackButton.HEIGHT_PERCENT_TOTAL*Game.totalHeight);
		
		
		
		//PERCENT OF TOTAL SCREEN HEIGHT OF VARIOUS BUTTONS
		
		
		//Page Title: .08
		//Margin: .1
		//Weapon upgrades: .12
		//Margin: .03
		//Upgrades: .12
		//MARGIN: .03
		//Message: .07
		//Cost: .05
		//Margin: .03
		//Buy Button: .15
		//Margin: .03
		//Your Money: .08
		//Margin: .04
		//Back Button: .15
		
		//weapon upgrades
		buttons.add(new UpgradeLaserSpeedButton(thread, Game.screenWidth/2-UpgradeLaserSpeedButton.WIDTH_PERCENT_WIDTH*Game.screenWidth-upgradeButtonMarginWidth, titleHeight+titleMarginHeight+weaponUpgradeHeight/2,thread.getGame().getPlayer().getLaser()));
		buttons.add(new UpgradeLaserPowerButton(thread, Game.screenWidth/2, titleHeight+titleMarginHeight+weaponUpgradeHeight/2, thread.getGame().getPlayer().getLaser()));
		buttons.add(new UpgradeRepairButton(thread, Game.screenWidth/2+UpgradeLaserSpeedButton.WIDTH_PERCENT_WIDTH*Game.screenWidth+upgradeButtonMarginWidth, titleHeight+titleMarginHeight+weaponUpgradeHeight/2));
		
		buttons.add(new UpgradeArmorButton(thread, Game.screenWidth/2-UpgradeLaserSpeedButton.WIDTH_PERCENT_WIDTH*Game.screenWidth-upgradeButtonMarginWidth, titleHeight+titleMarginHeight+weaponUpgradeHeight+upgradeButtonMargin+weaponUpgradeHeight/2));
		buttons.add(new UpgradeScoreMultiplierButton(thread, Game.screenWidth/2, titleHeight+titleMarginHeight+weaponUpgradeHeight+upgradeButtonMargin+weaponUpgradeHeight/2));
		buttons.add(new UpgradeBoosterButton(thread, Game.screenWidth/2+UpgradeLaserSpeedButton.WIDTH_PERCENT_WIDTH*Game.screenWidth+upgradeButtonMarginWidth, titleHeight+titleMarginHeight+weaponUpgradeHeight+upgradeButtonMargin+weaponUpgradeHeight/2));
		
		buttons.add(new UpgradeBuyButton(thread, Game.totalHeight-backButtonHeight-upgradeButtonMargin-buyButtonHeight*.5f));
		
		buttons.add(new UpgradeBackButton(thread, Game.totalHeight-backButtonHeight*.5f));
		
		//backgroundColor = Color.parseColor("#90000000");
		backgroundColor = Color.parseColor("#ff000000");
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.FILL);
	}

	@Override
	public
	void drawBackground(Canvas canvas) {
		
		canvas.drawColor(backgroundColor);
		paint.setTextSize(TITLE_HEIGHT_PERCENT_TOTAL*Game.totalHeight);
		canvas.drawText("UPGRADE", Game.screenWidth/2, titleHeight, paint);
		
		//canvas.drawRect(upgradeBackground, paint);
		
	}
	
	@Override
	public void draw(Canvas canvas){
		
		//draw background
		drawBackground(canvas);
		
		//draw all buttons, this does the same as abstract GameScreen class except it ghosts out the buy button if you cannot afford upgrade
		
		for (GameButton button : buttons) {
			
			
			if (button instanceof UpgradeBuyButton){
				//does not draw buy button at all if nothing is selected
				if (selected != null) {
					if (thread.getGame().getGold() < selected.getPrice()) {
						//canvas.drawRect(button.getHitBox(), paint);
						
					} else {
						button.draw(canvas);
					}
				}

			} else if (button instanceof UpgradeButton){
				//does not draw upgrades that are not available;
				if (((UpgradeButton) button).available()){
					button.draw(canvas);
				}
			} else{
				button.draw(canvas);
			}
		}
		
		thread.getGame().drawHealthBar(canvas);
		
		//if something is selected, writes the description and cost.  if price is more than current gold, cost is displayed in red
		if (selected!=null){
			paint.setTextSize(UPGRADE_MESSAGE_HEIGHT_PERCENT_TOTAL*Game.totalHeight/3);
			canvas.drawText(selected.getMessage(), Game.screenWidth/2, titleHeight+titleMarginHeight+weaponUpgradeHeight+upgradeButtonMargin+weaponUpgradeHeight+upgradeButtonMargin+(UPGRADE_MESSAGE_HEIGHT_PERCENT_TOTAL*Game.totalHeight/3)/2, paint);
			paint.setTextSize(UPGRADE_COST_HEIGHT_PERCENT_TOTAL*Game.totalHeight);
			
			if (thread.getGame().getGold()<selected.getPrice()){
				paint.setColor(Color.RED);
			}
			canvas.drawText(""+selected.getPrice(),Game.screenWidth/2,titleHeight+titleMarginHeight+weaponUpgradeHeight+upgradeButtonMargin+upgradeMessageHeight+weaponUpgradeHeight+upgradeButtonMargin+(UPGRADE_COST_HEIGHT_PERCENT_TOTAL*Game.totalHeight)/2,paint);
			paint.setColor(Color.WHITE);
		}
		
		
		//draws amount of gold player possesses
		paint.setTextSize(TOTAL_GOLD_HEIGHT_PERCENT_TOTAL*Game.totalHeight);
		canvas.drawText("$$: "+thread.getGame().getGold(),Game.screenWidth/2, titleHeight+titleMarginHeight+weaponUpgradeHeight+upgradeButtonMargin+upgradeMessageHeight + upgradeCostHeight+upgradeButtonMargin+buyButtonHeight+upgradeButtonMargin+(TOTAL_GOLD_HEIGHT_PERCENT_TOTAL*Game.totalHeight)/2, paint);

	}
	
	
	public void buyUpgrades(){

		if (selected==null){
			return;
		}
		if (thread.getGame().getGold()<selected.getPrice()){
			 
		} else{
			thread.getGame().incGold(selected.getPrice()*-1);
			selected.upgrade();
			selected.selected = false;
			selected = null;
		}
	}
	
	public void select(UpgradeButton selectedButton){
		

		if (selected!=null){
		selected.selected = false;
		}
		
		selected = selectedButton;
		selected.selected = true;
		
		
		

		
	}

}