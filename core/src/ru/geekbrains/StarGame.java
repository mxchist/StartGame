package ru.geekbrains;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import ru.geekbrains.screen.MenuScreen;

public class StarGame extends Game {
	private Music music;
	private final float SOUND_VOLUME = 0.8f;

	@Override
	public void create() {
		music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
		music.setVolume(SOUND_VOLUME);
		music.play();
		setScreen(new MenuScreen(this));
	}
}
