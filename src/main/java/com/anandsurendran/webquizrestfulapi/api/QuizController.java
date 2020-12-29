package com.anandsurendran.webquizrestfulapi.api;

import com.anandsurendran.webquizrestfulapi.entity.AnswerArray;
import com.anandsurendran.webquizrestfulapi.entity.AnswerResponse;
import com.anandsurendran.webquizrestfulapi.entity.QuizQuestion;
import com.anandsurendran.webquizrestfulapi.repo.QuizRepository;
import com.anandsurendran.webquizrestfulapi.service.QuizModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class QuizController { //TODO Add api to get list of questions, count, delete by ID, exists

    private static final String QUIZ_NOT_FOUND_MESSAGE = "Requested question not found";

    private static final AnswerResponse RIGHT_ANSWER_RESPONSE = new AnswerResponse(true, "Congratulations, you're right!");
    private static final AnswerResponse WRONG_ANSWER_RESPONSE = new AnswerResponse(false, "Wrong answer! Please, try again.");
    @Autowired
    private final QuizRepository quizRepository;
    private final QuizModelAssembler assembler;

    public QuizController(QuizRepository quizRepository, QuizModelAssembler assembler) {
        this.quizRepository = quizRepository;
        this.assembler = assembler;
    }

//    @PostMapping(path = "/quizzes")
//    public QuizQuestion addQuestion(@Valid @RequestBody QuizQuestion inputQuestion) {
//        return quizRepository.save(inputQuestion);
//    }

    @PostMapping(path = "/quizzes")
    public ResponseEntity<EntityModel<QuizQuestion>> addQuestion(@Valid @RequestBody QuizQuestion inputQuestion) {
        EntityModel<QuizQuestion> entityModel = assembler.toModel(quizRepository.save(inputQuestion));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

//    @GetMapping(path = "/quizzes/{id}")
//    public QuizQuestion getQuizByID(@PathVariable int id) {
//        return quizRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, QUIZ_NOT_FOUND_MESSAGE));
//    }

    @GetMapping(path = "/quizzes/{id}")
    @Cacheable("quizzes")
    public EntityModel<QuizQuestion> getQuizByID(@PathVariable int id) {
        QuizQuestion foundQuiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, QUIZ_NOT_FOUND_MESSAGE));
        return assembler.toModel(foundQuiz);
    }

//    @GetMapping(path = "/quizzes")
//    public List<QuizQuestion> getAllQuiz() {
//        return (List<QuizQuestion>) quizRepository.findAll();
//    }

    @GetMapping(path = "/quizzes")
    @Cacheable("quizzes")
    public CollectionModel<EntityModel<QuizQuestion>> getAllQuiz() {

        List<QuizQuestion> quizQuestions = (List<QuizQuestion>) quizRepository.findAll();

        List<EntityModel<QuizQuestion>> allQuizList = quizQuestions.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(allQuizList,
                linkTo(methodOn(QuizController.class).getAllQuiz()).withSelfRel());
    }

    @PostMapping(path = "/quizzes/{id}/solve")
    public EntityModel<AnswerResponse> answerAQuiz(@PathVariable int id, @Valid @RequestBody AnswerArray answer) {
        EntityModel<AnswerResponse> rightAnswerEntityModel = EntityModel.of(RIGHT_ANSWER_RESPONSE,
                linkTo(methodOn(QuizController.class).getQuizByID(id)).withSelfRel(),
                linkTo(methodOn(QuizController.class).getAllQuiz()).withRel("quizzes"));

        EntityModel<AnswerResponse> wrongAnswerEntityModel = EntityModel.of(RIGHT_ANSWER_RESPONSE,
                linkTo(methodOn(QuizController.class).getQuizByID(id)).withSelfRel(),
                linkTo(methodOn(QuizController.class).getAllQuiz()).withRel("quizzes"));

        QuizQuestion answeredQuestion = quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, QUIZ_NOT_FOUND_MESSAGE));
        int[] rightAnswers = answeredQuestion.getAnswer();
        int[] answerArray = answer.getAnswer();
        if (rightAnswers.length == 0 && answerArray.length == 0) {
            return rightAnswerEntityModel;
        }
        if (rightAnswers.length > 0 && answerArray.length > 0) {
            Arrays.sort(rightAnswers);
            Arrays.sort(answerArray);
            if (Arrays.equals(rightAnswers, answerArray)) {
                return rightAnswerEntityModel;
            }
        }
        return wrongAnswerEntityModel;
    }
}
