package com.example.logincardview.dao

import com.example.logincardview.models.Local
import com.example.logincardview.models.LocalRepository

object DaoLocal2 {
    val myDao by lazy {
        getDataLocals()
    }

    private fun getDataLocals() : List<Local> = LocalRepository.locales


}