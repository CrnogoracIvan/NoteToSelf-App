package com.example.ivancrnogorac.app_notetoself;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Ivan Crnogorac on 7/31/2017.
 */

public class DialogShowNote extends DialogFragment {

    private Note mNote;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_show_note, null);

        TextView txtTitle = (TextView) dialogView.findViewById(R.id.txtTitle);
        TextView txtDescription = (TextView) dialogView.findViewById(R.id.txtDescritpion);
        ImageView ivImportant = (ImageView) dialogView.findViewById(R.id.imageImportant);
        ImageView ivTodo = (ImageView) dialogView.findViewById(R.id.imageViewToDo);
        ImageView ivIdea = (ImageView) dialogView.findViewById(R.id.imageViewIdea);
        Button btnOK = (Button) dialogView.findViewById(R.id.btnOk);

        //Setting title and desc
        txtTitle.setText(mNote.getTitle());
        txtDescription.setText(mNote.getDescription());

        //Setting icons
        if(!mNote.isImportant()){
            ivImportant.setVisibility(View.GONE);
        }
        if(!mNote.isTodo()){
            ivTodo.setVisibility(View.GONE);
        }
        if (!mNote.isIdea()){
            ivIdea.setVisibility(View.GONE);
        }

        //Setting message on screen
        builder.setView(dialogView).setMessage("Your note");

        //Setting ok button
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return  builder.create();
    }

    //Receive a note from the MainActivity
    public void sendNoteSelected (Note noteSelected){
        mNote = noteSelected;
    }
}
