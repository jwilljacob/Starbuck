package com.jacob.starbuck;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.avos.avoscloud.AVAnalytics;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnEditorAction;
import timber.log.Timber;


public class LoginActivity extends ActionBarActivity {
  @InjectView(R.id.phone)
  EditText phone;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AVAnalytics.trackAppOpened(getIntent());

    // skip login
    if (Preferences.hasPhoneNum()) {
      Timber.d("Skip login, has phone num");
      CalendarActivity.startActivity(this, Preferences.getPhoneNum());
      finish();
      return;
    }

    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);
  }

  @OnEditorAction(R.id.phone)
  boolean onEditorAction(KeyEvent key) {
    Timber.d("complete");
    String num = phone.getText().toString().trim();
    Preferences.setPhoneNum(num);
    CalendarActivity.startActivity(LoginActivity.this, num);
    finish();
    return true;
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

    // noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
