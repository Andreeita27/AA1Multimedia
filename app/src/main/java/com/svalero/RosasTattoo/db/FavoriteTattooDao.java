package com.svalero.RosasTattoo.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavoriteTattooDao {

    @Query("SELECT * FROM favorite_tattoo ORDER BY tattooId DESC")
    List<FavoriteTattoo> getAll();

    @Query("SELECT * FROM favorite_tattoo WHERE tattooId = :tattooId LIMIT 1")
    FavoriteTattoo getByTattooId(long tattooId);

    @Insert
    void insert(FavoriteTattoo favoriteTattoo);

    @Update
    void update(FavoriteTattoo favoriteTattoo);

    @Delete
    void delete(FavoriteTattoo favoriteTattoo);

    @Query("DELETE FROM favorite_tattoo WHERE tattooId = :tattooId")
    void deleteByTattooId(long tattooId);
}