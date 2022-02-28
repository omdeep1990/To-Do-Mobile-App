package com.adapterclass.todomobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.adapterclass.todomobileapplication.databinding.ActivityDialogBoxBinding;
import com.adapterclass.todomobileapplication.databinding.ActivityMainBinding;
import com.google.type.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    private ActivityMainBinding binding;
    ActivityDialogBoxBinding profileBinding, profileBindingUpdate;
    public DatabaseAdapter adapter;
    private Cursor cursor;
    Dialog dialog;
    private int clickedPosition;
    private boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        adapter = new DatabaseAdapter(this);
        adapter.openDatabase();

        loadDataInListview();

        binding.listView.setOnItemLongClickListener(this);
        registerForContextMenu(binding.listView);




    }

    public void loadDataInListview() {
        CustomListAdapter adapter = new CustomListAdapter(getAvengersList(), this);
        binding.listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_create_profile:
                profileBinding = ActivityDialogBoxBinding.inflate(getLayoutInflater());
                Dialog dialog = new Dialog(this);
                dialog.setContentView(profileBinding.getRoot());
                dialog.setCancelable(false);
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                profileBinding.buttonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        adapter.insertData(MainActivity.this, profileBinding.editTitle.getText().toString(),
                                profileBinding.editMessage.getText().toString(),getDateTime());
                        dialog.dismiss();
                        loadDataInListview();
                    }
                });
                profileBinding.buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.item_delete_all_profile:
                adapter.deleteAllRecords(MainActivity.this);
                loadDataInListview();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<AvengersData> getAvengersList() {
        List<AvengersData> avengersDataList = new ArrayList<>();
        cursor = adapter.getAllData();
        if (cursor.getCount() > 0) {
            Log.d("DATA", "" + cursor);
            cursor.moveToFirst();
            do {
                AvengersData avengersData = new AvengersData();

                avengersData.setRowId(cursor.getString(0));
                avengersData.setTitle(cursor.getString(1));
                avengersData.setMessage(cursor.getString(2));
                avengersData.setDateTime(cursor.getString(3));
                avengersDataList.add(avengersData);
            } while (cursor.moveToNext());
        }
        return avengersDataList;
    }


    public void btn_showDialog(View view) {
        profileBinding = ActivityDialogBoxBinding.inflate(getLayoutInflater());
        Dialog dialog = new Dialog(this);
        dialog.setContentView(profileBinding.getRoot());
        dialog.setCancelable(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        profileBinding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profileBinding.editTitle==null) {
                    Toast.makeText(getApplicationContext(), "Please enter your title", Toast.LENGTH_SHORT);
                    return;
                }if(profileBinding.editMessage==null) {
                    Toast.makeText(getApplicationContext(), "Please enter your message", Toast.LENGTH_SHORT);
                    return;
                }else {
                    adapter.insertData(MainActivity.this, profileBinding.editTitle.getText().toString(), profileBinding.editMessage.getText().toString(), getDateTime());
                    dialog.dismiss();
                    loadDataInListview();
                }

            }
        });

        profileBinding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.profile_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_delete_record:
                cursor.moveToPosition(clickedPosition);
                String rowId = cursor.getString(0);
                adapter.deleteSingleRecord(MainActivity.this, rowId);

                loadDataInListview();
                break;
            case R.id.item_update_record:
                isUpdate = true;
                cursor.moveToPosition(clickedPosition);

                profileBindingUpdate = ActivityDialogBoxBinding.inflate(getLayoutInflater());
                Dialog dialog = new Dialog(this);
                dialog.setContentView(profileBindingUpdate.getRoot());
                profileBindingUpdate.buttonSave.setText("Update");
                dialog.setCancelable(false);
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                profileBindingUpdate.editTitle.setText(cursor.getString(1));
                profileBindingUpdate.editMessage.setText(cursor.getString(2));


                profileBindingUpdate.buttonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        adapter.updateRecord(MainActivity.this, profileBindingUpdate.editTitle.getText().toString(),
                                profileBindingUpdate.editMessage.getText().toString(),
                                getDateTime(), cursor.getString(0));
                        dialog.dismiss();

                        loadDataInListview();
                    }
                });





                profileBinding.buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        clickedPosition = position;
        return false;
    }

    public  String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}