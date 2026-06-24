package com.example.kolokvijum2.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.kolokvijum2.model.Country;

import java.util.List;

@Dao
public interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Country> countries);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Country country);

    @Query("SELECT * FROM countries ORDER BY id DESC LIMIT 1")
    Country getLast();

    @Query("SELECT COUNT(*) FROM countries")
    int count();

    @Query("SELECT * FROM countries")
    List<Country> getAll();

    @Delete
    void delete(Country country);
}
