package android.lokemon.game_objects;

import android.lokemon.R;
import android.lokemon.R.drawable;

public class PokeBall extends BagItem {

	public PokeBall(int count) 
	{
		super("Pok� Ball (empty)", count, 3, "Select an empty Pok� Ball to capture a wild Pok�mon.");
		this.spriteID = R.drawable.pokeball;
	}
	
	public PokeBall() {this(0);}
}
