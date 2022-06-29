package com.synechron;

import com.synechron.exception.InvalidWordException;
import com.synechron.translator.EnglishTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
class WordCounterTranslatorTest {

    WordCounter wordCounter;
    @Mock
    EnglishTranslator englishTranslator;

    @BeforeEach
    void setup() {
        wordCounter = new WordCounter(englishTranslator);
    }

    @Test
    void shouldIncrementWordCountOfDifferentLanguage() throws InvalidWordException {

        Mockito.when(englishTranslator.translate("flower")).thenReturn("flower");
        Mockito.when(englishTranslator.translate("flor")).thenReturn("flower");
        Mockito.when(englishTranslator.translate("blume")).thenReturn("flower");

        wordCounter.add("flower");
        wordCounter.add("flor");
        wordCounter.add("blume");

        assertEquals(3, wordCounter.getWordCount("flower"));
    }

}