package br.com.hellodev

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform