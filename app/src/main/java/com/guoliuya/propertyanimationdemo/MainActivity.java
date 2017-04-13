package com.guoliuya.propertyanimationdemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guoliuya.util.ViewUtil;

public class MainActivity extends AppCompatActivity {
    private ImageView ivCastle, ivLight1, ivLight2, ivLight3, ivLight4, ivWay1, ivWay2, ivWay3, ivWay4, ivFire1, ivFire2, ivFire3, ivFire4;
    private LinearLayout llCastle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setView();
        findViewById(R.id.click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doAnimation();
            }
        });
    }

    private void setView() {
        ivCastle.setImageResource(R.mipmap.cb);
        ivWay1.setImageResource(R.mipmap.f1);
        ivWay2.setImageResource(R.mipmap.f2);
        ivWay3.setImageResource(R.mipmap.f3);
        ivWay4.setImageResource(R.mipmap.f4);
        ivFire1.setImageResource(R.mipmap.y1);
        ivFire2.setImageResource(R.mipmap.y2);
        ivFire3.setImageResource(R.mipmap.y3);
        ivFire4.setImageResource(R.mipmap.y4);
        ivLight1.setImageResource(R.mipmap.g1);
        ivLight2.setImageResource(R.mipmap.g2);
        ivLight3.setImageResource(R.mipmap.g3);
        ivLight4.setImageResource(R.mipmap.g4);
    }

    private void initView() {
        ivCastle = (ImageView) findViewById(R.id.iv_castle);
        llCastle = (LinearLayout) findViewById(R.id.ll_castle);
        ivLight1 = (ImageView) findViewById(R.id.iv_light1);
        ivLight2 = (ImageView) findViewById(R.id.iv_light2);
        ivLight3 = (ImageView) findViewById(R.id.iv_light3);
        ivLight4 = (ImageView) findViewById(R.id.iv_light4);
        ivWay1 = (ImageView) findViewById(R.id.iv_way1);
        ivWay2 = (ImageView) findViewById(R.id.iv_way2);
        ivWay3 = (ImageView) findViewById(R.id.iv_way3);
        ivWay4 = (ImageView) findViewById(R.id.iv_way4);
        ivFire1 = (ImageView) findViewById(R.id.iv_fire1);
        ivFire2 = (ImageView) findViewById(R.id.iv_fire2);
        ivFire3 = (ImageView) findViewById(R.id.iv_fire3);
        ivFire4 = (ImageView) findViewById(R.id.iv_fire4);
    }

    private void doAnimation() {

        Animator lighterAni = getLightersAnimator();
        Animator castleAni = getCastleShowingAnimator();
        Animator fireworks = getFireworksAnimator();
        Animator fireworks2 = getFireworksAnimator();

        AnimatorSet set = new AnimatorSet();
        set.play(lighterAni).with(castleAni);
        set.play(castleAni).after(1100).before(fireworks);
        set.play(fireworks).after(1400).before(fireworks2);
        set.play(fireworks2).after(2200);
        set.start();
    }

    private Animator getLightersAnimator() {
        Animator light1 = getLighterAnimator(ivLight1, -15, 20, -30, 50);
        Animator light2 = getLighterAnimator(ivLight2, 0, -50, 30, 30);
        Animator light3 = getLighterAnimator(ivLight3, 0, 50, -30, -30);
        Animator light4 = getLighterAnimator(ivLight4, -15, -30, 10, -55);
        AnimatorSet set = new AnimatorSet();
        set.play(light1).with(light4);
        set.play(light4);
        set.play(light2).with(light3).after(200);
        return set;
    }

    private Animator getLighterAnimator(ImageView lighter, int rotation1, int rotation2, int rotation3,
            int rotation4) {
        Animator ani1 = getLighterAnimator(lighter, rotation1, rotation2);
        Animator ani2 = getLighterAnimator(lighter, rotation2, rotation3);
        Animator ani3 = getLighterAnimator(lighter, rotation3, rotation4);
        AnimatorSet set = new AnimatorSet();
        set.addListener(new ViewShowListener(lighter));
        set.play(ani1).before(ani2);
        set.play(ani2).before(ani3);
        return set;
    }

    private Animator getLighterAnimator(ImageView lighter, int origin, int destination) {
        ObjectAnimator lighterAni = ObjectAnimator.ofFloat(lighter, "rotation", origin, destination);
        lighterAni.setDuration(800);
        return lighterAni;
    }

    private Animator getCastleShowingAnimator() {
        float castleSY = ViewUtil.getScreenHeight(this) + ViewUtil.dp2Px(this, 240);
        float castleDY =
                ViewUtil.getScreenHeight(this) - ViewUtil.dp2Px(this, 240) - ViewUtil.dp2Px(this, 30);
        PropertyValuesHolder castleX1 = PropertyValuesHolder.ofFloat("y", castleSY, castleDY);

        PropertyValuesHolder castleScaleX = PropertyValuesHolder.ofFloat("scaleX", 0.5f, 1.0f);
        PropertyValuesHolder castleScaleY = PropertyValuesHolder.ofFloat("scaleY", 0.5f, 1.0f);

        ObjectAnimator castleAni = ObjectAnimator
                .ofPropertyValuesHolder(llCastle, castleX1, castleScaleX, castleScaleY);
        castleAni.addListener(new ViewShowListener(ivCastle));
        castleAni.addListener(new ViewShowListener(llCastle));
        castleAni.setInterpolator(new OvershootInterpolator());
        castleAni.setDuration(1000);
        return castleAni;
    }

    private Animator getFireworksAnimator() {
        Animator fireworkAni1 = getFireWorkAnimator(ivWay1, 155, ivFire1);
        Animator fireworkAni2 = getFireWorkAnimator(ivWay2, 162, ivFire2);
        Animator fireworkAni3 = getFireWorkAnimator(ivWay3, 157, ivFire3);
        Animator fireworkAni4 = getFireWorkAnimator(ivWay4, 111, ivFire4);
        AnimatorSet set = new AnimatorSet();
        set.play(fireworkAni1).with(fireworkAni2);
        set.play(fireworkAni2).after(200);
        set.play(fireworkAni4).with(fireworkAni3);
        set.play(fireworkAni3).after(200);
        return set;
    }

    private Animator getFireWorkAnimator(final ImageView way, final int wayHeight, final ImageView fire) {
        ValueAnimator wayAni = ValueAnimator.ofFloat(0f, ViewUtil.dp2Px(way.getContext(), wayHeight));
        wayAni.setDuration(300);
        wayAni.addListener(new ViewShowListener(way));
        wayAni.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            int lastHeight;

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float value = (Float) valueAnimator.getAnimatedValue();
                way.getLayoutParams().height = value.intValue();
                lastHeight = value.intValue();
                way.requestLayout();
            }
        });

        ObjectAnimator wayScaleAni = ObjectAnimator.ofFloat(way, "alpha", 1, 0.2f);
        wayScaleAni.setDuration(500);

        PropertyValuesHolder alphaValue = PropertyValuesHolder.ofFloat("alpha", 0, 1);
        PropertyValuesHolder scaleXValue = PropertyValuesHolder.ofFloat("scaleX", 0, 1);
        PropertyValuesHolder scaleYValue = PropertyValuesHolder.ofFloat("scaleY", 0, 1);
        ObjectAnimator fireAni = ObjectAnimator
                .ofPropertyValuesHolder(fire, alphaValue, scaleXValue, scaleYValue);
        fireAni.setDuration(600);
        fireAni.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                fire.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        way.setVisibility(View.GONE);
                    }
                }, 300);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fire.setVisibility(View.GONE);
                    }
                }, 300);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        AnimatorSet set = new AnimatorSet();
        set.play(wayAni).with(wayScaleAni);
        set.play(wayScaleAni).before(fireAni);
        set.play(fireAni);
        return set;
    }

    class ViewShowListener implements Animator.AnimatorListener {
        private View view;

        public ViewShowListener(View view) {
            this.view = view;
        }

        @Override
        public void onAnimationStart(Animator animator) {
            view.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animator animator) {

        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    }
}
