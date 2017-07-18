package io.github.yashladha.project.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.github.yashladha.project.Models.ChatMessage;
import io.github.yashladha.project.R;

public class ChatAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final int SENDER = 0;
  private static final int RECEIVER = 1;
  private String TAG = getClass().getSimpleName();

  private List<ChatMessage> chatMessages;
  private String uid;

  public ChatAdapter(List<ChatMessage> chatMessages, String uid) {
    this.chatMessages = chatMessages;
    this.uid = uid;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater lm = LayoutInflater.from(parent.getContext());
    View view;
    switch (viewType) {
      case SENDER:
        view = lm.inflate(R.layout.sender_chat, parent, false);
        return new SenderViewHolder(view);
      case RECEIVER:
        view = lm.inflate(R.layout.receiver_chat, parent, false);
        return new ReceiverViewHolder(view);
    }
    return null;
  }

  @Override
  public int getItemViewType(int position) {
    ChatMessage tempMessage = chatMessages.get(position);
    Log.d(TAG, tempMessage.toString());
    if (tempMessage.getUserUid().equals(uid)) {
      Log.d(TAG, "Inflate own profile");
      return SENDER;
    } else {
      Log.d(TAG, "Inflate other's profile");
      return RECEIVER;
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    ChatMessage tempMessage = chatMessages.get(position);
    switch (holder.getItemViewType()) {
      case SENDER:
        SenderViewHolder view1 = (SenderViewHolder) holder;
        view1.messageBox.setText(tempMessage.getBody());
        break;
      case RECEIVER:
        ReceiverViewHolder view2 = (ReceiverViewHolder) holder;
        view2.messageBox.setText(tempMessage.getBody());
        break;
    }
  }

  @Override
  public int getItemCount() {
    return chatMessages.size();
  }

  private class SenderViewHolder extends RecyclerView.ViewHolder {
    TextView messageBox;
    SenderViewHolder(View itemView) {
      super(itemView);
      messageBox = (TextView) itemView.findViewById(R.id.tv_sender_text);
    }
  }

  private class ReceiverViewHolder extends RecyclerView.ViewHolder {
    TextView messageBox;
    ReceiverViewHolder(View itemView) {
      super(itemView);
      messageBox = (TextView) itemView.findViewById(R.id.tv_receiver_text);
    }
  }
}
