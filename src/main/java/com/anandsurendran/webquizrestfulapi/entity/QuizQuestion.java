package com.anandsurendran.webquizrestfulapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@ToString @AllArgsConstructor
public class QuizQuestion {
    @Getter @Setter
    private String title;
    @Getter @Setter
    private String text;
    @Getter @Setter
    private String[] options;
    @Getter @Setter @JsonIgnore
    private int answer;
}
