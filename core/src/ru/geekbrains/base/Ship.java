package ru.geekbrains.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.sprite.Explosion;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Bullet;
import java.util.List;

public class Ship extends Sprite {
    protected final Vector2 v;

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected Vector2 bulletV;
    protected float bulletHeight;
    protected int damage;
    protected int colorResetCounter;

    protected TextureRegion bulletRegion;
    protected ExplosionPool explosionPool;
    protected float shootTimer;
    protected Sound sound ;

    protected float reloadInterval;
    protected float reloadTimer;
    protected int hp;
    protected int originalHp;
    protected Vector2 v0 = new Vector2(0, -0.2f);

    private final float SOUND_VOLUME = 0.1f;

    public Ship() {
        this.worldBounds = new Rect();
        v = new Vector2();
    }

//    public Ship(TextureAtlas atlas) {
//        this.bulletPool = bulletPool;
//        this.bulletV = new Vector2();
//    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
        this.v = new Vector2();
    }

    public Ship(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound sound) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        this.sound = sound;
        this.v = new Vector2();
        this.bulletV = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, this.damage);
        this.sound.play(SOUND_VOLUME);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            shoot();
            reloadTimer = 0f;
        }
        if (colorResetCounter > 0)
            this.frame = --colorResetCounter == 0 ? 0 : 1;
    }

    @Override
    public void destroy () {
        super.destroy();
        boom();
    }

    private void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }

    public boolean isOverlaped (Sprite other) {
        return (
                other.getLeft() >= getLeft() & other.getLeft() <= getRight()
                || other.getLeft() >= getRight() &  other.getRight() <= getRight()

        ) &
                (
                        other.getTop() <= getTop() & other.getTop() >= getBottom()
                || other.getBottom() <= getTop() & other.getBottom() >= getBottom()
        );
    }

    public void setDamage(Bullet bullet) {
        this.colorResetCounter = 3;
        this.hp -= bullet.getDamage();
        if (this.hp <= 0) {
            destroy();
        }
    }

    public void setDamage(Ship ship) {
        this.colorResetCounter = 3;
        this.hp -= ship.getOriginalHp();
//        System.out.printf("My name: %10s, my hp: %5d, my original hp: %3d %n", getClass().getName(), this.hp, this.originalHp);
        if (this.hp <= 0) {
            destroy();
        }
    }


    public int getOriginalHp () {
        return  this.originalHp;
    }
}
