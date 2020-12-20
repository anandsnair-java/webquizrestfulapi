package com.anandsurendran.webquizrestfulapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
public class AnswerResponse {
    @Getter
    @Setter
    private boolean success;
    @Getter @Setter
    private String feedback;
}
