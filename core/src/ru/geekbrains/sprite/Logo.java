package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Logo extends Sprite {
    final private float MOTION = 0.04f;     // коэффициент скорости
    final private float STEP = 0.05f;       // уставка - минимальное расстояние между кликом и текущим положением, когда они считаются ещё не одинаковы и фигура не дошла до финальной точки

    public Vector2 touch;
    private Vector2 correction;             // временный объект для определния величины корректировки направления
    private Vector2 positionBegin;          // положение картинки в момент касания. Необходимо для визуального эффекта - программа сначала "промахивается" мимо точки назначения, а потом пододвигает картунку на нужное место.
    public Vector2 common;

    public Logo (Texture region) {
        super(new TextureRegion(region));
        touch = new Vector2();
        correction = new Vector2(0,0);
        common = new Vector2();
    }

    @Override
    public void update(float delta) {
        if (this.correction.len() != 0) {
//            super.render(delta);
            float lenBefore = this.correction.len();
            this.correction.scl(MOTION);
            if (correction.len() < STEP) {
                this.pos.set(touch);
                this.correction.set(0,0);
//                img = new Texture("peka.jpg");
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
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        this.touch.set(touch);
        this.positionBegin = new Vector2(this.pos);
        this.correction.set(touch);
        correction.sub(this.positionBegin);

        return false;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.3f);
    }
}
