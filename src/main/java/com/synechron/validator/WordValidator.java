package com.synechron.validator;

public class WordValidator {
    public boolean isValidWord(String word){
        return word != null && word.matches("[a-zA-Z]+");
    }
}
