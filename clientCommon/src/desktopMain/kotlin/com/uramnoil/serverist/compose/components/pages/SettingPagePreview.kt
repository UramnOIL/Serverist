package com.uramnoil.serverist.compose.components.pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.uramnoil.serverist.application.user.UpdateUserCommandUseCaseInput
import com.uramnoil.serverist.application.user.UpdateUserCommandUseCaseInputPort

@Preview
@Composable
private fun Preview() {
    SettingPage { _, _ ->
        object : UpdateUserCommandUseCaseInputPort {
            override fun execute(input: UpdateUserCommandUseCaseInput) {
                TODO("Not yet implemented")
            }
        }
    }
}