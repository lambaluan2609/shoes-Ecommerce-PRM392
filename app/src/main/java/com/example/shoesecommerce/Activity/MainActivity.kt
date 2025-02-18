package com.example.shoesecommerce.Activity


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shoesecommerce.Model.CategoryModel
import com.example.shoesecommerce.Model.SliderModel
import com.example.shoesecommerce.R
import com.example.shoesecommerce.ViewModel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityScreen()
        }
    }
}

@Composable
fun MainActivityScreen() {
    val viewModel=MainViewModel()

    val banners= remember { mutableStateListOf<SliderModel>() }
    val categories = remember { mutableStateListOf<CategoryModel>() }

    var showBannerLoading by remember { mutableStateOf(true)  }
    var showCategoryLoading by remember { mutableStateOf(true) }



    //banner
    LaunchedEffect(Unit) {
        viewModel.loadBanner().observeForever {
            banners.clear()
            banners.addAll(it)
            showBannerLoading=false
        }
    }


    //category
    LaunchedEffect(Unit) {
        viewModel.loadCategory().observeForever {

            //Lắng nghe dữ liệu từ ViewModel, khi có dữ liệu mới, nó sẽ cập nhật danh sách categories.
            categories.clear()

            //Xóa danh sách cũ và cập nhật danh sách mới.
            categories.addAll(it)

            // Khi dữ liệu đã tải xong, biến này giúp giao diện biết để dừng hiển thị loading.
            showCategoryLoading=false
        }
    }

        //Welcome back, name, search and notification
    ConstraintLayout(modifier = Modifier.background(Color.White)) {
        val (scrollList, bottomMenu) = createRefs()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(scrollList) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
        ){
            item {
                Row ( modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 70.dp)
                    .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Column {
                        Text("Welcome Back", color = Color.Black)
                        Text("Lam Ba Luan",
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                            )
                    }
                    Row {
                        Image(
                            painter = painterResource(R.drawable.search_icon),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Image(
                            painter = painterResource(R.drawable.bell_icon),
                            contentDescription = null
                        )
                    }
                }
            }
            //Banner
            item {
                if (showBannerLoading){
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator() // Hiển thị vòng tròn loading nếu chưa có dữ liệu
                    }
                }else{
                    Banners(banners)    // Nếu đã có dữ liệu, gọi hàm Banners để hiển thị ảnh
                }
            }


            //Category
            item {
                Text(
                    text="Official Brand",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                        .padding(horizontal = 16.dp)
                )
            }

            item {
                if(showCategoryLoading){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        contentAlignment = Alignment.Center
                    )   {
                        CircularProgressIndicator()
                    }
                } else {
                    CategoryList(categories)
                }
            }
        }
    }
}

@Composable
fun CategoryList(categories: SnapshotStateList<CategoryModel>) {
    var selectedIndex by remember { mutableStateOf(-1) }
    val context = LocalContext.current

    LazyRow(modifier = Modifier
        .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp)
    ) {

        items(categories.size) {index->
            CategoryItem(item = categories[index], isSelected = selectedIndex == index,
                onItemClick = {
                    selectedIndex = index
                    Handler(Looper.getMainLooper()).postDelayed({

                    }, 500)
                }
            )
        }
    }
}


@Composable
fun CategoryItem(item: CategoryModel, isSelected: Boolean, onItemClick: () -> Unit) {
    Column (
        modifier = Modifier
            .clickable (onClick = onItemClick), horizontalAlignment = Alignment.CenterHorizontally
    ){
            AsyncImage(
                model = (item.picUrl),
                contentDescription = item.title,
                modifier = Modifier
                    .size(if (isSelected) 60.dp else 50.dp)
                    .background(
                        color = if (isSelected) colorResource(R.color.darkBrown) else colorResource(
                            R.color.lightBrown
                        ),
                        shape = RoundedCornerShape(100.dp)
                    ),
                contentScale = ContentScale.Inside,
                colorFilter = if(isSelected){
                    ColorFilter.tint(Color.White)
                } else {
                    ColorFilter.tint(Color.Black)
                }
            )
        Spacer(modifier = Modifier.padding(top=8.dp ))
        Text(
            text = item.title,
            color = colorResource(R.color.darkBrown),
            fontWeight = FontWeight.Bold
        )
        }

}


@Composable
fun SectionTitle(title: String, actionText: String) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title,
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = actionText,
            color = colorResource(R.color.darkBrown),
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Banners(banners: List<SliderModel>) {
    AutoSlidingCarousel(banners = banners)
} //Hàm Banners() nhận danh sách ảnh banners rồi truyền vào AutoSlidingCarousel() để hiển thị.

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AutoSlidingCarousel(
    modifier: Modifier=Modifier.padding(top=16.dp),
    pagerState: PagerState= remember { PagerState() },
    banners: List<SliderModel>
) {
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    Column(modifier = modifier.fillMaxSize() ) {
        HorizontalPager(count = banners.size, state = pagerState ) { page ->
            AsyncImage( // dùng để tải ảnh lên url 1 cách bất đồng bộ , dùng coil libary
                model = ImageRequest.Builder(LocalContext.current)  //Tạo một yêu cầu tải ảnh trong ngữ cảnh của ứng dụng (LocalContext.current).
                    .data(banners[page].url) //Lấy URL ảnh từ danh sách banners, sử dụng page làm chỉ mục.
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp, bottom = 8.dp)
                    .height(150.dp)
            )
        }
        DotIndicator(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .align(Alignment.CenterHorizontally),
            totalDots = banners.size,
            selectedIndex = if (isDragged)pagerState.currentPage else pagerState.currentPage, //Kiểm tra xem người dùng có đang kéo trang không
            dotSize = 8.dp
        )
    }
}
@Composable
fun DotIndicator(
    modifier: Modifier=Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectColor: Color= colorResource(R.color.darkBrown),
    unSelectColor: Color= colorResource(R.color.grey),
    dotSize: Dp
){
    LazyRow(
        modifier = modifier
            .wrapContentSize(),
    ) {
        items(totalDots){ index ->
            IndicatorDot(
                color = if (index==selectedIndex)selectColor else unSelectColor,
                size = dotSize
            )
            if (index!= totalDots-1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}
@Composable
fun IndicatorDot(
    modifier: Modifier=Modifier,
    size: Dp,
    color: Color
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}
