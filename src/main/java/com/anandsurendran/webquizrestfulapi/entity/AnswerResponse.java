package com.anandsurendran.webquizrestfulapi.entity;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor
public class AnswerResponse {
    @Getter
    @Setter
    private boolean success;
    @Getter @Setter
    private String feedback;
}
