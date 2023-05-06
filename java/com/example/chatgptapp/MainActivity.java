package com.example.chatgptapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity<url> extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView welcometext;
    EditText writehere;
    ImageView send;
    List<Messege>messegeList;
    MessegeAdapter messegeAdapter;

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        messegeList =new ArrayList<>();

        recyclerView=findViewById(R.id.recyclerview);
        welcometext=findViewById(R.id.tvWelcome);
        writehere=findViewById(R.id.etWriteHere);
        send=findViewById(R.id.imageViewSend);

        //setup recycler view
        messegeAdapter=new MessegeAdapter(messegeList);
        recyclerView.setAdapter(messegeAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question=writehere.getText().toString().trim();
                addtoChat(question,Messege.SENT_BY_ME);
                writehere.setText("");
                callAPI(question);
                welcometext.setVisibility(View.GONE);
            }
        });


    }
    void  addtoChat(String messege,String sentBy){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messegeList.add(new Messege(messege,sentBy));
                messegeAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messegeAdapter.getItemCount());
            }
        });
    }

    void addResponse(String response){
        messegeList.remove(messegeList.size()-1);
        addtoChat(response,Messege.SENT_BY_BOT);
    }

    void callAPI(String question){
        //okhttp
        messegeList.add(new Messege("Typing... ",Messege.SENT_BY_BOT));

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model","text-davinci-003");
            jsonBody.put("prompt",question);
            jsonBody.put("max_tokens",4000);
            jsonBody.put("temperature",0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(jsonBody.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header("Authorization","Bearer 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")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Failed to load response due to "+e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    JSONObject  jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0).getString("text");
                        addResponse(result.trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }else{
                    addResponse("Failed to load response due to "+response.body().toString());
                }
            }
        });

    }

}

