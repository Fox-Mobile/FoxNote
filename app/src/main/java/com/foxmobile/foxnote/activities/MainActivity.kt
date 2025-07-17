package com.foxmobile.foxnote.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.foxmobile.foxnote.navigation.NavigationRoot
import com.foxmobile.foxnote.ui.theme.FoxNoteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(SystemBarStyle.dark(0))
        super.onCreate(savedInstanceState)
        setContent {
            FoxNoteTheme {
                    NavigationRoot(
                        modifier = Modifier
                            .fillMaxSize()
                    )
            }
        }
    }
}