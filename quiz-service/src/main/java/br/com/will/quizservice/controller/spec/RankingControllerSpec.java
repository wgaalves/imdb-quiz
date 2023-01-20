package br.com.will.quizservice.controller.spec;

import br.com.will.quizservice.controller.dto.ScoreDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Tag(description = "Ranking Controller", name = "Ranking")
public interface RankingControllerSpec {

    @Operation(summary = "Get my score", description = "getScore")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Unexpected Error")
    })
    @PreAuthorize("hasAuthority('SCOPE_QUIZ_PLAY')")
    @PostMapping("/")
    ResponseEntity<ScoreDTO> individual(Principal principal);

    @Operation(summary = "get rankings", description = "ranking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Unexpected Error")
    })
    @PreAuthorize("hasAuthority('SCOPE_QUIZ_PLAY')")
    @PostMapping("/all")
    ResponseEntity<List<ScoreDTO>> rankings();
}
