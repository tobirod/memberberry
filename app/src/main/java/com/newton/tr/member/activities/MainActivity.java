package com.newton.tr.member.activities;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.newton.tr.member.adapters.FragmentPagerAdapter;
import com.newton.tr.member.fragments.TabNote;
import com.newton.tr.member.fragments.TabShop;
import com.newton.tr.member.fragments.TabTask;
import com.newton.tr.member.R;

public class MainActivity extends AppCompatActivity implements TabTask.OnFragmentInteractionListener, TabShop.OnFragmentInteractionListener, TabNote.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        TabLayout tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_action_task).setText("TASKS"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_action_shop).setText("SHOPPING LIST"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_action_note).setText("NOTES"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.viewPager);
        final FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){

        // set toolbar logo to center programmatically
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarText = findViewById(R.id.toolBarText);
        int offset = (toolbar.getWidth() / 2) - (toolbarText.getWidth() / 2);
        // set
        toolbarText.setX(offset);

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
        if (id == R.id.action_userGuide) {
            userGuide();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void userGuide() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View userGuideView = getLayoutInflater().inflate(R.layout.alertdialog_userguide, null);
        final Button doneButton = userGuideView.findViewById(R.id.userGuideAlertDialogDone);

        dialogBuilder.setView(userGuideView);
        final AlertDialog userGuideDialog = dialogBuilder.create();
        userGuideDialog.show();

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userGuideDialog.dismiss();

            }
        });

    }
}
