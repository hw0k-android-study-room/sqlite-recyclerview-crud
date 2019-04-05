package kr.hs.dgsw.databaseapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryItemViewHolder> {

    private List<UserBean> userList;
    private ItemClickListener listener;

    public HistoryAdapter(List<UserBean> userList, ItemClickListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HistoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HistoryItemViewHolder(
                LayoutInflater
                        .from(viewGroup.getContext())
                        .inflate(R.layout.item_history, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryItemViewHolder viewHolder, int i) {
        UserBean user = userList.get(i);
        viewHolder.textViewName.setText(user.getName());
        viewHolder.textViewSequence.setText(String.valueOf(user.getSequenceNumber()));

        final int index = i;
        viewHolder.itemView.setOnClickListener(view -> listener.onItemClick(view, index));
    }

    @Override
    public int getItemCount() {
        if (userList == null) {
            return 0;
        }
        return userList.size();
    }
}
