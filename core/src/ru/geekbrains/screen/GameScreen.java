package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.Enemy;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.sprite.BattleShip;
import ru.geekbrains.utils.EnemyEmitter;
import java.util.List;
import ru.geekbrains.sprite.GameOver;
import ru.geekbrains.sprite.ButtonReplay;

public class GameScreen extends BaseScreen {
    private final int LEFT_COEFF = -1;
    private final int RIGHT_COEFF = 1;

    private Texture bg;
    private Background background;
    private TextureAtlas atlas, mainAtlas;
    private Star[] stars;
    private BattleShip battleShip;
    private int coeff;
    private BulletPool bulletPool;

    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;
    private EnemyEmitter enemyEmitter;

    private enum State{PLAYING, GAME_OVER};
    private State state;
    private GameOver gameOver;
    private ButtonReplay buttonReplay;

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
        explosionPool = new ExplosionPool(mainAtlas);
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds);
        battleShip = new BattleShip(mainAtlas, bulletPool, explosionPool);
        enemyEmitter = new EnemyEmitter(mainAtlas, enemyPool);
        this.state = State.PLAYING;
        this.gameOver = new GameOver(mainAtlas);
    }

    @Override
    public void render(float delta) {
        battleShip.setX(coeff);
        super.render(delta);
        update(delta);
        free();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        battleShip.resize(worldBounds);
        enemyEmitter.resize(worldBounds);
        if (state == State.GAME_OVER) {
            gameOver.resize(worldBounds);
            buttonReplay.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        mainAtlas.dispose();
        bulletPool.dispose();
        battleShip.dispose();
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
        if (state == State.GAME_OVER)
            buttonReplay.touchDown(touch, pointer, button);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.GAME_OVER)
            buttonReplay.touchUp(touch, pointer, button);
        return super.touchUp(touch, pointer, button);
    }

    private void update(float delta) {
        enemyPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
        bulletPool.updateActiveSprites(delta);
        for (Star star : stars) {
            star.update(delta);
        }

        if (state == State.PLAYING) {
            checkDamage();
            checkCollision();
            battleShip.update(delta);
            enemyEmitter.generate(delta);
        }
    }

    private void free() {
        bulletPool.freeAllDestroyed();
        enemyPool.freeAllDestroyed();
        explosionPool.freeAllDestroyed();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);

        if (state == State.PLAYING) {
            battleShip.draw(batch);
        }
            else if (state == State.GAME_OVER) {
            gameOver.draw(batch);
            buttonReplay.draw(batch);
        }

        batch.end();
    }

    private void checkDamage () {
        for (Bullet bullet : bulletPool.getActiveObjects()) {
            for (Enemy enemy : enemyEmitter.getEnemyPool()) {
                if (bullet.getOwner() != enemy & enemy.isOverlaped(bullet)) {
                    enemy.setDamage(bullet);
//                    View destroying bullet properties
//                    System.out.printf("%10s: %10d, x: %10f %n", "Bullet is", bullet.hashCode(), bullet.getLeft());
                    bullet.destroy();
                }
            }
            if (bullet.getOwner() != battleShip & battleShip.isOverlaped(bullet)) {
                battleShip.setDamage(bullet);
//                    View destroying bullet properties
//                    System.out.printf("%10s: %10d, x: %10f %n", "Bullet is", bullet.hashCode(), bullet.getLeft());
                bullet.destroy();
                if (battleShip.isDestroyed()) {
                    this.state = State.GAME_OVER;
                    buttonReplay = new ButtonReplay(mainAtlas, this);
                    gameOver.resize(worldBounds);
                    buttonReplay.resize(worldBounds);
                }
            }

        }
    }

    private void checkCollision() {
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (battleShip.isOverlaped(enemy)) {
                enemy.setDamage(battleShip);
                battleShip.setDamage(enemy);
            }
        }
    }

}
