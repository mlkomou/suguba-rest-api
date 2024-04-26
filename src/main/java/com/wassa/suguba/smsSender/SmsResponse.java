package com.wassa.suguba.smsSender;

public class SmsResponse {
   public String message;
   public boolean success;
   public String campaign_id;

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public boolean isSuccess() {
      return success;
   }

   public void setSuccess(boolean success) {
      this.success = success;
   }

   public String getCampaign_id() {
      return campaign_id;
   }

   public void setCampaign_id(String campaign_id) {
      this.campaign_id = campaign_id;
   }

   @Override
   public String toString() {
      return "SmsResponse{" +
              "message='" + message + '\'' +
              ", success=" + success +
              ", campaign_id='" + campaign_id + '\'' +
              '}';
   }
}
