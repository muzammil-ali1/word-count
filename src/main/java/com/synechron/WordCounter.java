package com.synechron;

import com.synechron.exception.InvalidWordException;
import com.synechron.translator.EnglishTranslator;
import com.synechron.validator.WordValidator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WordCounter {
    private final Map<String, Integer> wordCounter;
    private final WordValidator validator;
    private final EnglishTranslator englishTranslator;

    public WordCounter(EnglishTranslator englishTranslator) {
        this.wordCounter = new ConcurrentHashMap<>();
        this.validator = new WordValidator();
        this.englishTranslator = englishTranslator;
    }

    public void add(String word) throws InvalidWordException {
        if(!validator.isValidWord(word)) {
            throw new InvalidWordException();
        }
        wordCounter.merge(englishTranslator.translate(word), 1, Integer::sum);
    }

    public int getWordCount(String word) {
        return wordCounter.getOrDefault(word, 0);
    }
}
