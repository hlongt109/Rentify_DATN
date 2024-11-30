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
import com.rentify.user.app.view.intro.IntroScreen
import com.rentify.user.app.view.intro.SplashScreen
import com.rentify.user.app.view.navigator.AppNavigation
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
import com.rentify.user.app.view.staffScreens.PersonalProfileScreen.PersonalProfileScreen
import com.rentify.user.app.view.staffScreens.UpdatePostScreen.UpdatePostScreen
import com.rentify.user.app.view.staffScreens.addContractScreen.AddContractScreens
import com.rentify.user.app.view.staffScreens.postingList.PostingListScreen
import com.rentify.user.app.view.userScreens.AddPostScreen.AddPostScreen
import com.rentify.user.app.view.userScreens.BillScreen.BillScreen

import com.rentify.user.app.view.staffScreens.addPostScreen.AddPostScreens
import com.rentify.user.app.view.staffScreens.contract.contractComponents.ContractDetailScreen
import com.rentify.user.app.view.staffScreens.contract.contractComponents.ContractImageScreen
import com.rentify.user.app.view.staffScreens.postingList.PostingListComponents.PostDetailScreen
import com.rentify.user.app.view.userScreens.CategoryPostScreen.CategoryPostScreen
import com.rentify.user.app.view.userScreens.IncidentReport.IncidentReportScreen
import com.rentify.user.app.view.userScreens.SearchRoomScreen.FilterScreen
import com.rentify.user.app.view.userScreens.SearchRoomScreen.PostRoomScreen
import com.rentify.user.app.view.userScreens.UpdatePostScreen.UpdatePostUserScreen
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
                CategoryPostScreen(navController)
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
            composable("UpdateRoomScreen/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")
                UpdateRoomScreen(navController = navController, id = id ?: "")
            }
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
            composable("post_user_detail/{postId}") { backStackEntry ->
                val postId = backStackEntry.arguments?.getString("postId")
                if (postId != null) {
                    PostDetailUserScreen( navController = navController,postId = postId)
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
            composable(ROUTER.ADDBILL_STAFF.name){
                AddBillStaff(navController = navController)
            }
            composable(ROUTER.ADDCONTRAC_STAFF.name){
                AddContractScreens(navController = navController)
            }
            composable("contract_detail/{postId}") { backStackEntry ->
                val contractId = backStackEntry.arguments?.getString("postId")
                if (contractId != null) {
                    ContractDetailScreen( navController = navController,contractId =contractId)
                }

            }
            composable("contract_image_detail/{postId}") { backStackEntry ->
                val contractId = backStackEntry.arguments?.getString("postId")
                if (contractId != null) {
                    ContractImageScreen( navController = navController,contractId =contractId)
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
        UpdateRoomScreen,
        RoomDetailScreen,
        BILL_STAFF,
        ADDBILL_STAFF,
        RENTAL_POST,
        CONTRACT_STAFF,
        POSTING_STAFF,
        REPORT_STAFF,
        ADDPOST_staff,
    ADDCONTRAC_STAFF
    }
}