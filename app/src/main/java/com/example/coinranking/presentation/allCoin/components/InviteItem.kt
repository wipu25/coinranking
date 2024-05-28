package com.example.coinranking.presentation.allCoin.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coinranking.R
import com.example.coinranking.presentation.ui.theme.Black
import com.example.coinranking.presentation.ui.theme.CardBackground
import com.example.coinranking.presentation.ui.theme.CustomSpanStyle
import com.example.coinranking.presentation.ui.theme.InviteBackground
import com.example.coinranking.presentation.ui.theme.LightBlue
import com.example.coinranking.presentation.ui.theme.Typography
import com.example.coinranking.utilities.customShadow

@Preview
@Composable
fun InviteItem() {
    val uriHandler = LocalUriHandler.current
    val inviteString = buildAnnotatedString {
        append(stringResource(id = R.string.invite_friend_desc))
        append(" ")
        pushStringAnnotation(tag = "invite", annotation = "https://careers.lmwn.com/")
        withStyle(style = CustomSpanStyle.bodySmall.copy(color = LightBlue)) {
            append(stringResource(id = R.string.invite_friend_button))
        }

        pop()
    }

    Row(
        modifier = Modifier
            .padding(vertical = 6.dp, horizontal = 8.dp)
            .customShadow(offsetY = 2.dp, offsetX = 1.dp, cornersRadius = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = InviteBackground)
            .clickable { uriHandler.openUri("https://careers.lmwn.com/") }
            .padding(horizontal = 16.dp, vertical = 22.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(CardBackground)
                .padding(9.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.invite_icon),
                contentDescription = "coin",
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = inviteString, style = Typography.labelSmall, color = Black)
    }
}