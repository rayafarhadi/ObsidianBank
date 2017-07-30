package com.bank.bankingapp.messages;

import com.bank.databasehelper.DatabaseUpdateHelper;

public class Message {

  private int messageId;
  private int userId;
  private String message;
  private boolean viewed;

  public Message(int messageId, int userId, String message, boolean viewed){
    this.messageId = messageId;
    this.userId = userId;
    this.message = message;
    this.viewed = viewed;
  }

  public String viewMessage(){
    this.viewed = true;
    DatabaseUpdateHelper.updateUserMessageState(this.messageId);
    return this.message;
  }

  public int getMessageId() {
    return messageId;
  }

  public int getUserId() {
    return userId;
  }

  public String getMessage() {
    return message;
  }

  public boolean isViewed() {
    return viewed;
  }

}
