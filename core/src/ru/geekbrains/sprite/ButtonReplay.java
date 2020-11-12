package ru.geekbrains.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.StarGame;
import ru.geekbrains.base.ScaledButton;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;

public class ButtonReplay extends ScaledButton {

    private final Game game = new StarGame();
    private GameScreen gameScreen;

    private static final float MARGIN = 0.05f;

    public ButtonReplay(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.05f);
        setBottom(worldBounds.getBottom() + worldBounds.getHeight()/5);
        setLeft(worldBounds.getLeft() + MARGIN);
    }

    @Override
    public void action() {
        System.out.printf("%s %n", "Santa Blaus");
        game.setScreen(gameScreen);
     //   this.gameScreen.show();
    }
}
