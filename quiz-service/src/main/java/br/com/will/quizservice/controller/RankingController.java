package br.com.will.quizservice.controller;

import br.com.will.quizservice.controller.dto.ScoreDTO;
import br.com.will.quizservice.controller.spec.RankingControllerSpec;
import br.com.will.quizservice.service.RankingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@RequestMapping("/v1/ranking")
@AllArgsConstructor
@Controller
public class RankingController implements RankingControllerSpec {

    private final RankingService rankingService;
    @Override
    public ResponseEntity<ScoreDTO> individual(Principal principal) {
        return ResponseEntity.status(HttpStatus.OK).body(rankingService.getMyScore(principal.getName()));
    }

    @Override
    public ResponseEntity<List<ScoreDTO>> rankings() {
        return ResponseEntity.status(HttpStatus.OK).body(rankingService.getRankings());
    }
}
