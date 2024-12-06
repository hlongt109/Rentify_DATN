document.addEventListener('DOMContentLoaded', function () {
    const landlordId = localStorage.getItem('user_id');
    const apiUrl = `/api/booking/list/${landlordId}`; // URL lấy danh sách booking của landlord

    // Hàm chuyển đổi trạng thái thành chuỗi hiển thị
    const getStatusText = (status) => {
        switch (status) {
            case 0: return '<span class="status-pending">Chưa xử lý</span>';
            case 1: return '<span class="status-completed">Đã xử lý</span>';
            case -1: return '<span class="status-rejected">Đã hủy</span>';
            default: return '<span class="status-unknown">Không xác định</span>';
        }
    };

    // Hàm lấy dữ liệu từ API và cập nhật bảng
    const fetchData = async () => {
        try {
            // Lấy danh sách booking
            const response = await fetch(apiUrl);
            const result = await response.json();
            const reportTableBody = document.getElementById('report-table-body');

            reportTableBody.innerHTML = '';

            if (result.data && Array.isArray(result.data)) {
                for (let index = 0; index < result.data.length; index++) { // Sử dụng vòng lặp for với biến index
                    const item = result.data[index];

                    // Khai báo userResult ngoài try-catch để tránh lỗi
                    let userResult = null;

                    try {
                        // Gọi API để lấy thông tin người dùng
                        const userResponse = await fetch(`/api/booking/name?userId=${item.user_id}`);
                        userResult = await userResponse.json();
                        console.log(userResult); // Debug: Log kết quả trả về từ API
                    } catch (error) {
                        console.error('Lỗi khi gọi API:', error);
                    }

                    // Kiểm tra nếu dữ liệu trả về hợp lệ
                    const userName = userResult && userResult.data ? userResult.data : 'N/A'; // Lấy tên người hẹn từ API

                    // Tạo hàng mới trong bảng với tên người hẹn
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${index + 1}</td> <!-- Thêm index vào bảng -->
                        <td>${userName}</td> <!-- Hiển thị tên người hẹn -->
                        <td>${new Date(item.check_in_date).toLocaleString('vi-VN', { timeZone: 'Asia/Ho_Chi_Minh' })}</td>
                        <td>${getStatusText(item.status)}</td>
                        <td>
                            <button class="btn btn-info btn-sm" onclick="handleView('${item._id}')">Xem</button>
                            <button class="btn btn-warning btn-sm" onclick="handleEdit('${item._id}', ${item.status})">Sửa</button>
                            <button class="btn btn-danger btn-sm" onclick="handleDelete('${item._id}')">Xóa</button>
                        </td>
                    `;
                    reportTableBody.appendChild(row);
                }
            } else {
                console.error('Dữ liệu không hợp lệ hoặc không có dữ liệu.');
            }
        } catch (error) {
            console.error('Có lỗi khi lấy dữ liệu từ API:', error);
        }
    };

    // Gọi hàm fetchData khi trang tải xong
    fetchData();
});






// Biến để lưu ID của booking cần sửa
let currentBookingId = null;

// Hiển thị modal sửa trạng thái
const openEditStatusModal = (id, currentStatus) => {
    currentBookingId = id;
    document.getElementById('statusSelect').value = currentStatus; // Set trạng thái hiện tại vào dropdown
    document.getElementById('editStatusModal').style.display = 'block'; // Hiển thị modal
};

// Đóng modal sửa trạng thái
const closeEditStatusModal = () => {
    document.getElementById('editStatusModal').style.display = 'none'; // Ẩn modal
    currentBookingId = null; // Reset ID
};

// Lưu thay đổi trạng thái
const saveStatusChange = async () => {
    const newStatus = document.getElementById('statusSelect').value; // Lấy trạng thái mới từ dropdown

    try {
        // Gửi yêu cầu cập nhật trạng thái lên server
        const response = await fetch(`/api/booking/update/${currentBookingId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                status: newStatus,
            }),
        });

        const result = await response.json();

        if (response.ok) {
            alert('Trạng thái đã được cập nhật!');
            // Cập nhật lại bảng sau khi sửa thành công
            //fetchData();  // Đảm bảo gọi fetchData sau khi nó đã được định nghĩa
            window.location.reload();
            closeEditStatusModal(); // Đóng modal sau khi lưu
        } else {
            alert(`Có lỗi khi cập nhật trạng thái: ${result.message}`);
        }
    } catch (error) {
        console.error('Có lỗi khi kết nối API:', error);
        alert('Có lỗi khi cập nhật trạng thái');
    }
};

// Chức năng hiển thị modal khi nhấn "Sửa"
const handleEdit = (id, currentStatus) => {
    openEditStatusModal(id, currentStatus);
};
