package net.firekesti.soundboard;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import de.greenrobot.event.EventBus;

/**
 * An adapter for Sounds
 */
public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.ViewHolder> {
    private Sound[] sounds;

    public SoundAdapter(Sound[] soundArray) {
        sounds = soundArray;
    }

    @Override
    public SoundAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sound_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(sounds[position].getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(sounds[position]);
            }
        });

        boolean isFavorite = sounds[position].getFavorite();
        holder.favButton.setImageResource(isFavorite ? R.drawable.ic_favorite_white_24dp : R.drawable
                .ic_favorite_outline_white_24dp);
        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean newFavStatus = !sounds[position].getFavorite();
                sounds[position].setFavorite(newFavStatus);
                ((ImageButton) v).setImageResource(newFavStatus ? R.drawable.ic_favorite_white_24dp : R.drawable
                        .ic_favorite_outline_white_24dp);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sounds.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageButton favButton;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            favButton = (ImageButton) v.findViewById(R.id.fav_button);
        }
    }
}
