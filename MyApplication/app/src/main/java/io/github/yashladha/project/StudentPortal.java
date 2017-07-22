package io.github.yashladha.project;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import io.github.yashladha.project.studentFragments.ChatStudent;
import io.github.yashladha.project.studentFragments.Course;
import io.github.yashladha.project.studentFragments.Home;
import io.github.yashladha.project.studentFragments.Lecture;
import io.github.yashladha.project.studentFragments.Library;
import io.github.yashladha.project.studentFragments.SettingsStudent;
import io.github.yashladha.project.studentFragments.Storage;

public class StudentPortal extends AppCompatActivity {

  private String[] mPanelTitles;
  private DrawerLayout mDrawerLayout;
  private ListView mListView;
  private ActionBarDrawerToggle mDrawerToggle;
  private CharSequence mDrawerTitle;
  private CharSequence mTitle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_student_portal);

    mTitle = mDrawerTitle = getTitle();
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
    mPanelTitles = getResources().getStringArray(R.array.student);

    mListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
        mPanelTitles));
    mListView.setOnItemClickListener(new DrawerItemClickListener());
    setFragment(0); // Default Fragment layout
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
        fragment = new Course();
        break;
      case 2:
        fragment = new Lecture();
        break;
      case 3:
        fragment = new ChatStudent();
        break;
      case 4:
        fragment = new Library();
        break;
      case 5:
        fragment = new Storage();
        break;
      case 7:
        fragment = new SettingsStudent();
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
