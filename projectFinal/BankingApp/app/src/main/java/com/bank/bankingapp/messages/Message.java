package com.bank.bankingapp.messages;


import android.content.Context;

import com.bank.bankingapp.database.DatabaseHelper;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 2185744541705762899L;
    private int messageId;
    private int userId;
    private String message;
    private boolean viewed;

    public Message(int messageId, int userId, String message, boolean viewed) {
        this.messageId = messageId;
        this.userId = userId;
        this.message = message;
        this.viewed = viewed;
    }

    public String viewMessage(Context context) {

        DatabaseHelper db = new DatabaseHelper(context);

        this.viewed = true;
        db.updateUserMessageState(this.messageId);
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
