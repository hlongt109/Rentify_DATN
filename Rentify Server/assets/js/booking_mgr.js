document.addEventListener('DOMContentLoaded', function () {
    const landlordId = localStorage.getItem('user_id');
    const apiUrl = `/api/booking/list/${landlordId}`; // URL lấy danh sách booking của landlord

    // Hàm chuyển đổi trạng thái thành chuỗi hiển thị
    const getStatusText = (status) => {
        switch (status) {
            case 0: return '<span class="status-pending">Chưa xử lý</span>';
            case 1: return '<span class="status-completed">Đang xử lý</span>';
            case 2: return '<span class="status-rejected2">Đã xem</span>';
            case 3: return '<span class="status-rejected">Đã hủy</span>';
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
                    const rowId = `roomName-${index}`; // Tạo ID duy nhất cho mỗi hàng
                    const buildingId = `buildingName-${index}`;

                    // Tạo hàng mới trong bảng với tên người hẹn
                    const formatDateString = (dateStr) => {
                        const [day, month, yearAndTime] = dateStr.split('/');
                        return `${month}/${day}/${yearAndTime}`; // Chuyển đổi thành MM/DD/YYYY HH:mm
                    };

                    const row = document.createElement('tr');
                    row.classList.add('item'); // Thêm lớp 'item'
                    row.dataset.status = item.status; // Thêm thuộc tính 'data-status'
                    row.innerHTML = `
                        <td>${index + 1}</td> <!-- Thêm index vào bảng -->
                        <td>${userName}</td> 
                        <td id="${rowId}">Đang tải...</td> <!-- Tên phòng -->
                        <td id="${buildingId}">Đang tải...</td> <!-- Tên tòa nhà -->
                        <td id="phone-${item._id}">Đang tải...</td> <!-- Số điện thoại -->
                        <td class="check-in-date">
                            <div class="time">${new Date(formatDateString(item.check_in_date)).toLocaleTimeString('vi-VN', { timeZone: 'Asia/Ho_Chi_Minh' })}</div>
                            <div class="date">${new Date(formatDateString(item.check_in_date)).toLocaleDateString('vi-VN', { timeZone: 'Asia/Ho_Chi_Minh' })}</div>
                        </td>
                        </td>
                        <td>${getStatusText(item.status)}</td>
                        <td>
                            <button class="btn btn-warning btn-sm" onclick="handleEdit('${item._id}', ${item.status})">Sửa</button>
                            <button class="btn btn-danger btn-sm" onclick="handleDelete('${item._id}')">Xóa</button>
                        </td>
                    `;

                    reportTableBody.appendChild(row);


                    // Gọi API để lấy tên phòng
                    try {
                        const roomResponse = await fetch(`/api/booking/room_name/${item._id}`);
                        const roomData = await roomResponse.json();
                        if (roomData.success) {
                            document.getElementById(rowId).innerText = roomData.data.roomName; // Cập nhật tên phòng vào bảng
                        } else {
                            document.getElementById(rowId).innerText = 'Không xác định';
                        }
                    } catch (error) {
                        console.error('Lỗi khi lấy tên phòng:', error);
                        document.getElementById(rowId).innerText = 'Không xác định';
                    }
                    // Gọi API để lấy tên tòa nhà
                    try {
                        const buildingResponse = await fetch(`/api/booking/building_name/${item._id}`);
                        const buildingData = await buildingResponse.json();
                        if (buildingData.success) {
                            document.getElementById(buildingId).innerText = buildingData.data.buildingName;
                        } else {
                            document.getElementById(buildingId).innerText = 'Không xác định';
                        }
                    } catch (error) {
                        console.error('Lỗi khi lấy tên tòa nhà:', error);
                        document.getElementById(buildingId).innerText = 'Không xác định';
                    }
                    try {
                        const phoneResponse = await fetch(`/api/booking/user/phone/${item.user_id}`);
                        const phoneData = await phoneResponse.json();
                        document.getElementById(`phone-${item._id}`).innerText = phoneData.success ? phoneData.data : "Không có dữ liệu";
                    } catch (error) {
                        console.error("Lỗi khi lấy số điện thoại:", error);
                        document.getElementById(`phone-${item._id}`).innerText = "Lỗi";
                    }
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
// Hàm để lọc theo trạng thái
// Lắng nghe sự kiện click vào các item trong dropdown
document.querySelectorAll('#roomFilter .dropdown-item').forEach(item => {
    item.addEventListener('click', function (event) {
        event.preventDefault(); // Ngăn chặn hành động mặc định của liên kết

        // Lấy giá trị trạng thái từ thuộc tính data-status
        const status = this.getAttribute('data-status');

        // Cập nhật trạng thái trên button
        document.getElementById('statusButton').textContent = this.textContent;

        // Gọi hàm lọc dữ liệu với trạng thái được chọn
        filterByStatus(status);
    });
});

// Hàm lọc dữ liệu theo trạng thái
function filterByStatus(status) {
    // Đây là ví dụ đơn giản, bạn cần thay thế bằng cách thực hiện lọc trên dữ liệu của bạn
    console.log("Lọc theo trạng thái:", status);

    // Ví dụ: lọc các phần tử hoặc cập nhật giao diện người dùng (UI)
    // Dưới đây là cách bạn có thể lọc các dữ liệu trong một bảng hoặc danh sách
    const items = document.querySelectorAll('.item'); // Giả sử các item có lớp "item"

    items.forEach(item => {
        if (status === "" || item.dataset.status === status) {
            item.style.display = ''; // Hiển thị item nếu nó khớp với trạng thái
        } else {
            item.style.display = 'none'; // Ẩn item nếu nó không khớp
        }
    });
}




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

// Hàm xử lý xóa booking
async function handleDelete(bookingId) {
    const confirmation = confirm("Bạn có chắc chắn muốn xóa mục này không?");
    if (!confirmation) {
        return; // Người dùng từ chối xóa
    }

    try {
        // Gửi yêu cầu DELETE tới API
        const response = await fetch(`/api/booking/delete/${bookingId}`, {
            method: 'DELETE',
        });

        if (response.ok) {
            const result = await response.json();
            alert(result.message || "Xóa thành công");
            window.location.reload();
            //fetchData();
        } else {
            const errorResult = await response.json();
            alert(errorResult.message || "Có lỗi xảy ra khi xóa");
        }
    } catch (error) {
        console.error("Lỗi khi xóa:", error);
        alert("Đã xảy ra lỗi khi xóa mục");
    }
}
