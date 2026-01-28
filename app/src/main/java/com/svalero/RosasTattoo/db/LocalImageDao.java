package com.svalero.RosasTattoo.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface LocalImageDao {

    @Query("SELECT imageUri FROM local_images WHERE type = :type AND entityId = :entityId LIMIT 1")
    String getImageUri(String type, Long entityId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsert(LocalImage localImage);

    @Query("DELETE FROM local_images WHERE type = :type AND entityId = :entityId")
    void delete(String type, Long entityId);
}