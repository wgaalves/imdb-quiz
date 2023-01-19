package br.com.will.quizservice.client;

import br.com.will.quizservice.client.dto.MovieResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "imbdApi", url = "http://www.omdbapi.com/")
public interface ImdbClient {

    @GetMapping()
    MovieResponseDTO getMovie(@RequestParam("i") String imdbId, @RequestParam("apikey")String apikey);

}
