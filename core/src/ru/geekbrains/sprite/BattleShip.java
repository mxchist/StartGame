package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;

public class BattleShip extends Sprite {
    private final float MOTION = 0.01f;

    private Vector2 v;
    private Rect worldBounds;

    private float animateTimer;
    private float animateInterval;

    public BattleShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship").split(195, 287)[0][0]);
        float vx = 0;// Rnd.nextFloat(-0.005f, 0.005f);
        float vy = 0; //Rnd.nextFloat(-0.2f, -0.05f);
        worldBounds = new Rect();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.1f);
        float posX = (worldBounds.getLeft() + worldBounds.getRight())/2;
        float posY = worldBounds.getBottom() + (worldBounds.getBottom() + worldBounds.getHeight())/5;
        pos.set(posX, posY);
    }

    public void setX (int coeff) {
            pos.add(coeff * MOTION, 0);

        if (getRight() >= worldBounds.getRight()) {
            pos.add(- coeff * MOTION, 0);
        }
        else if (getLeft() <= worldBounds.getLeft()) {
            pos.add(- coeff * MOTION, 0);
        }
    }

}
