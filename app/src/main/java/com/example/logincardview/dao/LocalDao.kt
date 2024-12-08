package com.example.logincardview.dao

import com.example.logincardview.interfaces.InterfaceDao
import com.example.logincardview.models.Local
import com.example.logincardview.models.LocalRepository

class LocalDao private constructor() : InterfaceDao{

    companion object {
        val myDao : LocalDao by lazy {
            LocalDao()
        }
    }

    override fun getDataLocals(): List<Local> = LocalRepository.locales


}