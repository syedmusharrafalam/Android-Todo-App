package com.rapidapps.instagram.realtimedatabase;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String ARTIST_NAME="artistName";
    public static final String ARTIST_ID="artistId";
EditText editTextName;
    Button buttonAdd;
    Spinner spinnerGenres;
    FirebaseAuth mAuth;
    DatabaseReference databaseArtists;
    ListView listViewArtist;
    List<Artist> artists;
    TextView textView,textViewUpdate;
    String uid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView=(TextView)findViewById(R.id.textViewUser);
        databaseArtists= FirebaseDatabase.getInstance().getReference("artist");




        editTextName= (EditText) findViewById(R.id.editTextName);
        buttonAdd= (Button) findViewById(R.id.buttonAddArtisit);
        spinnerGenres= (Spinner) findViewById(R.id.spinnerGenres);
        listViewArtist=(ListView)findViewById(R.id.listViewArtist);
        artists=new ArrayList<>();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addArtist();
                editTextName.setText("");
            }
        });



       listViewArtist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Artist artist=artists.get(i);
                Intent intent = new Intent(getApplicationContext(),AddTrackActivity.class);
                intent.putExtra(ARTIST_ID,artist.getArtistId());
                intent.putExtra(ARTIST_NAME,artist.getArtistName());
                startActivity(intent);
            }
        });

        listViewArtist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Artist artist=artists.get(i);
                showUpdateDialog(artist.getArtistId(),artist.getArtistName());
                return false;
            }
        });

      Toolbar toolbar= (Toolbar) findViewById(R.id.toolbarView);
        //setSupportActionBar(toolbar);*

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
                ArtisitList adapter=new ArtisitList(MainActivity.this,artists);
                listViewArtist.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void showUpdateDialog(final String artistId, String artistName)
    {
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView =inflater.inflate(R.layout.update_dialog,null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName =(EditText) dialogView.findViewById(R.id.editTextName);
        final Button buttonUpdate=(Button) dialogView.findViewById(R.id.buttonUpdate);
        final Spinner spinnerGenres=(Spinner)dialogView.findViewById(R.id.spinnerGenres);
        final Button buttonDelete=(Button) dialogView.findViewById(R.id.buttonDelete);
        dialogBuilder.setTitle("Updating Artist"+artistName);
        final AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=editTextName.getText().toString().trim();
                String genre=spinnerGenres.getSelectedItem().toString();
                if(TextUtils.isEmpty(name))
                {
                    editTextName.setError("Name Required");
                }
                    updateArtist(artistId,name,genre);
                alertDialog.dismiss();

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteArtist(artistId);
                alertDialog.dismiss();
            }
        });
    }

    private void deleteArtist(String artistId) {
        DatabaseReference drArtist=FirebaseDatabase.getInstance().getReference("artist").child(artistId);
        DatabaseReference drTracks=FirebaseDatabase.getInstance().getReference("tracks").child(artistId);
        drArtist.removeValue();
        drTracks.removeValue();
        Toast.makeText(this,"Artist is deleted",Toast.LENGTH_LONG).show();


    }

    private boolean updateArtist(String id,String name,String genre)
    {
      DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("artist").child(id);
        Artist artist=new Artist(id,name,genre);
        databaseReference.setValue(artist);
        Toast.makeText(this,"Artist Updated successfully",Toast.LENGTH_LONG).show();

        return true;
    }




    private void addArtist() {
        String name=editTextName.getText().toString().trim();
        String genre=spinnerGenres.getSelectedItem().toString();
        if(!TextUtils.isEmpty(name))
        {
          String id=databaseArtists.push().getKey();
            Artist artist= new Artist(id,name,genre);
            databaseArtists.child(id).setValue(artist);
            Toast.makeText(this,"Artist Added",Toast.LENGTH_LONG).show();

        }
        else
        {
            Toast.makeText(this,"you should enter a name",Toast.LENGTH_LONG).show();
        }


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
