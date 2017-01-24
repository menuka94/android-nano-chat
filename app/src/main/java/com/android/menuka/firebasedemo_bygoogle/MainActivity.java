package com.android.menuka.firebasedemo_bygoogle;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseApp app;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSend = (Button) findViewById(R.id.btnSend);
        final EditText txtMessage = (EditText) findViewById(R.id.txtMessage);
        ListView listViewMessages = (ListView) findViewById(R.id.listViewMessages);

//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://android-nano-chat.firebaseio.com/");

        btnSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ChatMessage chat = new ChatMessage("Harry", txtMessage.getText().toString());
                ref.push().setValue(chat);
                txtMessage.setText("");
            }
        });

        final List<ChatMessage> messages = new LinkedList<>();
        final ArrayAdapter<ChatMessage> adapter = new ArrayAdapter<ChatMessage>(
                this, android.R.layout.two_line_list_item, messages
        ){
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = getLayoutInflater().inflate(android.R.layout.two_line_list_item, parent, false);
                }

                ChatMessage chat = messages.get(position);
                ((TextView)convertView.findViewById(android.R.id.text1)).setText(chat.getName());
                ((TextView)convertView.findViewById(android.R.id.text2)).setText(chat.getMessage());

                return convertView;
            }
        };
        listViewMessages.setAdapter(adapter);

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatMessage chat = dataSnapshot.getValue(ChatMessage.class);
                messages.add(chat);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
