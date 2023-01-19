package br.com.will.quizservice.controller.spec;

import br.com.will.quizservice.controller.dto.QuestionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Tag(description = "Quiz Controller", name = "Quiz")
public interface QuizControllerSpec {


    @Operation(summary = "create new quiz", description = "create")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Unexpected Error")
    })
    @PreAuthorize("hasAuthority('QUIZ_PLAY_ROLE')")
    @PostMapping("/start")
    ResponseEntity<Void> start();

    @Operation(summary = "get Question", description = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Unexpected Error")
    })
    @PreAuthorize("hasAuthority('QUIZ_PLAY_ROLE')")
    @GetMapping("/next")
    ResponseEntity<QuestionDTO> getNextQuestion();


    @Operation(summary = "Set Answer", description = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Unexpected Error")
    })
    @PreAuthorize("hasAuthority('QUIZ_PLAY_ROLE')")
    @PutMapping("/question/{referenceId}/answer/{movieId}")
    ResponseEntity<Void> setAnswer(@PathVariable String referenceId, @PathVariable String movieId);


    @Operation(summary = "Finish Quiz", description = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Unexpected Error")
    })
    @PreAuthorize("hasAuthority('QUIZ_PLAY_ROLE')")
    @PostMapping("/finish")
    ResponseEntity<Void> finish();

}
