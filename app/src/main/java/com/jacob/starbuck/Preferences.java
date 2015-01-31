package com.jacob.starbuck;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/**
 * Created by zhangxiaobo on 15/1/31.
 */
public class Preferences {
  public static SharedPreferences preferences = PreferenceManager
      .getDefaultSharedPreferences(StarbucksApplication.getAppContext());

  public static final String KEY_PHONE = "phone";

  public static boolean hasPhoneNum() {
    String num = preferences.getString(KEY_PHONE, "");
    return !TextUtils.isEmpty(num);
  }

  public static void setPhoneNum(String num) {
    preferences.edit().putString(KEY_PHONE, num).commit();
  }

  public static String getPhoneNum() {
    return preferences.getString(KEY_PHONE, "");
  }
}
