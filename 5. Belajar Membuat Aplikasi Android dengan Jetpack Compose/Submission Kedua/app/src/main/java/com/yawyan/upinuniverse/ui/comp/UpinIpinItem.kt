package com.yawyan.upinuniverse.ui.comp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yawyan.upinuniverse.R
import com.yawyan.upinuniverse.ui.theme.UpinUniverseTheme


@Composable
fun UpinIpinItem(
    photo: Int,
    name: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Image(
            painter = painterResource(photo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(160.dp)
        )
        Text(
            text = name,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(start = 3.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PetItemPreview() {
    UpinUniverseTheme {
        UpinIpinItem(name = "Upin", photo = R.drawable.upin)
    }
}