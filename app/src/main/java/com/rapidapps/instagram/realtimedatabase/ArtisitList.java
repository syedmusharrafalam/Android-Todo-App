package com.rapidapps.instagram.realtimedatabase;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by User on 1/30/2018.
 */

public class ArtisitList extends ArrayAdapter<Artist> {
    private Activity context;
    private List<Artist> artistList;
    public ArtisitList(Activity context,List<Artist>artistList)
    {
        super(context,R.layout.list_layout,artistList);
        this.context=context;
        this.artistList=artistList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.list_layout,null,true);
        TextView textViewName=(TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewGenre=(TextView) listViewItem.findViewById(R.id.textViewGenre);
        Artist artist=artistList.get(position);
        textViewName.setText(artist.getArtistName());
        textViewGenre.setText(artist.getArtisGenre());
        return listViewItem;
    }
}
