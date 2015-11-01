package net.firekesti.soundboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * An adapter for Sounds
 */
public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.ViewHolder> {
    private ArrayList<Sound> sounds;

    public SoundAdapter(ArrayList<Sound> soundArray) {
        sounds = soundArray;
    }

    public void onlyShowFavorites() {
        for (Sound sound : new ArrayList<>(sounds)) {
          if (!sound.getFavorite()) {
              notifyItemRemoved(sounds.indexOf(sound));
              sounds.remove(sound);
          }
        }
    }

    public void showAllSounds(Context context) {
        sounds = SoundStore.getAllSounds(context);
        notifyDataSetChanged();
    }

    @Override
    public SoundAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sound_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText(sounds.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(sounds.get(holder.getAdapterPosition()));
            }
        });

        boolean isFavorite = sounds.get(position).getFavorite();
        holder.favButton.setImageResource(isFavorite ? R.drawable.ic_favorite_white_24dp : R.drawable
                .ic_favorite_outline_white_24dp);
        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean newFavStatus = !sounds.get(position).getFavorite();
                sounds.get(position).setFavorite(newFavStatus);
                if (newFavStatus) {
                    ((ImageButton) v).setImageResource(R.drawable.ic_favorite_white_24dp);
                    v.setContentDescription(v.getContext().getString(R.string.fav_desc));
                } else {
                    ((ImageButton) v).setImageResource(R.drawable.ic_favorite_outline_white_24dp);
                    v.setContentDescription(v.getContext().getString(R.string.not_fav_desc));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sounds.size();
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
