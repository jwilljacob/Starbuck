package com.jacob.starbuck;

import android.app.Application;
import android.content.Context;

/**
 * Created by zhangxiaobo on 15/1/31.
 */
public class StarbucksApplication extends Application {
  private static Context sContext;

  @Override
  public void onCreate() {
    super.onCreate();
    sContext = getApplicationContext();
  }

  public static Context getAppContext() {
    return sContext;
  }
}
