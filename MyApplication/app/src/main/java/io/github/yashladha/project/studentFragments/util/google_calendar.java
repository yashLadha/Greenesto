package io.github.yashladha.project.studentFragments.util;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.easing.linear.Linear;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.calendar.CalendarScopes;

import java.util.List;

import io.github.yashladha.project.R;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class google_calendar extends Fragment implements EasyPermissions.PermissionCallbacks{

  private GoogleAccountCredential mCredential;
  private TextView mOutputText;
  private Button mCallApiButton;
  private ProgressDialog mProgress;

  static final int REQUEST_ACCOUNT_PICKER = 1000;
  static final int REQUEST_AUTHORIZATION = 1001;
  static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
  static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

  private static final String BUTTON_TEXT = "Call Google Calendar API";
  private static final String PREF_ACCOUNT_NAME = "accountName";
  private static final String[] SCOPES = { CalendarScopes.CALENDAR_READONLY };

  public google_calendar() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_google_calendar, container, false);
    LinearLayout fragmentLayout = new LinearLayout(getActivity());
    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.MATCH_PARENT
    );
    fragmentLayout.setLayoutParams(lp);
    fragmentLayout.setOrientation(LinearLayout.VERTICAL);
    fragmentLayout.setPadding(16, 16, 16, 16);
    return view;
  }

  @Override
  public void onPermissionsGranted(int requestCode, List<String> perms) {

  }

  @Override
  public void onPermissionsDenied(int requestCode, List<String> perms) {

  }
}
