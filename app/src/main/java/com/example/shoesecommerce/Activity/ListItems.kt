package com.example.shoesecommerce.Activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.shoesecommerce.Model.ItemsModel

@Composable
fun PopularItem(items: List<ItemsModel>, pos: Int) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentHeight()
    ) {
        AsyncImage(
            model = items[pos].picUrl.firstOrNull(),
            contentDescription = items[pos].title,
            modifier = Modifier
                .width(175.dp)
                .background(
                    colorResource(com.example.shoesecommerce.R.color.lightBrown),
                    shape = RoundedCornerShape(10.dp)
                )
                .height(195.dp)
                .clickable {

                },
            contentScale = ContentScale.Crop
        )
        Text(
            text = items[pos].title,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 8.dp)
        )
        Row(
            modifier = Modifier
                .width(175.dp)
                .padding(top = 4.dp)
        ) {
            Row {
                Image(
                    painter = painterResource(id = com.example.shoesecommerce.R.drawable.star),
                    contentDescription = "Rating",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = items[pos].rating.toString(),
                    color = Color.Black,
                    fontSize = 15.sp
                )
            }
            Text(
                text = "$${items[pos].price}",
                color = colorResource(com.example.shoesecommerce.R.color.darkBrown),
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxSize(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ListItems(items: List<ItemsModel>) {
    LazyRow(
        modifier = Modifier
            .padding(top = 8.dp)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items.size) { index: Int ->
            PopularItem(items, index)
        }
    }
}

@Composable
fun ListItemsFullSize(items: List<ItemsModel>){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items.size) {row ->
            PopularItem(items, row)
        }
    }
}