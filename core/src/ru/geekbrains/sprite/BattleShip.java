package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Ship;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;

public class BattleShip extends Ship {
    private final float MOTION = 0.01f;
    private final float SHOOT_INTERVAL = 0.2f;

    private Vector2 touch;

    public BattleShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship").split(195, 287)[0][0]);
        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        bulletV = new Vector2(0, 0.5f);
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.1f);
        float posX = (worldBounds.getLeft() + worldBounds.getRight())/2;
        float posY = worldBounds.getBottom() + (worldBounds.getBottom() + worldBounds.getHeight())/5;
        pos.set(posX, posY);
    }

    @Override
    public void update(float delta) {
        if (this.touch != null) {
            if (touch.x - MOTION > pos.x) {
                pos.add( MOTION, 0);
            }
            else if (touch.x + MOTION < pos.x) {
                pos.add( -MOTION, 0);
            }
            else {
                pos.set(touch.x, pos.y);
                touch = null;
            }
            }
        shootTimer += delta;
        if (shootTimer > SHOOT_INTERVAL) {
            shoot();
            shootTimer = 0;
        }
    }

    public void setTouch (Vector2 touch) {
        this.touch = touch;
    }

    public void dispose() {
        sound.dispose();
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
