package com.anandsurendran.webquizrestfulapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@ToString @NoArgsConstructor @Entity @EqualsAndHashCode
public class QuizQuestion {
    @Getter @Setter @Id @GeneratedValue
    private Integer id;
    @Getter @Setter @NotEmpty
    private String title;
    @Getter @Setter @NotEmpty
    private String text;
    @Getter @Setter @NotEmpty @Size(min = 2)
    private String[] options;
    @Getter @Setter @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int[] answer;


}
