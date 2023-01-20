package br.com.will.quizservice.service;

import br.com.will.quizservice.Repository.QuizRepository;
import br.com.will.quizservice.client.ImdbClient;
import br.com.will.quizservice.client.dto.MovieResponseDTO;
import br.com.will.quizservice.config.QuizConfig;
import br.com.will.quizservice.controller.dto.QuestionDTO;
import br.com.will.quizservice.controller.dto.QuizDTO;
import br.com.will.quizservice.domain.Movie;
import br.com.will.quizservice.domain.Question;
import br.com.will.quizservice.domain.Quiz;
import br.com.will.quizservice.exception.ActiveQuestionException;
import br.com.will.quizservice.exception.AlreadyStartedQuizException;
import br.com.will.quizservice.exception.QuestionNotFound;
import br.com.will.quizservice.exception.QuizActiveNotfoundException;
import br.com.will.quizservice.util.ConverterUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class QuizService {

  private final ImdbClient imdbClient;
  private final QuizRepository quizRepository;
  private ConverterUtil mapper;
  private QuizConfig quizConfig;

  public String start(String userId) {
    if (quizRepository.existsByFinishedAndAndUserId(Boolean.FALSE, userId)) {
      throw new AlreadyStartedQuizException(
          "Já Existe um quiz em curso , finalize antes de iniciar um novo.");
    }

    return quizRepository.save(Quiz.builder().userId(userId).build()).getId();
  }

  public QuestionDTO newQuestion(String userId) {
  log.info(userId);
    Quiz quiz = getActiveQuiz(userId);
    Question newQuestion = null;
    if (quiz.getQuestions() != null && quiz.getQuestions().stream().anyMatch(question -> question.getUserAnswer() == null)) {
      List<Question> questionList = quiz.getQuestions().stream().filter(question -> question.getUserAnswer() == null).toList();
        return mapper.convertTo(questionList.get(0), QuestionDTO.class);
    }else if(quiz.getQuestions() == null){
        newQuestion = generateQuiz(2);
    } else {
        newQuestion = generateQuiz(2);
        while (compare(quiz, newQuestion.getMovies())) {
          newQuestion = generateQuiz(2);
        }
    }

    quiz.getQuestions().add(newQuestion);
    save(quiz);
    return mapper.convertTo(newQuestion, QuestionDTO.class);
  }

  public void answer(String userId, String referenceId, String movieId) {
    Quiz quiz = getActiveQuiz(userId);
    if (quiz.getQuestions().stream().noneMatch(question -> question.getUserAnswer() == null)) {
      throw new QuestionNotFound("Questão não encontrada.");
    }
    quiz.getQuestions()
        .forEach(
            question -> {
              if (question.getReferenceId().equals(referenceId)) {
                question.setUserAnswer(movieId);
                save(quiz);
              }
            });

    if((int) quiz.getQuestions().stream().filter(question -> question.getIscorrect() == Boolean.FALSE).count() > 3){
      quiz.setFinished(Boolean.TRUE);
      save(quiz);
    }
  }

  public void finish(String userId){

    if (!quizRepository.existsByFinishedAndAndUserId(Boolean.FALSE, userId)) {
      throw new QuizActiveNotfoundException(
              "Não Existe nenhum quiz ativo no momento.");
    }

    Quiz quiz = getActiveQuiz(userId);
    quiz.setFinished(Boolean.TRUE);
    save(quiz);


  }

  public Quiz getActiveQuiz(String userId){
    return quizRepository.findByUserIdAndFinished(userId, Boolean.FALSE);
  }

  private boolean compare(Quiz quiz, List<Movie> newMovies) {
    return quiz.getQuestions().stream()
        .anyMatch(
            question ->
                (question.getMovies().stream()
                        .anyMatch(
                            movie -> movie.getImdbID().contains(newMovies.get(0).getImdbID())))
                    && (question.getMovies().stream()
                        .anyMatch(
                            movie -> movie.getImdbID().contains(newMovies.get(1).getImdbID()))));
  }

  public void save(Quiz quiz) {
    quizRepository.save(quiz);
  }

  public Question generateQuiz(Integer size) {
    ArrayList<Movie> movies = new ArrayList<>();

    getRandomImdbId(size)
        .forEach(
            item -> {
              MovieResponseDTO response = imdbClient.getMovie(item, "8ae0070f");
              log.info(response.toString());
              movies.add(
                  Movie.builder()
                      .title(response.title)
                      .genre(response.genre)
                      .year(response.year)
                      .type(response.type)
                      .rating(roundDouble(Double.parseDouble(response.imdbRating)))
                      .poster(response.poster)
                      .imdbID(response.imdbID)
                      .build());
            });

    return Question.builder().movies(movies).build();
  }

  public List<QuizDTO> getQuizzes() {
    return mapper.mapList(quizRepository.findAll(), QuizDTO.class);
  }

  public Quiz getQuiz(String id) {
    return quizRepository.findById(id).get();
  }

  private Collection<List<Movie>> divideList(List<Movie> movieDTOList, int size) {
    final AtomicInteger counter = new AtomicInteger(0);
    return movieDTOList.stream()
        .collect(Collectors.groupingBy(s -> counter.getAndIncrement() / size))
        .values();
  }

  private List<String> getRandomImdbId(Integer size) {
    ArrayList<String> sortedMovies = new ArrayList<>();
    while (sortedMovies.size() < size) {
      Random random = new Random();
      int randomNum = random.ints(0, quizConfig.getMovieList().size()).findFirst().getAsInt();
      if (!sortedMovies.contains(quizConfig.getMovieList().get(randomNum))) {
        sortedMovies.add(quizConfig.getMovieList().get(randomNum));
      }
    }
    return sortedMovies;
  }

  private double roundDouble(Double number) {
    return Math.round(number * 1000.0) / 1000.0;
  }
}
