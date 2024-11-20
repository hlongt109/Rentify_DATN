package com.rentify.user.app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rentify.user.app.view.auth.ForgotPasswordScreen
import com.rentify.user.app.view.auth.LoginScreenApp
import com.rentify.user.app.view.auth.RegisterScreen
import com.rentify.user.app.view.intro.IntroScreen
import com.rentify.user.app.view.intro.SplashScreen
import com.rentify.user.app.view.navigator.AppNavigation
import com.rentify.user.app.view.staffScreens.ReportScreen.ReportScreen
import com.rentify.user.app.view.staffScreens.RoomDetailScreen.RoomDetailScreen
import com.rentify.user.app.view.staffScreens.addRoomScreen.AddRoomScreen
import com.rentify.user.app.view.staffScreens.building.BuildingScreen
import com.rentify.user.app.view.staffScreens.homeScreen.HomeScreen
import com.rentify.user.app.view.staffScreens.personalScreen.PersonalScreen
import com.rentify.user.app.view.userScreens.CategoryPostScreen.CategoryPostScreen
import com.rentify.user.app.view.staffScreens.BillScreenStaff.AddBillStaff
import com.rentify.user.app.view.staffScreens.BillScreenStaff.BillScreenStaff
import com.rentify.user.app.view.staffScreens.PersonalProfileScreen.PersonalProfileScreen
import com.rentify.user.app.view.staffScreens.postingList.PostingListScreen
import com.rentify.user.app.view.userScreens.AddPostScreen.AddPostScreen
import com.rentify.user.app.view.userScreens.BillScreen.BillScreen

