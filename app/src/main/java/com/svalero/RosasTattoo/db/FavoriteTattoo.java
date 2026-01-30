package com.svalero.RosasTattoo.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_tattoo")
public class FavoriteTattoo {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long tattooId;
    private boolean instagram;

    private String style;
    private String tattooDate;
    private String imageUrl;
    private String tattooDescription;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getTattooId() { return tattooId; }
    public void setTattooId(long tattooId) { this.tattooId = tattooId; }

    public boolean isInstagram() { return instagram; }
    public void setInstagram(boolean instagram) { this.instagram = instagram; }

    public String getStyle() { return style; }
    public void setStyle(String style) { this.style = style; }

    public String getTattooDate() { return tattooDate; }
    public void setTattooDate(String tattooDate) { this.tattooDate = tattooDate; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getTattooDescription() { return tattooDescription; }
    public void setTattooDescription(String tattooDescription) { this.tattooDescription = tattooDescription; }
}