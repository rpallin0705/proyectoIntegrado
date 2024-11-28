package com.example.logincardview.interfaces

import com.example.logincardview.models.Local

interface InterfaceDao {
    fun getDataLocals() : List<Local>
}