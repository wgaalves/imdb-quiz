package br.com.will.quizservice.controller.handler;

import br.com.will.quizservice.enums.ErrorENUM;
import br.com.will.quizservice.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class QuizServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    private static final String DETAIL = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                      HttpHeaders headers, HttpStatus status,
                                                                      WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        return handleExceptionInternal(ex,
                new ErrorMessage(status, ErrorENUM.BAD_REQUEST, ex.getMessage()),
                headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(
            MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        var problemType = ErrorENUM.BAD_REQUEST;
        var detail = ex.getMessage();
        var problem = new ErrorMessage(status, problemType, detail);

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
                                                         HttpStatus status,
                                                         WebRequest request) {

        return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, HttpHeaders headers,
                                                            HttpStatus status, WebRequest request,
                                                            BindingResult bindingResult) {
        var problemType = ErrorENUM.BAD_REQUEST;

        List<ErrorMessage> problemObjects = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                    String name = objectError.getObjectName();

                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }

                    return new ErrorMessage(name, message, ErrorENUM.BAD_REQUEST);
                })
                .collect(Collectors.toList());

        var problem = new ErrorMessage(status, problemType, DETAIL, problemObjects);

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var problem = new ErrorMessage(HttpStatus.BAD_REQUEST, ErrorENUM.BAD_REQUEST, ex.getMessage());
        return handleExceptionInternal(ex, problem, headers, status, request);
    }


    // Custom exceptions
    @ExceptionHandler(AlreadyStartedQuizException.class)
    public ResponseEntity<Object> handleMsisdnNotFound(AlreadyStartedQuizException ex, WebRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var problemType = ErrorENUM.RESOURCE_NOT_FOUND;
        var detail = ex.getMessage();

        var problem = new ErrorMessage(status, problemType, detail);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(QuizActiveNotfoundException.class)
    public ResponseEntity<Object> handlerQuizNotFound(QuizActiveNotfoundException ex, WebRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var problemType = ErrorENUM.RESOURCE_NOT_FOUND;
        var detail = ex.getMessage();

        var problem = new ErrorMessage(status, problemType, detail);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(QuestionNotFound.class)
    public ResponseEntity<Object> handlerQuestionNotFound(QuestionNotFound ex, WebRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var problemType = ErrorENUM.RESOURCE_NOT_FOUND;
        var detail = ex.getMessage();

        var problem = new ErrorMessage(status, problemType, detail);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ActiveQuestionException.class)
    public ResponseEntity<Object> handlerActiveQuestionException(ActiveQuestionException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var problemType = ErrorENUM.BAD_REQUEST;
        var detail = ex.getMessage();

        var problem = new ErrorMessage(status, problemType, detail);
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

}
