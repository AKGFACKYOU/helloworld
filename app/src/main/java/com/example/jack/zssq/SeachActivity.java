package com.example.jack.zssq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SeachActivity extends AppCompatActivity {

    private static final String TAG ="SeachActivity" ;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.bt_search)
    Button btSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_seach);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_search)
    public void onViewClicked() {
        String str=etSearch.getText().toString();
        if(str!=null) {

            if(!("").equals(str)) {
                Log.e(TAG, "onViewClicked: " + etSearch.getText());
                Intent intent = new Intent(this, BookListActivity.class);
                intent.putExtra("search_context", str);
//            Log.e(TAG, "onViewClicked: ");
                startActivity(intent);
                finish();
            }
            else
            {
                Toast.makeText(this, "你还没有输入书名", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "你还没有输入书名", Toast.LENGTH_SHORT).show();
        }
    }
}
