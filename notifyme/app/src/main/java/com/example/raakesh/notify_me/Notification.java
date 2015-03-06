package com.example.raakesh.notify_me;

/**
 * Created by raakesh on 15/1/15.
 */
public class Notification {
     public String id;
    public String author;
    public String title;
    public String content;
    public String type;
    public String status;
    Notification(String id,String author,String title,String content,String type,String status){
        this.id=id;
        this.author=author;
        this.title=title;
        this.content=content;
        this.type=type;
        this.status=status;
    }
    public String getId(){return this.id;}
    public void setId(String id){this.id=id;}
    public String getAuthor(){return this.author;}
   // public void setAuthor(){this.author=author;}
    public String getTitle(){return this.title;}
    public void setTitle(String title){this.title=title;}
    public String getContent(){return this.content;}
    public void setContent(String content){this.content=content;}
    public String getType(){return this.type;}
    public void setType(String type){this.type=type;}
    public String getStatus(){return this.status;}
    public void setStatus(){this.status=status;}
}
