package com.example.ivancrnogorac.app_notetoself;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private NoteAdapter mNoteAdapter;
    private boolean mSound;
    private int mAnimOption;
    private SharedPreferences mPrefs;

    public void createNewNote (Note n){
        mNoteAdapter.addNote(n);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNoteAdapter = new NoteAdapter();
        ListView listNote = (ListView) findViewById(R.id.listView);
        listNote.setAdapter(mNoteAdapter);

        //Handle clicks on the ListView
        listNote.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int whichItem, long id) {
                //Create temp note which is a reference to the Note that just been clicked
                Note tempNote = mNoteAdapter.getItem(whichItem);
                //Create mew dialog window
                DialogShowNote dialog = new DialogShowNote();
                // Send in a reference to the note to be shown
                dialog.sendNoteSelected(tempNote);
                //Show dialog
                dialog.show(getSupportFragmentManager(),"");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_add){
            DialogNewNote dialog = new DialogNewNote();
            dialog.show(getSupportFragmentManager(),"");
        }
        if(id == R.id.action_settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPrefs = getSharedPreferences("Note to self", MODE_PRIVATE);
        mSound = mPrefs.getBoolean("sound", true);
        mAnimOption = mPrefs.getInt("anim option", SettingsActivity.FAST);
    }

    //----------------------------------------------NOTE ADAPTER -------------------------------------------
    public class NoteAdapter extends BaseAdapter{
        List<Note> noteList = new ArrayList<Note>();

        @Override
        public int getCount() {
            return noteList.size();
        }

        @Override
        public Note getItem(int whichItem) {
            return noteList.get(whichItem);
        }

        @Override
        public long getItemId(int whichItem) {
            return whichItem;
        }

        @Override
        public View getView(int whichItem, View view, ViewGroup viewGroup) {

            //Has view been inflated already
            if (view == null) {
                //if not, do so here
                //First create a LayoutInflater
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //now instantiate view using Inflater.inflate using the list item layout
                view = inflater.inflate(R.layout.listitem, viewGroup,false);

                //grab references to all views
                TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
                TextView txtDescription = (TextView) view.findViewById(R.id.txtDescritpion);
                ImageView ivImportant = (ImageView) view.findViewById(R.id.imageViewImportant);
                ImageView ivToDo = (ImageView) view.findViewById(R.id.imageViewToDo);
                ImageView ivIdea = (ImageView) view.findViewById(R.id.imageViewIdea);

                //Hide any imageView that are not relevant;
                Note tempNote = noteList.get(whichItem);
                if (!tempNote.isImportant()) {
                    ivImportant.setVisibility(View.GONE);
                }
                if (!tempNote.isTodo()){
                    ivToDo.setVisibility(View.GONE);
                }
                if (!tempNote.isIdea()) {
                    ivIdea.setVisibility(View.GONE);
                }
                //set title and desc.
                txtTitle.setText(tempNote.getTitle());
                txtDescription.setText(tempNote.getDescription());
            }

            return view;
        }
        public void addNote (Note n){
            noteList.add(n);
            notifyDataSetChanged();
        }
    }
}
