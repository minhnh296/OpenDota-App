package vn.edu.usth.opendota.adapters;

import static android.content.ContentValues.TAG;

import static vn.edu.usth.opendota.utils.HeroesDb.getHeroNameById;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.edu.usth.opendota.R;
import vn.edu.usth.opendota.models.Matches;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {

    private List<Matches> matches = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void submit(List<Matches> newList) {
        matches = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_matches_frame, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesAdapter.ViewHolder holder, int position) {
        Matches item = matches.get(position);

        Log.d(TAG, "Match Details: " + item.toString());

        holder.tvTitle.setText("All Draft Normal");

        String kda = item.getKills() + "/" + item.getDeaths() + "/" + item.getAssists();
        holder.tvKda.setText(kda);
        Log.d(TAG, "kda: " + kda);

        Date date = new Date(item.getStartTime() * 1000);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = sdf.format(date);
        holder.tvTimeEnded.setText(formattedDate);

        int minutes = (int) (item.getDuration() / 60);
        int seconds = (int) (item.getDuration() % 60);
        @SuppressLint("DefaultLocale") String formattedTime = String.format("%02d:%02d", minutes, seconds);
        holder.idDuration.setText(formattedTime);

        int heroId = (int) item.getHeroID();
        String heroName = getHeroNameById(heroId);
        String imageUrl = "https://cdn.dota2.com/apps/dota2/images/heroes/"+heroName+"_full.png";
        Picasso.get().load(imageUrl).into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        private final TextView tvKda;
        private final TextView tvTimeEnded;
        private final TextView idDuration;
        private final ImageView avatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvKda = itemView.findViewById(R.id.tv_kda);
            tvTimeEnded = itemView.findViewById(R.id.tv_time_ended);
            idDuration = itemView.findViewById(R.id.id_duration);
            avatar = itemView.findViewById(R.id.avatar);
        }
    }
}