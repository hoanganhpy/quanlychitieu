package vn.edu.hutech.quanlychitieu.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import vn.edu.hutech.quanlychitieu.R;
import vn.edu.hutech.quanlychitieu.activity.AddTransaction;
import vn.edu.hutech.quanlychitieu.activity.TransactionDetail;
import vn.edu.hutech.quanlychitieu.adapter.TransactionListViewAdapter;
import vn.edu.hutech.quanlychitieu.model.Transaction;
import vn.edu.hutech.quanlychitieu.model.TransactionDate;
import vn.edu.hutech.quanlychitieu.model.TransactionStatistic;
import vn.edu.hutech.quanlychitieu.model.WalletType;
import vn.edu.hutech.quanlychitieu.util.ConversionUtil;
import vn.edu.hutech.quanlychitieu.util.DateUtil;
import vn.edu.hutech.quanlychitieu.util.SQLiteUtil;

public class TransactionFragment extends Fragment implements View.OnClickListener, TransactionListViewAdapter.OnTransactionItemClickListener {

    public static final int ADD_TRANS_RESULT_CODE = 1;

    private Date currentDate;

    private TextView tvTotalMoney, tvPreviousDate, tvNextPage, tvCurrentPage, tvNoTransaction;

    SQLiteUtil sqLiteUtil;

    ArrayList<Object> data;

    RecyclerView rvTransaction;

    TransactionListViewAdapter adapter;

    SwipeRefreshLayout swipeRefreshLayout;

    FloatingActionButton fabAddTrans;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transaction, container, false);

         rvTransaction = rootView.findViewById(R.id.rvTransaction);

        rootView.findViewById(R.id.transaction_next_page).setOnClickListener(this);
        rootView.findViewById(R.id.transaction_prev_page).setOnClickListener(this);

        tvTotalMoney = rootView.findViewById(R.id.tvTotalMoney);
        tvPreviousDate = rootView.findViewById(R.id.tvPreviousPage);
        tvNextPage = rootView.findViewById(R.id.tvNextPage);
        tvCurrentPage = rootView.findViewById(R.id.tvCurrentPage);
        tvNoTransaction = rootView.findViewById(R.id.tvNoTransaction);
        swipeRefreshLayout = rootView.findViewById(R.id.srlTransaction);
        fabAddTrans = rootView.findViewById(R.id.btnAddTransaction);

        sqLiteUtil = new SQLiteUtil(getActivity());

        currentDate = Calendar.getInstance().getTime();

        data = new ArrayList<>();

        adapter = new TransactionListViewAdapter(getActivity(), data);
        rvTransaction.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTransaction.setAdapter(adapter);

        adapter.setOnItemClickedListener(this);

        fabAddTrans.setOnClickListener(this);

        fetchAndFillData();
        updatePagesTitle();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchAndFillData();
            }
        });
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.transaction_next_page:
                btnNextPageClick();
                break;
            case R.id.transaction_prev_page:
                btnPrevPageClick();
                break;
            case R.id.btnAddTransaction:
                Intent i = new Intent(getActivity(), AddTransaction.class);
                startActivityForResult(i, ADD_TRANS_RESULT_CODE);
                break;
        }
    }

    private void btnNextPageClick() {

        int currentMonth = DateUtil.getMonth(currentDate);
        int currentYear = DateUtil.getYear(currentDate);

        int nowInMonth = DateUtil.getMonth(Calendar.getInstance().getTime());
        int nowInYear = DateUtil.getYear(Calendar.getInstance().getTime());

        if (nowInYear > currentYear || (nowInYear == currentYear && nowInMonth > currentMonth)) {
            currentDate = DateUtil.getNextMonth(currentDate);
            updatePagesTitle();
            fetchAndFillData();
        }
    }

    private void btnPrevPageClick() {
        currentDate = DateUtil.getPrevMonth(currentDate);
        updatePagesTitle();
        fetchAndFillData();
    }

    private void updatePagesTitle() {
        Date nextMonth = DateUtil.getNextMonth(currentDate);
        Date prevMonth = DateUtil.getPrevMonth(currentDate);

        String nextMonthText = DateUtil.formatDateBaseOnMonth(nextMonth);
        String prevMonthText = DateUtil.formatDateBaseOnMonth(prevMonth);
        String currentMonthText = DateUtil.formatDateBaseOnMonth(currentDate);

        tvPreviousDate.setText(prevMonthText);
        tvNextPage.setText(nextMonthText);
        tvCurrentPage.setText(currentMonthText);
    }

    private void fetchAndFillData() {
        swipeRefreshLayout.setRefreshing(false);
        Date firstDay = DateUtil.getFirstDayOfThisMonth(currentDate);
        Date lastDay = DateUtil.getLastDayOfThisMonth(currentDate);
        List<Transaction> transactions = sqLiteUtil.getTransactionInRange(firstDay, lastDay);

        long firstDayMoney = sqLiteUtil.getMoneyAmountInSpecificDay(firstDay, WalletType.BOTH);
        long lastDayMoney = sqLiteUtil.getMoneyAmountInSpecificDay(ConversionUtil.timestampToDate(DateUtil.getEndDayTime(DateUtil.getSmallerDate(lastDay, Calendar.getInstance().getTime()))), WalletType.BOTH);

        long currentMoney = sqLiteUtil.getCurrentMoney(WalletType.BOTH);

        data.clear();

        data.add(new TransactionStatistic(firstDayMoney, lastDayMoney));

        if (transactions.size() != 0) {
            rvTransaction.setVisibility(View.VISIBLE);
            tvNoTransaction.setVisibility(View.GONE);
            int currentDay = -1;
            for (Transaction transaction : transactions) {
                if (DateUtil.getDayOfYear(transaction.getDate()) != currentDay) {
                    currentDay = DateUtil.getDayOfYear(transaction.getDate());
                    data.add(new TransactionDate(transaction.getDate(), sqLiteUtil.getMoneyInADay(transaction.getDate(), WalletType.BOTH)));
                }
                data.add(transaction);
            }
            adapter.notifyDataSetChanged();
        } else {
            rvTransaction.setVisibility(View.GONE);
            tvNoTransaction.setVisibility(View.VISIBLE);
        }
        tvTotalMoney.setText(ConversionUtil.doubleToString(currentMoney));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ADD_TRANS_RESULT_CODE && resultCode == Activity.RESULT_OK) {
            assert data != null;
            Transaction transaction = (Transaction) Objects.requireNonNull(data.getExtras()).getSerializable("result");
            assert transaction != null;
            sqLiteUtil.insertTransaction(transaction);
            fetchAndFillData();
        }
    }

    @Override
    public void onTransactionItemClick(int transactionId) {
        Intent intent = new Intent(getActivity(), TransactionDetail.class);
        intent.putExtra("id", transactionId);
        startActivity(intent);
    }
}
