package com.anandsurendran.webquizrestfulapi.api;

import com.anandsurendran.webquizrestfulapi.entity.AnswerResponse;
import com.anandsurendran.webquizrestfulapi.entity.QuizQuestion;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class QuizController {

    @GetMapping(path = "/quiz")
    public QuizQuestion getQuiz() {
        QuizQuestion defaultQuestion = new QuizQuestion(
                "The Java Logo",
                "What is depicted on the Java logo?",
                new String[]{"Robot", "Tea leaf", "Cup of coffee", "Bug"}
        );
        return defaultQuestion;
    }

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
}
