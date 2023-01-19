package br.com.will.quizservice.exception;

public class QuestionNotFound extends RuntimeException {

    public QuestionNotFound(String message) {
        super(message);
    }

}
