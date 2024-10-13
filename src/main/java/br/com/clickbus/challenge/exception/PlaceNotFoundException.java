package br.com.clickbus.challenge.exception;


public class PlaceNotFoundException extends RuntimeException{

    public PlaceNotFoundException(String message){
        super(message);
    }

}
