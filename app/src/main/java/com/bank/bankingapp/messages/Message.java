package com.bank.messages;

import com.bank.database.DatabaseUpdater;
import com.bank.databasehelper.DatabaseUpdateHelper;
import java.io.Serializable;

public class Message implements Serializable{

  private static final long serialVersionUID = 2185744541705762899L;
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
