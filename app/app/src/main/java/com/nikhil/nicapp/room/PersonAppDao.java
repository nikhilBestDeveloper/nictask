package com.nikhil.nicapp.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.nikhil.nicapp.model.Person;

import java.util.List;
import java.util.Optional;

@Dao
public
interface PersonAppDao {
    @Insert
    long  insert(Person person);

    @Update
    int update(Person person);

    @Delete
    void delete(Person person);

    @Query("SELECT * FROM persons")
    List<Person> getAllPersons();

    @Query("SELECT * FROM persons WHERE id = :id ORDER BY id desc")
    Optional<Person> getPersonById(int id);
}
