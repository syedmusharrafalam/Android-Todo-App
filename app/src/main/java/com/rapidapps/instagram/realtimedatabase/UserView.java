package com.rapidapps.instagram.realtimedatabase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2/2/2018.
 */

public class UserView extends AppCompatActivity {
    public static final String ARTIST_NAME="artistName";
    public static final String ARTIST_ID="artistId";
    TextView myText;
    ListView lv;
    FirebaseAuth mAuth;
    DatabaseReference databaseArtists;
    List<Artist> artists;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);
        mAuth=FirebaseAuth.getInstance();
        myText=(TextView)findViewById(R.id.myText);
        databaseArtists= FirebaseDatabase.getInstance().getReference("artist");
        lv=(ListView)findViewById(R.id.listViewUser);
        artists=new ArrayList<>();
/*        Toolbar toolbarUser= (Toolbar) findViewById(R.id.toolbarUser);
        setSupportActionBar(toolbarUser);*/
       FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            myText.setText(user.getDisplayName());
        }




        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Artist artist=artists.get(i);
                Intent intent = new Intent(getApplicationContext(),UserViewTrack.class);
                intent.putExtra(ARTIST_ID,artist.getArtistId());
                intent.putExtra(ARTIST_NAME,artist.getArtistName());
                startActivity(intent);
            }
        });




        Toolbar toolbarUser= (Toolbar) findViewById(R.id.toolbarUser);







    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                artists.clear();
                for (DataSnapshot artistSnapShort:dataSnapshot.getChildren())
                {

                    Artist artist =artistSnapShort.getValue(Artist.class);
                    artists.add(artist);
                }
                ArtisitList adapter=new ArtisitList(UserView.this,artists);
                lv.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case  R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this,LogInActivity.class));
                break;
        }


        return true;
    }







    }
