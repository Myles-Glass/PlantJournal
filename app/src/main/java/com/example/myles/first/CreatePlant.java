package com.example.myles.first;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class CreatePlant extends Activity {

    private Button confirmPlantBtn;
    private Plant newPlant = new Plant();
    private String nameField;
    private String lightField;
    private String waterField;
    private String soilField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //Default
        setContentView(R.layout.activity_create_plant); //Default

        //The popup
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        //Change size by changing the decimals
        getWindow().setLayout((int)(width * 0.7), (int)(height * 0.8));

        //Positions the popup
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20; //Raises it by 20
        getWindow().setAttributes(params);

        //TextView textview= (TextView) findViewById(R.id.editText);
        //textview.setMovementMethod(new ScrollingMovementMethod());


        //Button for creating a plant
        confirmPlantBtn = (Button) findViewById(R.id.confirm_plant_button);
        confirmPlantBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {

                //Name Field
                //Sets the text from editNameText to the name of the plant.
                EditText edText1 = (EditText) findViewById(R.id.nameCreate_EditText);
                nameField = edText1.getText().toString();
                newPlant.setName(nameField);

                //Light Field
                //Sets the text from editNameText to the name of the plant.
                EditText edText2 = (EditText) findViewById(R.id.lightCreate_EditText);
                lightField = edText2.getText().toString();
                newPlant.setLight(lightField);

                //Water Field
                //Sets the text from editNameText to the name of the plant.
                EditText edText3 = (EditText) findViewById(R.id.waterCreate_EditText);
                waterField = edText3.getText().toString();
                newPlant.setWater(waterField);

                //Soil Field
                //Sets the text from editNameText to the name of the plant.
                EditText edText4 = (EditText) findViewById(R.id.soilCreate_EditText);
                soilField = edText4.getText().toString();
                newPlant.setSoil(soilField);

                //Sends the data back to the main activity.
                Intent intent = new Intent();
                intent.putExtra("New Plant", newPlant);
                setResult(RESULT_OK, intent);

                finish();
            }
        });
    }
}
