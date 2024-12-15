import com.google.gson.annotations.SerializedName
import com.rentify.user.app.model.Building
import com.rentify.user.app.model.Room_post
import com.rentify.user.app.model.User

data class Contract(
    val _id: String,
    val manage_id: String,
    val user_id: List<User>?, // Thông tin người dùng
    val room_id: Room_post?, // Thông tin phòng
    val building_id: Building?,
    val photos_contract: List<String>?,
    val content: String,
    val start_date: String,
    val end_date: String,
    val status: Int,
    val createdAt: String,
    val duration: String,
    val startDay: String,
    val paymentCycle: String
)

data class ContractResponse(
    @SerializedName("_id")val _id: String,
    @SerializedName("building_id")val building_id: BuildingInfoOfContract,
    @SerializedName("content")val content: String,
    @SerializedName("created_at")val created_at: String,
    @SerializedName("end_date")val end_date: String,
    @SerializedName("manage_id")val manage_id: String,
    @SerializedName("photos_contract")val photos_contract: List<String>,
    @SerializedName("room_id")val room_id: RoomInfoOfContract,
    @SerializedName("start_date")val start_date: String,
    @SerializedName("status")val status: Int,
    @SerializedName("user_id")val user_id: List<UserInfoOfContract>
)

data class RoomInfoOfContract(
    @SerializedName("_id")val _id: String,
    @SerializedName("price")val price: Int,
    @SerializedName("room_name")val room_name: String,
    @SerializedName("room_type")val room_type: String
)

data class BuildingInfoOfContract(
    @SerializedName("_id")val _id: String,
    @SerializedName("nameBuilding")val nameBuilding: String
)

data class UserInfoOfContract(
    @SerializedName("_id")val _id: String,
    @SerializedName("name")val name: String
)