package com.rentify.user.app.repository.SupportRepository

data class APISupportResponse(
    val status: Int,
    val message: String,
    val data: List<Support> // Danh sách các báo cáo
)
data class CreateSupportResponse(
    val message: String,
    val support: AddSupport
)

data class AddSupport(
    val userId: String,
    val roomId: String,
    val buildingId: String,
    val titleSupport: String,
    val contentSupport: String,
    val image: List<String>,
    val status: Int,
//    val created_at: String,
//    val updated_at: String
)


data class Support(
    val _id: String,
    val user_id: User,
    val room_id: Room,
    val building_id: Building,
    val title_support: String,
    val content_support: String,
    val image: List<String>, // Danh sách hình ảnh
    val status: Int,
    val created_at: String,
    val updated_at: String
)

data class User(
    val _id: String,
    val name: String,
    val email: String
)

data class Room(
    val _id: String,
    val room_name: String
)

data class Building(
    val _id: String,
    val building_name: String
)

//getRoom
data class ContractBuilding(
    val building_id: String,
    val building_name: String,
    val address: String
)

data class ContractRoom(
    val room_id: String,
    val room_number: String,
    val floor_area: Int,
    val price: Int,
    val status: Int,
    val building: ContractBuilding
)

data class ContractRoomData(
    val contract_id: String,
    val start_date: String,
    val end_date: String,
    val room: ContractRoom
)

data class ContractRoomResponse(
    val status: Int,
    val message: String,
    val data: List<ContractRoomData>
)

data class ContractResponse(
    val status: Int,
    val message: String,
    val data: List<Contract>
)

data class Contract(
    val manageId: String, // ID của nhân viên hoặc chủ tòa tạo hợp đồng
    val buildingId: String, // ID của tòa nhà
    val roomId: String, // ID của phòng
    val userIds: List<String>, // Danh sách ID của người sử dụng hợp đồng
    val photosContract: List<String>, // Danh sách các ảnh hợp đồng
    val content: String, // Nội dung hợp đồng
    val startDate: String, // Ngày bắt đầu hợp đồng
    val endDate: String, // Ngày kết thúc hợp đồng
    val status: Int = 0, // Trạng thái hợp đồng (0: còn hiệu lực, 1: hết hiệu lực)
    val createdAt: String // Ngày tạo hợp đồng
)
