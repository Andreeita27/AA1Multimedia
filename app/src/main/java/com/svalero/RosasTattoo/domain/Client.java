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

    @Override
    public String toString() {
        return clientName + " " + clientSurname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientSurname() {
        return clientSurname;
    }

    public void setClientSurname(String clientSurname) {
        this.clientSurname = clientSurname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isShowPhoto() {
        return showPhoto;
    }

    public void setShowPhoto(boolean showPhoto) {
        this.showPhoto = showPhoto;
    }
}