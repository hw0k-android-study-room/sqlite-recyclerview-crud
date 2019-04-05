package kr.hs.dgsw.databaseapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int[] layoutIds = {R.id.layoutForm, R.id.layoutHome};
    private LinearLayout[] layouts;
    private EditText editTextUserName;
    private TextView textViewUserName;
    private SharedPreferences preferences;

    private HistoryDBHelper dbHelper;

    private Button buttonMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layouts = new LinearLayout[layoutIds.length];
        for (int i = 0; i < layouts.length; i++) {
            layouts[i] = findViewById(layoutIds[i]);
        }

        dbHelper = new HistoryDBHelper(this, "userdb", null, 1);

        editTextUserName = findViewById(R.id.editTextUserName);
        textViewUserName = findViewById(R.id.textViewUserName);
        preferences = getSharedPreferences("user", MODE_PRIVATE);
        String userName = preferences.getString("userName", null);
        if (userName == null) {
            showLayout(R.id.layoutForm);
        }
        else {
            textViewUserName.setText(userName);
            showLayout(R.id.layoutHome);
        }

        buttonMove = findViewById(R.id.buttonMove);

        buttonMove.setOnClickListener(view -> startActivity(new Intent(this, HistoryActivity.class)));
    }

    private void showLayout(int id) {
        for (LinearLayout layout: layouts) {
            if (layout.getId() == id) {
                layout.setVisibility(View.VISIBLE);
            }
            else {
                layout.setVisibility(View.GONE);
            }
        }
    }

    public void onLogin(View v) {
        if (preferences == null) {
            return;
        }

        String userName = editTextUserName.getText().toString().trim();
        if (userName.length() == 0) {
            return;
        }

//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString("userName", userName);
//        editor.apply();

        dbHelper.insert(new UserBean(userName));
        dbHelper.logAll();

        textViewUserName.setText(userName);
        showLayout(R.id.layoutHome);
    }

    public void onLogout(View v) {
        if (preferences == null) {
            return;
        }

//        SharedPreferences.Editor editor = preferences.edit();
//        editor.remove("userName");
//        editor.apply();

        showLayout(R.id.layoutForm);
    }
}
