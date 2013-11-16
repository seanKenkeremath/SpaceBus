package sean.k.uts2120;

import java.util.ArrayList;

import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class UpgradeMenu extends GameMenu {

	private ArrayList<UpgradeButton> buttons;
	UpgradeButton selected;
	UpgradeGridAdapter gridAdapter;

	TextView upgradeMessage;
	TextView upgradeCost;
	TextView yourMoney;
	Button buyButton;

	public UpgradeMenu(GameActivity activity) {
		super(activity, GameActivity.MENU_UPGRADES);
	}

	@Override
	void initialize() {

		buttons = new ArrayList<UpgradeButton>();
		gridAdapter = new UpgradeGridAdapter(activity, this);

		Button backButton = (Button) activity
				.findViewById(R.id.upgrade_screen_back);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				activity.getThread().pause(new LevelCompleteMenu(activity, 0L));

			}

		});

		buyButton = (Button) activity.findViewById(R.id.upgrade_screen_buy);
		buyButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				buyUpgrades();
			}

		});

		buyButton.setVisibility(View.INVISIBLE); // initially nothing is
													// selected

		Game game = activity.getGame();
		buttons.add(new UpgradeLaserSpeedButton(this, game));
		buttons.add(new UpgradeLaserPowerButton(this, game));
		buttons.add(new UpgradeRepairButton(this, game));

		buttons.add(new UpgradeArmorButton(this, game));
		buttons.add(new UpgradeScoreMultiplierButton(this, game));
		buttons.add(new UpgradeBoosterButton(this, game));

		upgradeMessage = (TextView) activity
				.findViewById(R.id.upgrade_screen_message);

		upgradeCost = (TextView) activity
				.findViewById(R.id.upgrade_screen_cost);

		yourMoney = (TextView) activity
				.findViewById(R.id.upgrade_screen_your_gold);

		GridView grid = (GridView) activity
				.findViewById(R.id.upgrade_screen_button_grid);
		grid.setAdapter(gridAdapter);

		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				buttons.get(position).click();
			}

		});

		initializeBuyInfo();

	}

	public ArrayList<UpgradeButton> getUpgradeButtons() {
		return buttons;
	}

	public void select(UpgradeButton selectedButton) {

		if (selected != null) {
			selected.selected = false;
		}

		selected = selectedButton;
		selected.selected = true;

		upgradeMessage.setText(selected.getMessage());
		upgradeCost.setText("Cost: $" + selected.getPrice());

		if (activity.getGame().getGold() < selected.getPrice()) {
			upgradeCost.setTextColor(Color.RED);
			buyButton.setVisibility(View.INVISIBLE);

		} else {
			upgradeCost.setTextColor(Color.GREEN);
			buyButton.setVisibility(View.VISIBLE);

		}

		gridAdapter.notifyDataSetChanged();

	}

	private void initializeBuyInfo() {
		yourMoney.setText("You have: $" + activity.getGame().getGold());
		buyButton.setVisibility(View.INVISIBLE);
		upgradeCost.setText("");
		upgradeMessage.setText("");

	}

	public void buyUpgrades() {

		if (selected == null) {
			return;
		}
		if (activity.getGame().getGold() < selected.getPrice()) {

		} else {
			activity.getGame().incGold(selected.getPrice() * -1);
			selected.upgrade();
			selected.selected = false;
			selected = null;
			gridAdapter.notifyDataSetChanged();

			initializeBuyInfo();

		}
	}

}
