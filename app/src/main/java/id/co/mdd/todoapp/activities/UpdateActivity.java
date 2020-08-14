package id.co.mdd.todoapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

import id.co.mdd.todoapp.R;

public class UpdateActivity extends AppCompatActivity {

    final public static int code = 320;
    TextInputEditText etData;
    SwitchMaterial swStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        etData = findViewById(R.id.etData);
        swStatus = findViewById(R.id.swStatus);
    }
}