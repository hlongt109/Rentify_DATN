package com.rentify.user.app.view.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rentify.user.app.R


@Preview (showBackground = true)
@Composable
fun IntroScreen() {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.a),
            contentDescription = "Intro Screen",
            modifier = Modifier.size(300.dp)
        )
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "New Place, New Home!",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold
            )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Are you ready to uproot and start over in a new area?",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Rentify will help you on your journey!",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = { /*TODO*/ },
            modifier = Modifier
                .size(width = 300.dp, height = 50.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White)) {
            Text(text = "Login")

        }
        Spacer(modifier = Modifier.height(15.dp))

        ElevatedButton(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Color.White, // Set the container color to green
                contentColor = Color.Black // Set the text color to black
            ),
            modifier = Modifier
                .size(width = 300.dp, height = 50.dp)
                .clip(RoundedCornerShape(30.dp))
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(30.dp))
        ) {
            Text("Sign Up")
        }
    }


}
