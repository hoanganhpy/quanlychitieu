package vn.edu.hutech.quanlychitieu.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import vn.edu.hutech.quanlychitieu.R;
import vn.edu.hutech.quanlychitieu.model.Transaction;
import vn.edu.hutech.quanlychitieu.model.TransactionGroup;
import vn.edu.hutech.quanlychitieu.model.WalletType;
import vn.edu.hutech.quanlychitieu.util.ConversionUtil;
import vn.edu.hutech.quanlychitieu.util.DateUtil;
import vn.edu.hutech.quanlychitieu.util.GroupIconUtil;
import vn.edu.hutech.quanlychitieu.util.SQLiteUtil;

public class EditTransaction extends AppCompatActivity implements View.OnClickListener {

    public static final int SELECT_GROUP_CODE = 1;

    TextView tvDatePickerValue, tvCancelTransaction, tvSaveTransaction, tvSelectedGroup;
    ImageView ivTransactionGroupIcon;
    EditText etAmountOfMoney, etNote;
    DatePickerDialog picker;
    LinearLayout btnClickSelectGroup;
    SQLiteUtil sqLiteUtil;
    Date selectedDate;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);

        mapView();

        init();

        id = getIntent().getIntExtra("id", 0);
        Transaction transaction = sqLiteUtil.getTransactionById(id);
        selectedDate = transaction.getDate();
        tvDatePickerValue.setText(DateUtil.formatDate(transaction.getDate()));
        tvSelectedGroup.setText(transaction.getGroup().getName());
        ivTransactionGroupIcon.setImageResource(GroupIconUtil.getGroupIcon(transaction.getGroup().getName()));

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedString = formatter.format(transaction.getMoneyAmount());
        etAmountOfMoney.setText(formattedString);
        etNote.setText(transaction.getNote());
        ivTransactionGroupIcon.setImageResource(GroupIconUtil.getGroupIcon(transaction.getGroup().getName()));
    }

    private void init() {
        tvDatePickerValue.setOnClickListener(this);
        sqLiteUtil = new SQLiteUtil(EditTransaction.this);
        etAmountOfMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                etAmountOfMoney.removeTextChangedListener(this);
                try {
                    String givenstring = editable.toString();
                    Long longval;
                    if (givenstring.contains(",")) {
                        givenstring = givenstring.replaceAll(",", "");
                    }
                    longval = Long.parseLong(givenstring);
                    DecimalFormat formatter = new DecimalFormat("#,###,###");
                    String formattedString = formatter.format(longval);
                    etAmountOfMoney.setText(formattedString);
                    etAmountOfMoney.setSelection(etAmountOfMoney.getText().length());
                    // to place the cursor at the end of text
                } catch (Exception nfe) {
                    nfe.printStackTrace();
                }
                etAmountOfMoney.addTextChangedListener(this);
            }
        });
        tvCancelTransaction.setOnClickListener(this);
        tvSaveTransaction.setOnClickListener(this);
        selectedDate = Calendar.getInstance().getTime();
        tvSelectedGroup.setOnClickListener(this);
        btnClickSelectGroup.setOnClickListener(this);
    }

    private void mapView() {
        tvDatePickerValue = findViewById(R.id.tvDatePickerValueEdit);
        etAmountOfMoney = findViewById(R.id.etEditAmountOfMoney);
        tvCancelTransaction = findViewById(R.id.btnCancelEditTransaction);
        tvSaveTransaction = findViewById(R.id.btnSaveEditTransaction);
        tvSelectedGroup = findViewById(R.id.tvSelectedGroupEdit);
        etNote = findViewById(R.id.etTransactionNoteEdit);
        ivTransactionGroupIcon = findViewById(R.id.ivTransGroupIconEdit);
        btnClickSelectGroup = findViewById(R.id.btnClickEditSelectGroup);
    }

    @Override
    public void onClick(View view) {
        Intent returnIntent = new Intent();
        switch (view.getId()) {
            case R.id.tvDatePickerValueEdit:
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog

                picker = new DatePickerDialog(EditTransaction.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                selectedDate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
                                tvDatePickerValue.setText(DateUtil.formatDate(selectedDate));
                            }
                        }, year, month, day);
                picker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
                picker.show();
                break;
            case R.id.btnCancelEditTransaction:
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
                break;
            case R.id.btnSaveEditTransaction:
                if (etAmountOfMoney.getText().toString().equals("") || tvSelectedGroup.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Lỗi");
                    builder.setMessage("Vui lòng điền đầy đủ các thông tin");
                    builder.setCancelable(false);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    double amountOfMoney = ConversionUtil.stringToInt(etAmountOfMoney.getText().toString().replace(",", ""), 0);
                    TransactionGroup group = sqLiteUtil.getGroupByGroupName(tvSelectedGroup.getText().toString());
                    String note = etNote.getText().toString();
                    Date date = selectedDate;
                    int walletType = WalletType.NORMAL_WALLET;
                    if (group.getType() == TransactionGroup.OUTGOING) {
                        amountOfMoney = -amountOfMoney;
                    }
                    returnIntent.putExtra("result", new Transaction(id, amountOfMoney, group, note, date, walletType));
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
                break;
            case R.id.btnClickEditSelectGroup:
                Intent i = new Intent(EditTransaction.this, SelectTransactionGroup.class);
                startActivityForResult(i, SELECT_GROUP_CODE);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_GROUP_CODE && resultCode == RESULT_OK) {
            assert data != null;
            String groupName = data.getStringExtra("groupName");
            tvSelectedGroup.setText(groupName);
            assert groupName != null;
            ivTransactionGroupIcon.setImageResource(GroupIconUtil.getGroupIcon(groupName));
        }
    }
}
