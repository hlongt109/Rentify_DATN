package com.rentify.user.app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rentify.user.app.view.auth.ForgotPasswordScreen
import com.rentify.user.app.view.auth.LoginScreenApp
import com.rentify.user.app.view.auth.RegisterScreen
import com.rentify.user.app.view.auth.components.PreForgotPass
import com.rentify.user.app.view.intro.IntroScreen
import com.rentify.user.app.view.intro.SplashScreen
import com.rentify.user.app.view.navigator.AppNavigation
import com.rentify.user.app.view.navigator.BottomNavigation
import com.rentify.user.app.view.navigator.ROUTER
import com.rentify.user.app.view.staffScreens.ListRommScreen.ListRoomScreen
import com.rentify.user.app.view.staffScreens.PersonalProfileScreen.PersonalProfileScreen
import com.rentify.user.app.view.staffScreens.ReportScreen.ReportScreen
import com.rentify.user.app.view.staffScreens.RoomDetailScreen.RoomDetailScreen
import com.rentify.user.app.view.staffScreens.UpdateRoomScreen.UpdateRoomScreen
//import com.rentify.user.app.view.staffScreens.RoomDetailScreen.RoomDetailScreen
import com.rentify.user.app.view.staffScreens.addRoomScreen.AddRoomScreen
import com.rentify.user.app.view.staffScreens.building.BuildingScreen
import com.rentify.user.app.view.staffScreens.homeScreen.HomeScreen
import com.rentify.user.app.view.staffScreens.personalScreen.PersonalScreen
import com.rentify.user.app.view.userScreens.CategoryPostScreen.CategoryPostScreen
import com.rentify.user.app.view.staffScreens.BillScreenStaff.AddBillStaff
import com.rentify.user.app.view.staffScreens.BillScreenStaff.BillScreenStaff
import com.rentify.user.app.view.staffScreens.BillScreenStaff.UpdateBillStaff
import com.rentify.user.app.view.staffScreens.NotificationScreen.Notification_staffScreen
import com.rentify.user.app.view.staffScreens.PersonalProfileScreen.PersonalProfileScreen
import com.rentify.user.app.view.staffScreens.ReportScreen.Components.DaHoanThanh
import com.rentify.user.app.view.staffScreens.ReportScreen.Components.ListSupportByRoom
import com.rentify.user.app.view.staffScreens.UpdatePostScreen.UpdatePostScreen
import com.rentify.user.app.view.staffScreens.addContractScreen.AddContractScreens
import com.rentify.user.app.view.staffScreens.postingList.PostingListScreen
import com.rentify.user.app.view.userScreens.AddPostScreen.AddPostScreen
import com.rentify.user.app.view.userScreens.BillScreen.BillScreen

