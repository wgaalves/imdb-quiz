package br.com.will.quizservice.Repository;

import br.com.will.quizservice.domain.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {
}
