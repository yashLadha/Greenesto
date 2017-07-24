package io.github.yashladha.project;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import io.github.yashladha.project.Fragments.ChatStudent;
import io.github.yashladha.project.Fragments.Course;
import io.github.yashladha.project.Fragments.Home;
import io.github.yashladha.project.Fragments.Lecture;
import io.github.yashladha.project.Fragments.Library;
import io.github.yashladha.project.Fragments.Report;
import io.github.yashladha.project.Fragments.SettingsStudent;
import io.github.yashladha.project.Fragments.Storage;
import io.github.yashladha.project.Fragments.Tools;

public class Portal extends AppCompatActivity {

  private static final FirebaseUser CUR_USER = FirebaseAuth.getInstance().getCurrentUser();
  private static final DatabaseReference CUR_DATA = FirebaseDatabase.getInstance()
      .getReference()
      .child(CUR_USER.getUid());
  private String TAG = getClass().getSimpleName();

  private String[] mPanelTitles;
  private DrawerLayout mDrawerLayout;
  private ListView mListView;
  private ActionBarDrawerToggle mDrawerToggle;
  private CharSequence mDrawerTitle;
  private CharSequence mTitle;
  private int id_ = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_student_portal);

    mTitle = mDrawerTitle = "Portal";
    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    mDrawerToggle = new ActionBarDrawerToggle(this,
        mDrawerLayout,
        R.string.navigation_drawer_open,
        R.string.navigation_drawer_close) {
      @Override
      public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
        getSupportActionBar().setTitle(mTitle);
        invalidateOptionsMenu();
      }

      @Override
      public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        getSupportActionBar().setTitle(mDrawerTitle);
        invalidateOptionsMenu();
      }
    };

    mDrawerLayout.setDrawerListener(mDrawerToggle);
    getSupportActionBar().setHomeButtonEnabled(true);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    mListView = (ListView) findViewById(R.id.left_drawer);
    CUR_DATA.child("UserInfo").child("option").addListenerForSingleValueEvent(
        new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
              String type_ = (String) dataSnapshot.getValue();
              Log.d(TAG, type_);
              if (Objects.equals(type_, "Student")) {
                mPanelTitles = getResources().getStringArray(R.array.student);
                id_ = 0;
              }
              else if (Objects.equals(type_, "Teacher")) {
                mPanelTitles = getResources().getStringArray(R.array.teacher);
                id_ = 1;
              }
              else if (Objects.equals(type_, "Parent")) {
                mPanelTitles = getResources().getStringArray(R.array.parent);
                id_ = 2;
              }
              else {
                mPanelTitles = getResources().getStringArray(R.array.admin);
                id_ = 3;
              }
              mListView.setAdapter(new ArrayAdapter<String>(
                  getApplicationContext(),
                  android.R.layout.simple_list_item_1,
                  mPanelTitles));
              mListView.setOnItemClickListener(new DrawerItemClickListener());
              setFragment(0); // Default Fragment layout
            }
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {
            Log.e(TAG, databaseError.getMessage());
          }
        }
    );
  }

  private void setDefaultFragment() {

  }

  @Override
  protected void onPostCreate(@Nullable Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    mDrawerToggle.syncState();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    mDrawerToggle.onConfigurationChanged(newConfig);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (mDrawerToggle.onOptionsItemSelected(item)) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    boolean drawerOpen = mDrawerLayout.isDrawerOpen(mListView);
    return super.onPrepareOptionsMenu(menu);
  }

  class DrawerItemClickListener implements ListView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      setFragment(position);
    }
  }

  private void setFragment(int position) {
    Fragment fragment = null;
    switch (position) {
      case 0:
        fragment = new Home();
        break;
      case 1:
        if (id_ == 0 || id_ == 3)
          fragment = new Course();
        else if (id_ == 1)
          fragment = new Lecture();
        else
          fragment = new Tools();
        break;
      case 2:
        if (id_ == 0)
          fragment = new Lecture();
        else if (id_ == 1)
          fragment = new ChatStudent();
        break;
      case 3:
        if (id_ == 0)
          fragment = new ChatStudent();
        else if (id_ == 1)
          fragment = new Library();
        break;
      case 4:
        if (id_ == 0)
          fragment = new Library();
        else if (id_ == 1)
          fragment = new Storage();
        break;
      case 5:
        if (id_ == 0)
          fragment = new Storage();
        else if (id_ == 1)
          fragment = new Tools();
        break;
      case 6:
        if (id_ == 0)
          fragment = new Tools();
        else if (id_ == 1)
          fragment = new SettingsStudent();
        break;
      case 7:
        if (id_ == 0)
          fragment = new SettingsStudent();
        else if (id_ == 1)
          fragment = new Report();
        break;
    }
    Bundle bundle = new Bundle();
    bundle.putString("title", mPanelTitles[position]);
    fragment.setArguments(bundle);
    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction()
        .replace(R.id.content_frame, fragment)
        .commit();

    mListView.setItemChecked(position, true);
    mTitle = mPanelTitles[position];
    getSupportActionBar().setTitle(mPanelTitles[position]);
    mDrawerLayout.closeDrawer(mListView);
  }

}
