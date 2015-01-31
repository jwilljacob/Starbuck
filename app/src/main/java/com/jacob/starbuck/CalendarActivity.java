package com.jacob.starbuck;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.timtak.widget.AnalogChronometer;

import butterknife.ButterKnife;
import butterknife.InjectView;
import timber.log.Timber;


public class CalendarActivity extends Activity {

  public static final String EXTRA_PHONE = "phone";

  @InjectView(R.id.clock)
  AnalogChronometer clock;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_calendar);
    ButterKnife.inject(this);

    Intent intent = getIntent();
    String phone = intent.getStringExtra(EXTRA_PHONE);
    Timber.d("phone num is " + phone);

    clock.setTime(15, 0, 0);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_calendar, menu);
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

  public static void startActivity(Context context, String num) {
    Intent intent = new Intent(context, CalendarActivity.class);
    intent.putExtra(EXTRA_PHONE, num);
    context.startActivity(intent);
  }
}
