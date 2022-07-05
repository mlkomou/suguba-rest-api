package com.wassa.suguba.app.payload;

import java.time.LocalDateTime;
import java.util.Date;

public class IntervalleDate {
   public int page;
   public int size;
   public Long partenaireId;
   public LocalDateTime dateDebut;
   public LocalDateTime dateFin;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    public Long getPartenaireId() {
        return partenaireId;
    }

    public void setPartenaireId(Long partenaireId) {
        this.partenaireId = partenaireId;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    @Override
    public String toString() {
        return "IntervalleDate{" +
                "page=" + page +
                ", size=" + size +
                ", partenaireId=" + partenaireId +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }
}
