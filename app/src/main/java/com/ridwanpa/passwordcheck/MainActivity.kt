package com.ridwanpa.passwordcheck

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ridwanpa.passwordcheck.ui.theme.PasswordCheckTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val password by viewModel.password.collectAsState("")
            val score by viewModel.score.collectAsState(0)
            val strength by viewModel.strength.collectAsState(Strength.VERY_WEAK)
            PasswordCheckTheme {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxSize()
                ) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = password,
                        placeholder = { Text(text = "Password") },
                        singleLine = true,
                        onValueChange = { viewModel.changePassword(it) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = score.toFloat() / 100,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(50))
                            .height(8.dp),
                        backgroundColor = Color.LightGray,
                        color = when (strength) {
                            Strength.VERY_SECURE, Strength.SECURE -> Color.Green
                            Strength.VERY_STRONG, Strength.AVERAGE, Strength.STRONG -> Color.Yellow
                            else -> Color.Red
                        }
                    )
                }
            }
        }
    }
}
