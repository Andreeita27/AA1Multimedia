package com.svalero.RosasTattoo.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;

@Entity(
        tableName = "local_images",
        primaryKeys = {"type", "entityId"},
        indices = {@Index(value = {"type", "entityId"}, unique = true)}
)
public class LocalImage {

    @NonNull
    private String type;

    @NonNull
    private Long entityId; // ID del backend

    @NonNull
    private String imageUri;

    public LocalImage(@NonNull String type, @NonNull Long entityId, @NonNull String imageUri) {
        this.type = type;
        this.entityId = entityId;
        this.imageUri = imageUri;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

    @NonNull
    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(@NonNull Long entityId) {
        this.entityId = entityId;
    }

    @NonNull
    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(@NonNull String imageUri) {
        this.imageUri = imageUri;
    }
}