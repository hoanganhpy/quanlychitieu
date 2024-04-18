package vn.edu.hutech.quanlychitieu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import vn.edu.hutech.quanlychitieu.R;
import vn.edu.hutech.quanlychitieu.util.SQLiteUtil;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener {

    TextView btnCancel, btnSave;
    EditText etPassword, etConfirmPassword;
    SQLiteUtil sqLiteUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        btnCancel = findViewById(R.id.btnCancelChangePIN);
        btnSave = findViewById(R.id.btnSavePIN);

        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etPasswordConfirm);

        sqLiteUtil = new SQLiteUtil(ChangePassword.this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancelChangePIN:
                finish();
                break;
            case R.id.btnSavePIN:
                String result = validateData();
                if (result.equals("")) {
                    String newPIN = etPassword.getText().toString();
                    sqLiteUtil.updateProperties(SQLiteUtil.PROPERTIES_USER_SECRET_KEY, newPIN);
                    finish();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Lỗi");
                    builder.setMessage(result);
                    builder.setCancelable(false);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                break;
        }
    }

    private String validateData() {
        String password = etPassword.getText().toString();
        String confirm = etConfirmPassword.getText().toString();
        if (password.isEmpty() || confirm.isEmpty()) {
            return "Các trường không được bỏ trống!";
        }
        if (password.length() < 4) {
            return "Mã PIN phải từ 4 ký tự trở lên!";
        }
        if (!password.equals(confirm)) {
            return "Hai mã không khớp!";
        }
        return "";
    }
}
