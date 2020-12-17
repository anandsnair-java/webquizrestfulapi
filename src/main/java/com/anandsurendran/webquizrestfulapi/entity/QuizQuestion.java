package com.anandsurendran.webquizrestfulapi.entity;

import lombok.*;

@ToString @AllArgsConstructor
public class QuizQuestion {
    @Getter @Setter
    private String title;
    @Getter @Setter
    private String text;
    @Getter @Setter
    private String[] options;
}
