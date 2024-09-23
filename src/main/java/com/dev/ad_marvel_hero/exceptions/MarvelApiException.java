package com.dev.ad_marvel_hero.exceptions;


public class MarvelApiException extends RuntimeException{

    public MarvelApiException(String errorMessage) {
        super(errorMessage);
    }
}
