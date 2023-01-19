package br.com.will.quizservice.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@Data
public class Movie {

  private String title;
  private String year;
  private String genre;
  private String poster;
  private String imdbID;
  private Double rating;
  private String type;
}
