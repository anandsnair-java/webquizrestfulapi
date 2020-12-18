package com.anandsurendran.webquizrestfulapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;



@ToString @AllArgsConstructor @NoArgsConstructor
public class QuizQuestion {
    @Getter @Setter
    private int id;
    @Getter @Setter
    private String title;
    @Getter @Setter
    private String text;
    @Getter @Setter
    private String[] options;
    @Getter @Setter @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int answer;
}
