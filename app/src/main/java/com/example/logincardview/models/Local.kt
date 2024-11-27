package com.example.logincardview.models

class Local (
    var nombre : String,
    var direccion : String,
    var contacto : String,
    var valoracion: Int
    ) {

    override fun toString(): String {
        return "{" +
                "nombre : \"${nombre}\"" +
                "direccion : \"${direccion}\"" +
                "contacto : \"${contacto}\"" +
                "valoracion : \"${valoracion}\"" +
                "}"
    }
}