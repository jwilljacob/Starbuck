package com.jacob.starbuck.test;

import android.test.InstrumentationTestCase;

import com.jacob.starbuck.Preferences;

import junit.framework.Assert;


/**
 * Created by zhangxiaobo on 15/2/2.
 */
public class UnitTest extends InstrumentationTestCase {
  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testA() {
    assertEquals(true, 1 == 3 - 2);
  }

  public void testPrefer() {
    Preferences.setPhoneNum("123456");
    boolean has = Preferences.hasPhoneNum();
    Assert.assertEquals(true, has);

    Assert.assertEquals("123456", Preferences.getPhoneNum());
  }
}
