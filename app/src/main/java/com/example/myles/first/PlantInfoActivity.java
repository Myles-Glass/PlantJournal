package com.example.myles.first;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class PlantInfoActivity extends AppCompatActivity {

    private Plant infoPlant = new Plant();
    private Button deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_info);

        initWindow();

        fillPlantInfo();

        deleteBtn = (Button) findViewById(R.id.delete_button);
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {

                infoPlant.setMarkForDeletion(true);

                //Sends the data back to the main activity.
                Intent intent = new Intent();
                intent.putExtra("Clicked Plant", infoPlant);

                finish();
            }
        });

    }

    private void initWindow()
    {
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
    }

    private void fillPlantInfo()
    {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            infoPlant = (Plant) bundle.getSerializable("Info Plant");
        }

        //The name of the plant
        TextView nameTextView = (TextView)findViewById(R.id.nameInfo_TextView);
        nameTextView.setText(infoPlant.getName());

        //The light of the plant
        TextView lightTextView = (TextView)findViewById(R.id.lightInfo_TextView);
        lightTextView.setText(infoPlant.getLight());

        //The water of the plant
        TextView waterTextView = (TextView)findViewById(R.id.waterInfo_TextView);
        waterTextView.setText(infoPlant.getWater());

        //The soil of the plant
        TextView soilTextView = (TextView)findViewById(R.id.soilInfo_TextView);
        soilTextView.setText(infoPlant.getSoil());
    }
}
