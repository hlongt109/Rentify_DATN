package com.rentify.user.app.databseLocal


// Lưu trữ dữ liệu cục bộ:
// - Cho phép ứng dụng lưu trữ và truy cập dữ liệu ngay cả khi không có kết nối internet. Thường dùng Room (SQLite) cho việc này.
// - Giúp tổ chức dữ liệu cục bộ theo bảng (table) giống như trong cơ sở dữ liệu truyền thống.
// Hỗ trợ xử lý dữ liệu ngoại tuyến:
//  - Giúp ứng dụng hoạt động mượt mà hơn bằng cách lưu cache dữ liệu nhận từ API và giảm bớt các yêu cầu không cần thiết lên server.

// =============================================================================

// - Để theo dõi dữ liệu từ API và biết khi nào cần cập nhật dữ liệu cục bộ,
//   cách đơn giản và tiện lợi nhất là sử dụng mô hình Repository Pattern kết hợp với Room và LiveData hoặc Flow.

// I. Giải pháp gợi ý:
// 1. Sử dụng Room + LiveData/Flow:
// -Room cung cấp khả năng theo dõi thay đổi dữ liệu từ database một cách reactive thông qua LiveData hoặc Flow.
//  Khi dữ liệu trong database thay đổi, các thành phần như ViewModel hoặc Fragment sẽ được thông báo tự động.
//  Dữ liệu từ Room sẽ được theo dõi liên tục, và nếu dữ liệu trên API thay đổi, bạn có thể cập nhật nó vào Room.

// 2. Sử dụng Repository để phối hợp giữa API và Room:
// - Repository sẽ là lớp trung gian kết hợp việc lấy dữ liệu từ API và Room Database.
//   Khi người dùng mở ứng dụng, Repository sẽ kiểm tra dữ liệu từ Room trước, sau đó gọi API để kiểm tra dữ liệu mới.
//   Nếu có dữ liệu mới từ API, dữ liệu trong Room sẽ được cập nhật.

// II. Các bước thực hiện:
// B1: Lấy dữ liệu từ API và Room thông qua Repository:
// - Khi gọi dữ liệu từ API, kiểm tra dữ liệu đã thay đổi so với dữ liệu hiện tại trong Room.
// - Nếu có dữ liệu mới, cập nhật lại Room Database.
// B2. Sử dụng LiveData hoặc Flow để theo dõi:
// - Sử dụng LiveData hoặc Flow để theo dõi dữ liệu từ Room. Khi dữ liệu trong Room thay đổi, giao diện người dùng sẽ được cập nhật ngay lập tức.


// ==== Đè mô tham khảo :D ==========================================================================

// 1. Tạo Repository:
// - Repository là lớp trung gian quản lý luồng dữ liệu từ cả API và Room Database.
// - Dữ liệu từ API sẽ được lấy và lưu vào Room để hiển thị lên UI.
// class UserRepository(private val apiService: ApiService, private val userDao: UserDao) {
//    // Lấy dữ liệu từ API và Room, trả về Flow để quan sát thay đổi dữ liệu
//    fun getUsers(): Flow<List<UserEntity>> = flow {
//        // Lấy dữ liệu cục bộ từ Room Database
//        val localData = userDao.getAllUsers()
//        emit(localData)
//
//        // Gọi API để kiểm tra xem có dữ liệu mới không
//        val apiData = apiService.getUsers()
//        if (apiData.isSuccessful) {
//            val usersFromApi = apiData.body()
//            // So sánh và cập nhật dữ liệu mới vào Room nếu cần
//            if (usersFromApi != null && usersFromApi != localData) {
//                userDao.insertUsers(usersFromApi)
//            }
//        }
//        // Emit dữ liệu mới từ Room
//        emit(userDao.getAllUsers())
//    }.flowOn(Dispatchers.IO) // Chạy trên luồng IO để không ảnh hưởng tới Main Thread
// }

// 2. Tạo DAO với Room:
// - DAO (Data Access Object) là lớp cung cấp các phương thức thao tác với cơ sở dữ liệu SQLite thông qua Room.
// @Dao
// interface UserDao {
//    // Truy vấn tất cả dữ liệu từ bảng "users"
//    @Query("SELECT * FROM users")
//    fun getAllUsers(): List<UserEntity>
//
//    // Thêm hoặc cập nhật danh sách người dùng trong Room
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertUsers(users: List<UserEntity>)
// }

// 3. Sử dụng trong ViewModel:
// - ViewModel sẽ quản lý dữ liệu từ Repository và cung cấp dữ liệu để UI quan sát thông qua Flow hoặc LiveData.
// class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
//
//    // Flow để theo dõi danh sách người dùng
//    val usersFlow: Flow<List<UserEntity>> = userRepository.getUsers().stateIn(
//        scope = viewModelScope,  // Sử dụng scope của ViewModel
//        started = SharingStarted.WhileSubscribed(5000), // Bắt đầu theo dõi khi có subscriber
//        initialValue = emptyList() // Dữ liệu khởi tạo là danh sách trống
//    )
// }

// 4. Quan sát dữ liệu từ View:
// - Trong Jetpack Compose, sử dụng collectAsState để lắng nghe và hiển thị dữ liệu từ Flow trong ViewModel.
// @Composable
// fun UserScreen(
//    userViewModel: UserViewModel = hiltViewModel() // Tự động lấy ViewModel với Hilt
// ) {
//    // Sử dụng collectAsState để lắng nghe Flow từ ViewModel
//    val users by userViewModel.usersFlow.collectAsState(initial = emptyList())
//
//    // Hiển thị dữ liệu trong giao diện người dùng
//    Column(modifier = Modifier.padding(16.dp)) {
//        if (users.isEmpty()) {
//            Text(text = "Không có người dùng nào", style = MaterialTheme.typography.h6)
//        } else {
//            users.forEach { user ->
//                Text(text = "${user.name} - ${user.email}")
//            }
//        }
//    }
// }