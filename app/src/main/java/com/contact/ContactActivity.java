package com.contact;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.simplecontacts.R;

import butterknife.Bind;
import butterknife.ButterKnife;


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ContactActivity extends AppCompatActivity {
    private static final String TAG = "ContactActivity";
    @Bind(R.id.tv_name) TextView tvName;
    @Bind(R.id.appBar) Toolbar toolbar;
   // @Bind((R.id.iv_background)) ImageView ivBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp,
                getTheme()));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        tvName.setText("TEST");

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Window window = getWindow();

        window.setTransitionBackgroundFadeDuration(300);
        window.setEnterTransition(getEnterTransition());
        window.setExitTransition(getExitTransition());
        window.setReturnTransition(getExitTransition());

        /*View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions); */
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        //ActionBar actionBar = getActionBar();
        //actionBar.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact, menu);
        return true;
    }

    private TransitionSet getEnterTransition() {
        return createTransitions(new Fade(Fade.IN));
    }

    private TransitionSet getExitTransition() {
        return createTransitions(new Fade(Fade.OUT), new Slide(Gravity.BOTTOM));
    }

    private TransitionSet createTransitions(Transition... transitions) {
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.setOrdering(TransitionSet.ORDERING_TOGETHER);
        for (Transition transition : transitions) {
            transition.setDuration(300);
            transitionSet.addTransition(transition);
        }
        return transitionSet;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case android.R.id.home:
                finishAfterTransition();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }
}
