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

    @Override
    public String toString() {
        return professionalName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProfessionalName() {
        return professionalName;
    }

    public void setProfessionalName(String professionalName) {
        this.professionalName = professionalName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public boolean isBooksOpened() {
        return booksOpened;
    }

    public void setBooksOpened(boolean booksOpened) {
        this.booksOpened = booksOpened;
    }

    public int getYearsExperience() {
        return yearsExperience;
    }

    public void setYearsExperience(int yearsExperience) {
        this.yearsExperience = yearsExperience;
    }
}