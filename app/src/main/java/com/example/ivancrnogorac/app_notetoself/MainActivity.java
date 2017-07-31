package com.example.ivancrnogorac.app_notetoself;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    //Temp code
    Note mTempNote = new Note();

    public void createNewNote (Note n){
        //Temp code
        mTempNote = n;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Temp code
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create new DialogShowNote called dialog
                DialogShowNote dialog = new DialogShowNote();
                //Send the note via sendNoteSelected
                dialog.sendNoteSelected(mTempNote);
                //create the dialog
                dialog.show(getSupportFragmentManager(),"123");
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

        return super.onOptionsItemSelected(item);
    }
}
