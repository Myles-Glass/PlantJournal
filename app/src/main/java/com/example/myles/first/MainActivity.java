package com.example.myles.first;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button createBtn;
    private static final String TAG = "MainActivity";
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;

    private ArrayList<String> plantNames = new ArrayList<>();
    private PlantList plantList = new PlantList();
    private Plant newPlant = new Plant();
    private Plant clickedPlant = new Plant();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //First file read
        plantList = readFromFile(getApplicationContext());

        //First call to populate recyclerview list
        initPlantList();

        //Button for creating a plant
        createBtn = (Button) findViewById(R.id.create_plant_button);
        createBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), CreatePlant.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    /*
     * Initiates the plantNames arraylist. Rebuilds it every time this is called.
     */
    private void initPlantList()
    {
        //Log for size of plantList, plant objects
        Log.d(TAG, "plantList Size: " + plantList.getSize());

        //Resets name arraylist each time initPlantList is called.
        plantNames = new ArrayList<String>();

        //Loop that adds plant names to plantNames arraylist.
        for(int i = 0; i < plantList.getSize();i++)
        {
            //This if statement checks each plant for deletion status and removes it from arraylist
            // if it was marked.
            if (plantList.getPlant(i).getMarkForDeletion() == true)
            {
                plantList.removePlant(plantList.getPlant(i));
                Log.d(TAG, "-------------------plant removed");
            }
            else
            {
                plantNames.add(plantList.getPlant(i).getName());
            }


        }

        //Calls the RecyclerView method. Must be called or else none of the recyclerview will work.
        initRecyclerView();
    }

    /*
     * Initiates the Recycler View and adds plant names to it
     */
    private void initRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(plantList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //clickedPlant = adapter.getClickedPlant();
    }

    /*
     * This method is called when the Create Plant activity finishes
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the CreatePlant with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                //Adds the plant object from CreatePlant activity to
                // the plantList ArrayList
                newPlant = (Plant) data.getSerializableExtra("New Plant");
                plantList.addPlant(newPlant);
            }
        }
    }

    /*
     * Method that is called when the activity is resumed.
     * It calls the initPlantList method to update the plantName arraylist.
     */
    @Override
    protected void onResume()
    {
        super.onResume();

        writeToFile(plantList, getApplicationContext());
        //Bundle bundle = this.getIntent().getExtras();
        clickedPlant = (Plant) getIntent().getSerializableExtra("Clicked Plant");
        initPlantList();
    }

    private void writeToFile(PlantList data,Context context) {
        try {
            ObjectOutputStream outputStreamWriter = new ObjectOutputStream (context.openFileOutput("plantlist.txt", Context.MODE_PRIVATE));
            outputStreamWriter.writeObject(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }



    private PlantList readFromFile(Context context) {

        PlantList plantListRead = new PlantList();

        try {
            FileInputStream fis = context.openFileInput("plantlist.txt");
            ObjectInputStream inputStream = new ObjectInputStream(fis);

            String pathLog = MainActivity.this.getFilesDir().getAbsolutePath();
            Log.d(TAG, "FILE DIRECTORY: " + pathLog);

            if ( inputStream != null ) {
                plantListRead = (PlantList) inputStream.readObject();
                inputStream.close();
            }
        }
        catch (FileNotFoundException e)
        {
            Log.e("login activity", "File not found: " + e.toString());
        }
        catch (IOException e)
        {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        catch (ClassNotFoundException e)
        {
            Log.e("login activity", "Class not found PlantList: " + e.toString());
        }

        return plantListRead;
    }
}
