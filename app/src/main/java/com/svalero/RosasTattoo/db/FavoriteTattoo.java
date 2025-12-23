package com.svalero.RosasTattoo.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_tattoo")
public class FavoriteTattoo {
    @PrimaryKey
    private long tattooId;
    private boolean instagram;

    public long getTattooId() {
        return tattooId;
    }

    public void setTattooId(long tattooId) {
        this.tattooId = tattooId;
    }

    public boolean isInstagram() {
        return instagram;
    }

    public void setInstagram(boolean instagram) {
        this.instagram = instagram;
    }
}