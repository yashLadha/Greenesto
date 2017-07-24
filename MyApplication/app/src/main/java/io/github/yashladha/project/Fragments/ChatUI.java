package io.github.yashladha.project.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import io.github.yashladha.project.Adapter.ChatAdapter;
import io.github.yashladha.project.Models.ChatMessage;
import io.github.yashladha.project.R;
import io.github.yashladha.project.User;

public class ChatUI extends AppCompatActivity {

  private String TAG = getClass().getSimpleName();
  public static final FirebaseDatabase DATABASE = FirebaseDatabase.getInstance();
  private static final FirebaseUser CURUSER = FirebaseAuth.getInstance().getCurrentUser();
  private static final String UID = CURUSER.getUid();

  private EditText messageBox;
  private RecyclerView messageList;
  private FloatingActionButton sendButton;
  private ImageView voiceCall;
  private ImageView videoCall;

  private User tempChatUser;
  private HashSet<String> messages;
  private ChatAdapter chatAdapter;
  public static final DatabaseReference CUR_USER_REF = DATABASE.getReference()
      .child(UID)
      .child("Messages");
  private List<ChatMessage> messageCnt;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat_ui);

    inflateChat inflatter = new inflateChat();
    inflatter.execute(DATABASE);

    getSupportActionBar().setTitle("Chat");

    messages = new HashSet<>();
    messageCnt = new ArrayList<>();
    messageBox = (EditText) findViewById(R.id.et_message_input);
    sendButton = (FloatingActionButton) findViewById(R.id.fab_chat_send);
    messageList = (RecyclerView) findViewById(R.id.rv_chat_messages);
    RecyclerView.LayoutManager lm = new LinearLayoutManager(getApplicationContext());
    messageList.setLayoutManager(lm);
    chatAdapter = new ChatAdapter(messageCnt, UID);
    messageList.setAdapter(chatAdapter);

    voiceCall = (ImageView) findViewById(R.id.iv_voice_call);
    videoCall = (ImageView) findViewById(R.id.iv_video_call);

    voiceCall.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.d(TAG, "Voice Call initiated");
        Toast.makeText(getApplicationContext(), "Voice Call", Toast.LENGTH_SHORT).show();
      }
    });

    videoCall.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.d(TAG, "Video Call initiated");
        Toast.makeText(getApplicationContext(), "Video Call", Toast.LENGTH_SHORT).show();
      }
    });

    sendButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String textMessage = messageBox.getText().toString();
        if (textMessage.length() > 0 && tempChatUser != null) {
          ChatMessage tempMessage = new ChatMessage(textMessage, UID);
          String id_ = uniqueId();
          sendMessage(id_, tempMessage);
          messageBox.setText("");
        }
      }
    });

  }

  private void circularRevelVoice(View view) {
  }

  private void listenChats() {
    DatabaseReference listen = DATABASE.getReference()
        .child(tempChatUser.getUid())
        .child("Messages");
    listen.addValueEventListener(
        new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot item : dataSnapshot.getChildren()) {
              String key = item.getKey();
              if (messages.contains(key)) {
                Log.d(TAG, "Message already inflated");
              } else {
                messages.add(key);
                ChatMessage tempMessage = item.getValue(ChatMessage.class);
                messageCnt.add(tempMessage);
                chatAdapter.notifyDataSetChanged();
              }
            }
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {
            Log.e(TAG, databaseError.getMessage());
          }
        }
    );
  }

  private void sendMessage(String id_, ChatMessage message) {
    DatabaseReference userRef = DATABASE.getReference()
        .child(tempChatUser.getUid())
        .child("Messages");
    messages.add(id_);
    CUR_USER_REF.child(id_).setValue(message)
    .addOnCompleteListener(new OnCompleteListener<Void>() {
      @Override
      public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful())
          Log.d(TAG, "User database updated successfully");
      }
    });
    userRef.child(id_).setValue(message)
    .addOnCompleteListener(new OnCompleteListener<Void>() {
      @Override
      public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful())
          Log.d(TAG, "Chat user database also gets updated");
      }
    });
    messageCnt.add(message);
    chatAdapter.notifyDataSetChanged();
  }

  private class inflateChat extends AsyncTask<FirebaseDatabase, Void, Void> {

    @Override
    protected Void doInBackground(FirebaseDatabase... params) {
      Intent intent = getIntent();
      tempChatUser = (User) intent.getSerializableExtra("userObject");

      final List<ChatMessage> chat = new ArrayList<>();
      DatabaseReference chatData = params[0].getReference()
          .child(tempChatUser.getUid())
          .child("Messages");

      chatData.addListenerForSingleValueEvent(
          new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              for (DataSnapshot item : dataSnapshot.getChildren()) {
                String key = item.getKey();
                Log.d(TAG, key);
                ChatMessage tempMessage = item.getValue(ChatMessage.class);
                if (messages.contains(key)) {
                  Log.d(TAG, "Message already present");
                } else {
                  messageCnt.add(tempMessage);
                  messages.add(key);
                  chatAdapter.notifyDataSetChanged();
                }
              }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
              Log.e(TAG, databaseError.getMessage());
            }
          }
      );
      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      listenChats();
    }
  }

  private String uniqueId() {
    Calendar calendar = Calendar.getInstance();
    return String.valueOf(calendar.getTimeInMillis());
  }
}
