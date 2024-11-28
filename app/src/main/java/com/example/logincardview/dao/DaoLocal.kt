package com.example.logincardview.dao

import com.example.logincardview.interfaces.InterfaceDao
import com.example.logincardview.models.Local
import com.example.logincardview.models.LocalRepository

class DaoLocal private constructor() : InterfaceDao{

    companion object {
        val myDao : DaoLocal by lazy {
            DaoLocal()
        }
    }

    override fun getDataLocals(): List<Local> = LocalRepository.locales


}