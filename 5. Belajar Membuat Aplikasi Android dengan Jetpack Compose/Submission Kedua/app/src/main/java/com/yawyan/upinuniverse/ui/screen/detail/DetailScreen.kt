package com.yawyan.upinuniverse.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yawyan.upinuniverse.R
import com.yawyan.upinuniverse.ViewModelFactory
import com.yawyan.upinuniverse.injection.Injection
import com.yawyan.upinuniverse.ui.common.UiState
import com.yawyan.upinuniverse.ui.theme.UpinUniverseTheme

@Composable
fun DetailScreen(
    UpinId: Long,
    viewModel: Detail = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateBack: () -> Unit,

    ) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { data ->
        when (data) {
            is UiState.Loading -> {
                viewModel.getById(UpinId)
            }

            is UiState.Success -> {
                val upinData = data.data
                DetailContent(
                    image = upinData.upin.photo,
                    title = upinData.upin.name,
                    desc = upinData.upin.desc,
                    onBackClick = navigateBack,
                )

            }

            is UiState.Error -> {

            }
        }
    }

}

@Composable
fun DetailContent(
    @DrawableRes image: Int,
    title: String,
    desc: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Box {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth()
            )
        }
        Surface(
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    UpinUniverseTheme {
        DetailContent(
            R.drawable.upin,
            "Upin",
            "Abang",
            onBackClick = {},
        )
    }
}