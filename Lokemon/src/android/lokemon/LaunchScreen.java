package android.lokemon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

public class LaunchScreen extends Activity implements View.OnClickListener{
	
	private Button new_button;
	private Button continue_button;
	private TextView welcome_text;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // get menu elements
        new_button = (Button)findViewById(R.id.new_button);
        new_button.setOnClickListener(this);
        continue_button = (Button)findViewById(R.id.continue_button);
        continue_button.setOnClickListener(this);
        welcome_text = (TextView)findViewById(R.id.welcome_text);
        
        // load trainer data if there is any
        Trainer.loadTrainer(this);
    }
    
    public void onStart () 
    {
    	// check if a player already exists
        if (Trainer.player == null)
        {
        	welcome_text.setText("Welcome, trainer!");
        	continue_button.setEnabled(false);
        	continue_button.setVisibility(View.GONE);
        }
        else
        {
        	welcome_text.setText("Trainer: " + Trainer.player.nickname);
        	continue_button.setEnabled(true);
        	continue_button.setVisibility(View.VISIBLE);
        }
    	super.onStart();
    } 
    
    public void onClick(View v)
    {
    	if (v == new_button)
    	{
    		Log.d("Input", "'New game' pressed");
    		Game.loadGameData(this);
    		Intent intent = new Intent(v.getContext(), IntroScreen.class);
            startActivityForResult(intent, 0);
    	}
    	else if (v == continue_button)
    	{
    		Log.d("Input", "'Continue' pressed");
    		Game.loadGameData(this);
    		Intent intent = new Intent(v.getContext(), GameScreen.class);
            startActivityForResult(intent, 0);
    	}
    }
}