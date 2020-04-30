package ru.geekbrains;
import com.badlogic.gdx.Game;

import ru.geekbrains.screen.MenuScreen;

public class StarGame extends Game {

	@Override
	public void create () {
		try {
			setScreen(new MenuScreen());
		}
		catch (RuntimeException exc) {
			exc.printStackTrace();
		}
	}
}
