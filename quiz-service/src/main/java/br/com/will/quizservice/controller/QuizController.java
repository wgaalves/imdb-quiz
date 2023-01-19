package br.com.will.quizservice.controller;

import br.com.will.quizservice.controller.dto.QuestionDTO;
import br.com.will.quizservice.controller.dto.QuizDTO;
import br.com.will.quizservice.controller.spec.QuizControllerSpec;
import br.com.will.quizservice.enums.QuizSizeEnum;
import br.com.will.quizservice.service.QuizService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RequestMapping("/v1")
@Controller
public class QuizController implements QuizControllerSpec {

    private final QuizService service;
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    @Override
    public ResponseEntity<Void> start() {
        service.start(authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<QuestionDTO> getNextQuestion() {
        return ResponseEntity.status(HttpStatus.OK).body(service.newQuestion(authentication.getName()));
    }

    @Override
    public ResponseEntity<Void> setAnswer(String referenceId, String movieId) {
        service.answer(authentication.getName(), referenceId, movieId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<Void> finish() {
        service.finish(authentication.getName());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
