package vn.edu.hutech.quanlychitieu.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import vn.edu.hutech.quanlychitieu.R;
import vn.edu.hutech.quanlychitieu.activity.SelectDateRange;
import vn.edu.hutech.quanlychitieu.model.Transaction;
import vn.edu.hutech.quanlychitieu.model.TransactionGroup;
import vn.edu.hutech.quanlychitieu.model.WalletType;
import vn.edu.hutech.quanlychitieu.util.ConversionUtil;
import vn.edu.hutech.quanlychitieu.util.DateUtil;
import vn.edu.hutech.quanlychitieu.util.SQLiteUtil;

public class ReportFragment extends Fragment implements BottomSheetFragment.BottomSheetListener {

    public static final int SELECT_NEW_RANGE_CODE = 1;

    TextView tvStartDate, tvEndDate, tvMoneyInTheBegin, tvMoneyInTheEnd;
    private PieChart pieChart;
    private long monthTotalIncoming;
    private long monthTotalOutGoing;
    final int[] MY_COLORS = {Color.rgb(192,0,0), Color.rgb(255,0,0), Color.rgb(255,192,0),
            Color.rgb(127,127,127), Color.rgb(146,208,80), Color.rgb(0,176,80), Color.rgb(79,129,189)};

    SwipeRefreshLayout swipeRefreshLayout;
    ImageView btnShowModal;
    SQLiteUtil sqLiteUtil;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report, container, false);

        pieChart = rootView.findViewById(R.id.pieChart1);

        swipeRefreshLayout = rootView.findViewById(R.id.srlReporting);

        btnShowModal = rootView.findViewById(R.id.btnShowModal);

        tvStartDate = rootView.findViewById(R.id.tvReportStartDate);
        tvEndDate = rootView.findViewById(R.id.tvReportEndDate);
        tvMoneyInTheBegin = rootView.findViewById(R.id.tvMoneyInTheBegin);
        tvMoneyInTheEnd = rootView.findViewById(R.id.tvMoneyInTheEnd);

        sqLiteUtil = new SQLiteUtil(getActivity());

        btnShowModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                bottomSheetFragment.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        Date now = Calendar.getInstance().getTime();
        Date firstDay = DateUtil.getFirstDayOfThisMonth(now);
        Date lastDay = DateUtil.getLastDayOfThisMonth(now);
        updateData(firstDay, lastDay);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                drawChart();
            }
        });

        return rootView;
    }

    private void drawChart() {
        PieDataSet pieDataSet = new PieDataSet(getData(),"");

        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.animateXY(500, 500);
        pieChart.invalidate();
        pieChart.setDrawEntryLabels(false);
        pieChart.setDrawCenterText(false);

        swipeRefreshLayout.setRefreshing(false);
    }

    private ArrayList<PieEntry> getData(){
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) monthTotalIncoming, "Khoản thu"));
        entries.add(new PieEntry((float) monthTotalOutGoing, "Khoản chi"));
        return entries;
    }

    @Override
    public void onItemClickListener(String position) {
        if (position.equals("1")) {
            Date now = Calendar.getInstance().getTime();
            Date firstDay = DateUtil.getFirstDayOfThisMonth(now);
            Date lastDay = DateUtil.getLastDayOfThisMonth(now);
            updateData(firstDay, lastDay);
        } else if (position.equals("2")) {
            Date now = Calendar.getInstance().getTime();
            Date firstDay = DateUtil.getPrevMonth(now);
            Date lastDay = DateUtil.getLastDayOfThisMonth(firstDay);
            updateData(firstDay, lastDay);
        } else {
            Intent i = new Intent(getActivity(), SelectDateRange.class);
            startActivityForResult(i, SELECT_NEW_RANGE_CODE);
        }
    }

    public void updateData(Date startDay, Date endDay) {

        tvStartDate.setText(ConversionUtil.dateToString(startDay));
        tvStartDate.setText(ConversionUtil.dateToString(endDay));
        tvMoneyInTheBegin.setText(ConversionUtil.doubleToString(sqLiteUtil.getMoneyAmountInSpecificDay(startDay, WalletType.BOTH)));
        tvMoneyInTheEnd.setText(ConversionUtil.doubleToString(sqLiteUtil.getMoneyAmountInSpecificDay(endDay, WalletType.BOTH)));

        List<Transaction> transactions = sqLiteUtil.getTransactionInRange(startDay, endDay);

        monthTotalOutGoing = 0;
        monthTotalIncoming = 0;
        for (Transaction transaction : transactions) {
            if (transaction.getGroup().getType() == TransactionGroup.OUTGOING) {
                monthTotalOutGoing += Math.abs(transaction.getMoneyAmount());
            } else {
                monthTotalIncoming += Math.abs(transaction.getMoneyAmount());
            }
        }

        drawChart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == SELECT_NEW_RANGE_CODE && resultCode == Activity.RESULT_OK) {
            assert data != null;
            Date startDay = (Date) Objects.requireNonNull(data.getExtras()).getSerializable("startDay");
            Date endDay = (Date) Objects.requireNonNull(data.getExtras()).getSerializable("endDay");
            updateData(startDay, endDay);
        }
    }
}
