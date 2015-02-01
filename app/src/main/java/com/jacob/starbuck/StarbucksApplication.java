package com.jacob.starbuck;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by zhangxiaobo on 15/1/31.
 */
public class StarbucksApplication extends Application {
  private static Context sContext;

  @Override
  public void onCreate() {
    super.onCreate();
    sContext = getApplicationContext();

    AVOSCloud.initialize(this, "5uxaqtmf7byh23sxod374rubp4ik5uc4fnqsle12mzq7qyz5",
        "hoodcj3tuq4m3fa6m1yil3qju2t1ulm5wa3ewc22hkr1qaoy");
    AVOSCloud.setDebugLogEnabled(true);
  }

  public static Context getAppContext() {
    return sContext;
  }
}
