package com.rapidapps.instagram.realtimedatabase;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddTrackActivity extends AppCompatActivity {
TextView textViewArtistName;
    EditText editTextTrackName;
    SeekBar seekBarRating;
    Button buttonAddTrack,buttonUpdate;
    ListView listViewTracks;
    DatabaseReference databaseTracks;
    List<Track> tracks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);
        textViewArtistName= (TextView) findViewById(R.id.textViewArtisitName);
        editTextTrackName=(EditText) findViewById(R.id.editTextTrackName);
        seekBarRating=(SeekBar)findViewById(R.id.seekBarRating);
        listViewTracks=(ListView)findViewById(R.id.listViewTracks);
        buttonAddTrack=(Button)findViewById(R.id.buttonAddTrack);
        buttonUpdate=(Button)findViewById(R.id.buttonUpdate);
        Intent intent=getIntent();
        tracks=new ArrayList<>();


        String id=intent.getStringExtra(MainActivity.ARTIST_ID);
        String name=intent.getStringExtra(MainActivity.ARTIST_NAME);
        textViewArtistName.setText(name);
        databaseTracks= FirebaseDatabase.getInstance().getReference("tracks").child(id);
        buttonAddTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTrack();
                editTextTrackName.setText("");
            }
        });







       /* listViewArtist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                return false;
            }
        });*/


















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
                TrackList trackListAdapter = new TrackList(AddTrackActivity.this,tracks);
                listViewTracks.setAdapter(trackListAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void saveTrack() {
        String trackName=editTextTrackName.getText().toString().trim();
        int rating=seekBarRating.getProgress();
        if(!TextUtils.isEmpty(trackName))
        {
          String id=databaseTracks.push().getKey();
            Track track= new Track(id,trackName,rating);
            databaseTracks.child(id).setValue(track);
            Toast.makeText(this,"Track saved successfully",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"Track Name should name be empty",Toast.LENGTH_LONG).show();
        }


    }








}
