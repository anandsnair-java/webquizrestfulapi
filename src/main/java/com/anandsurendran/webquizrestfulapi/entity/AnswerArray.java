package com.anandsurendran.webquizrestfulapi.entity;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor @ToString
public class AnswerArray {
    @Getter @Setter
    private int[] answer;
}
