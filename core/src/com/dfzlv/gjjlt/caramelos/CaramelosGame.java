package com.dfzlv.gjjlt.caramelos;


import com.badlogic.gdx.Game;


public class CaramelosGame  extends Game {

	@Override
	public void create() {
		ShowMenuScreen();
	}

	public void ShowMenuScreen(){setScreen(new MenuScreen(this));}
	public void ShowSuperBullScreen(int level){setScreen(new CaramelosScreen(this,level));}
	public void ShowAboutScreen(){setScreen(new AboutScreen(this));}
	public void ShowStatusScreen( boolean victory,int level){setScreen(new StatusScreen(this, victory, level));}
}
