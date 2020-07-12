package com.example.migranet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FindFriendsActivity extends AppCompatActivity {

    LinearLayout layout;
    String session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);

        layout = (LinearLayout)findViewById(R.id.friends);

        session=((MigraNet)this.getApplication()).getSession();

        refresh(null);
    }

    public void refresh(View view){
        JSONObject params = new JSONObject();
        try {
            params.put("user_session", Long.parseLong(session));
            params.put("user_limit",100);
        } catch (JSONException e){
            e.printStackTrace();
        }

        Log.v(null,"Requested friends "+params.toString());
        JSONRPCSender sender = new JSONRPCSender("http://81.91.176.31:9989/");
        Future<JSONObject> result = sender.send_json("friends.find", params);
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                try {
                    JSONObject answer = result.get();
                    if (answer.has("result")){
                        Log.v(null,answer.toString());

                        JSONArray friends = answer.getJSONArray("result");

                        LayoutInflater inflater = getLayoutInflater();

                        for (int i=0;i<friends.length();i++){
                            JSONObject item = friends.getJSONObject(i);

                            View new_view = inflater.inflate(R.layout.friend,layout);

                            CardView card_view =(CardView) layout.getChildAt(i);
                            card_view.setTag(item.getString("user_id"));
                            String name = item.getString("first_name")+" "+item.getString("second_name");



                            TextView name_view1 = (TextView)   ((CardView)card_view).getChildAt(0);
                            name_view1.setText(name);
                            TextView birthday_view = (TextView)   ((CardView)card_view).getChildAt(2);
                            birthday_view.setText(item.getString("birthday"));


//                            TextView description_view1 = (TextView)   ((CardView)card_view).getChildAt(1);
//                            description_view1.setText(item.getString("first_name"));


                        }


                    } else{
                        //goto_events(null);
//                        description_view.setText(answer.toString());
                    }
                } catch (ExecutionException | InterruptedException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void goto_home(View view){
        Intent intent = new Intent(FindFriendsActivity.this, MainActivity.class);
        startActivity(intent);

    }
    public void check_profile(View view){
        ((MigraNet)this.getApplication()).setFriendId(view.getTag().toString());
        Intent intent = new Intent(FindFriendsActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
}