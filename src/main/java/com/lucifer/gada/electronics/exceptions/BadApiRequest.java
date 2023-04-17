package com.lucifer.gada.electronics.exceptions;

public class BadApiRequest extends RuntimeException{

    public BadApiRequest(String message){
        super(message);
    }

    public BadApiRequest(){
        super("BAD Request!!");
    }
}
