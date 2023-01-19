package br.com.will.quizservice.controller.dto;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuizDTO {

    private List<QuestionDTO> questions;
}
