package br.com.will.quizservice.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class MovieResponseDTO{
    @JsonProperty("Title")
    public String title;
    @JsonProperty("Year") 
    public String year;
    @JsonProperty("Genre") 
    public String genre;
    @JsonProperty("Poster") 
    public String poster;
    public String imdbRating;
    public String imdbID;
    @JsonProperty("Type") 
    public String type;
}