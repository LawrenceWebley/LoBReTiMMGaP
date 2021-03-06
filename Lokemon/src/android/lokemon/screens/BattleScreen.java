package android.lokemon.screens;

import android.app.*;
import android.content.*;
import android.lokemon.Battle;
import android.lokemon.G;
import android.lokemon.R;
import android.lokemon.game_objects.Pokemon;
import android.lokemon.popups.BagPopup;
import android.lokemon.popups.MovesPopup;
import android.lokemon.popups.PokemonPopup;
import android.os.Bundle;
import android.widget.*;
import android.view.*;

public class BattleScreen extends Activity implements View.OnClickListener{
	
	// battle action buttons
	private Button switch_button;
	private Button attack_button;
	private Button bag_button;
	private Button run_button;
	private ViewGroup battle_buttons;
	
	// display widgets
	private ImageView player_poke;
	private TextView player_name;
	private TextView player_hp;
	private ProgressBar player_bar;
	private TextView player_level;
	private ImageView opp_poke;
	private TextView opp_name;
	private TextView opp_hp;
	private ProgressBar opp_bar;
	private TextView opp_level;
	
	private ImageView pokeballs;
	
	// dialogs
	private ProgressDialog progressDialog;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.battle);
        
        // get battle buttons
        switch_button = (Button)findViewById(R.id.switch_button);
        switch_button.setOnClickListener(this);
        attack_button = (Button)findViewById(R.id.attack_button);
        attack_button.setOnClickListener(this);
        bag_button = (Button)findViewById(R.id.bag_button);
        bag_button.setOnClickListener(this);
        run_button = (Button)findViewById(R.id.run_button);
        run_button.setOnClickListener(this);
        battle_buttons = (ViewGroup)findViewById(R.id.buttons);
        
        // get Pokemon display widgets
        player_poke = (ImageView)findViewById(R.id.my_poke);
        player_name = (TextView)findViewById(R.id.my_name);
        player_level = (TextView)findViewById(R.id.my_level);
        player_hp = (TextView)findViewById(R.id.my_health_num);
        player_bar = (ProgressBar)findViewById(R.id.my_health);
        opp_poke = (ImageView)findViewById(R.id.opp_poke);
        opp_name = (TextView)findViewById(R.id.opp_name);
        opp_level = (TextView)findViewById(R.id.opp_level);
        opp_hp = (TextView)findViewById(R.id.opp_health_num);
        opp_bar = (ProgressBar)findViewById(R.id.opp_health);
        
        pokeballs = (ImageView)findViewById(R.id.pokeballs);
        
        // set up dialogs
        progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        new Battle(this);
	}
	
	public void onClick(View v)
	{
		if (v == switch_button)
		{
			Intent intent = new Intent(v.getContext(), PokemonPopup.class);
	        startActivityForResult(intent, 1);
		}
		else if (v == attack_button)
		{
			Intent intent = new Intent(v.getContext(), MovesPopup.class);
	        startActivityForResult(intent, 2);
		}
		else if (v == bag_button)
		{
			Intent intent = new Intent(v.getContext(), BagPopup.class);
	        startActivityForResult(intent, 3);
		}
		else if (v == run_button)
			onBackPressed();
	}
	
	protected void onActivityResult (int requestCode, int resultCode, Intent data)
	{
		switch(requestCode)
		{
		case 1:
			G.battle.switchPlayerPoke(resultCode);
			break;
		case 2:
			G.battle.selectMove(resultCode);
			showProgressDialog("Waiting for opponent...");
			break;
		case 3:
			G.battle.useItem(resultCode);
			break;
		}
	}
	
	// players have to leave battle by explicitly running away
	public void onBackPressed()
	{
		// create an alert dialog to ask for confirmation
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to run away?");
		builder.setCancelable(false);
		builder.setPositiveButton("Run!", new DialogInterface.OnClickListener() 
			{
	           public void onClick(DialogInterface dialog, int id) 
	           {
	        	   if (G.battle.run())
	       			finish();
	           }
	       })
	       .setNegativeButton("Stay", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	                dialog.cancel();
	           }
	       });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void onPause()
	{
		super.onPause();
		showBattleInterface(false);
	}
	
	public void onResume()
	{
		super.onResume();
		showBattleInterface(true);
	}
	
	// display new player Pokemon
	public void switchPlayerPoke(Pokemon newPoke)
	{
		player_poke.setImageResource(newPoke.getSpriteAttack());
		setPlayerPokeDetails(newPoke);
	}
	
	// display new opponent Pokemon
	public void switchOppPoke(Pokemon newPoke)
	{
		opp_poke.setImageResource(newPoke.getSpriteNormal());
		setOppPokeDetails(newPoke);
	}
	
	public void showProgressDialog(String message)
    {
    	progressDialog.setMessage(message);
    	progressDialog.show();
    }
	
	// display new player Pokemon stats
	public void setPlayerPokeDetails(Pokemon poke)
	{
		player_name.setText(poke.getName());
		player_level.setText("Lv. " + poke.getLevel());
		int hp_c = poke.getHP();
		int hp_t = poke.getTotalHP();
		player_hp.setText(hp_c + "/" + hp_t);
		player_bar.setProgress((int)(hp_c/(float)hp_t * 100.0f));
	}
	
	// display new opponent Pokemon stats
	public void setOppPokeDetails(Pokemon poke)
	{
		opp_name.setText(poke.getName());
		opp_level.setText("Lv. " + poke.getLevel());
		int hp_c = poke.getHP();
		int hp_t = poke.getTotalHP();
		opp_hp.setText(hp_c + "/" + hp_t);
		opp_bar.setProgress((int)(hp_c/(float)hp_t * 100.0f));
	}
	
	public void showBattleInterface(boolean show)
	{
		int state = show?View.VISIBLE:View.INVISIBLE;
		battle_buttons.setVisibility(state);
	}
	
	public void setNumPokemon(int num)
	{
		pokeballs.getDrawable().setLevel(num);
	}
	
	public void disableSwitch() 
	{
		switch_button.setClickable(false);
		switch_button.getBackground().setAlpha(128);
	}
	
	public void disableBag() 
	{
		bag_button.setClickable(false);
		bag_button.getBackground().setAlpha(128);
	}
}
