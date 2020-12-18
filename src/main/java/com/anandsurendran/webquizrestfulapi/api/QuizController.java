package com.anandsurendran.webquizrestfulapi.api;

import com.anandsurendran.webquizrestfulapi.api.repo.QuizRepository;
import com.anandsurendran.webquizrestfulapi.entity.AnswerArray;
import com.anandsurendran.webquizrestfulapi.entity.AnswerResponse;
import com.anandsurendran.webquizrestfulapi.entity.QuizQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class QuizController {

    private static final String QUIZ_NOT_FOUND_MESSAGE = "Requested question not found";

    private static final AnswerResponse RIGHT_ANSWER_RESPONSE = new AnswerResponse(true, "Congratulations, you're right!");
    private static final AnswerResponse WRONG_ANSWER_RESPONSE = new AnswerResponse(false, "Wrong answer! Please, try again.");
    @Autowired
    private final QuizRepository quizRepository;

    public QuizController(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @PostMapping(path = "/quizzes")
    public QuizQuestion addQuestion(@Valid @RequestBody QuizQuestion inputQuestion) {
        return quizRepository.save(inputQuestion);
    }

    @GetMapping(path = "/quizzes/{id}")
    public QuizQuestion getQuizByID(@PathVariable int id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,QUIZ_NOT_FOUND_MESSAGE));
    }

    @GetMapping(path = "/quizzes")
    public List<QuizQuestion> getAllQuiz() {
        return (List<QuizQuestion>) quizRepository.findAll();
    }

    @PostMapping(path = "/quizzes/{id}/solve")
    public AnswerResponse answerAQuiz(@PathVariable int id, @Valid @RequestBody AnswerArray answer) {
        QuizQuestion answeredQuestion = quizRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, QUIZ_NOT_FOUND_MESSAGE));
        int[] rightAnswers = answeredQuestion.getAnswer();
        int[] answerArray = answer.getAnswer();
        if (rightAnswers.length == 0 && answerArray.length==0) {
            return RIGHT_ANSWER_RESPONSE;
        }
        if (rightAnswers.length>0 && answerArray.length>0) {
            Arrays.sort(rightAnswers);
            Arrays.sort(answerArray);
            if (Arrays.equals(rightAnswers,answerArray)) {
                return RIGHT_ANSWER_RESPONSE;
            }
        }
        return WRONG_ANSWER_RESPONSE;
    }
}
