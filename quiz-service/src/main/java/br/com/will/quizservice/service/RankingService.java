package br.com.will.quizservice.service;

import br.com.will.quizservice.Repository.QuizRepository;
import br.com.will.quizservice.controller.dto.ScoreDTO;
import br.com.will.quizservice.domain.Movie;
import br.com.will.quizservice.domain.Quiz;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RankingService {

  private final QuizRepository quizRepository;

  public ScoreDTO getMyScore(String user) {
    List<Quiz> quizzes = quizRepository.findByUserId(user);
    return ScoreDTO.builder()
        .score(quizzes.stream().mapToInt(Quiz::getScore).sum() / quizzes.size())
        .build();
  }

  public List<ScoreDTO> getRankings() {
    List<ScoreDTO> rankings = new ArrayList<>();
    List<Quiz> quiz = quizRepository.findDistinctByUserId();
    List<String> idList = quiz.stream().map(Quiz::getUserId).toList();
    idList.forEach(
        user -> {
          List<Quiz> user_quizzes = quizRepository.findAllByUserIdAndFinished(user, Boolean.TRUE);
          rankings.add(
              ScoreDTO.builder()
                  .score(user_quizzes.stream().mapToInt(Quiz::getScore).sum() / user_quizzes.size())
                  .user(user)
                  .build());
        });

    rankings.sort(Comparator.comparing(ScoreDTO::getScore).reversed());
    return rankings;

  }
}
