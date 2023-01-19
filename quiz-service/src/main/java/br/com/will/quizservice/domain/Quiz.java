package br.com.will.quizservice.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class Quiz {

    @Id
    private String id;
    private String userId;
    @Builder.Default
    private List<Question> questions= new ArrayList<>();

    @Builder.Default
    private boolean finished =  Boolean.FALSE;

}
