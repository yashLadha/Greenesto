package io.github.yashladha.project.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.yashladha.project.Models.ChatMessage;
import io.github.yashladha.project.R;

public class ChatAdapter  extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

  private List<ChatMessage> chatMessages;

  public ChatAdapter(List<ChatMessage> chatMessages) {
    this.chatMessages = chatMessages;
  }

  @Override
  public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.chat_person, parent, false);
    return new ViewHolder(view);
  }



  @Override
  public void onBindViewHolder(ChatAdapter.ViewHolder holder, int position) {
    ChatMessage tempMessage = chatMessages.get(position);
  }

  @Override
  public int getItemCount() {
    return chatMessages.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    ViewHolder(View itemView) {
      super(itemView);
    }
  }
}
