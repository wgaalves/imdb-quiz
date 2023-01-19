package br.com.will.quizservice.controller.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    public String title;
    public String year;
    public String genre;
    public String poster;
    public String imdbID;
    public String type;
}
