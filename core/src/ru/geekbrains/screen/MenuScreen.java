package ru.geekbrains.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    Texture img, backgroundFox;
    TextureRegion region;
    private Vector2 pos;                    // текущее положнеие
    private Vector2 touch;
    private Vector2 correction;             // временный объект для определния величины корректировки направления
    private Vector2 positionBegin;          // положение картинки в момент касания. Необходимо для визуального эффекта - программа сначала "промахивается" мимо точки назначения, а потом пододвигает картунку на нужное место.
    final private float MOTION = 0.04f;     // коэффициент скорости
    final private float STEP = 0.05f;       // уставка - минимальное расстояние между кликом и текущим положением, когда они считаются ещё не одинаковы и фигура не дошла до финальной точки

    @Override
    public void show() {
        super.show();
        img = new Texture("peka.jpg");
        backgroundFox = new Texture("background_fox.jpg");
        region = new TextureRegion(backgroundFox);
        pos = new Vector2();
        touch = new Vector2();
        correction = new Vector2(0,0);
    }

    @Override
    public void render(float delta) {
        if (this.correction.len() != 0) {
            super.render(delta);
            float lenBefore = this.correction.len();
            this.correction.scl(MOTION);
            if (correction.len() < STEP) {
                this.pos.set(touch);
                this.correction.set(0,0);
                img = new Texture("peka.jpg");
            }
           else {
                pos.add(correction);
                this.correction.set(touch);
                if (correction.sub(this.pos).len() > lenBefore) {
                    positionBegin = pos;
                }
                this.correction.set(touch);
                correction.sub(this.positionBegin);
            }
        }
        batch.begin();
        batch.draw(region, 0, 0);
        batch.draw(img, pos.x, pos.y, 100, 100);
        batch.end();
    }

    @Override
    public void dispose () {
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        this.touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        this.positionBegin = new Vector2(this.pos);
        img = new Texture("peka_scholar.jpg");
        this.correction.set(touch);
        correction.sub(this.positionBegin);
        System.out.println("touchDown touch.x = " + touch.x + " touch.y = " + touch.y);
        return super.touchDown(screenX, screenY, pointer, button);
    }

}
