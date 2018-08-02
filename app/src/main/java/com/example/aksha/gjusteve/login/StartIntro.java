package com.example.aksha.gjusteve.login;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.example.aksha.gjusteve.R;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class StartIntro extends IntroActivity {
    @Override
    public void onBackPressed() {

    }

    @Override
    public void lockSwipeIfNeeded() {
        super.lockSwipeIfNeeded();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setButtonBackVisible(false);
        setButtonNextVisible(false);
        setButtonCtaVisible(true);
        setButtonCtaTintMode(BUTTON_CTA_TINT_MODE_TEXT);

        addSlide(new SimpleSlide.Builder()
                .title(R.string.events_and_more)
                .description(R.string.events_description)
                .image(R.drawable.preloginevents)
                .background(R.color.color_material_metaphor)
                .backgroundDark(R.color.color_dark_material_metaphor)
                .buttonCtaLabel("Next")
                .buttonCtaClickListener(v -> nextSlide())
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(R.string.academic_calendar)
                .description(R.string.calendar_description)
                .image(R.drawable.preeventcalendar)
                .background(R.color.color_material_bold)
                .backgroundDark(R.color.color_dark_material_bold)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .buttonCtaLabel("Next")
                .buttonCtaClickListener(v -> nextSlide())
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(R.string.academic_performance)
                .description(R.string.academic_performance_description)
                .image(R.drawable.preloginstats)
                .background(R.color.color_custom_fragment_1)
                .backgroundDark(R.color.color_dark_custom_fragment_1)
                .buttonCtaLabel("Next")
                .buttonCtaClickListener(v -> nextSlide())
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(R.string.feedback_support)
                .description(R.string.feedback_support_description)
                .image(R.drawable.preloginfeedback)
                .background(R.color.color_material_motion)
                .backgroundDark(R.color.color_dark_material_motion)
                .buttonCtaLabel("LET'S GET STARTED")
                .buttonCtaClickListener(v -> {
                    Intent intent = new Intent(StartIntro.this,LoginChoice.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    finish();
                })
                .canGoForward(false)
                .build());

    }

}
