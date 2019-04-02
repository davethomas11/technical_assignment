package com.daveanthonythomas.technicalassessment.item.model

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ItemDAO {

    @Query("SELECT * FROM Item")
    fun getAll(): DataSource.Factory<Int, Item>

    @Query("SELECT * FROM Item ORDER BY name")
    fun getAllAlphabetic(): DataSource.Factory<Int, Item>

    @Query("SELECT * FROM Item ORDER BY createdOn")
    fun getAllOrderByCreation(): DataSource.Factory<Int, Item>

    @Insert
    fun addItem(item: Item)

    @Insert
    fun addItems(vararg item: Item)

    @Query("DELETE FROM Item")
    fun deleteAll()

    @Query("SELECT * FROM Item WHERE name = :name")
    fun getItem(name: String): Item
}