import com.rentify.user.app.view.staffScreens.addPostScreen.AddPostScreens
import com.rentify.user.app.view.staffScreens.contract.contractComponents.ContractDetailScreen
import com.rentify.user.app.view.staffScreens.contract.contractComponents.ContractImageScreen
import com.rentify.user.app.view.staffScreens.postingList.PostingListComponents.PostDetailScreen
import com.rentify.user.app.view.staffScreens.scheduleScreen.ScheduleDetails
import com.rentify.user.app.view.staffScreens.scheduleScreen.ScheduleScreen
import com.rentify.user.app.view.userScreens.BaiDangYeuThich.BaiDangYeuThich
import com.rentify.user.app.view.userScreens.CategoryPostScreen.CategoryPostScreen
import com.rentify.user.app.view.userScreens.DieuKhoanChinhSach.DieuKhoanChinhSach
import com.rentify.user.app.view.userScreens.IncidentReport.IncidentReportScreen
import com.rentify.user.app.view.userScreens.QuanLiDichVu.QuanLiDichVu
import com.rentify.user.app.view.userScreens.SearchRoomScreen.FilterScreen
import com.rentify.user.app.view.userScreens.SearchRoomScreen.PostRoomScreen
import com.rentify.user.app.view.userScreens.SearchRoomateScreen.SearchRoomateComponent.SeachRoomateDetailScreen
import com.rentify.user.app.view.userScreens.SearchRoomateScreen.SearchRoommateScreen
import com.rentify.user.app.view.userScreens.SurroundingRoomsScreen.SearchMapScreen
import com.rentify.user.app.view.userScreens.SurroundingRoomsScreen.SurroundingRoomScreen
import com.rentify.user.app.view.userScreens.ThongBaoScreens.ThongBaoScreen
import com.rentify.user.app.view.userScreens.UpdatePostScreen.UpdatePostUserScreen
import com.rentify.user.app.view.userScreens.addIncidentReportScreen.AddIncidentReportScreen
import com.rentify.user.app.view.userScreens.appointment.AppointmentScreen
import com.rentify.user.app.view.userScreens.cancelContract.CancelContractScreen
import com.rentify.user.app.view.userScreens.chatScreen.TinnhanScreen
import com.rentify.user.app.view.userScreens.contractScreen.ContractImageScreen
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
import com.rentify.user.app.view.userScreens.roomdetailScreen.LayoutRoomdetails2
import com.rentify.user.app.view.userScreens.saleRoomScreen.SaleRoomPostScreen
import com.rentify.user.app.view.userScreens.searchPostRoomScreen.SearchPostRoonmScreen
import com.rentify.user.app.view.userScreens.searchPostRoomateScreen.Component.PostDetailUserScreen
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
        NavHost(navController = navController, startDestination = ROUTER.SPLASH.name) {
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
                CategoryPostScreen(navController)
            }
            composable(ROUTER.ThongBaoScreen.name) {
                ThongBaoScreen(navController)
            }
            composable(ROUTER.QuanLiDichVuUser.name) {
                QuanLiDichVu(navController)
            }
            composable(ROUTER.SEARCHPOSTROOMATE.name) {
                SearchPostRoomateScreen(navController = navController)
            }
            composable(ROUTER.SEARCHPOSTROOM.name) {
                SearchPostRoonmScreen(navController = navController)
            }
            composable(
                route = "${ROUTER.ADDPOST.name}?postType={postType}",
                arguments = listOf(
                    navArgument("postType") {
                        type = NavType.StringType
                        defaultValue = "default" // Giá trị mặc định nếu không truyền
                    }
                )
            ) {
                AddPostScreen(navController = navController)
            }
            composable(ROUTER.INCIDENTREPORT.name) {
                IncidentReportScreen(navController = navController)
            }
            composable(ROUTER.ADDINCIDENTREPORT.name) {
                AddIncidentReportScreen(navController = navController)
            }

            composable(
                route = "CONTRACT/{imageUrls}",
                arguments = listOf(navArgument("imageUrls") { type = NavType.StringType })
            ) { backStackEntry ->
                val imageUrlsJson = backStackEntry.arguments?.getString("imageUrls") ?: "[]"
                ContractImageScreen(navController, imageUrlsJson)
            }

            composable(ROUTER.CANCELCONTRACT.name) {
                CancelContractScreen(navController = navController)
            }
            composable(ROUTER.TogeTher.name) {
                TogetherScreen(navController = navController)
            }
            composable(
                "TINNHAN/{userId}/{userName}",
                arguments = listOf(
                    navArgument("userId") { type = NavType.StringType },
                    navArgument("userName") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val receiverId = backStackEntry.arguments?.getString("userId") ?: ""
                val receiverName = backStackEntry.arguments?.getString("userName") ?: ""
                TinnhanScreen(navController = navController, receiverId, name = receiverName)
            }

            composable("PaymentConfirmation/{amount}/{building_id}/{_id}/{room_id}") { backStackEntry ->
                val amount = backStackEntry.arguments?.getString("amount")?.toInt() ?: 0
                val buildingId = backStackEntry.arguments?.getString("building_id") ?: ""
                val invoiceId = backStackEntry.arguments?.getString("_id") ?: ""
                val roomId = backStackEntry.arguments?.getString("room_id") ?: ""
                PaymentConfirmationScreen(
                    invoiceId = invoiceId,
                    amount = amount,
                    buildingId = buildingId,
                    roomId = roomId,
                    navController = navController
                )
            }

            composable("Payments/{amount}/{buildingId}/{_id}") { backStackEntry ->
                val amount = backStackEntry.arguments?.getString("amount")?.toInt() ?: 0
                val buildingId = backStackEntry.arguments?.getString("buildingId") ?: ""
                val invoiceId = backStackEntry.arguments?.getString("_id") ?: ""
                PaymentScreen(
                    invoiceId = invoiceId,
                    amount = amount,
                    buildingId = buildingId,
                    navController = navController
                )
            }

            composable(ROUTER.ConTract.name) {
                com.rentify.user.app.view.userScreens.contract.ContractScreen(navController = navController)
            }
            composable(ROUTER.Search_room.name) {
                PostRoomScreen(navController = navController)
            }
            composable(ROUTER.Search_roommate.name) {
                SearchRoommateScreen(navController = navController)
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
                AddRoomScreen(navController = navController, buildingId = "")
            }

            composable(ROUTER.PersonalProfileScreen.name) {
                PersonalProfileScreen(navController = navController)
            }
            composable(ROUTER.ReportScreen.name) {
                ReportScreen(navController = navController)
            }
            composable("ADDROOM/{buildingId}") { backStackEntry ->
                val buildingId = backStackEntry.arguments?.getString("buildingId")
                AddRoomScreen(navController = navController, buildingId = buildingId)
            }
            composable(ROUTER.HOME_STAFF.name) {
                HomeScreen(navController = navController)
            }
            composable(ROUTER.BaiDangYeuThich.name) {
                BaiDangYeuThich(navController = navController)
            }
            composable(ROUTER.DieuKhoanChinhSach.name) {
                DieuKhoanChinhSach(navController = navController)
            }

            composable(ROUTER.BUILDING.name) {
                BuildingScreen(navController = navController)
            }
            composable("RoomDetailScreen/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")
                RoomDetailScreen(navController = navController, id = id ?: "")
            }

            composable("ListRoom/{buildingId}") { backStackEntry ->
                val buildingId = backStackEntry.arguments?.getString("buildingId")
                ListRoomScreen(navController = navController, buildingId = buildingId)
            }
            composable("ListSupportByRoom/{buildingId}") { backStackEntry ->
                val buildingId = backStackEntry.arguments?.getString("buildingId")
                ListSupportByRoom(navController = navController, buildingId = buildingId)
            }
            composable("UpdateRoomScreen/{id}/{buildingId}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""
                val buildingId = backStackEntry.arguments?.getString("buildingId") ?: ""
                UpdateRoomScreen(navController = navController, id = id, buildingId = buildingId)
            }

            composable("UpdateBillStaff/{invoiceId}") { backStackEntry ->
                val invoiceId = backStackEntry.arguments?.getString("invoiceId")
                UpdateBillStaff(navController = navController, invoiceId = invoiceId)
            }

            composable(ROUTER.RENTAL_POST.name) {
                RentalPostScreen(navController = navController, title = null)
            }

            composable(ROUTER.SaleRoomScreen.name) {
                SaleRoomPostScreen(navController = navController, title = null)
            }

            composable(
                route = "RENTAL_POST/{title}",
                arguments = listOf(navArgument("title") {
                    type = NavType.StringType
                    nullable = true
                })
            ) { backStackEntry ->
                val title = backStackEntry.arguments?.getString("title")
                RentalPostScreen(navController = navController, title = title)
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
                    PostDetailScreen(navController = navController, postId = postId)
                }
            }
            composable("SearchRoomate_detail/{postId}") { backStackEntry ->
                val postId = backStackEntry.arguments?.getString("postId")
                if (postId != null) {
                    SeachRoomateDetailScreen(navController = navController, postId = postId)
                }
            }
            composable("post_user_detail/{postId}") { backStackEntry ->
                val postId = backStackEntry.arguments?.getString("postId")
                if (postId != null) {
                    PostDetailUserScreen(navController = navController, postId = postId)
                }
            }
            composable("update_post_screen/{postId}") { backStackEntry ->
                val postId = backStackEntry.arguments?.getString("postId")
                if (postId != null) {
                    UpdatePostScreen(navController = navController, postId = postId)
                }
            }
            composable("update_post_user_screen/{postId}") { backStackEntry ->
                val postId = backStackEntry.arguments?.getString("postId")
                if (postId != null) {
                    UpdatePostUserScreen(navController = navController, postId = postId)
                }
            }
            composable(ROUTER.ADDBILL_STAFF.name) {
                AddBillStaff(navController = navController)
            }
            composable(ROUTER.ADDCONTRAC_STAFF.name) {
                AddContractScreens(navController = navController)
            }
            composable("contract_detail/{postId}") { backStackEntry ->
                val contractId = backStackEntry.arguments?.getString("postId")
                if (contractId != null) {
                    ContractDetailScreen(navController = navController, contractId = contractId)
                }

            }
            composable("contract_image_detail/{postId}") { backStackEntry ->
                val contractId = backStackEntry.arguments?.getString("postId")
                if (contractId != null) {
                    ContractImageScreen(navController = navController, contractId = contractId)
                }

            }
            //
            composable(ROUTER.Schedule.name) {
                ScheduleScreen(navController)
            }

            composable(
                "${ROUTER.ScheduleDetails.name}/{id}",
                arguments = listOf(navArgument("id") {type = NavType.StringType})
            ) {backStackEntry ->
                val idBooking = backStackEntry.arguments?.getString("id")
                idBooking?.let{
                    ScheduleDetails(id = idBooking, navController)
                }
            }

            //những màn hình thiên thêm
            composable(
                route = "ROOMDETAILS/{roomId}",
                arguments = listOf(navArgument("roomId") { type = NavType.StringType })
            ) { backStackEntry ->
                val roomId = backStackEntry.arguments?.getString("roomId")
                LayoutRoomdetails(navController = navController, roomId = roomId)
            }
            composable(
                route = "ROOMDETAILS2/{roomId}",
                arguments = listOf(navArgument("roomId") { type = NavType.StringType })
            ) { backStackEntry ->
                val roomId = backStackEntry.arguments?.getString("roomId")
                LayoutRoomdetails2(navController = navController, roomId = roomId)
            }
            composable(ROUTER.AppointmentScreen.name) {
                AppointmentScreen(navController = navController)
            }

            //phong forgot
            composable(ROUTER.PREFORGOT.name) {
                PreForgotPass(navController)
            }
            composable(
                ROUTER.PREFORGOT.name + "/{email}/{navigationType}",
                arguments = listOf(
                    navArgument("email") { type = NavType.StringType },
                    navArgument("navigationType") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val email = backStackEntry.arguments?.getString("email") ?: ""
                val navigationType = backStackEntry.arguments?.getString("navigationType") ?: ""
                PreForgotPass(navController, email = email, navigationType = navigationType)
            }
            composable(
                ROUTER.FORGOTPASS.name + "/{email}/{navigationType}",
                arguments = listOf(
                    navArgument("email") { type = NavType.StringType },
                    navArgument("navigationType") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val email = backStackEntry.arguments?.getString("email") ?: ""
                val navigationType = backStackEntry.arguments?.getString("navigationType") ?: ""
                ForgotPasswordScreen(navController, email, navigationType)
            }

            composable(ROUTER.BottomTest.name) {
                BottomNavigation(navController)
            }
            //ggmap
            composable(
                ROUTER.ROOMMAP.name + "/{latitude},{longitude}",
                arguments = listOf(
                    navArgument("latitude") { type = NavType.FloatType },
                    navArgument("longitude") { type = NavType.FloatType }
                )
            ) { backStackEntry ->
                val latitude = backStackEntry.arguments?.getFloat("latitude")?.toDouble() ?: 0.0
                val longitude = backStackEntry.arguments?.getFloat("longitude")?.toDouble() ?: 0.0
                SurroundingRoomScreen(
                    navController = navController,
                    latitude = latitude,
                    longitude = longitude
                )
            }
            composable(ROUTER.BottomTest.name) {
                BottomNavigation(navController)
            }
            composable(ROUTER.SEARCHMAP.name) {
                SearchMapScreen(navController = navController)
            }
            composable(ROUTER.Notification_staffScreen.name) {
                Notification_staffScreen(navController = navController)
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
        UpdateRoomScreen,
        RoomDetailScreen,
        BILL_STAFF,
        ADDBILL_STAFF,
        RENTAL_POST,
        CONTRACT_STAFF,
        POSTING_STAFF,
        REPORT_STAFF,
        ADDPOST_staff,
        Search_roommate,
        AppointmentScreen,
        BaiDangYeuThich,
        DieuKhoanChinhSach,
        //
        Schedule,
        ScheduleDetails,
        //những màn hình thiên thêm
        ADDCONTRAC_STAFF,
        ListSupportByRoom,
        QuanLiDichVuUser,
        BottomTest,
        SaleRoomScreen,
        PREFORGOT,
        ROOMMAP,
        SEARCHMAP,
        ThongBaoScreen,
        Notification_staffScreen
    }
}

