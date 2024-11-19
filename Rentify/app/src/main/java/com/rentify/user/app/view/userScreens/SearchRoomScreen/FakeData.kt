package com.rentify.user.app.view.userScreens.SearchRoomScreen

import com.rentify.user.app.model.FakeModel.FakeService

import com.rentify.user.app.model.Post
import com.rentify.user.app.model.PostType
import com.rentify.user.app.model.Room
import com.rentify.user.app.model.FakeModel.FakeTypeArrange
import com.rentify.user.app.model.Model.Location
import com.rentify.user.app.ui.theme.arrangeDown
import com.rentify.user.app.ui.theme.arrangeTop
import com.rentify.user.app.ui.theme.clock
import com.rentify.user.app.ui.theme.home_arrange

class FakeData {
    val rooms = listOf(
        Room(
            landlordId = "landlord_001",
            buildingId = "building_001",
            roomType = "phòng trọ",
            description = "Phòng trọ sạch sẽ, thoáng mát",
            price = 2000000.0,
            size = "20m2",
            availabilityStatus = "available",
            videoRoom = "video_url_1",
            photosRoom = listOf("photo_url_1", "photo_url_2"),
            serviceIds = listOf("washing_machine", "fridge"),
            amenities = listOf("wifi", "air_conditioner"),
            serviceFees = listOf("electricity_fee", "water_fee"),
            limitPerson = 2,
            status = 0,
            createdAt = "2024-01-01",
            updatedAt = "2024-01-10"
        ),
        Room(
            landlordId = "landlord_002",
            buildingId = "building_002",
            roomType = "nguyên căn",
            description = "Nhà nguyên căn rộng rãi, thích hợp cho gia đình",
            price = 15000000.0,
            size = "100m2",
            availabilityStatus = "not available",
            videoRoom = "video_url_2",
            photosRoom = listOf("photo_url_3", "photo_url_4"),
            serviceIds = listOf("washing_machine", "fridge", "tv"),
            amenities = listOf("wifi", "air_conditioner", "garage"),
            serviceFees = listOf("electricity_fee", "water_fee", "garbage_fee"),
            limitPerson = 6,
            status = 1,
            createdAt = "2024-02-01",
            updatedAt = "2024-02-15"
        ),
        Room(
            landlordId = "landlord_003",
            buildingId = "building_003",
            roomType = "chung cư",
            description = "Chung cư cao cấp, view đẹp",
            price = 10000000.0,
            size = "60m2",
            availabilityStatus = "available",
            videoRoom = "video_url_3",
            photosRoom = listOf("photo_url_5", "photo_url_6"),
            serviceIds = listOf("fridge", "tv", "microwave"),
            amenities = listOf("wifi", "air_conditioner", "gym", "swimming_pool"),
            serviceFees = listOf("electricity_fee", "water_fee", "service_fee"),
            limitPerson = 4,
            status = 0,
            createdAt = "2024-03-01",
            updatedAt = "2024-03-10"
        ),
        Room(
            landlordId = "landlord_004",
            buildingId = "building_004",
            roomType = "homestay",
            description = "Homestay gần biển, tiện nghi đầy đủ",
            price = 5000000.0,
            size = "30m2",
            availabilityStatus = "available",
            videoRoom = "video_url_4",
            photosRoom = listOf("photo_url_7", "photo_url_8"),
            serviceIds = listOf("washing_machine", "fridge", "tv", "kitchen"),
            amenities = listOf("wifi", "air_conditioner", "kitchen", "balcony"),
            serviceFees = listOf("electricity_fee", "water_fee"),
            limitPerson = 3,
            status = 1,
            createdAt = "2024-04-01",
            updatedAt = "2024-04-15"
        ),
        Room(
            landlordId = "landlord_005",
            buildingId = "building_005",
            roomType = "chung cư mini",
            description = "Chung cư mini tiện lợi, gần trung tâm",
            price = 3000000.0,
            size = "25m2",
            availabilityStatus = "available",
            videoRoom = "video_url_5",
            photosRoom = listOf("photo_url_9", "photo_url_10"),
            serviceIds = listOf("fridge", "microwave"),
            amenities = listOf("wifi", "air_conditioner", "parking"),
            serviceFees = listOf("electricity_fee", "water_fee"),
            limitPerson = 2,
            status = 0,
            createdAt = "2024-05-01",
            updatedAt = "2024-05-10"
        )
    )

