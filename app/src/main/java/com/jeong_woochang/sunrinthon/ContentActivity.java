package com.jeong_woochang.sunrinthon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ContentActivity extends AppCompatActivity {

    String title_, content_;
    ImageView back;
    TextView title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        title=(TextView)findViewById(R.id.title);
        content=(TextView)findViewById(R.id.content);
        back=(ImageView)findViewById(R.id.back_btn);

        Intent intent=new Intent();
        title_= intent.getStringExtra("title");
        content_=intent.getStringExtra("content");

        title.setText(title_);
        content.setText(content_);
    }
}
