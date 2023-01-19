package br.com.will.quizservice.controller.dto;

import lombok.*;

import java.util.List;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private String id;
    private List<MovieDTO> movies;
}
