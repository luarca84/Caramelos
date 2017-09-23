package com.dfzlv.gjjlt.caramelos;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class CaramelosGame  extends Game {

	@Override
	public void create() {
		InitPreferences();
		ShowMenuScreen();
	}

	public void InitPreferences()
	{
		Preferences prefs = Gdx.app.getPreferences("MyPreferences");
		if(!prefs.contains("sound"))
		{
			prefs.putBoolean("sound",true);
			prefs.flush();
		}
	}

	public void ShowMenuScreen(){setScreen(new MenuScreen(this));}
	public void ShowSuperBullScreen(int level){setScreen(new CaramelosScreen(this,level));}
	public void ShowAboutScreen(){setScreen(new AboutScreen(this));}
	public void ShowOptionsScreen(){setScreen(new OptionsScreen(this));}
	public void ShowStatusScreen( boolean victory,int level){setScreen(new StatusScreen(this, victory, level));}
}
