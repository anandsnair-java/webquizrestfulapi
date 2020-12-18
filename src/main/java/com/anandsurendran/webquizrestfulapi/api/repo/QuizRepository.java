package com.anandsurendran.webquizrestfulapi.api.repo;

import com.anandsurendran.webquizrestfulapi.entity.QuizQuestion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends CrudRepository<QuizQuestion, Integer> {

}
