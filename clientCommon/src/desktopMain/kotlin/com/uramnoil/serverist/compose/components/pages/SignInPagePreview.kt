package com.uramnoil.serverist.compose.components.pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.uramnoil.serverist.application.auth.SignInUseCaseInputPort

@Preview
@Composable
private fun Preview() {
    SignInPage(
        { _, _ ->
            SignInUseCaseInputPort {

            }
        }
    ) {

    }
}