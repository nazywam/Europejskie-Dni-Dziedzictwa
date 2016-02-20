package hacksilesia.europejskiednidziedzictwa;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SlideshowActivity extends AppCompatActivity {

    private View[] slides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);
        slides = new View[2];
        slides[0] = findViewById(R.id.slide1);
        slides[1] = findViewById(R.id.slide2);
        slides[0].setAlpha(0);
        slides[1].setAlpha(0);
        fadeIn(0);
    }

    private void fadeIn(final int s1) {
        slides[s1].animate()
                .alpha(1f)
                .setDuration(1000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        super.onAnimationCancel(animation);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        fadeOut(s1);
                    }
                });
    }

    private void fadeOut(final int s1) {
        slides[s1].animate()
                .alpha(0f)
                .setDuration(1000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        super.onAnimationCancel(animation);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (s1 + 1 == slides.length) {
                            Intent intent = new Intent(SlideshowActivity.this, MapsActivity.class);
                            startActivity(intent);
                        } else
                            fadeIn(s1 + 1);
                    }
                });
    }

}
