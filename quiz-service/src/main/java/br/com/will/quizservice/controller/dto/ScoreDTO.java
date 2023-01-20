package br.com.will.quizservice.controller.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ScoreDTO {
    private String user;
    private int score;
}
