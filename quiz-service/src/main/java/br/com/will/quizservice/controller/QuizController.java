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

    @PreAuthorize("hasAuthority('SCOPE_TEST')")
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        return ResponseEntity.status(HttpStatus.OK).body("Scopes: " + authentication.getAuthorities());
    }

    @Override
    public ResponseEntity<Void> start() {
        service.start("123");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<QuestionDTO> getNextQuestion(String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.newQuestion(userId));
    }

    @Override
    public ResponseEntity<Void> setAnswer(String userId, String referenceId, String movieId) {
        service.answer(userId, referenceId, movieId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<Void> finish(String userId) {
        service.finish(userId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
