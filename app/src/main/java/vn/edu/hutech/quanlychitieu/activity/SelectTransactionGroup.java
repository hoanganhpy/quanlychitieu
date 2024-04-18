package vn.edu.hutech.quanlychitieu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hutech.quanlychitieu.R;
import vn.edu.hutech.quanlychitieu.adapter.SelectTransactionGroupListViewAdapter;
import vn.edu.hutech.quanlychitieu.model.TransactionGroup;
import vn.edu.hutech.quanlychitieu.util.SQLiteUtil;

public class SelectTransactionGroup extends AppCompatActivity {

    RecyclerView recyclerView;
    List<TransactionGroup> data;
    List<TransactionGroup> incomingData;
    List<TransactionGroup> outGoingData;
    SelectTransactionGroupListViewAdapter adapter;
    SQLiteUtil sqLiteUtil;
    TextView btnCancelTransaction, tvIncoming, tvOutgoing;
    LinearLayout btnIncoming, btnOutgoing, buttonGroupWrapper;
    int currentTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_transaction_group);

        mapView();

        init();

    }

    private void init() {

        sqLiteUtil = new SQLiteUtil(SelectTransactionGroup.this);
        data = sqLiteUtil.getAllGroup();

        incomingData = new ArrayList<>(); outGoingData = new ArrayList<>();
        for (TransactionGroup transactionGroup : data) {
            if (transactionGroup.getType() == TransactionGroup.INCOMING) {
                incomingData.add(transactionGroup);
            } else {
                outGoingData.add(transactionGroup);
            }
        }

        data.clear();
        data.addAll(incomingData);

        adapter = new SelectTransactionGroupListViewAdapter(SelectTransactionGroup.this, data);

        currentTab = 1;

        recyclerView.setLayoutManager(new LinearLayoutManager(SelectTransactionGroup.this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickedListener(new SelectTransactionGroupListViewAdapter.OnItemClickedListener() {
            @Override
            public void onItemClick(String groupName) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("groupName", groupName);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        btnCancelTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED, returnIntent);
                finish();
            }
        });

        btnIncoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentTab == 2) {
                    btnIncoming.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    tvIncoming.setTextColor(getResources().getColor(R.color.colorWhite));

                    btnOutgoing.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    tvOutgoing.setTextColor(getResources().getColor(R.color.colorBlack));

                    btnOutgoing.setBackgroundResource(R.drawable.bg_bordered);

                    currentTab = 1;
                    data.clear();
                    data.addAll(incomingData);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        btnOutgoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentTab == 1) {
                    btnOutgoing.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    tvOutgoing.setTextColor(getResources().getColor(R.color.colorWhite));

                    btnIncoming.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    tvIncoming.setTextColor(getResources().getColor(R.color.colorBlack));

                    btnIncoming.setBackgroundResource(R.drawable.bg_bordered);

                    currentTab = 2;
                    data.clear();
                    data.addAll(outGoingData);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void mapView() {
        tvIncoming = findViewById(R.id.tvIncoming);
        tvOutgoing = findViewById(R.id.tvOutgoing);
        btnIncoming = findViewById(R.id.btnIncoming);
        btnOutgoing = findViewById(R.id.btnOutgoing);
        recyclerView = findViewById(R.id.rvTransactionGroup);
        buttonGroupWrapper = findViewById(R.id.buttonGroupWrapper);
        btnCancelTransaction = findViewById(R.id.btnCancelTransaction);

    }
}
