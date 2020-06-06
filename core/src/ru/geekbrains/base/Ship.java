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

public class Ship extends Sprite {
    protected final Vector2 v0;
    protected final Vector2 v;

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected Vector2 bulletV;
    protected float bulletHeight;
    protected int damage;


    protected TextureRegion bulletRegion;
    protected ExplosionPool explosionPool;
    protected float shootTimer;
    protected Sound sound ;

    protected float reloadInterval;
    protected float reloadTimer;
    protected int hp;

    public Ship() {
        this.worldBounds = new Rect();
        v0 = new Vector2();
        v = new Vector2();
    }

//    public Ship(TextureAtlas atlas) {
//        this.bulletPool = bulletPool;
//        this.bulletV = new Vector2();
//    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
        this.v0 = new Vector2();
        this.v = new Vector2();
    }

    public Ship(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound sound) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        this.sound = sound;
        this.v0 = new Vector2();
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
        bullet.set(this, bulletRegion, pos, bulletV, 0.01f, worldBounds, 1);
        this.sound.play(0.2f);
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

}
