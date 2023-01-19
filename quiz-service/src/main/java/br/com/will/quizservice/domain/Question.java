package br.com.will.quizservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Document
@Builder
@Getter
@Setter
@AllArgsConstructor
public class Question {

  @Builder.Default
  private String referenceId = UUID.randomUUID().toString();
  private List<Movie> movies;
  private String userAnswer;

  @Transient
  public boolean getIscorrect() {
    return this.userAnswer.equals(movies.stream().max(Comparator.comparing(Movie::getRating)).get().getImdbID());
  }
}
