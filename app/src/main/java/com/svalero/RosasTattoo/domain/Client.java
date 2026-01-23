package com.svalero.RosasTattoo.domain;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Client {
    private long id;
    private String clientName;
    private String clientSurname;
    private String email;
    private String phone;
    private String birthDate;
    private boolean showPhoto;
}