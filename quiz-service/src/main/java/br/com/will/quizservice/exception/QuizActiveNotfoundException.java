package br.com.will.quizservice.exception;

public class QuizActiveNotfoundException extends RuntimeException {

  public QuizActiveNotfoundException(String message) {
    super(message);
  }
}
