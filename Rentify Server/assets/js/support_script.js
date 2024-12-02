// =================== CẤU HÌNH VÀ BIẾN TOÀN CỤC ===================
const landlordId = localStorage.getItem('user_id');
console.log("landlord ID: ", landlordId);

const roomsApiUrl = `http://localhost:3000/api/rooms/${landlordId}`;
const supportApiUrl = `http://localhost:3000/api/support_mgr/list/${landlordId}`;

let originalData = []; // Lưu dữ liệu gốc


function loadData() {
    fetch(supportApiUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(responseData => {
            const data = responseData.data || [];
            console.log('Dữ liệu hỗ trợ:', data);
            originalData = data; // Lưu dữ liệu gốc
            renderTableData(originalData); // Hiển thị dữ liệu
        })
        .catch(error => console.error("Lỗi tải dữ liệu:", error));
}

function renderTableData(data) {
    const userTable = document.getElementById("user-table");
    userTable.innerHTML = data.length > 0
        ? data.map((item, index) => `
            <tr data-id="${item._id}" data-status="${item.status}">
                <td>${index + 1}</td>
                <td>${item._id || "Không có ID"}</td>
                <td>${item.title_support || "Không có dữ liệu"}</td>
                <td>${item.content_support || "Không có dữ liệu"}</td>
                <td class="status-column">${item.status === 1 ? "Đang đợi" : "Đã hoàn thành"}</td>
                <td class="text-center">
                    <button class="btn btn-primary btn-sm" data-show-list='${JSON.stringify(item)}' 
                        onclick="openDetailModal(this)">Xem chi tiết</button>
                    <button class="btn btn-warning btn-sm" onclick="editUser('${item._id}', ${item.status})">Sửa</button>
                </td>
            </tr>`).join("")
        : "<tr><td colspan='6'>Không có dữ liệu</td></tr>";
}


function loadRoomFilter() {
    fetch(roomsApiUrl)
        .then(response => response.json())
        .then(data => {
            const roomFilter = document.getElementById("roomFilter");
            roomFilter.innerHTML = `
            <option value="">Tất cả</option> <!-- Tùy chọn Tất cả -->
            <option value="supported">Đã hỗ trợ</option> <!-- Tùy chọn Đã hỗ trợ -->
            ${data.map(room => `<option value="${room._id}">${room.room_name}</option>`).join("")}
        `;
        })
        .catch(error => console.error("Lỗi tải danh sách phòng:", error));
}


/** Khi thay đổi phòng */
document.getElementById("roomFilter").addEventListener("change", (event) => {
    const selectedValue = event.target.value;

    // Lọc dữ liệu dựa trên bộ lọc
    let filteredData;
    if (selectedValue === "supported") {
        filteredData = originalData.filter(item => item.status === 0); // Đã hỗ trợ
    } else if (selectedValue) {
        filteredData = originalData.filter(item => item.room_id === selectedValue); // Theo phòng
    } else {
        filteredData = originalData; // Tất cả
    }

    renderTableData(filteredData); // Cập nhật giao diện
});

/** Khi nhấn nút sửa */
async function editUser(userId, currentStatus) {
    document.getElementById("editUserId").value = userId;
    document.getElementById("editRole").value = currentStatus;

    const editModal = new bootstrap.Modal(document.getElementById("editUserModal"));
    editModal.show();
}
// ** Khởi tạo dữ liệu khi trang tải */
document.addEventListener("DOMContentLoaded", () => {
    loadData(); // Tải dữ liệu hỗ trợ
    loadRoomFilter(); // Tải danh sách phòng
});

document.getElementById("editUserForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const userId = document.getElementById("editUserId").value;
    const status = parseInt(document.getElementById("editRole").value); // Trạng thái mới
    try {
        const response = await fetch(`/api/support_mgr/update/${userId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ status }),
        });
        const data = await response.json();

        if (response.ok) {
            // Cập nhật dữ liệu cục bộ trong originalData
            const updatedItem = originalData.find(item => item._id === userId);
            if (updatedItem) {
                updatedItem.status = status;
            }

            // Gọi renderTableData để cập nhật giao diện
            const selectedRoomId = document.getElementById("roomFilter").value;
            let filteredData;

            if (selectedRoomId === "supported") {
                filteredData = originalData.filter(item => item.status === 0);
            } else if (selectedRoomId) {
                filteredData = originalData.filter(item => item.room_id._id === selectedRoomId);
            } else {
                filteredData = originalData;
            }

            renderTableData(filteredData);

            // Ẩn modal sửa
            const editUserModal = bootstrap.Modal.getInstance(document.getElementById("editUserModal"));
            editUserModal.hide();
            alert("Cập nhật trạng thái thành công!");
        } else {
            const data = await response.json();
            throw new Error(data.message || "Cập nhật thất bại.");
        }
    } catch (error) {
        alert(`Lỗi: ${error.message}`);
    }
});


// Hiển thị modal chi tiết
function openDetailModal(button) {
    const showList = JSON.parse(button.getAttribute('data-show-list'));
    const modalContent = document.getElementById('modalContent');
    const showSupport = document.getElementById('showSupport');

    // Lấy thông tin Building từ building_id
    const buildingId = showList.building_id; // Đây là building_id trong model Support

    // Tạo một API gọi để lấy thông tin Building theo buildingId
    fetch(`http://localhost:3000/api/buildings/address/${buildingId}`)
        .then(response => response.json())
        .then(buildingData => {
            const building = buildingData.data;

            // Hiển thị ảnh (nếu có)
            modalContent.innerHTML = showList.image?.length
                ? showList.image.map(img => `<img src="/${img}" class="img-fluid mb-2" alt="Không thể tải ảnh">`).join("")
                : '<p>Không có ảnh để hiển thị.</p>';

            // Hiển thị thông tin yêu cầu hỗ trợ và thông tin Building
            showSupport.innerHTML = `
                <div class="table-responsive shadow-lg p-4 bg-white rounded">
    <h5 class="text-start mb-3 text-primary">Dữ liệu yêu cầu</h5>
    <table class="table table-bordered table-hover align-middle">
        <tbody>
            <tr class="table-light">
                <td class="fw-bold" style="width: 150px;">ID:</td>
                <td>${showList._id}</td>
            </tr>
            <tr>
                <td class="fw-bold">Title:</td>
                <td>${showList.title_support}</td>
            </tr>
            <tr class="table-light">
                <td class="fw-bold">Content:</td>
                <td>${showList.content_support}</td>
            </tr>
            <tr>
                <td class="fw-bold">Address:</td>
                <td>${building ? building.address : 'Không có địa chỉ'}</td>
            </tr>
            <tr class="table-light">
                <td class="fw-bold">Tên phòng:</td>
                <td id="roomName">Đang tải...</td>
            </tr>
        </tbody>
    </table>
</div>

            `;

            // Gọi API để lấy tên phòng
            fetch(`http://localhost:3000/api/support_mgr/room_name/${showList._id}`)
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        document.getElementById('roomName').innerText = data.data.roomName;
                    } else {
                        console.log('Không tìm thấy tên phòng');
                    }
                })
                .catch(error => console.error('Lỗi khi lấy tên phòng:', error));

            // Mở modal chi tiết
            new bootstrap.Modal(document.getElementById('detailModal')).show();
        })
        .catch(error => {
            console.error("Lỗi khi tải thông tin Building:", error);
            alert("Lỗi khi tải thông tin Building.");
        });
}