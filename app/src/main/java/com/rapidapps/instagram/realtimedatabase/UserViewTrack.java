package com.rapidapps.instagram.realtimedatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserViewTrack extends AppCompatActivity {
    TextView textViewProfile;
    ListView listViewTrack;
    DatabaseReference databaseTracks;
    List<Track> tracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_track);
        textViewProfile=(TextView)findViewById(R.id.textViewProfile);
        listViewTrack=(ListView)findViewById(R.id.listViewTracks);
        Intent intent=getIntent();
        tracks=new ArrayList<>();
        String id=intent.getStringExtra(MainActivity.ARTIST_ID);
        String name=intent.getStringExtra(MainActivity.ARTIST_NAME);
        databaseTracks= FirebaseDatabase.getInstance().getReference("tracks").child(id);

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseTracks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tracks.clear();
                for(DataSnapshot trackSnapshort:dataSnapshot.getChildren())
                {
                    Track track=trackSnapshort.getValue(Track.class);
                    tracks.add(track);
                }
                TrackList trackListAdapter = new TrackList(UserViewTrack.this,tracks);
                listViewTrack.setAdapter(trackListAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }





}
