package br.com.hellodev

import androidx.compose.ui.window.ComposeUIViewController
import br.com.hellodev.di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoin() }
) { App() }