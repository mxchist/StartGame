package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.sprite.BattleShip;

public class GameScreen extends BaseScreen {
    private final int LEFT_COEFF = -1;
    private final int RIGHT_COEFF = 1;

    private Texture bg;
    private Background background;
    private TextureAtlas atlas, mainAtlas;
    private Star[] stars;
    private BattleShip battleShip;
    private int coeff;
    private BattleShip mainShip;
    private BulletPool bulletPool;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        atlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.tpack"));
        mainAtlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        stars = new Star[64];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        mainShip = new BattleShip(atlas, bulletPool);
    }

    @Override
    public void render(float delta) {
        battleShip.setX(coeff);
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        battleShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        mainAtlas.dispose();
        bulletPool.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case 21:
                this.coeff += LEFT_COEFF;
                break;
            case 22:
                this.coeff += RIGHT_COEFF;
                break;
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case 21:
                this.coeff -= LEFT_COEFF;
                break;
            case 22:
                this.coeff -= RIGHT_COEFF;
                break;
        }
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        battleShip.setTouch(touch);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return super.touchUp(touch, pointer, button);
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        battleShip.update(delta);
        bulletPool.updateActiveSprites(delta);
    }

    private void free() {
        bulletPool.freeAllDestroyed();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        battleShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        batch.end();
    }
}
