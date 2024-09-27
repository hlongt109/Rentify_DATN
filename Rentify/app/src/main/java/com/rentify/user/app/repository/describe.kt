package com.rentify.user.app.repository

// Xử lý dữ liệu từ cả local (Room) và remote (API)
// Repository có trách nhiệm lấy dữ liệu từ các nguồn khác nhau (API, cơ sở dữ liệu local) và trả về cho ViewModel.
// Repository cũng có thể thực hiện việc đồng bộ hóa dữ liệu giữa các nguồn khác nhau.
// Repository cung cấp các phương thức cho ViewModel để lấy, thêm, cập nhật hoặc xóa dữ liệu.
// ViewModel chỉ cần gọi các phương thức này mà không cần phải lo lắng về cách dữ liệu được lấy hoặc lưu trữ.

// Repository có thể chứa logic để quyết định từ nguồn dữ liệu nào nên lấy thông tin.
// Ví dụ, nếu dữ liệu không có sẵn trong cơ sở dữ liệu địa phương, nó có thể thực hiện một yêu cầu mạng để lấy dữ liệu mới và cập nhật cơ sở dữ liệu địa phương.
// Repository thường sử dụng các phương thức như LiveData hoặc Flow để thông báo cho ViewModel về sự thay đổi dữ liệu.

// ===================================================================
// class UserRepository(
//    private val apiService: ApiService,
//    private val userDao: UserDao
// ) {
//
//    suspend fun getUsers(): List<User> {
//        // Lấy dữ liệu từ API
//        val response = apiService.getUsers()
//        if (response.isSuccessful) {
//            response.body()?.let { users ->
//                // Lưu vào cơ sở dữ liệu
//                userDao.insertUsers(users)
//            }
//        }
//        // Lấy dữ liệu từ cơ sở dữ liệu
//        return userDao.getAllUsers()
//    }
// }