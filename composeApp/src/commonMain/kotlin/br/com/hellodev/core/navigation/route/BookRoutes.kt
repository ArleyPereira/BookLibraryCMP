package br.com.hellodev.core.navigation.route

import kotlinx.serialization.Serializable

sealed class BookRoutes {

    @Serializable
    data object Home : BookRoutes()

    @Serializable
    data class Details(val id: Int) : BookRoutes()

    @Serializable
    data class Manage(val id: Int? = null) : BookRoutes()

}