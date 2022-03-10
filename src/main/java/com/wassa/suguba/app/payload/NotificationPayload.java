package com.wassa.suguba.app.payload;

public class NotificationPayload {
   public String titre;
   public String description;
   public String type;
   public Long commandeId;
   public Long produitId;
   public Long oneSignalUserId;

    public Long getOneSignalUserId() {
        return oneSignalUserId;
    }

    public void setOneSignalUserId(Long oneSignalUserId) {
        this.oneSignalUserId = oneSignalUserId;
    }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    public Long getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(Long commandeId) {
        this.commandeId = commandeId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
