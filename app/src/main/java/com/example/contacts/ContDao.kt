package com.example.contacts

import androidx.room.*

@Dao
interface ContDao {
    @get:Query("SELECT * FROM conts")
    val all: List<ContEntity>

    @Query("SELECT * FROM conts WHERE id = :id")
    fun getById(id: Long): ContEntity

    @Insert
    fun insert(todo: ContEntity): Long

    @Update
    fun update(todo: ContEntity)

    @Delete
    fun delete(todo: ContEntity)

}