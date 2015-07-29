package com.contact.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simplecontacts.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.VH> {
    private final OnContactClickListener listener;

    public interface OnContactClickListener {
        void onContactClick(View view, int position);
    }

    public ContactAdapter(OnContactClickListener listener) {
        this.listener = listener;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new VH(itemView);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.tvName.setText("test");
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class VH extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_name) TextView tvName;

        public VH(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onContactClick(v, 1);
                }
            });
        }
    }
}
