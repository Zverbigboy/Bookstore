package com.s3k3l3v.bookstore.entity;

import java.io.Serializable;
import java.sql.Date;

public class DateFine implements Serializable{
    private Date datetimes;
    private int abonement;

    @Override
    public String toString() {
        return "DateFine{" +
                "datetimes=" + datetimes +
                ", abonement=" + abonement +
                '}';
    }

    public Date getDatetimes() {
        return datetimes;
    }

    public void setDatetimes(Date datetimes) {
        this.datetimes = datetimes;
    }

    public int getAbonement() {
        return abonement;
    }

    public void setAbonement(int abonement) {
        this.abonement = abonement;
    }
}
