package com.hapramp.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hapramp.R;
import com.hapramp.api.DataServer;
import com.hapramp.fragments.CompetitionFragment;
import com.hapramp.fragments.EarningFragment;
import com.hapramp.fragments.HomeFragment;
import com.hapramp.fragments.ProfileFragment;
import com.hapramp.fragments.SettingsFragment;
import com.hapramp.interfaces.FetchUserCallback;
import com.hapramp.models.response.FetchUserResponse;
import com.hapramp.preferences.HaprampPreferenceManager;
import com.hapramp.steem.SteemHelper;
import com.hapramp.utils.Constants;
import com.hapramp.utils.FontManager;
import com.hapramp.views.extraa.CreateButtonView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.bittrade.libs.steemj.SteemJ;

public class HomeActivity extends AppCompatActivity implements FetchUserCallback, CreateButtonView.ItemClickListener {


    @BindView(R.id.search_icon)
    TextView searchIcon;
    @BindView(R.id.bottomBar_home)
    TextView bottomBarHome;
    @BindView(R.id.bottomBar_competition)
    TextView bottomBarCompetition;
    @BindView(R.id.createNewBtn)
    CreateButtonView createButtonView;
    @BindView(R.id.bottomBar_profile)
    TextView bottomBarProfile;
    @BindView(R.id.bottomBar_settings)
    TextView bottomBarSettings;
    @BindView(R.id.notification_icon)
    TextView notificationIcon;
    @BindView(R.id.notification_count)
    TextView notificationCount;
    @BindView(R.id.notification_container)
    RelativeLayout notificationContainer;
    @BindView(R.id.action_bar_container)
    RelativeLayout actionBarContainer;

    @BindView(R.id.contentPlaceHolder)
    FrameLayout contentPlaceHolder;
    @BindView(R.id.bottomBar_home_text)
    TextView bottomBarHomeText;
    @BindView(R.id.bottomBar_competition_text)
    TextView bottomBarCompetitionText;
    @BindView(R.id.bottomBar_profile_text)
    TextView bottomBarProfileText;
    @BindView(R.id.bottomBar_settings_text)
    TextView bottomBarSettingsText;

    private final int BOTTOM_MENU_HOME = 7;
    private final int BOTTOM_MENU_COMP = 8;
    private final int BOTTOM_MENU_PROFILE = 9;
    private final int BOTTOM_MENU_SETTINGS = 10;
    private final int BOTTOM_MENU_EARNINGS = 11;
    @BindView(R.id.toolbar_drop_shadow)
    FrameLayout toolbarDropShadow;
    @BindView(R.id.postUploadStatus)
    TextView postUploadStatus;
    @BindView(R.id.shadow)
    ImageView shadow;
    @BindView(R.id.bottombar_container)
    LinearLayout bottombarContainer;
    @BindView(R.id.haprampIcon)
    ImageView haprampIcon;
    private int lastMenuSelection = BOTTOM_MENU_HOME;

    private final int FRAGMENT_HOME = 12;
    private final int FRAGMENT_COMPETITION = 13;
    private final int FRAGMENT_PROFILE = 14;
    private final int FRAGMENT_SETTINGS = 15;
    private final int FRAGMENT_EARNINGS = 16;

    private Fragment currentVisibleFragment;
    private Typeface materialTypface;
    private FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    private CompetitionFragment competitionFragment;
    private ProfileFragment profileFragment;
    private SettingsFragment settingsFragment;
    private EarningFragment earningFragment;
    private ProgressDialog progressDialog;
    private PostUploadReceiver postUploadReceiver;
    private NotificationUpdateReceiver notificationUpdateReceiver;
    private boolean isReceiverRegistered;

    private final Runnable hideStatus = new Runnable() {
        @Override
        public void run() {
            postUploadStatus.setVisibility(View.GONE);
        }
    };

    private Handler mHandler = new Handler();

    private class NotificationUpdateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            setNotifications();
        }
    }

    private class PostUploadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (homeFragment.isAdded())
                homeFragment.forceReloadData();

            if (profileFragment.isAdded())
                profileFragment.reloadPosts();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setupToolbar();
        initObjects();
        attachListeners();
        testSteemInit();
        postUploadReceiver = new PostUploadReceiver();
        notificationUpdateReceiver = new NotificationUpdateReceiver();

