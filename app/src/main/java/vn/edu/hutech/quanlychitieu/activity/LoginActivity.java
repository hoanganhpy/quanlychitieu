package vn.edu.hutech.quanlychitieu.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import vn.edu.hutech.quanlychitieu.R;
import vn.edu.hutech.quanlychitieu.util.SQLiteUtil;
import vn.edu.hutech.quanlychitieu.util.SharedPrefsUtil;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    TextView tvSignUp;
    Button btnLogin;
    EditText etSecretKey;
    String secretKey;
    SQLiteUtil sqLiteUtil;
    TextView tvPasswordHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        mapView();

        sqLiteUtil = new SQLiteUtil(LoginActivity.this);
        btnLogin.setOnClickListener(this);

        if (!SharedPrefsUtil.containsPreferenceKey(getApplicationContext(), SharedPrefsUtil.LOGGED_IN)) {
            tvPasswordHint.setVisibility(View.VISIBLE);
        }

    }

    private void mapView() {
        btnLogin = findViewById(R.id.btnLogin);
        etSecretKey = findViewById(R.id.etSecretKey);
        tvPasswordHint = findViewById(R.id.paswordHint);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                login();
                break;
        }
    }

    private void login() {
        secretKey = etSecretKey.getText().toString();
        authenticate(secretKey);
    }

    private void authenticate(String secretKey) {
        if (sqLiteUtil.getProperty(SQLiteUtil.PROPERTIES_USER_SECRET_KEY).equals(secretKey)) {
            if (!SharedPrefsUtil.containsPreferenceKey(getApplicationContext(), SharedPrefsUtil.LOGGED_IN)) {
                SharedPrefsUtil.setPreferenceValue(getApplicationContext(), SharedPrefsUtil.LOGGED_IN, "TRUE");
                Intent intent = new Intent(LoginActivity.this, ActivityInitMoney.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Lỗi");
            builder.setMessage("Mã PIN không chính xác");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}