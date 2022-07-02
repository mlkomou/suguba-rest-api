package com.wassa.suguba.app.payload;


import java.util.List;

public class SendSms {
  public List<SmsContent> messages;
  public String sender_id;

   public List<SmsContent> getMessages() {
      return messages;
   }

   public void setMessages(List<SmsContent> messages) {
      this.messages = messages;
   }

   public String getSender_id() {
      return sender_id;
   }

   public void setSender_id(String sender_id) {
      this.sender_id = sender_id;
   }
}
