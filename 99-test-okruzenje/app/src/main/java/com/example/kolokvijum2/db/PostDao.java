package com.example.kolokvijum2.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.kolokvijum2.model.Post;

import java.util.List;

@Dao
public interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Post> posts);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Post post);

    @Query("SELECT * FROM posts LIMIT 1")
    Post getFirst();

    @Query("SELECT * FROM posts")
    List<Post> getAll();

    @Query("SELECT COUNT(*) FROM posts")
    int count();

    @Delete
    void delete(Post post);

    @Query("DELETE FROM posts WHERE rowid IN (SELECT rowid FROM posts LIMIT 1)")
    void deleteFirst();
}
