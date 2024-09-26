package ru.geekbrains;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import ru.geekbrains.screen.MenuScreen;

public class StarGame extends Game {
	private Music music;

	@Override
	public void create() {
		music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
		music.play();
		setScreen(new MenuScreen(this));
	}
}
