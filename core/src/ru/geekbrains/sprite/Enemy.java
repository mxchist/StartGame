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

    protected final float v0x = 0;
    protected final float v0y = -0.2f;
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
        this.originalHp = this.hp = hp;
        setHeightProportion(height);
        this.v.set(v0x, v0y);

        isRevealed = false;
        System.out.printf("me: %10d, hp: %2d, v: %4.3f, vCur: %4.3f, v0: %4.3f %n", hashCode(), this.hp, this.v.y, this.vCur.y, this.v0.y);
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
                    this.v.set(vCur);
                }
                isRevealed = true;
            }
        }
    }

}
