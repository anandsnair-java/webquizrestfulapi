package com.anandsurendran.webquizrestfulapi.api;

import com.anandsurendran.webquizrestfulapi.entity.AnswerResponse;
import com.anandsurendran.webquizrestfulapi.entity.QuizQuestion;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api")
public class QuizController {

    private static final String QUIZ_NOT_FOUND_MESSAGE = "Requested question not found";

    private static final AnswerResponse RIGHT_ANSWER_RESPONSE = new AnswerResponse(true, "Congratulations, you're right!");
    private static final AnswerResponse WRONG_ANSWER_RESPONSE = new AnswerResponse(false, "Wrong answer! Please, try again.");


    ArrayList<QuizQuestion> quizQuestions = new ArrayList<>();
    private static AtomicInteger id = new AtomicInteger();

//    @GetMapping(path = "/quiz")
//    public QuizQuestion getQuiz() {
//        QuizQuestion defaultQuestion = new QuizQuestion(
//                id.getAndIncrement(),
//                "The Java Logo",
//                "What is depicted on the Java logo?",
//                new String[]{"Robot", "Tea leaf", "Cup of coffee", "Bug"},
//                2
//        );
//        return defaultQuestion;
//    }

    @PostMapping(path = "/quiz")
    public AnswerResponse answerQuiz(@RequestParam String answer) {
        AnswerResponse response = new AnswerResponse();
        if (answer.equals("2")) {
            response.setSuccess(true);
            response.setFeedback("Congratulations, you're right!");
        } else {
            response.setSuccess(false);
            response.setFeedback("Wrong answer! Please, try again.");
        }
        return response;
    }

    @PostMapping(path = "/quizzes")
    public QuizQuestion addQuestion(@RequestBody QuizQuestion inputQuestion) {
        QuizQuestion inputQuestionWithID = new QuizQuestion(id.getAndIncrement(),
                inputQuestion.getTitle(),
                inputQuestion.getText(),
                inputQuestion.getOptions(),
                inputQuestion.getAnswer());
        quizQuestions.add(inputQuestionWithID);
        return quizQuestions.get(quizQuestions.size() - 1);
    }

    @GetMapping(path = "/quizzes/{id}")
    public QuizQuestion getQuizByID(@PathVariable int id) {
        if (id >= quizQuestions.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, QUIZ_NOT_FOUND_MESSAGE);
        }
        QuizQuestion questionAtID = quizQuestions.get(id);
        return questionAtID;
    }

    @GetMapping(path = "/quizzes")
    public ArrayList<QuizQuestion> getAllQuiz() {
        return quizQuestions;
    }

    @PostMapping(path = "/quizzes/{id}/solve")
    public AnswerResponse answerAQuiz(@PathVariable int id, @RequestParam int answer) {
        if (id >= quizQuestions.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, QUIZ_NOT_FOUND_MESSAGE);
        }
        if (quizQuestions.get(id).getAnswer() == answer) {
            return RIGHT_ANSWER_RESPONSE;
        }
        return WRONG_ANSWER_RESPONSE;
    }
}
