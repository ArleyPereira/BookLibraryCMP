package br.com.hellodev

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import br.com.hellodev.core.navigation.host.BookNavHost
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        BookNavHost(navHostController = rememberNavController())
    }
}