package com.svalero.RosasTattoo.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteTattoo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavoriteTattooDao favoriteTattooDao();
}