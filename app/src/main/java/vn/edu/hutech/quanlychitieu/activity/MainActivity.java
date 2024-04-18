package vn.edu.hutech.quanlychitieu.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import vn.edu.hutech.quanlychitieu.R;
import vn.edu.hutech.quanlychitieu.adapter.NotificationAdapter;
import vn.edu.hutech.quanlychitieu.adapter.TransactionListViewAdapter;
import vn.edu.hutech.quanlychitieu.fragment.BottomSheetFragment;
import vn.edu.hutech.quanlychitieu.fragment.MoreFragment;
import vn.edu.hutech.quanlychitieu.fragment.NotificationFragment;
import vn.edu.hutech.quanlychitieu.fragment.ReportFragment;
import vn.edu.hutech.quanlychitieu.fragment.TransactionFragment;

public class MainActivity extends AppCompatActivity implements BottomSheetFragment.BottomSheetListener, NotificationAdapter.OnItemClickedListener, TransactionListViewAdapter.OnTransactionItemClickListener {

    BottomNavigationView bottomNavigationView;

    final Fragment moreFragment = new MoreFragment();
    final Fragment notificationFragment = new NotificationFragment();
    final Fragment reportFragment = new ReportFragment();
    final Fragment transactionFragment = new TransactionFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment activeFragment = transactionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        String[] PERMISSIONS = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS
        };

        if (!hasPermissions(getApplicationContext(), PERMISSIONS)) {
            ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, 200);
        }

        mapView();

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationListener);
        fm.beginTransaction().add(R.id.frag_container, moreFragment, "4").hide(moreFragment).commit();
        fm.beginTransaction().add(R.id.frag_container, notificationFragment, "3").hide(notificationFragment).commit();
        fm.beginTransaction().add(R.id.frag_container,reportFragment, "2").hide(reportFragment).commit();
        fm.beginTransaction().add(R.id.frag_container,transactionFragment, "1").commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.nav_action_more:
                    fm.beginTransaction().hide(activeFragment).show(moreFragment).commit();
                    activeFragment = moreFragment;
                    return true;
                case R.id.nav_action_notification:
                    fm.beginTransaction().hide(activeFragment).show(notificationFragment).commit();
                    activeFragment = notificationFragment;
                    return true;
                case R.id.nav_action_report:
                    fm.beginTransaction().hide(activeFragment).show(reportFragment).commit();
                    activeFragment = reportFragment;
                    return true;
                case R.id.nav_action_transaction:
                    fm.beginTransaction().hide(activeFragment).show(transactionFragment).commit();
                    activeFragment = transactionFragment;
                    return true;
            }
            return false;
        }
    };

    private void mapView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onItemClickListener(String position) {
        ((ReportFragment)reportFragment).onItemClickListener(position);
    }

    @Override
    public void onItemClick(int notificationId) {
        ((NotificationFragment) notificationFragment).onItemClick(notificationId);
    }

    @Override
    public void onTransactionItemClick(int transactionId) {
        ((TransactionFragment) transactionFragment).onTransactionItemClick(transactionId);
    }
}