    val post1 = Post(
        userId = "user1",
        title = "Tìm người ở ghép phòng trọ quận 1",
        content = "Phòng sạch sẽ, an ninh, cần tìm bạn nữ ở ghép. Gần trung tâm và thuận tiện giao thông.",
        status = 1, // 1: bài đăng đang hoạt động
        video = listOf("https://example.com/video1.mp4"),
        photo = listOf("https://example.com/photo1.jpg", "https://example.com/photo2.jpg"),
        postType = PostType.ROOMATE,
        createdAt = "2023-10-01T10:00:00Z",
        updatedAt = "2023-10-02T12:00:00Z"
    )

    val post2 = Post(
        userId = "user2",
        title = "Tìm phòng trọ gần cao đẳng FPT",
        content = "Căn hộ tiện nghi, phù hợp cho người đi làm, sinh viên. Có nội thất đầy đủ, view đẹp.",
        status = 1,
        video = listOf("https://example.com/video2.mp4"),
        photo = listOf("https://example.com/photo3.jpg", "https://example.com/photo4.jpg"),
        postType = PostType.RENT,
        createdAt = "2023-09-25T08:30:00Z",
        updatedAt = "2023-09-27T09:00:00Z"
    )

    val post3 = Post(
        userId = "user3",
        title = "Tìm người ở ghép căn hộ tại quận 7",
        content = "Căn hộ cao cấp, có phòng gym và hồ bơi. Cần tìm bạn nam ở ghép, sạch sẽ và ngăn nắp.",
        status = 0, // 0: bài đăng chưa hoạt động
        video =  listOf("https://example.com/video2.mp4"),
        photo = listOf("https://example.com/photo5.jpg"),
        postType = PostType.ROOMATE,
        createdAt = "2023-08-15T15:45:00Z",
        updatedAt = "2023-08-16T16:00:00Z"
    )

    val post4 = Post(
        userId = "user4",
        title = "Cho thuê phòng trọ giá rẻ quận 5",
        content = "Phòng trọ rộng rãi, thoáng mát. Gần chợ, khu ăn uống và các tiện ích.",
        status = 1,
        video =  listOf("https://example.com/video2.mp4"),
        photo = listOf("https://example.com/photo6.jpg", "https://example.com/photo7.jpg"),
        postType = PostType.RENT,
        createdAt = "2023-07-10T10:20:00Z",
        updatedAt = "2023-07-12T11:30:00Z"
    )

    val post5 = Post(
        userId = "user5",
        title = "Cho thuê căn hộ cao cấp view đẹp",
        content = "Căn hộ full nội thất, view hồ bơi và thành phố. Có bảo vệ 24/7 và hầm để xe.",
        status = 1,
        video = listOf("https://example.com/video3.mp4", "https://example.com/video4.mp4"),
        photo = listOf("https://example.com/photo8.jpg"),
        postType = PostType.RENT,
        createdAt = "2023-06-05T14:50:00Z",
        updatedAt = "2023-06-06T15:00:00Z"
    )

    val listPost = listOf(post1, post2, post3, post4, post5)
    val listFakeTypeArrange_1s = listOf(
        FakeTypeArrange(icon = clock, title = "Tin mới"),
        FakeTypeArrange(icon = arrangeTop, title = "Giá cao nhất"),
        FakeTypeArrange(icon = arrangeDown, title = "Giá thấp nhất"),
    )
    val listFakeTypeArrange_2s = listOf(
        FakeTypeArrange(icon = home_arrange, title = "Phòng trọ"),
        FakeTypeArrange(icon = home_arrange, title = "Nguyên căn"),
        FakeTypeArrange(icon = home_arrange, title = "HomeStay"),
        FakeTypeArrange(icon = home_arrange, title = "Chung cư"),
        FakeTypeArrange(icon = home_arrange, title = "Chung cư mini")
    )
    val listFakeService = listOf(
        FakeService("Vệ sinh khép kín"),
        FakeService("Gác xếp"),
        FakeService("Ban công"),
        FakeService("Ra vào vân tay"),
        FakeService("Không chung chủ"),
        FakeService("Nuôi pet"),
    )

    val listFakeService_2 = listOf(
        FakeService("Điều hòa"),
        FakeService("Nóng lạnh"),
        FakeService("Kệ bếp"),
        FakeService("Tủ lạnh"),
        FakeService("Giường ngủ"),
        FakeService("Máy giặt"),
    )
}