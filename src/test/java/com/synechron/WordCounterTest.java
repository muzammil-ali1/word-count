package com.synechron;

import com.synechron.exception.InvalidWordException;
import com.synechron.translator.EnglishTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WordCounterTest {
    WordCounter wordCounter;
    @Mock
    EnglishTranslator englishTranslator;

    @BeforeEach
    void setup() {
        wordCounter = new WordCounter(englishTranslator);
    }

    @Test
    void shouldAddANewWord() throws InvalidWordException {
        mockTranslator("flower");
        wordCounter.add("flower");


        assertEquals(1, wordCounter.getWordCount("flower"));
    }

    @Test
    void shouldBeAbleToAddAWordMoreThanOnce() throws InvalidWordException {
        mockTranslator("flower");
        wordCounter.add("flower");
        wordCounter.add("flower");

        assertEquals(2, wordCounter.getWordCount("flower"));
    }

    @Test
    void shouldBeAbleToAddMultipleWords() throws InvalidWordException {
        mockTranslator("flower");
        mockTranslator("branch");

        wordCounter.add("flower");
        wordCounter.add("branch");
        wordCounter.add("flower");
        wordCounter.add("branch");
        wordCounter.add("branch");

        assertEquals(2, wordCounter.getWordCount("flower"));
        assertEquals(3, wordCounter.getWordCount("branch"));
    }

    @Test
    void shouldThrowErrorWhenAddNonAlphabeticWord(){
        assertThrows(InvalidWordException.class, () -> wordCounter.add("flower32"));
        assertEquals(0, wordCounter.getWordCount("flower32"));
    }

    @Test
    void shouldThrowErrorWhenAddNull(){
        assertThrows(InvalidWordException.class, () -> wordCounter.add(null));
    }

    private void mockTranslator(String str) {
        Mockito.when(englishTranslator.translate(str)).thenReturn(str);
    }



}