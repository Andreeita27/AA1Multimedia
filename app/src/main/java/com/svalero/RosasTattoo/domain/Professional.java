package com.svalero.RosasTattoo.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Professional {
    private long id;
    private String professionalName;
    private String birthDate;
    private String description;
    private String profilePhoto;
    private boolean booksOpened;
    private int yearsExperience;
}