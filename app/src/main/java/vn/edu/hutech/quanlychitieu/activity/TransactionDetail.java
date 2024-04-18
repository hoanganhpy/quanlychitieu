package vn.edu.hutech.quanlychitieu.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import vn.edu.hutech.quanlychitieu.R;
import vn.edu.hutech.quanlychitieu.model.Transaction;
import vn.edu.hutech.quanlychitieu.util.ConversionUtil;
import vn.edu.hutech.quanlychitieu.util.DateUtil;
import vn.edu.hutech.quanlychitieu.util.GroupIconUtil;
import vn.edu.hutech.quanlychitieu.util.SQLiteUtil;

public class TransactionDetail extends AppCompatActivity implements View.OnClickListener {

    public static final int EDIT_TRANSACTION_CODE = 1;

    TextView btnBack, btnEditTransaction;
    TextView tvTransDetailMoney, tvTransDetailNote, tvTransDetailDate, tvTransDetailGroup;
    SQLiteUtil sqLiteUtil;
    ImageView ivTransDetailGroupLogo;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);

        btnBack = findViewById(R.id.btnBack);
        btnEditTransaction = findViewById(R.id.btnEditTransaction);
        tvTransDetailDate = findViewById(R.id.tvTransDetailDate);
        tvTransDetailMoney = findViewById(R.id.tvTransDetailMoney);
        tvTransDetailNote = findViewById(R.id.tvTransDetailNote);
        tvTransDetailGroup = findViewById(R.id.tvTransDetailGroup);
        ivTransDetailGroupLogo = findViewById(R.id.ivTransDetailGroupLogo);


        sqLiteUtil = new SQLiteUtil(TransactionDetail.this);

        id = getIntent().getIntExtra("id", 0);

        Transaction transaction = sqLiteUtil.getTransactionById(id);

        tvTransDetailDate.setText(DateUtil.formatDate(transaction.getDate()));
        tvTransDetailMoney.setText(ConversionUtil.doubleToString(transaction.getMoneyAmount()));
        tvTransDetailNote.setText(transaction.getNote());
        tvTransDetailGroup.setText(transaction.getGroup().getName());
        ivTransDetailGroupLogo.setImageResource(GroupIconUtil.getGroupIcon(transaction.getGroup().getName()));

        btnBack.setOnClickListener(this);
        btnEditTransaction.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnEditTransaction:
                Intent i = new Intent(TransactionDetail.this, EditTransaction.class);
                i.putExtra("id", id);
                startActivityForResult(i, EDIT_TRANSACTION_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_TRANSACTION_CODE && resultCode == RESULT_OK) {
            assert data != null;
            Transaction transaction = (Transaction) Objects.requireNonNull(data.getExtras()).getSerializable("result");
            assert transaction != null;
            sqLiteUtil.updateTransaction(transaction);

            tvTransDetailDate.setText(DateUtil.formatDate(transaction.getDate()));
            tvTransDetailMoney.setText(ConversionUtil.doubleToString(transaction.getMoneyAmount()));
            tvTransDetailNote.setText(transaction.getNote());
            tvTransDetailGroup.setText(transaction.getGroup().getName());
            ivTransDetailGroupLogo.setImageResource(GroupIconUtil.getGroupIcon(transaction.getGroup().getName()));

        }
    }
}
