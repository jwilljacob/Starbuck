package com.jacob.starbuck;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.timtak.widget.AnalogChronometer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import timber.log.Timber;


public class CalendarActivity extends Activity {

  public static final String EXTRA_PHONE = "phone";

  @InjectView(R.id.clock)
  AnalogChronometer clock;

  @InjectView(R.id.slogan)
  TextView slogan;

  @InjectView(R.id.time)
  TextView time;

  @InjectView(R.id.date)
  TextView date;

  @InjectView(R.id.today)
  TextView todayText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_calendar);
    ButterKnife.inject(this);

    Intent intent = getIntent();
    final String phone = intent.getStringExtra(EXTRA_PHONE);
    Timber.d("phone num is " + phone);

    Calendar calendar = Calendar.getInstance();
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    date.setText(month + 1 + "月" + day + "日");

    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    String today = df.format(calendar.getTime());
    Timber.d("today:" + today);

    todayText.setText("今天");

    // Try find work time today
    final AVQuery<AVObject> query = new AVQuery<AVObject>("WorkTime");
    query.whereEqualTo("phone", phone);
    query.whereEqualTo("date", Integer.parseInt(today));
    query.findInBackground(new FindCallback<AVObject>() {
      public void done(List<AVObject> avObjects, AVException e) {
        if (e == null) {
          if (avObjects.size() > 1) {
            // Toast.makeText(CalendarActivity.this, "有多个日程安排，请联系管理员", Toast.LENGTH_SHORT).show();
          }

          if (avObjects.size() == 0) {
            // Toast.makeText(CalendarActivity.this, "今天还没有日程安排，看看明天", Toast.LENGTH_SHORT).show();
            showTips("您今天貌似不上班");
            queryTomorrow(phone);
            return;
          }

          AVObject avObject = avObjects.get(0);
          showTime(avObject.getString("startHour"));

        } else {
          e.printStackTrace();
          Toast.makeText(CalendarActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  private void queryTomorrow(String phone) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_MONTH, 1);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    date.setText(month + 1 + "月" + day++ + "日");

    todayText.setText("明天");

    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    String today = df.format(calendar.getTime());
    Timber.d("today:" + today);

    // Try find work time today
    AVQuery<AVObject> query = new AVQuery<AVObject>("WorkTime");
    query.whereEqualTo("phone", phone);
    query.whereEqualTo("date", Integer.parseInt(today));
    query.findInBackground(new FindCallback<AVObject>() {
      public void done(List<AVObject> avObjects, AVException e) {
        if (e == null) {
          if (avObjects.size() > 1) {
            Toast.makeText(CalendarActivity.this, "有多个日程安排，请联系管理员", Toast.LENGTH_SHORT).show();
          }

          if (avObjects.size() == 0) {
            Toast.makeText(CalendarActivity.this, "这两天还没有日程安排", Toast.LENGTH_SHORT).show();
            showTips("您这两天貌似不上班");
            return;
          }

          AVObject avObject = avObjects.get(0);
          showTime(avObject.getString("startHour"));

        } else {
          e.printStackTrace();
          Toast.makeText(CalendarActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  private void showTime(String start) {
    int hour = Integer.parseInt(start.split(":")[0]);
    int min = Integer.parseInt(start.split(":")[1]);
    clock.setTime(hour, min, 0);
    clock.setVisibility(View.VISIBLE);
    time.setVisibility(View.VISIBLE);
    slogan.setVisibility(View.GONE);
  }

  private void showTips(String tips) {
    clock.setVisibility(View.GONE);
    time.setVisibility(View.GONE);
    slogan.setVisibility(View.VISIBLE);
    slogan.setText(tips);
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

  @OnClick(R.id.submit)
  public void reset(View view) {
    Preferences.setPhoneNum("");
    startActivity(new Intent(this, LoginActivity.class));
    finish();
  }
}
