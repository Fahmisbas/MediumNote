package com.fahmisbas.mediumnote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    public static ArrayList<Notes> userNotes;
    private OnItemClickCallback onItemClickCallback;
    private OnItemLongClickCallback onItemLongClickCallback;
    private Context context;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setOnItemLongClickCallback(OnItemLongClickCallback onItemLongClickCallback) {
        this.onItemLongClickCallback = onItemLongClickCallback;
    }

    NotesAdapter(Context context, ArrayList<Notes> userNotes){
        this.context = context;
        this.userNotes = userNotes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notes,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Notes notes = userNotes.get(position);

        holder.tvTitle.setText(notes.getTitle());
        holder.tvNotes.setText(notes.getNotes());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClick(position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemLongClickCallback.onItemLongClick(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return userNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle,tvNotes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvNotes = itemView.findViewById(R.id.tv_notes);
        }
    }

    public interface OnItemClickCallback{
        void onItemClick(int position);
    }
    public interface OnItemLongClickCallback{
        void onItemLongClick(int position);
    }
}
