package com.example.coinranking.presentation.allCoin.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.sharp.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.coinranking.R
import com.example.coinranking.presentation.ui.theme.SearchBackground
import com.example.coinranking.presentation.ui.theme.SearchIcon
import com.example.coinranking.presentation.ui.theme.SearchText
import com.example.coinranking.presentation.ui.theme.Typography
import com.example.coinranking.utilities.customShadow

@Composable
fun SearchRow(text: String,isSearching: Boolean,onClear: () -> Unit,onUpdateField: (String) -> Unit) {
    Row(
        Modifier
            .padding(16.dp)
            .then(
                if (isSearching) Modifier.customShadow(
                    offsetY = 4.dp,
                    shadowBlurRadius = 4.dp,
                    cornersRadius = 8.dp,
                    alpha = 0.25F
                ) else Modifier
            )
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primaryContainer),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            leadingIcon = {
                Icon(
                    Icons.Outlined.Search,
                    contentDescription = stringResource(R.string.search),
                    Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            trailingIcon = {
                if (isSearching)
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(SearchIcon)
                            .clickable { onClear() })
                    {
                        Icon(
                            Icons.Sharp.Close,
                            contentDescription = stringResource(R.string.search),
                            tint = SearchBackground
                        )
                    }
            },
            value = text,
            textStyle = Typography.bodyMedium,
            modifier = Modifier
                .weight(weight = 1f),
            onValueChange = {
                onUpdateField(it)
            },
            placeholder = { Text(text = stringResource(R.string.search), color = SearchText) },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            )
        )
    }
}