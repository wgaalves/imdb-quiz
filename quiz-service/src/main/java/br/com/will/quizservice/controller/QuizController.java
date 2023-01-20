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

import java.security.Principal;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RequestMapping("/v1")
@Controller
public class QuizController implements QuizControllerSpec {

    private final QuizService service;


    @Override
    public ResponseEntity<Void> start(Principal principal) {
        service.start(principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<QuestionDTO> getNextQuestion(Principal principal) {
        return ResponseEntity.status(HttpStatus.OK).body(service.newQuestion(principal.getName()));
    }

    @Override
    public ResponseEntity<Void> setAnswer(Principal principal, String referenceId, String movieId) {
        service.answer(principal.getName(), referenceId, movieId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<Void> finish(Principal principal) {
        service.finish(principal.getName());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
