package com.example.siedler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GamesRecyclerAdapter extends RecyclerView.Adapter<GamesRecyclerAdapter.ItemViewHolder> {
    private Context context;
    private List<GamesRecyclerItem> gamesRecyclerItemList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    public GamesRecyclerAdapter(Context context, List<GamesRecyclerItem> gamesRecyclerItemList){
        this.context = context;
        this.gamesRecyclerItemList = gamesRecyclerItemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item_games, parent, false), onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position){
        holder.itemId.setText(gamesRecyclerItemList.get(position).getId());
        holder.itemWinner.setText(getWinner(gamesRecyclerItemList.get(position).getPlayers()));
        holder.itemDate.setText(getDate(gamesRecyclerItemList.get(position).getDate()));
    }

    public String getWinner(String players){
        String[] _players = players.split(context.getString(R.string.delimiter_players));
        int winner_pos = 0;
        int winner_points = 0;
        for(int i=1; i<_players.length; i+=2){
            if(Integer.parseInt(_players[i]) > winner_points){
                winner_pos = i-1;
                winner_points = Integer.parseInt(_players[i]);
            }
        }
        return _players[winner_pos];
    }

    public String getDate(String date){
        String[] _date = date.split(context.getString(R.string.delimiter_date));
        return _date[2]+"."+_date[1]+"."+_date[0]+" "+_date[3]+":"+_date[4];
    }

    @Override
    public int getItemCount(){
        return gamesRecyclerItemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView itemId;
        TextView itemWinner;
        TextView itemDate;

        public ItemViewHolder(@NonNull View itemView, final OnItemClickListener clickListener){
            super(itemView);

            itemId = itemView.findViewById(R.id.item_id);
            itemWinner = itemView.findViewById(R.id.item_winner);
            itemDate = itemView.findViewById(R.id.item_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            clickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
