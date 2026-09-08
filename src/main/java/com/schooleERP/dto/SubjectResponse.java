package com.schooleERP.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SubjectResponse {

    private long id;

    private String subjectName;

    private String subjectCode;

    private String subjectDescription;
    private boolean active;


}
