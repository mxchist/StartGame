package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;

public class BattleShip extends Sprite {

    private Vector2 v;
    private Rect worldBounds;

    private float animateTimer;
    private float animateInterval;

    public BattleShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"));
        v = new Vector2();
        float vx = 0;// Rnd.nextFloat(-0.005f, 0.005f);
        float vy = 0; //Rnd.nextFloat(-0.2f, -0.05f);
        v.set(vx, vy);
        worldBounds = new Rect();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.1f);
        float posX = (worldBounds.getLeft() + worldBounds.getRight())/2;
        float posY = worldBounds.getBottom() + (worldBounds.getBottom() + worldBounds.getHeight())/5;
        pos.set(posX, posY);
        animateInterval = Rnd.nextFloat(0.5f, 1.5f);
    }

    @Override
    public void update(float delta) {
//        setScale(getScale() - 0.008f);
//        animateTimer += delta;
//        if (animateTimer >= animateInterval) {
//            setScale(1f);
            animateTimer = 0f;
//        }
//        pos.mulAdd(v, delta);
        checkBounds();
    }

    private void checkBounds() {
        if (getRight() < worldBounds.getLeft()) {
            setLeft(worldBounds.getRight());
        }
        if (getLeft() > worldBounds.getRight()) {
            setRight(worldBounds.getLeft());
        }
        if (getTop() < worldBounds.getBottom()) {
            setBottom(worldBounds.getTop());
        }
    }
}
