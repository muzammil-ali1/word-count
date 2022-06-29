package com.synechron;

import com.synechron.exception.InvalidWordException;
import com.synechron.translator.EnglishTranslator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ThreadSafetyTest {

    @Mock
    EnglishTranslator englishTranslator;

    @Test
    public void testCounterWithConcurrency() throws InterruptedException {
        Mockito.when(englishTranslator.translate("flower")).thenReturn("flower");
        Mockito.when(englishTranslator.translate("plant")).thenReturn("plant");

        int numberOfThreads = 100;
        ExecutorService service = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        WordCounter wordCounter = new WordCounter(englishTranslator);
        for (int i = 0; i < numberOfThreads; i++) {
            service.execute(() -> {
                try {
                    wordCounter.add("flower");
                    wordCounter.add("plant");
                } catch (InvalidWordException e) {
                    throw new RuntimeException(e);
                }
                latch.countDown();
            });
        }
        latch.await();
        assertEquals(numberOfThreads, wordCounter.getWordCount("flower"));
        assertEquals(numberOfThreads, wordCounter.getWordCount("plant"));
    }

}
