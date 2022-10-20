package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.sql.Date;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Comm {
        public Comm(String caption, String comnt, String author, int mark) { //LocalDateTime dateTime
            this.caption = caption;
            this.comnt = comnt;
            this.author = author;
            this.mark = mark;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.now();
            String formattedDateTime = dateTime.format(formatter); // "1986-04-08 12:30"

            this.actionDate = formattedDateTime;
    }

    public Comm(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "Поле не может быть пустым")
    @NotBlank(message = "Поле не должно состоять только из пробелов")
    @Size(min=2, max=50, message = "Размер данного поля должен быть в диапозоне от 1 до 50")
    private String comnt, caption;

    private String author;
    private int mark;
    private String actionDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getComnt() {
        return comnt;
    }

    public void setComnt(String comnt) {
        this.comnt = comnt;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getActionDate() {
        return actionDate;
    }

    public void setActionDate(String actionDate) {
        this.actionDate = actionDate;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    //    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getCaption() {
//        return caption;
//    }
//
//    public void setCaption(String caption) {
//        this.caption = caption;
//    }
//
//    public String getComnt() {
//        return comnt;
//    }
//
//    public void setComnt(String comnt) {
//        this.comnt = comnt;
//    }
//
//    public String getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(String author) {
//        this.author = author;
//    }
//
//    public int getMark() {
//        return mark;
//    }
//
//    public void setMark(int mark) {
//        this.mark = mark;
//    }
//
//    public String getTime() {
//        return actionDate;
//    }
//
//    public void setTime(String datetime) {
//        this.actionDate = datetime;
//    }
}
