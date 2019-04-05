package kr.hs.dgsw.databaseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private Button buttonDeleteAll;
    private HistoryDBHelper dbHelper;
    private RecyclerView historyList;
    private HistoryAdapter adapter;
    private List<UserBean> userList;

    private ItemClickListener itemClickListener = (view, i) -> {
        dbHelper.delete(userList.get(i));
        userList.remove(i);
        adapter.notifyDataSetChanged();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dbHelper = new HistoryDBHelper(this, "userdb", null, 1);

        buttonDeleteAll = findViewById(R.id.buttonDeleteAll);
        buttonDeleteAll.setOnClickListener(view -> {
            dbHelper.deleteAll();
            userList.clear();
            adapter.notifyDataSetChanged();
        });

        userList = dbHelper.getAll();
        adapter = new HistoryAdapter(userList, itemClickListener);

        historyList = findViewById(R.id.historyList);
        historyList.setLayoutManager(new LinearLayoutManager(this));
        historyList.setAdapter(adapter);
    }
}
