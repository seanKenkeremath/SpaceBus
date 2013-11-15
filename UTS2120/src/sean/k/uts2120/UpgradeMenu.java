package sean.k.uts2120;

import java.util.ArrayList;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

public class UpgradeMenu extends GameMenu{
	
	private ArrayList<UpgradeButton> buttons;
	UpgradeButton selected;
	UpgradeGridAdapter gridAdapter;


	public UpgradeMenu(GameActivity activity) {
		super(activity, GameActivity.MENU_UPGRADES);
	}

	@Override
	void initialize() {
		
		
		buttons = new ArrayList<UpgradeButton>();
		gridAdapter = new UpgradeGridAdapter(activity, this);
		
		Button backButton = (Button) activity.findViewById(R.id.upgrade_screen_back);
		backButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				activity.getThread().pause(new LevelCompleteMenu(activity, 0L));
				
			}
			
		});
		
		Button buyButton = (Button) activity.findViewById(R.id.upgrade_screen_buy);
		buyButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				buyUpgrades();
			}
			
		});
		
		Game game = activity.getGame();
		buttons.add(new UpgradeLaserSpeedButton(this, game));
		buttons.add(new UpgradeLaserPowerButton(this, game));
		buttons.add(new UpgradeRepairButton(this, game));
		
		buttons.add(new UpgradeArmorButton(this, game));
		buttons.add(new UpgradeScoreMultiplierButton(this, game));
		buttons.add(new UpgradeBoosterButton(this, game));
		
		
		GridView grid = (GridView) activity.findViewById(R.id.upgrade_screen_button_grid);
		grid.setAdapter(gridAdapter);
		
		grid.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				
				buttons.get(position).click();
			}
			
		});
				
	}
	
	public ArrayList<UpgradeButton> getUpgradeButtons(){
		return buttons;
	}
	
	
	public void select(UpgradeButton selectedButton){
		

		if (selected!=null){
		selected.selected = false;
		}
		
		selected = selectedButton;
		selected.selected = true;
		
		gridAdapter.notifyDataSetChanged();
	
	}
	
	public void buyUpgrades(){

		if (selected==null){
			return;
		}
		if (activity.getGame().getGold()<selected.getPrice()){
			 
		} else{
			activity.getGame().incGold(selected.getPrice()*-1);
			selected.upgrade();
			selected.selected = false;
			selected = null;
		}
	}

}
