package vn.edu.hutech.quanlychitieu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import vn.edu.hutech.quanlychitieu.R;
import vn.edu.hutech.quanlychitieu.model.Notification;
import vn.edu.hutech.quanlychitieu.util.ConversionUtil;
import vn.edu.hutech.quanlychitieu.util.SQLiteUtil;

public class NotificationDetail extends AppCompatActivity {

    SQLiteUtil sqLiteUtil;
    TextView tvDetailPushedDate, tvNotificationContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);

        sqLiteUtil = new SQLiteUtil(NotificationDetail.this);
        int id = getIntent().getIntExtra("id", 0);
        Notification notification = sqLiteUtil.getNotificationById(id);

        tvDetailPushedDate = findViewById(R.id.tvDetailPushedDate);
        tvNotificationContent = findViewById(R.id.tvDetailNotificationContent);

        tvDetailPushedDate.setText("Thông báo vào " + ConversionUtil.dateToString(notification.getDate(), "HH:mm:ss dd/MM/yyyy"));
        tvNotificationContent.setText(notification.getContent());

    }
}
