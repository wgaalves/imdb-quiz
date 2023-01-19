package br.com.will.quizservice.Repository;

import br.com.will.quizservice.domain.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends MongoRepository<Quiz, String> {

    @Override
    List<Quiz> findAll();

    boolean existsByFinishedAndAndUserId(boolean finished, String userId);
    Quiz findByUserIdAndFinished(String userId, boolean active);

}
