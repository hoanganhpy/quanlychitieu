package vn.edu.hutech.quanlychitieu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hutech.quanlychitieu.R;
import vn.edu.hutech.quanlychitieu.model.Notification;
import vn.edu.hutech.quanlychitieu.util.DateUtil;

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Notification> data = new ArrayList<>();

    public NotificationAdapter(Context mContext, @NonNull List<Notification> data) {
        this.mContext = mContext;
        this.data = data;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.row_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final NotificationViewHolder vholder = (NotificationViewHolder) viewHolder;
        vholder.setNotification(data.get(position));

        vholder.notificationWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickedListener != null) {
                    onItemClickedListener.onItemClick(data.get(position).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent;
        TextView tvDate;
        LinearLayout notificationWrapper;
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvNotificationContent);
            tvDate = itemView.findViewById(R.id.tvPushedDate);
            notificationWrapper = itemView.findViewById(R.id.notificationWrapper);
        }

        void setNotification(Notification notification) {
            tvContent.setText(notification.getContent());
            tvDate.setText(DateUtil.formatDate(notification.getDate()));
        }
    }

    public interface OnItemClickedListener {
        void onItemClick(int notificationId);
    }

    private NotificationAdapter.OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(NotificationAdapter.OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
