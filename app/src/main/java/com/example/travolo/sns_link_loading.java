package com.example.travolo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class sns_link_loading extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sns_link_loading);

        ImageView image = findViewById(R.id.iv_frame_loading);
        Glide.with(this).load(R.drawable.fish).into(image);

        Intent intent = getIntent();
        String id = intent.getExtras().getString("id");

        sendData(id);
    }
    public void sendData(final String id) {
        String URL = "http://211.253.26.214:15000/snsImgAnalysis";//통신할 서버 url

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {//json오브젝트로 응답받음
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String success = response.getString("success");//응답이 success일 경우
                    if (success != null && success.equals("1")) {
                        Intent intent = new Intent(sns_link_loading.this, checkpwd.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "아이디가 존재하지 않거나 비공개상태 입니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "sever error!", Toast.LENGTH_SHORT).show();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "네트워크 오류!", Toast.LENGTH_SHORT).show();
                return;
            }
        };

        //맵형태로 정보 전달
        JSONObject jsonObject = new JSONObject();//맵형태의 정보를 json으로 전송
        try{
            jsonObject.put("sns_id",id);
        }catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, responseListener, errorListener);
        loginRequest.setRetryPolicy(new DefaultRetryPolicy(3600000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));//서버와의 통신시간을 강제로 늘림
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(loginRequest);//전송
    }
}
