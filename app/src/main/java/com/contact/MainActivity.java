package com.contact;

import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import com.contact.adapters.ContactAdapter;
import com.simplecontacts.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements ContactAdapter.OnContactClickListener {
    private static final String TAG = "MainActivity";
    @Bind(R.id.contact_recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.appBar) Toolbar toolbar;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final long DURATION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ContactAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onContactClick(final View view, int position) {
        Log.d(TAG, "onContactClick");
        //turnOnImmersiveMode(view);

        Path path = new Path();
        path.moveTo(1f, -1f);
        //path.lineTo(0.2f, 0.2f);
        android.view.animation.Interpolator accelerate = AnimationUtils.loadInterpolator(MainActivity.this, android.R.interpolator.fast_out_linear_in);
        //startAnimation(accelerate, 1000, path);
        exit();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getWindow().setAllowEnterTransitionOverlap(true);
                Intent i = new Intent(MainActivity.this, ContactActivity.class);

               /* View decor = getWindow().getDecorView();
                View statusBar = decor.findViewById(android.R.id.statusBarBackground);
                View navBar = decor.findViewById(android.R.id.navigationBarBackground);
                View actionBar = decor.findViewById(getResources().getIdentifier(
                        "action_bar_container", "id", "android")); */

                View tvName = view.findViewById(R.id.tv_name);



                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation(MainActivity.this,
                                Pair.create(tvName, tvName.getTransitionName()));
                               /* Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME),
                                Pair.create(navBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME),
                                Pair.create(actionBar, actionBar.getTransitionName())); */
                startActivity(i, options.toBundle());
                //startActivity(i);
                //overridePendingTransition(R.anim.move_up, R.anim.move_up);
            }
        }, 1000);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "on resume called");
        enter();
    }

    private void enter() {
        TranslateAnimation moveUp = new TranslateAnimation(0, 0, -300, 0);
        moveUp.setDuration(DURATION);
        moveUp.setFillAfter(true);
        toolbar.startAnimation(moveUp);

        TranslateAnimation moveDown = new TranslateAnimation(0, 0, 2000, 0);
        moveDown.setDuration(DURATION);
        moveDown.setFillAfter(true);
        mRecyclerView.startAnimation(moveDown);
    }

    private void exit() {
        TranslateAnimation moveUp = new TranslateAnimation(0, 0, 0, -300);
        moveUp.setDuration(DURATION);
        moveUp.setFillAfter(true);
        toolbar.startAnimation(moveUp);

        TranslateAnimation moveDown = new TranslateAnimation(0, 0, 0, 2000);
        moveDown.setDuration(DURATION);
        moveDown.setFillAfter(true);
        mRecyclerView.startAnimation(moveDown);
    }

    public ObjectAnimator startAnimation(android.view.animation.Interpolator interpolator, long duration, Path path) {
        // This ObjectAnimator uses the path to change the x and y scale of the mView object.
        ObjectAnimator animator = ObjectAnimator.ofFloat(toolbar, View.SCALE_X, View.SCALE_Y, path);

        // Set the duration and interpolator for this animation
        animator.setDuration(duration);
        animator.setInterpolator(interpolator);

        animator.start();

        return animator;
    }

    private void turnOnImmersiveMode(final View flagsView) {
        int uiOptions = flagsView.getSystemUiVisibility();
        uiOptions &= ~View.SYSTEM_UI_FLAG_LOW_PROFILE;
        uiOptions |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        uiOptions |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        uiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE;
        uiOptions &= ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        flagsView.setSystemUiVisibility(uiOptions);
    }
}
