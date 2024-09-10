package com.cleanCoders.routes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GuessRouteHandlerTest {
    GuessRouteHandler guessRouteHandler;

    @BeforeEach
    void setup() {
        guessRouteHandler = new GuessRouteHandler();
    }

    @Test
    void parseCookiePutsAnswer71AndGuess0IntoAMap() {
        HashMap<String, Integer> parsedCookie = guessRouteHandler.parseCookie("answer=71; guesses=0");
        assertEquals(parsedCookie.get("answer"), 71);
        assertEquals(parsedCookie.get("guesses"), 0);
    }

    @Test
    void parseCookiePutsAnswer24AndGuess1IntoAMap() {
        HashMap<String, Integer> parsedCookie = guessRouteHandler.parseCookie("answer=24; guesses=1");
        assertEquals(parsedCookie.get("answer"), 24);
        assertEquals(parsedCookie.get("guesses"), 1);
    }

    @Test
    void getGuessGets3FromPostBody() {
        int guess = guessRouteHandler.getGuess("guess=3");
        assertEquals(3, guess);
    }

    @Test
    void getGuessGets22FromPostBody() {
        int guess = guessRouteHandler.getGuess("guess=22");
        assertEquals(22, guess);
    }

    @Test
    void getMessageReturnsIfGuessWasToHigh() {
        String message = guessRouteHandler.getMessage(5, 10, 1);
        String expected = "10 is too high! You have 6 guesses left.";
        assertEquals(expected, message);
    }

    @Test
    void getMessageReturnsIfGuessWasToLow() {
        String message = guessRouteHandler.getMessage(50, 10, 1);
        String expected = "10 is too low! You have 6 guesses left.";
        assertEquals(expected, message);
    }

    @Test
    void getMessageReturnsIfGuessesIsMoreThan1() {
        String message = guessRouteHandler.getMessage(50, 10, 2);
        String expected = "10 is too low! You have 5 guesses left.";
        assertEquals(expected, message);
    }

    @Test
    void getMessageReturnsIfGuessesIs7() {
        String message = guessRouteHandler.getMessage(50, 10, 7);
        String expected = "The answer was 50. You lose. The answer has been reset.";
        assertEquals(expected, message);
    }

    @Test
    void getMessageReturnsIfAnswerIsCorrect() {
        String message = guessRouteHandler.getMessage(50, 50, 7);
        String expected = "50 is correct. You win! I've picked a new number.";
        assertEquals(expected, message);
    }

    @Test
    void createCookieReturnsAnswer7() {
        String result = guessRouteHandler.createCookie("answer", 7);
        String expected = "answer=7; Path=/; HttpOnly";
        assertEquals(result, expected);
    }

    @Test
    void createCookieReturnsGuesses6() {
        String result = guessRouteHandler.createCookie("guesses", 6);
        String expected = "guesses=6; Path=/; HttpOnly";
        assertEquals(result, expected);
    }
}
