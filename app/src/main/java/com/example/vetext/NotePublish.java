package com.example.vetext;

public class NotePublish {
    public NotePublish(){
        // No -arg constructer
    }
    private String tittle;
    public NotePublish(String tittle){
        this.tittle=tittle;
    }

    public String getTittle() {
        return tittle;
    }
}
