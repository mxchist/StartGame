package ru.geekbrains.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    Texture img, backgroundFox;
    TextureRegion region;
    private Vector2 pos;
    private Vector2 motion;
    private Vector2 touch;
    private Vector2 correction;

    @Override
    public void show() {
        super.show();
        img = new Texture("peka.jpg");
        backgroundFox = new Texture("background_fox.jpg");
        region = new TextureRegion(backgroundFox);
        pos = new Vector2();
        motion = new Vector2(0.9f,0.9f);
        touch = new Vector2();
        correction = new Vector2();
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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        correction = touch.cpy().sub(pos);
        this.motion.set(correction.scl(0.01f));
        System.out.println("touchDown touch.x = " + touch.x + " touch.y = " + touch.y);
        return super.touchDown(screenX, screenY, pointer, button);
    }

}
