package com.example.moneymanager.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.test.moviebox.room.model.LoginTableModel

@Dao
interface DAOAccess {
    @Insert
    suspend fun insertData(loginTableModel: LoginTableModel)

    @Delete
    suspend fun delete(loginTableModel: LoginTableModel)

    @Query("SELECT * FROM Login")
    fun getLoginDetails() : LiveData<List<LoginTableModel>>
}