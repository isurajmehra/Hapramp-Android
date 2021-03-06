package com.hapramp.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.hapramp.api.DataServer;
import com.hapramp.main.HapRampMain;
import com.hapramp.logger.L;
import com.hapramp.utils.HashGenerator;
import com.hapramp.utils.MomentsUtils;

/**
 * Created by Ankit on 5/15/2017.
 */

public class HaprampPreferenceManager {

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static HaprampPreferenceManager mInstance;

    private static final String PREF_NAME = "hapramp_pref";
    private static final int PREF_MODE_PRIVATE = 1;
    private String articleAsDraft;

    public HaprampPreferenceManager(int i) {

        preferences = HapRampMain.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();

    }

    public static HaprampPreferenceManager getInstance() {
        if (mInstance == null) {
            mInstance = new HaprampPreferenceManager(0);
        }
        return mInstance;
    }

    public void clearPreferences() {
        DataServer.resetAPI();
        CachePreference.getInstance().clearCachePreferences();
        editor.clear();
        editor.apply();
        Log.d("Pref", "Cleared Prefs.");
    }

    public void setLoggedIn(boolean loggedIn) {
        editor.putBoolean("isLoggedIn", loggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean("isLoggedIn", false);
    }

    public void saveUserNameAndPpk(String username, String ppk) {

        editor.putString("username", username);
        editor.putString("ppk", ppk);
        editor.apply();

    }

    public String getCurrentSteemUsername() {
        return preferences.getString("username", "");
    }

    public String getPPK() {
        return preferences.getString("ppk", "");
    }

    public void saveCurrentUserInfoAsJson(String json) {
        editor.putString("c_user", json);
        editor.apply();
    }

    public String getCurrentUserInfoAsJson() {
        return preferences.getString("c_user", "");
    }

    public void saveAllCommunityListAsJson(String json) {

        Log.d("Pref", "saving " + json);
        editor.putString("allCommunity", json);
        editor.apply();

    }

    public String getAllCommunityAsJson() {

        return preferences.getString("allCommunity", "{}");

    }

    public void setCommunityTagToNamePair(String tag, String name) {
        editor.putString(tag + "_name", name);
        editor.apply();
    }

    public String getCommunityNameFromTag(String tag) {
        return preferences.getString(tag + "_name", "");
    }

    public void setCommunityTagToColorPair(String tag, String color) {
        editor.putString(tag + "_color", color);
        editor.apply();
    }

    public String getCommunityColorFromTag(String tag) {
        return preferences.getString(tag + "_color", "#009988");
    }

    public void saveUserSelectedCommunitiesAsJson(String json) {
        Log.d("Pref", "saving " + json);
        editor.putString("userSelectedCommunity", json);
        editor.apply();

    }

    public void setCommentCount(String permlink , int count){
        editor.putInt("comment_count_"+permlink,count);
        editor.apply();
    }

    public int getCommentCount(String permlink){
        return preferences.getInt("comment_count_"+permlink,0);
    }

    public String getUserSelectedCommunityAsJson() {

        return preferences.getString("userSelectedCommunity", "");

    }

    public String getUserToken() {
        return HashGenerator.getSHA2(getPPK());
    }

    public void saveUserProfile(String username, String json) {
        editor.putString("user_profile_data_" + username, json);
        editor.apply();
    }

    public String getUserProfile(String username) {
        return preferences.getString("user_profile_data_" + username, "");
    }


    public void setUserInfoAvailable(boolean available) {
        editor.putBoolean("userInfoAvailable", available);
        editor.apply();
    }


    public boolean isUserInfoAvailable() {
        return preferences.getBoolean("userInfoAvailable", false);
    }

    public void setUserId(String id) {
        Log.d("Pref", "setting user Id " + id);
        editor.putString("userId", id);
        editor.apply();
    }

    public String getUserId() {
        return preferences.getString("userId", "");
    }

    public void setUserEmail(String s) {
        editor.putString("userEmail", s);
        editor.apply();
    }

    public void setUser(String user) {
        L.D.m("Pref", "User json " + user);
        editor.putString("userJson", user);
        editor.apply();
    }

    public void setProfileHardRefreshRequired(boolean isRequired) {
        editor.putBoolean("profile_ref", isRequired);
        editor.apply();
    }

    public boolean isProfileHardRefreshRequired() {
        return preferences.getBoolean("profile_ref", false);
    }

    public void setPostHardRefreshRequired(boolean isRequired) {
        editor.putBoolean("post_ref", isRequired);
        editor.apply();
    }

    public boolean isPostHardRefreshRequired() {
        return preferences.getBoolean("post_ref", false);
    }

    public void savePostDraft(String s) {
        editor.putString("post_draft", s);
        editor.apply();
    }

    public String getPostDraft() {
        return preferences.getString("post_draft", "");
    }

    public void savePostMediaDraft(String filePath) {
        editor.putString("pmdraft", filePath);
        editor.apply();
    }

    public String getPostMediaDraft() {
        return preferences.getString("pmdraft", "");
    }

    public void saveArticleDraft(String s) {
        editor.putString("articleDraft", s);
        editor.apply();
    }

    public String getArticleDraft() {
        return preferences.getString("articleDraft", "");
    }

    public boolean isPostingServiceRunning() {
        return preferences.getBoolean("runningService", false);
    }

    public void setPostCachedStatus(boolean cachedStatus) {
        editor.putBoolean("postCached", cachedStatus);
        editor.apply();
    }

    public boolean isPostsCached() {
        return preferences.getBoolean("postCached", false);
    }

    public void setJobAvailable(boolean jobAvailable) {
        editor.putBoolean("newJob", jobAvailable);
        editor.apply();
    }

    public boolean isNewJobAvailble() {
        return preferences.getBoolean("newJob", false);
    }

    public void setPostingServiceRunning(boolean running) {
        editor.putBoolean("runningService", running);
        editor.apply();
    }

    public void incrementUnreadNotifications() {
        setUnreadNotification(getUnreadNotifications() + 1);
    }

    public void setUnreadNotification(int unreadNotification) {
        editor.putInt("unread_notif", unreadNotification);
        editor.apply();
    }

    public int getUnreadNotifications() {
        return preferences.getInt("unread_notif", 0);
    }

    public void setArticleAsDraft(String articleAsDraft) {
        editor.putString("articleDraft", articleAsDraft);
        editor.apply();
    }

    public String getArticleAsDraft() {
        return preferences.getString("articleDraft", "");
    }

    public String getAllPlatformUserAsJson() {
        return preferences.getString("platform_users", "");
    }

    public void saveAllPlatformUserAsJson(String json) {
        editor.putString("platform_users", json);
        editor.apply();
    }

    public void saveCurrentUserFollowingsAsJson(String json) {
        editor.putString("current_user_followings",json);
        editor.apply();
    }

    public String getCurrentUserFollowingsAsJson(){
        return  preferences.getString("current_user_followings","");
    }

    public void setLastPostCreatedAt(long timeMillis) {
        editor.putLong("last_created_at",timeMillis);
        editor.apply();
    }

    public long getLastPostCreatedAt(){
        return preferences.getLong("last_created_at",0);
    }
}
