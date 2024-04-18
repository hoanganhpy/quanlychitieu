package vn.edu.hutech.quanlychitieu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import vn.edu.hutech.quanlychitieu.R;
import vn.edu.hutech.quanlychitieu.util.SQLiteUtil;

public class ActivityInitMoney extends AppCompatActivity {

    Button btnDone;
    EditText etAmountOfMoney;
    SQLiteUtil sqLiteUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_money);

        sqLiteUtil = new SQLiteUtil(ActivityInitMoney.this);

        btnDone = findViewById(R.id.btnSaveInitMoney);
        etAmountOfMoney = findViewById(R.id.etInitMoney);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etAmountOfMoney.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setTitle("Lỗi");
                    builder.setMessage("Vui lòng điền số tiền");
                    builder.setCancelable(false);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    sqLiteUtil.updateProperties(SQLiteUtil.PROPERTIES_CURRENT_MONEY_AMOUNT_NORMAL, etAmountOfMoney.getText().toString());
                    Intent i = new Intent(ActivityInitMoney.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        });
    }
}
