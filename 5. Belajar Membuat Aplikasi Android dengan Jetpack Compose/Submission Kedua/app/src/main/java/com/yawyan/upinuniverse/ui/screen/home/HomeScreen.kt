package com.yawyan.upinuniverse.ui.screen.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yawyan.upinuniverse.R
import com.yawyan.upinuniverse.UpinApp
import com.yawyan.upinuniverse.ViewModelFactory
import com.yawyan.upinuniverse.injection.Injection
import com.yawyan.upinuniverse.model.FavUpin
import com.yawyan.upinuniverse.ui.common.UiState
import com.yawyan.upinuniverse.ui.comp.UpinIpinItem
import com.yawyan.upinuniverse.ui.theme.UpinUniverseTheme


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: Home = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    val query by viewModel.query


    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top
    ) {
        SearchBar(
            query = query,
            onQueryChange = viewModel::search,
            modifier = Modifier.fillMaxWidth()
        )
        viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    viewModel.getAllData()
                }

                is UiState.Success -> {
                    HomeContent(
                        upinList = uiState.data,
                        modifier = modifier,
                        navigateToDetail = navigateToDetail,
                    )
                }

                is UiState.Error -> {
                    val error = uiState.errorMessage
                    Log.e("HomeScreen", "Error: $error")
                }
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        placeholder = {
            Text(stringResource(R.string.search))
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)

    ) {

    }

}

@Composable
fun HomeContent(
    upinList: List<FavUpin>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(upinList) { data ->
            UpinIpinItem(
                photo = data.upin.photo,
                name = data.upin.name,
                modifier = Modifier.clickable {
                    navigateToDetail(data.upin.id)

                }

            )

        }
    }

}

@Preview(showBackground = true)
@Composable
fun UpinListPreview() {
    UpinUniverseTheme {
        UpinApp(

        )
    }
}