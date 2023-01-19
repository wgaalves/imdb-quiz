package br.com.will.quizservice.exception;

public class AlreadyStartedQuizException extends RuntimeException {

    public AlreadyStartedQuizException(String message) {
        super(message);
    }

}
