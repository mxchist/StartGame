package ru.geekbrains.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Ship;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.utils.EnemyEmitter;

public class Enemy extends Ship {

    protected final Vector2 v0 = new Vector2(0, -0.2f);
    private Vector2 vCur;
    private boolean isRevealed;

    public Enemy(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound sound) {
        super(bulletPool, explosionPool, worldBounds, sound);
        vCur = new Vector2();
        isRevealed = false;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getBottom() <= worldBounds.getBottom()) {
            destroy();
        }
        else
        checkPosition();
    }

    public void set(
            TextureRegion[] regions,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int damage,
            float reloadInterval,
            int hp,
            float height
    ) {
        this.regions = regions;
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        this.reloadTimer = 0;
        this.hp = hp;
        setHeightProportion(height);
        this.v.set(v0);

        isRevealed = false;
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int damage,
            float reloadInterval,
            int hp,
            float height
    ) {
        set(regions, bulletRegion, bulletHeight, bulletVY, damage, reloadInterval, hp, height);
        this.vCur.set(v);
    }

    protected void checkPosition () {
        if (isRevealed == false) {
            if (getTop() > worldBounds.getTop()) {
                this.v.set(v0);
            }
            else {
                shoot();
                if (this.hp == EnemyEmitter.ENEMY_BIG_HP || this.hp == EnemyEmitter.ENEMY_MEDIUM_HP) {
                    System.out.printf("this.v: %5f, vCur: %5f %n", this.v.y, vCur.y);
                    this.v.set(vCur);
                    System.out.printf("this.v: %5f %n", this.v.y);
                }
                isRevealed = true;
            }
        }
    }

}