import com.rentify.user.app.view.staffScreens.addPostScreen.AddPostScreens
import com.rentify.user.app.view.staffScreens.postingList.PostingListComponents.PostDetailScreen
import com.rentify.user.app.view.userScreens.CategoryPostScreen.CategoryPostScreen
import com.rentify.user.app.view.userScreens.IncidentReport.IncidentReportScreen
import com.rentify.user.app.view.userScreens.SearchRoomScreen.FilterScreen
import com.rentify.user.app.view.userScreens.SearchRoomScreen.PostRoomScreen
import com.rentify.user.app.view.userScreens.addIncidentReportScreen.AddIncidentReportScreen
import com.rentify.user.app.view.userScreens.cancelContract.CancelContractScreen
import com.rentify.user.app.view.userScreens.chatScreen.TinnhanScreen
import com.rentify.user.app.view.userScreens.contractScreen.ContractScreen
import com.rentify.user.app.view.userScreens.laundryScreen.LaundryScreen
import com.rentify.user.app.view.userScreens.laundrydetailScreen.LaundryDetailScreenScreen
import com.rentify.user.app.view.userScreens.messengerScreen.LayoutMessenger
import com.rentify.user.app.view.userScreens.paymentconfirmationScreen.PaymentConfirmationScreen
import com.rentify.user.app.view.userScreens.paymentscreen.PaymentScreen
import com.rentify.user.app.view.userScreens.personalScreen.LayoutPersonal
import com.rentify.user.app.view.userScreens.profileScreen.ProfileScreen
import com.rentify.user.app.view.userScreens.rentScreen.LayoutRent
import com.rentify.user.app.view.userScreens.rentalPost.RentalPostScreen
import com.rentify.user.app.view.userScreens.roomdetailScreen.LayoutRoomdetails
import com.rentify.user.app.view.userScreens.searchPostRoomScreen.SearchPostRoonmScreen
import com.rentify.user.app.view.userScreens.searchPostRoomateScreen.SearchPostRoomateScreen
import com.rentify.user.app.view.userScreens.serviceScreen.LayoutService
import com.rentify.user.app.view.userScreens.togetherScreen.TogetherScreen
import com.rentify.user.app.viewModel.RoomViewModel.RoomViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainNavigation()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Preview(showBackground = true)
    @Composable
    fun MainNavigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = ROUTER.HOME_STAFF.name) {
            composable(ROUTER.SPLASH.name) {
                SplashScreen(navController = navController)
            }
            composable(ROUTER.INTRO.name) {
                IntroScreen(navController = navController)
            }
            composable(ROUTER.LOGIN.name) {
                LoginScreenApp(navigator = navController)
            }
            composable(ROUTER.RESGITER.name) {
                RegisterScreen(navController = navController)
            }
            composable(ROUTER.FORGOTPASS.name) {
                ForgotPasswordScreen(navController = navController)
            }
            composable(ROUTER.HOME.name) {
                AppNavigation(navController)
            }
            composable(ROUTER.SERVICE.name) {
                LayoutService(navController = navController)
            }
            composable(ROUTER.RENT.name) {
                LayoutRent(navController = navController)
            }
            composable(ROUTER.MESSENGER.name) {
                LayoutMessenger(navController = navController)
            }
            composable(ROUTER.PERSONAL.name) {
                LayoutPersonal(navController = navController)
            }
            composable(ROUTER.ROOMDETAILS.name) {
                LayoutRoomdetails(navController = navController)
            }
            composable(ROUTER.PROFILE.name) {
                ProfileScreen(navController = navController)
            }
            composable(ROUTER.LAUDRY.name) {
                LaundryScreen(navController = navController)
            }
            composable(ROUTER.LAUDRYDETAIL.name) {
                LaundryDetailScreenScreen(navController = navController)
            }
            composable(ROUTER.CATEGORYPOST.name) {
                CategoryPostScreen( navController)
            }
            composable(ROUTER.SEARCHPOSTROOMATE.name) {
                SearchPostRoomateScreen(navController = navController)
            }
            composable(ROUTER.SEARCHPOSTROOM.name) {
                SearchPostRoonmScreen(navController = navController)
            }
            composable(ROUTER.ADDPOST.name) {
                AddPostScreen(navController = navController)
            }
            composable(ROUTER.INCIDENTREPORT.name) {
                IncidentReportScreen(navController = navController)
            }
            composable(ROUTER.ADDINCIDENTREPORT.name) {
                AddIncidentReportScreen(navController = navController)
            }
            composable(ROUTER.CONTRACT.name) {
                ContractScreen(navController = navController)
            }
            composable(ROUTER.CANCELCONTRACT.name) {
                CancelContractScreen(navController = navController)
            }
            composable(ROUTER.TogeTher.name) {
                TogetherScreen(navController = navController)
            }
            composable(ROUTER.TINNHAN.name) {
                TinnhanScreen(navController = navController)
            }
            composable(ROUTER.PaymentConfirmation.name) {
                PaymentConfirmationScreen(navController = navController)
            }
            composable(ROUTER.Payments.name) {
                PaymentScreen(navController = navController)
            }
            composable(ROUTER.ConTract.name) {
                com.rentify.user.app.view.userScreens.contract.ContractScreen(navController = navController)
            }
            composable(ROUTER.Search_room.name) {
                PostRoomScreen(navController = navController)
            }
            composable(ROUTER.Filter_room.name) {
                FilterScreen(navController = navController)
            }
            composable(ROUTER.Invoice_screen.name) {
                BillScreen(navController = navController)
            }
            composable(ROUTER.PersonalStaff.name) {
                PersonalScreen(navController = navController)
            }
            composable(ROUTER.ADDROOM.name) {
                AddRoomScreen(navController = navController)
            }
            composable(ROUTER.HOME_STAFF.name) {
                HomeScreen(navController = navController)
            }
            composable(ROUTER.BUILDING.name) {
                BuildingScreen(navController = navController)
            }
            composable(ROUTER.RoomDetailScreen.name) {
                RoomDetailScreen(navController = navController)
            }
            ///
            composable(ROUTER.RENTAL_POST.name) {
                RentalPostScreen(navController = navController)
            }
            composable(ROUTER.CONTRACT_STAFF.name) {
                com.rentify.manager.app.view.contract.ContractScreen(navController = navController)
            }
            composable(ROUTER.POSTING_STAFF.name) {
                PostingListScreen(navController = navController)
            }
            composable(ROUTER.BILL_STAFF.name) {
                BillScreenStaff(navController = navController)
            }
            composable(ROUTER.REPORT_STAFF.name) {
                ReportScreen(navController = navController)
            }
            composable(ROUTER.PersonalProfileScreen.name) {
                PersonalProfileScreen(navController = navController)
            }
            composable(ROUTER.ADDPOST_staff.name) {
                AddPostScreens(navController = navController)
            }
            composable("post_detail/{postId}") { backStackEntry ->
                val postId = backStackEntry.arguments?.getString("postId")
                if (postId != null) {
                    PostDetailScreen( navController = navController,postId = postId)
                }
            }
        }
    }

    enum class ROUTER {
        HOME,
        HOME_STAFF,
        SERVICE,
        RENT,
        MESSENGER,
        PERSONAL,
        ROOMDETAILS,
        INTRO,
        SPLASH,
        RESGITER,
        LOGIN,
        FORGOTPASS,
        PROFILE,
        LAUDRY,
        LAUDRYDETAIL,
        ADDPOST,
        CATEGORYPOST,
        SEARCHPOSTROOMATE,
        SEARCHPOSTROOM,
        TogeTher,
        TINNHAN,
        PaymentConfirmation,
        Payments,
        ConTract,
        ADDINCIDENTREPORT,
        FILTER,
        ADDEDITSERVICE,
        INCIDENTREPORT,
        CONTRACT,
        CANCELCONTRACT,
        Search_room,
        Filter_room,
        Invoice_screen,
        PersonalStaff,
        PersonalProfileScreen,
        ReportScreen,
        ADDROOM,
        BUILDING,
        RoomDetailScreen,
        BILL_STAFF,
        ADDBILL_STAFF,
        RENTAL_POST,
        CONTRACT_STAFF,
        POSTING_STAFF,
        REPORT_STAFF,
        ADDPOST_staff
    }
}
//@Composable
//fun SelectMedia(
//    onMediaSelected: (List<Uri>, List<Uri>) -> Unit
//) {
//    val selectedImages = remember { mutableStateListOf<Uri>() }
//    val selectedVideos = remember { mutableStateListOf<Uri>() }
//
//    // Launcher cho việc chọn nhiều ảnh từ album
//    val launcherImage = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.OpenMultipleDocuments(),
//        onResult = { uris ->
//            Log.d("LauncherImage", "Uris: $uris")
//            uris?.let {
//                selectedImages.addAll(it)
//                Log.d("SelectedImagesUpdated", selectedImages.toString())
//            }
//        }
//    )
//
//    val launcherVideo = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.OpenMultipleDocuments(),
//        onResult = { uris ->
//            Log.d("LauncherVideo", "Uris: $uris")
//            uris?.let {
//                selectedVideos.addAll(it)
//                Log.d("SelectedVideosUpdated", selectedVideos.toString())
//            }
//        }
//    )
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//
//    ) {
//        // Nút chọn nhiều ảnh từ album
//        Row(
//            modifier = Modifier.padding(5.dp),
//            verticalAlignment = Alignment.CenterVertically,
//        ) {
//            Row(
//                modifier = Modifier
//                    .clickable { launcherImage.launch(arrayOf("image/*")) } // "image/*" để chọn ảnh từ album
//                    .shadow(3.dp, shape = RoundedCornerShape(10.dp))
//                    .background(Color.White)
//                    .border(0.dp, Color(0xFFEEEEEE), RoundedCornerShape(10.dp))
//                    .padding(25.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.image),
//                    contentDescription = null,
//                    modifier = Modifier.size(30.dp)
//                )
//            }
//            Spacer(modifier = Modifier.width(15.dp))
//            Column {
//                Text(
//                    text = "Ảnh Phòng trọ",
//                    color = Color.Black,
//                    fontSize = 14.sp
//                )
//                Text(
//                    text = "Tối đa 10 ảnh",
//                    color = Color(0xFFBFBFBF),
//                    fontSize = 13.sp
//                )
//            }
//        }
//        // Hiển thị ảnh đã chọn
//        Spacer(modifier = Modifier.height(20.dp))
//        Text(
//            text = "Ảnh đã chọn:",
//            color = Color.Black,
//            fontSize = 16.sp
//        )
//        LazyRow(
//            modifier = Modifier.fillMaxWidth(),
//            contentPadding = PaddingValues(8.dp)
//        ) {
//            items(selectedImages) { imageUri ->
//                Image(
//                    painter = rememberImagePainter(imageUri),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(100.dp)
//                        .padding(4.dp)
//                )
//            }
//        }
//        Spacer(modifier = Modifier.height(17.dp))
//
//        // Nút chọn nhiều video từ album
//        Column(
//            modifier = Modifier
//                .clickable { launcherVideo.launch(arrayOf("video/*")) } // "video/*" để chọn video từ album
//                .fillMaxWidth()
//                .shadow(3.dp, shape = RoundedCornerShape(10.dp))
//                .background(Color.White)
//                .border(0.dp, Color(0xFFEEEEEE), RoundedCornerShape(10.dp))
//                .padding(25.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.video),
//                contentDescription = null,
//                modifier = Modifier.size(30.dp)
//            )
//            Spacer(modifier = Modifier.height(7.dp))
//            Text(
//                text = "Video",
//                color = Color.Black,
//                fontSize = 13.sp
//            )
//        }
//
//
//
//        // Hiển thị video đã chọn
//        Spacer(modifier = Modifier.height(20.dp))
//        Text(
//            text = "Video đã chọn:",
//            color = Color.Black,
//            fontSize = 16.sp
//        )
//        LazyRow(
//            modifier = Modifier.fillMaxWidth(),
//            contentPadding = PaddingValues(8.dp)
//        ) {
//            items(selectedVideos) { videoUri ->
//                // Sử dụng ExoPlayer hoặc một thư viện khác để hiển thị video
//                // Ví dụ dưới đây chỉ là placeholder
//                Box(
//                    modifier = Modifier
//                        .size(100.dp)
//                        .padding(4.dp)
//                        .background(Color.Gray, shape = RoundedCornerShape(8.dp)),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = "Video",
//                        color = Color.White,
//                        modifier = Modifier.align(Alignment.Center)
//                    )
//                }
//            }
//        }
//    }
//}
