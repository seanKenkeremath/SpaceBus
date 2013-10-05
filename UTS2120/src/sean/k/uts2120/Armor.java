package sean.k.uts2120;

import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Armor {

	float armorPercent; // prevents this percent of damage
	int armorLevel;  //the discrete upgrade level of the armor
	Player player;
	Paint armorPaint;
	
	final static float LEVEL_ONE = 0f;
	final static float LEVEL_TWO = .2f;
	final static float LEVEL_THREE = .4f;
	final static int MAX_LEVEL = 3;
	
	final static float STRIPE_WIDTH_PERCENT= Player.WIDTH_PERCENT*.1f;
	
	public Armor(Player thePlayer, int theLevel){
		armorPercent  = LEVEL_ONE;
		armorLevel = 1;
		while(armorLevel<theLevel){
			upgrade();
		}
		armorLevel = theLevel;
		player = thePlayer;
		armorPaint = new Paint();
		armorPaint.setStyle(Style.FILL);

		
	}
	
	
	public void upgrade(){
		if (armorLevel==1){
			armorPercent = LEVEL_TWO;
			armorLevel++;
		} else if (armorLevel==2){
			armorPercent = LEVEL_THREE;
			armorLevel++;
		}
	}
	
	public int protect(int damageAmount){
		return (int) (damageAmount-damageAmount*armorPercent);
	}
	
	public int getLevel(){
		return armorLevel;
	}
}
