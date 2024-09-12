package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;
import ru.geekbrains.pool.BulletPool;

public class EnemyShip extends Sprite {
    private final float MOTION = 0.005f;
    private final float SHOOT_INTERVAL = 0.2f;

    private Vector2 touch;
    private Rect worldBounds;

    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Vector2 bulletV;
    private float shootTimer;
    Sound sound;

    public EnemyShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("enemy1").split(220, 248)[0][0]);
        this.worldBounds = new Rect();
        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.bulletV = new Vector2(0, 0.5f);
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.1f);
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = worldBounds.getHeight() + (worldBounds.getHeight() - worldBounds.getHeight())/5;
        pos.set(posX, posY);
    }

    @Override
    public void update(float delta) {
        pos.add(0, -MOTION);
        checkBounds();
        shootTimer += delta;
        if (shootTimer > SHOOT_INTERVAL) {
            //shoot();
            shootTimer = 0;
        }
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, 0.01f, worldBounds, 1);
        sound.play();
    }

    private void checkBounds() {
        if (getTop() < worldBounds.getBottom()) {
            float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
            float posY = worldBounds.getHeight() + (worldBounds.getHeight() - worldBounds.getHeight())/5;
            pos.add(posX, posY);
        }
    }


}