//        if (!HaprampPreferenceManager.getInstance().isUserInfoAvailable()) {
//            fetchCompleteUserInfo();
//        } else {
        transactFragment(FRAGMENT_HOME);
        // }

    }

    private void testSteemInit() {
        new Thread() {
            @Override
            public void run() {
                SteemJ steemJ = SteemHelper.getSteemInstance();
                Log.d("HomeActivity", "" + steemJ);
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!isReceiverRegistered) {
            registerReceiver(postUploadReceiver, new IntentFilter(Constants.ACTION_POST_UPLOAD));
            registerReceiver(notificationUpdateReceiver, new IntentFilter(Constants.ACTION_NOTIFICATION_UPDATE));
            isReceiverRegistered = true;
        }

        //update notifications
        setNotifications();
//        String deviceName = android.os.Build.MODEL;
//        String deviceMan = android.os.Build.MANUFACTURER;
//        Log.d("Firebase", "Device "+deviceName+" Man: "+deviceMan);

    }

    @Override
    public void onBackPressed() {
        showExistAlert();
    }

    private void showExistAlert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Do you want to Exit ?")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", null);

        builder.show();

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (isReceiverRegistered) {
            unregisterReceiver(postUploadReceiver);
            unregisterReceiver(notificationUpdateReceiver);
            isReceiverRegistered = false;
        }

    }

    private void initObjects() {

        fragmentManager = getSupportFragmentManager();
        homeFragment = new HomeFragment();
        competitionFragment = new CompetitionFragment();
        profileFragment = new ProfileFragment();
        settingsFragment = new SettingsFragment();
        earningFragment = new EarningFragment();
        progressDialog = new ProgressDialog(this);

    }

    private void setNotifications() {

        int count = HaprampPreferenceManager.getInstance().getUnreadNotifications();
        if (count > 0) {
            notificationCount.setVisibility(View.VISIBLE);
            String _s = count > 9 ? "9+" : String.valueOf(count);
            notificationCount.setText(_s);
        } else {
            notificationCount.setVisibility(View.GONE);
        }

    }

    private void setupToolbar() {

        materialTypface = FontManager.getInstance().getTypeFace(FontManager.FONT_MATERIAL);
        searchIcon.setTypeface(materialTypface);
        notificationIcon.setTypeface(materialTypface);
        bottomBarHome.setTypeface(materialTypface);
        bottomBarCompetition.setTypeface(materialTypface);
        bottomBarProfile.setTypeface(materialTypface);
        bottomBarSettings.setTypeface(materialTypface);

    }

    private void attachListeners() {

        haprampIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check for the current selection
                if (lastMenuSelection == BOTTOM_MENU_HOME)
                    return;
                swapSelection(BOTTOM_MENU_HOME);
                transactFragment(FRAGMENT_HOME);
            }
        });

        bottomBarHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // check for the current selection
                if (lastMenuSelection == BOTTOM_MENU_HOME)
                    return;
                swapSelection(BOTTOM_MENU_HOME);
                transactFragment(FRAGMENT_HOME);
            }
        });


        bottomBarCompetition.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (lastMenuSelection == BOTTOM_MENU_EARNINGS)
                    return;
                swapSelection(BOTTOM_MENU_EARNINGS);
                transactFragment(FRAGMENT_EARNINGS);
            }
        });


        bottomBarProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (lastMenuSelection == BOTTOM_MENU_PROFILE)
                    return;
                swapSelection(BOTTOM_MENU_PROFILE);
                transactFragment(FRAGMENT_PROFILE);
            }
        });


        bottomBarSettings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (lastMenuSelection == BOTTOM_MENU_SETTINGS)
                    return;
                swapSelection(BOTTOM_MENU_SETTINGS);
                transactFragment(FRAGMENT_SETTINGS);
            }
        });


        createButtonView.setItemClickListener(this);

        notificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, NotificationsActivity.class));
            }
        });

    }

    private void transactFragment(int fragment) {

        switch (fragment) {
            case FRAGMENT_HOME:

                currentVisibleFragment = homeFragment;

                // if (!homeFragment.isAdded()) {
                fragmentManager.beginTransaction()
                        .addToBackStack("home")
                        .replace(R.id.contentPlaceHolder, homeFragment)
                        .commit();
                //    }

                //   showFragment(homeFragment);

                break;
            case FRAGMENT_COMPETITION:

                currentVisibleFragment = competitionFragment;

                //    if (!competitionFragment.isAdded()) {
                fragmentManager.beginTransaction()
                        .addToBackStack("competition")
                        .replace(R.id.contentPlaceHolder, competitionFragment)
                        .commit();
                //    }

                //    showFragment(competitionFragment);

                break;
            case FRAGMENT_PROFILE:

                currentVisibleFragment = profileFragment;

                //  if (!profileFragment.isAdded()) {
                fragmentManager.beginTransaction()
                        .addToBackStack("profile")
                        .replace(R.id.contentPlaceHolder, profileFragment)
                        .commit();
                //  }

                //   showFragment(profileFragment);

                break;
            case FRAGMENT_SETTINGS:

                currentVisibleFragment = settingsFragment;

                //  if (!settingsFragment.isAdded()) {
                fragmentManager.beginTransaction()
                        .addToBackStack("setting")
                        .replace(R.id.contentPlaceHolder, settingsFragment)
                        .commit();
                //   }

                //   showFragment(settingsFragment);

                break;

            case FRAGMENT_EARNINGS:

                currentVisibleFragment = earningFragment;

                //    if (!earningFragment.isAdded()) {
                fragmentManager.beginTransaction()
                        .addToBackStack("earning")
                        .replace(R.id.contentPlaceHolder, earningFragment)
                        .commit();
                //   }
                //   showFragment(earningFragment);
                break;

            default:
                break;

        }
    }

    private void showFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();

        //hide other fragment
        if (fragment != homeFragment) {
            fragmentManager.beginTransaction().hide(homeFragment).commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        if (fragment != earningFragment) {
            fragmentManager.beginTransaction().hide(earningFragment).commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }

        if (fragment != profileFragment) {
            fragmentManager.beginTransaction().hide(profileFragment).commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }

        if (fragment != settingsFragment) {
            fragmentManager.beginTransaction().hide(settingsFragment).commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }


    }

    private void swapSelection(int newSelectedMenu) {
        if (newSelectedMenu == lastMenuSelection)
            return;
        resetLastSelection(lastMenuSelection);
        switch (newSelectedMenu) {
            case BOTTOM_MENU_HOME:
                bottomBarHome.setTextColor(getResources().getColor(R.color.colorPrimary));
                bottomBarHomeText.setTextColor(getResources().getColor(R.color.colorPrimary));
                lastMenuSelection = BOTTOM_MENU_HOME;

                break;
            case BOTTOM_MENU_COMP:
                bottomBarCompetition.setTextColor(getResources().getColor(R.color.colorPrimary));
                bottomBarCompetitionText.setTextColor(getResources().getColor(R.color.colorPrimary));
                lastMenuSelection = BOTTOM_MENU_COMP;

                break;
            case BOTTOM_MENU_PROFILE:
                bottomBarProfile.setTextColor(getResources().getColor(R.color.colorPrimary));
                bottomBarProfileText.setTextColor(getResources().getColor(R.color.colorPrimary));
                lastMenuSelection = BOTTOM_MENU_PROFILE;

                break;

            case BOTTOM_MENU_SETTINGS:
                bottomBarSettings.setTextColor(getResources().getColor(R.color.colorPrimary));
                bottomBarSettingsText.setTextColor(getResources().getColor(R.color.colorPrimary));
                lastMenuSelection = BOTTOM_MENU_SETTINGS;

                break;

            case BOTTOM_MENU_EARNINGS:

                bottomBarCompetition.setTextColor(getResources().getColor(R.color.colorPrimary));
                bottomBarCompetitionText.setTextColor(getResources().getColor(R.color.colorPrimary));
                lastMenuSelection = BOTTOM_MENU_EARNINGS;

                break;

            default:
                break;
        }
    }

    private void resetLastSelection(int lastMenuSelection) {

        switch (lastMenuSelection) {
            case BOTTOM_MENU_HOME:
                bottomBarHome.setTextColor(Color.parseColor("#818080"));
                bottomBarHomeText.setTextColor(Color.parseColor("#818080"));
                break;
            case BOTTOM_MENU_COMP:
                bottomBarCompetition.setTextColor(Color.parseColor("#818080"));
                bottomBarCompetitionText.setTextColor(Color.parseColor("#818080"));
                break;
            case BOTTOM_MENU_PROFILE:
                bottomBarProfile.setTextColor(Color.parseColor("#818080"));
                bottomBarProfileText.setTextColor(Color.parseColor("#818080"));
                break;
            case BOTTOM_MENU_SETTINGS:
                bottomBarSettings.setTextColor(Color.parseColor("#818080"));
                bottomBarSettingsText.setTextColor(Color.parseColor("#818080"));
                break;
            case BOTTOM_MENU_EARNINGS:
                bottomBarCompetition.setTextColor(Color.parseColor("#818080"));
                bottomBarCompetitionText.setTextColor(Color.parseColor("#818080"));
                break;
            default:
                break;
        }
    }

    private void fetchCompleteUserInfo() {
        showProgress("Getting Your Account...");
        DataServer.fetchUser(this);
    }

    @Override
    public void onUserFetched(FetchUserResponse userResponse) {

        hideProgress();
        HaprampPreferenceManager.getInstance().setUserInfoAvailable(true);
        HaprampPreferenceManager.getInstance().setUser(new Gson().toJson(userResponse));
        HaprampPreferenceManager.getInstance().setLoggedIn(true);
        HaprampPreferenceManager.getInstance().setUserId(String.valueOf(userResponse.getId()));
        HaprampPreferenceManager.getInstance().setUserEmail(userResponse.getEmail());

        if (userResponse.getOrganization() == null) {
            redirectToOrgsPage();
        } else {
            if (userResponse.getSkills().size() == 0) {
                redirectToSkillsPage();
            } else {
                // Everything is set!
                // transact
                transactFragment(FRAGMENT_HOME);
            }
        }
    }


    private void redirectToOrgsPage() {
        hideProgress();

    }

    private void redirectToSkillsPage() {
        hideProgress();

    }

    private void showProgress(String msg) {
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    private void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onUserFetchedError() {

        hideProgress();
        Toast.makeText(this, "User Fetched Error!", Toast.LENGTH_LONG).show();
        hideProgress();
    }

    @Override
    public void onUserNotExists() {

    }

    @Override
    public void onCreateArticleButtonClicked() {
        Intent intent = new Intent(this, CreateArticleActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCreatePostButtonClicked() {
        Intent intent = new Intent(this, PostCreateActivity.class);
        startActivity(intent);
    }
}

