package ru.geekbrains.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    Texture img, backgroundFox;
    TextureRegion region;
    private Vector2 pos;
    private Vector2 motion;
    private Vector2 touch;

    @Override
    public void show() {
        super.show();
        img = new Texture("badlogic.jpg");
        backgroundFox = new Texture("background_fox.jpg");
        region = new TextureRegion(backgroundFox);
        pos = new Vector2();
        motion = new Vector2(0.9f,0.9f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        pos.add(motion);
        batch.begin();
        batch.draw(region, 0, 0);
        batch.draw(img, pos.x, pos.y, 100, 100);
        batch.end();
    }

    @Override
    public void dispose () {
        img.dispose();
        super.dispose();
    }


}
