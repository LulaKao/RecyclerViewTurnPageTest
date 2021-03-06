package com.quarterlife.recyclerviewturnpagetest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<ItemModel> itemList = new ArrayList<>();
    private TextView txt_page;
    private LinearLayout btn_previous, btn_next;
    private int current_page = 1, total_page_int = 0, list_start = 0, list_end = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init View
        recyclerView = findViewById(R.id.recyclerView);
        txt_page = findViewById(R.id.txt_page);
        btn_previous = findViewById(R.id.btn_previous);
        btn_next = findViewById(R.id.btn_next);

        // init OnClickListener
        btn_previous.setOnClickListener(this);
        btn_next.setOnClickListener(this);

        int img;

        // add data
        for(int i = 0; i < 33; i++){
            if(i < 12){
                img = R.drawable.example;
            } else if(i < 24){
                img = R.drawable.example2;
            } else{
                img = R.drawable.android;
            }

            itemList.add(new ItemModel(i + 1, img, 2, 630, 10,"PinkBear01"));
        }

        // 設置 LinearLayoutManager
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(MainActivity.this,4); // 設定一行四個項目
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // 設定垂直方向
        recyclerView.setLayoutManager(linearLayoutManager); // 綁定 recyclerView 和 linearLayoutManager

        // 計算有幾頁
        double total_page_double = itemList.size() / 12.0;
        total_page_int = (int) Math.ceil(total_page_double); // 無條件進位，並轉成 int

        // 設置資料
        setData();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_next){ // 下一頁
            if(current_page < total_page_int){ // 若還有下一頁
                current_page++;
                setData(); // 設置資料
            } else { // 若沒有下一頁
                Toast.makeText(this, "已到最底頁", Toast.LENGTH_SHORT).show();
            }

        } else { // 上一頁
            if(current_page > 1){ // 若還有上一頁
                current_page--;
                setData(); // 設置資料
            }else { // 若沒有上一頁
                Toast.makeText(this, "已到最前頁", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setData() {
        // 設定要顯示的內容區間
        list_start = (current_page - 1) * 12;
        if(itemList.size() < current_page * 12) list_end = itemList.size();
        else list_end = current_page * 12;

        // 設定 adapter
        if(adapter != null) adapter = null;
        adapter = new ItemAdapter(MainActivity.this, itemList.subList(list_start,list_end));
        recyclerView.setAdapter(adapter);

        // 設定要顯示的分頁數字
        txt_page.setText(current_page + "/" + total_page_int);
    }
}