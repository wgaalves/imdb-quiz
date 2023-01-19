package br.com.will.quizservice.exception;

public class ActiveQuestionException extends RuntimeException {

  public ActiveQuestionException(String message) {
    super(message);
  }
}
