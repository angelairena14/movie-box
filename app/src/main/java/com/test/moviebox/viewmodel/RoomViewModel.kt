package com.example.moneymanager.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moneymanager.repository.RoomRepository
import com.test.moviebox.room.model.LoginTableModel

class RoomViewModel : ViewModel(){
    var liveDataLogin : LiveData<List<LoginTableModel>>? = null

    fun insertData(context: Context, username : String, password : String){
        RoomRepository.insertData(context,username,password)
    }

    fun deleteData(context: Context, id : Int, username : String, password : String){
        RoomRepository.delete(context,id,username,password)
    }

    fun getLoginDetails(context: Context) : LiveData<List<LoginTableModel>>? {
        liveDataLogin = RoomRepository.getLoginDetails(context)
        return liveDataLogin
    }
}