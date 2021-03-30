package com.example.moneymanager.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.moneymanager.room.LoginDatabase
import com.test.moviebox.room.model.LoginTableModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class RoomRepository {
    companion object {
        var loginDatabase : LoginDatabase? = null
        var loginTableModel : LiveData<List<LoginTableModel>>? = null
        private fun initializeDB(context: Context) : LoginDatabase {
            return LoginDatabase.getDatabaseClient(context)
        }

        fun insertData(context: Context,username : String, password : String){
            loginDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                val loginDetails = LoginTableModel(username,password)
                loginDatabase?.loginDao()?.insertData(loginDetails)
            }
        }

        fun delete(context: Context,id: Int, username : String, password: String){
            loginDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                val loginDetails = LoginTableModel(username,password)
                loginDetails.id = id
                loginDatabase?.loginDao()?.delete(loginDetails)
            }
        }

        fun getLoginDetails(context: Context) : LiveData<List<LoginTableModel>>? {
            loginDatabase = initializeDB(context)
            loginTableModel = loginDatabase?.loginDao()?.getLoginDetails()
            return loginTableModel
        }
    }
}