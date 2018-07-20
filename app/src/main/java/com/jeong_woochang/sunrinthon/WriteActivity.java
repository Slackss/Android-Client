package com.jeong_woochang.sunrinthon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WriteActivity extends AppCompatActivity {

    EditText title,content;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        title=(EditText)findViewById(R.id.title);
        content=(EditText)findViewById(R.id.content);
        submit=(Button)findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WriteActivity.this, MainActivity.class);
                intent.putExtra("title",title.getText().toString());
                intent.putExtra("content",content.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
