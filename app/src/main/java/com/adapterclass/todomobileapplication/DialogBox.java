package com.adapterclass.todomobileapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import com.adapterclass.todomobileapplication.databinding.ActivityDialogBoxBinding;

public class DialogBox extends MainActivity {
    ActivityDialogBoxBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDialogBoxBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



//        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                adapter.insertData(DialogBox.this, binding.editTitle.getText().toString(), binding.editMessage.getText().toString());
//                dialog.dismiss();
//                loadDataInListview();
//            }
//        });

    }
}