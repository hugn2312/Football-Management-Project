package com.example.doanmh.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doanmh.R;
//import com.example.doanmh.ui.DetailActivity;
import com.example.doanmh.model.Team;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

    public class TeamAdapter extends FirebaseRecyclerAdapter<Team,TeamAdapter.TeamViewHolder> implements Filterable {
    private List<Team> lstTeam;
    private List<Team> lstTeamOld;
    public IClickItemTeamList iClickItemTeamList;


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public TeamAdapter(@NonNull FirebaseRecyclerOptions<Team> options,IClickItemTeamList listener) {
        super(options);
        this.iClickItemTeamList= listener;
        this.lstTeam = lstTeamOld;
        this.lstTeamOld = lstTeam;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    lstTeam = lstTeamOld;
                }else {
                    List<Team> list = new ArrayList<>();
                    for (Team team : lstTeamOld){
                        if (team.getName().toLowerCase().contains(strSearch.toLowerCase()));{
                            list.add(team);
                        }
                    }
                    lstTeam = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = lstTeam;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                lstTeam = (List<Team>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface IClickItemTeamList{
        void onClickItemTeam(Team team);
    }

    @Override
    protected void onBindViewHolder(@NonNull TeamAdapter.TeamViewHolder holder, int position, @NonNull Team team) {

        holder.name.setText(team.getName());
        Glide.with(holder.imageView.getContext())
                .load(team.getSurl())
                .placeholder(R.mipmap.ic_launcher_round)
                .centerCrop()
                .error(R.mipmap.ic_launcher_round)
                .into(holder.imageView);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemTeamList.onClickItemTeam(team);
            }
        });
    }



    @NonNull
    @Override
    public TeamAdapter.TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teams,parent,false);
        return new TeamViewHolder(view);
    }


    public class TeamViewHolder extends RecyclerView.ViewHolder {
        CardView layout;
        CircleImageView imageView;
        TextView name;
        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout_item_team);
            imageView = itemView.findViewById(R.id.imgClub);
            name = itemView.findViewById(R.id.clubName);
        }
    }
}
