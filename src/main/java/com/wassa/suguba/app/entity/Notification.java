package com.wassa.suguba.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "notification")
@Access(AccessType.FIELD)
public class Notification extends Generality{
    @Column(name = "oneSignalUserId")
    private String oneSignalUserId;
    @Column(name = "titre")
    private String titre;
    @Column(name = "description")
    private String description;
    @Column(name = "type")
    private String type;
    @Column(name = "lecture")
    private String lecture = "non";

    public String getOneSignalUserId() {
        return oneSignalUserId;
    }

    public void setOneSignalUserId(String oneSignalUserId) {
        this.oneSignalUserId = oneSignalUserId;
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

    public String getLecture() {
        return lecture;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
    }
}
