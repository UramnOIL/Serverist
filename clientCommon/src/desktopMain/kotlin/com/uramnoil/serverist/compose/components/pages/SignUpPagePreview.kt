package com.uramnoil.serverist.compose.components.pages

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.uramnoil.serverist.application.auth.SignUpUseCaseInputPort

@Preview
@Composable
fun SignUpPagePreview() {
    SignUpPage(
        { coroutineContext, signUpUseCaseOutputPort ->
            SignUpUseCaseInputPort {

            }
        },
        {

        }
    )
}