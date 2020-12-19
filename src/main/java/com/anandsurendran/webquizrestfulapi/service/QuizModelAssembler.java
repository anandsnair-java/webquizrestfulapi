package com.anandsurendran.webquizrestfulapi.service;

import com.anandsurendran.webquizrestfulapi.api.QuizController;
import com.anandsurendran.webquizrestfulapi.entity.QuizQuestion;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@Component
public class QuizModelAssembler implements RepresentationModelAssembler<QuizQuestion, EntityModel<QuizQuestion>> {

    @Override
    public EntityModel<QuizQuestion> toModel(QuizQuestion quizQuestion) {
        return EntityModel.of(quizQuestion,
                linkTo(methodOn(QuizController.class).getQuizByID(quizQuestion.getId())).withSelfRel(),
                linkTo(methodOn(QuizController.class).getAllQuiz()).withRel("quizzes"));
    }
}
