package android.lokemon;

public class PokeBall extends Item {

	public PokeBall(int count) 
	{
		super("Pok� Ball (empty)", count, 3, "Select an empty Pok� Ball to capture a wild Pok�mon.");
		this.spriteID = R.drawable.pokeball;
	}
	
	public PokeBall() {this(0);}
}
