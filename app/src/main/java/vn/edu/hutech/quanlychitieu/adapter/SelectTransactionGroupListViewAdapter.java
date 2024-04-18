package vn.edu.hutech.quanlychitieu.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hutech.quanlychitieu.R;
import vn.edu.hutech.quanlychitieu.model.TransactionGroup;
import vn.edu.hutech.quanlychitieu.util.GroupIconUtil;

public class SelectTransactionGroupListViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<TransactionGroup> data = new ArrayList<>();

    public SelectTransactionGroupListViewAdapter(Context mContext, @NonNull List<TransactionGroup> data) {
        this.mContext = mContext;
        this.data = data;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.row_transaction_group, parent, false);
        return new TransactionGroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        final TransactionGroupViewHolder vholder = (TransactionGroupViewHolder) viewHolder;
        vholder.setTransactionGroup(data.get(position));

        vholder.transactionGroupWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickedListener != null) {
                    onItemClickedListener.onItemClick(vholder.tvName.getText().toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TransactionGroupViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvName;
        LinearLayout transactionGroupWrapper;
        public TransactionGroupViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivTransactionGroupIcon);
            tvName = itemView.findViewById(R.id.tvTransactionGroupName);
            transactionGroupWrapper =itemView.findViewById(R.id.transactionGroupWrapper);
        }

        void setTransactionGroup(TransactionGroup transactionGroup) {
            ivIcon.setImageResource(GroupIconUtil.getGroupIcon(transactionGroup.getName()));
            tvName.setText(transactionGroup.getName());
        }
    }

    public interface OnItemClickedListener {
        void onItemClick(String groupName);
    }

    private OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
