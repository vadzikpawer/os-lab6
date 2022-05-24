package com.vadzik;

public class Procces {

    int size;
    int startIndex;
    int id;
    String state;
    int typeOfMemory;


    Procces(int size, int id){
        this.size = size;
        state = "Ready";
        this.id = id;
    }

    public void pauseProcces(){
        state = "Wait";
    }

}
