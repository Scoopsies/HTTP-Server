package com.cleanCoders.routes;

import com.cleanCoders.FileContent;
import com.cleanCoders.HttpRequest;
import com.cleanCoders.RouteHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class GuessRouteHandler implements RouteHandler {
    @Override
    public byte[] handle(HttpRequest request) throws IOException {

        if (Objects.equals("GET", request.get("method"))){
            String answerCookie = createCookie("answer", (int)(Math.random() * 100 + 1));
            String guessesCookie = createCookie("guesses", 0);
            String message = "";

            return buildGuessResponse(answerCookie, guessesCookie, message).getBytes();

        } else {
            HashMap<String, Integer> cookieMap = parseCookie(request.get("Cookie"));
            int guess = getGuess(request.get("body"));
            int answer = cookieMap.get("answer");
            int guesses = cookieMap.get("guesses") + 1;
            String message = getMessage(answer, guess, guesses);

            if (guesses > 7 || guess == answer) {
                guesses = 0;
                answer = (int)(Math.random() * 100 + 1);
            }

            String answerCookie = createCookie("answer", answer);
            String guessesCookie = createCookie("guesses", guesses);

            return buildGuessResponse(answerCookie, guessesCookie, message).getBytes();
        }
    }

    public String createCookie(String key, int value) {
        return key + "="+ value +"; Path=/; HttpOnly";
    }

     HashMap<String, Integer> parseCookie(String cookie) {
         HashMap<String, Integer> cookieMap = new HashMap<>();
         String[] cookieArray = cookie.split(";");

        for (String keyValue : cookieArray) {
            String[] keyValueArray = keyValue.split("=");
            cookieMap.put(keyValueArray[0].trim(), Integer.parseInt(keyValueArray[1]));
        }
        return cookieMap;
    }

    public int getGuess(String postBody) {
        return Integer.parseInt(postBody.split("=")[1]);
    }

    public String getMessage(int answer, int guess, int guesses) {
        String lowHigh;
        int guessesLeft = 7 - guesses;

        if (answer == guess)
            return answer + " is correct. You win! I've picked a new number.";

        if (guessesLeft == 0)
            return "The answer was " + answer +". You lose. The answer has been reset.";

        if (guess > answer) {
            lowHigh = "high";
        } else {
            lowHigh = "low";
        }

        return guess + " is too " + lowHigh + "! You have " + guessesLeft + " guesses left.";
    }

    private String buildGuessResponse(String answerCookie, String guessesCookie, String message) throws IOException {
        FileContent fc = new FileContent();
        String html = fc.getResourceTextFileContent("guess/index.html");

        return """
                    HTTP/1.1 200 OK
                    Content-Type: text/html
                    Server: httpServer
                    Set-Cookie: %s
                    Set-Cookie: %s
                    
                    %s"""
                .formatted(answerCookie,guessesCookie, html)
                .formatted(message);
    }
}