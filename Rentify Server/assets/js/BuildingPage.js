let building = []
let userId = localStorage.getItem('user_id');

// Lấy thông tin các tòa nhà
const fetchBuildings = async () => {
    try {
        const response = await axios.get(`/api/buildings/${userId}`);
        console.log(response.data); // Kiểm tra cấu trúc dữ liệu

        if (Array.isArray(response.data)) {
            building = response.data;
            renderTable();
        } else {
            console.error('Dữ liệu không phải là mảng:', response.data);
        }
    } catch (error) {
        console.error('Lỗi khi lấy dữ liệu:', error);
    }
};

// Render danh sách tòa nhà và thêm sự kiện cho các nút
const renderTable = () => {
    const tableBody = document.getElementById("building-table-body");
    tableBody.innerHTML = "";

    building.forEach((item, index) => {
        const isEvenRow = index % 2 === 0;
        const row = document.createElement('tr');
        row.style.backgroundColor = isEvenRow ? '#ffffff' : '#fafafb';

        row.innerHTML = `
    <td style="vertical-align: middle;">${item.nameBuilding}</td>
    <td style="vertical-align: middle;">${item.manager_id.username}</td>
    <td style="vertical-align: middle;">${item.manager_id.phoneNumber || ""}</td>
    <td style="vertical-align: middle;">${item.address}</td>
    <td style="vertical-align: middle;">${item.number_of_floors}</td>
    <td style="vertical-align: middle;">${item.service.length > 0 ? "Có" : "Không"}</td>
    <td> 
        <button class="btn btn-primary shadow-button" style="font-size: 0.85rem;" data-id="${item._id}">Chi tiết</button>
        <button class="btn btn-primary shadow-button" style="font-size: 0.85rem;" data-id="${item._id}">Chỉnh sửa</button>
        <button class="btn btn-danger shadow-button" style="font-size: 0.85rem;" data-id="${item._id}" data-toggle="modal" data-target="#deleteBuildingModal">Xoá</button>
    </td>
`;
        console.log('Render dòng, ID:', item._id);


        // Thêm sự kiện cho nút "Xem chi tiết"
        const detailButton = row.querySelector('button[data-id]');
        if (!detailButton.dataset.initialized) {
            detailButton.dataset.initialized = true; // Đánh dấu nút đã được khởi tạo
            detailButton.addEventListener('click', (event) => {
                const buildingId = event.target.getAttribute('data-id');
                const services = item.service || []; // Lấy dịch vụ từ item
                toggleRoomDetails(buildingId, row, services); // Truyền services vào toggleRoomDetails
            });
        }

        // Thêm sự kiện cho nút "Chỉnh sửa"
        const editButton = row.querySelectorAll('button[data-id]')[1]; // Chỉnh sửa là nút thứ 2
        editButton.addEventListener('click', (event) => {
            const buildingId = event.target.getAttribute('data-id');
            const buildingData = JSON.stringify(item);
            window.location.href = `/landlord/AddBuildingPage?id=${buildingId}&data=${encodeURIComponent(buildingData)}`;
        });

        tableBody.appendChild(row);
    });
};

const toggleRoomDetails = async (buildingId, row, services) => {
    // Kiểm tra nếu hàng hiển thị phòng đã tồn tại
    const existingDetails = row.nextElementSibling;
    if (existingDetails && existingDetails.classList.contains('room-details')) {
        existingDetails.remove(); // Nếu tồn tại, xóa đi
        return;
    }

    try {
        // Gửi yêu cầu đến server
        const response = await axios.get(`/api/get-room/${buildingId}`);
        console.log(response.data);

        // Tạo hàng mới hiển thị danh sách phòng
        const roomDetails = document.createElement('tr');
        roomDetails.classList.add('room-details');

        let roomsHTML = '';
        if (Array.isArray(response.data) && response.data.length > 0) {
            roomsHTML = response.data.map(room => `
                <div class="room-item" style="background: rgba(255, 255, 255, 0.8); border: 1px solid #ddd; padding: 10px; margin: 5px;" data-room-id="${room._id}">
                    <span>${room.room_name}</span>
                </div>
            `).join('');

            setTimeout(() => {
                const roomItems = roomDetails.querySelectorAll('.room-item');
                roomItems.forEach(roomItem => {
                    roomItem.addEventListener('click', (event) => {
                        const roomId = event.currentTarget.getAttribute('data-room-id');
                        window.location.href = `/landlord/UpdateRoom?buildingId=${buildingId}&roomId=${roomId}`;
                    });
                });
            }, 0);
        } else {
            // Nếu không có phòng
            roomsHTML = `<p style="margin: 5px; color: gray;">Hiện chưa có phòng nào.</p>`;
        }

        const addRoomButtonHTML = `
            <div class="add-room-container" style="margin-top: 10px;">
                <button class="btn btn-success add-room-button" data-building-id="${buildingId}" data-services='${JSON.stringify(services)}'>
                    Thêm phòng
                </button>
            </div>
        `;

        roomDetails.innerHTML = `<td colspan="7">
            <div class="rooms-container">
                ${roomsHTML}
                ${addRoomButtonHTML}
            </div>
        </td>`;

        // Thêm sự kiện cho nút "Thêm phòng"
        roomDetails.querySelector('.add-room-button').addEventListener('click', (event) => {
            const buildingId = event.target.getAttribute('data-building-id');
            const servicesData = event.target.getAttribute('data-services');
            window.location.href = `/landlord/AddRoom?buildingId=${buildingId}&services=${encodeURIComponent(servicesData)}`;
        });

        row.after(roomDetails);
    } catch (error) {
        // Xử lý khi không có phòng hoặc lỗi
        console.error('Lỗi khi lấy phòng:', error);

        const roomDetails = document.createElement('tr');
        roomDetails.classList.add('room-details');
        roomDetails.innerHTML = `<td colspan="7">
            <p style="margin: 5px; color: red;">Không thể tải dữ liệu phòng. Tòa nhà này có thể chưa có phòng.</p>
        </td>`;
        row.after(roomDetails);
    }
};

// Khởi tạo
const init = () => {
    fetchBuildings();
};

window.addEventListener('load', function () {
    const deleteRoomMessage = localStorage.getItem('deleteRoomMessage');

    if (deleteRoomMessage) {
        // Hiển thị thông báo Toastify
        Toastify({
            text: deleteRoomMessage,
            backgroundColor: "linear-gradient(to right, #00b09b, #96c93d)",
            duration: 3000,
        }).showToast();

        // Xóa thông báo khỏi localStorage để không hiển thị lại khi reload trang
        localStorage.removeItem('deleteRoomMessage');
    }
});

window.addEventListener('load', function () {
    const deleteRoomMessage = localStorage.getItem('updateRoomMessage');

    if (deleteRoomMessage) {
        // Hiển thị thông báo Toastify
        Toastify({
            text: deleteRoomMessage,
            backgroundColor: "linear-gradient(to right, #00b09b, #96c93d)",
            duration: 3000,
        }).showToast();

        // Xóa thông báo khỏi localStorage để không hiển thị lại khi reload trang
        localStorage.removeItem('updateRoomMessage');
    }
});

const deleteBuilding = async (buildingId) => {
    try {
        const response = await axios.delete(`/api/delete-buildings/${buildingId}`);

        // Nếu thành công
        console.log(response.data.message);

        // Hiển thị thông báo thành công
        Toastify({
            text: "Xóa tòa nhà thành công!",
            backgroundColor: "green",
            duration: 3000
        }).showToast();

        // Reload bảng
        await fetchBuildings();
    } catch (error) {
        // Xử lý lỗi
        if (error.response) {
            // Lỗi từ phía server (status code ngoài 2xx)
            console.error(error.response.data.error);

            Toastify({
                text: error.response.data.error,
                backgroundColor: "red",
                duration: 3000
            }).showToast();
        } else {
            // Lỗi khi gửi request hoặc lỗi khác
            console.error('Lỗi khi xóa tòa nhà:', error.message);

            Toastify({
                text: "Có lỗi xảy ra. Vui lòng thử lại!",
                backgroundColor: "red",
                duration: 3000
            }).showToast();
        }
    } finally {
        // Đóng modal
        $('#deleteBuildingModal').modal('hide');
    }
};


document.addEventListener("DOMContentLoaded", () => {
    let deleteBuildingId = null;
    $('#deleteBuildingModal').on('show.bs.modal', function (event) {
        const button = $(event.relatedTarget); 
        deleteBuildingId = button.data('id'); 
    });

    // Xử lý sự kiện khi nhấn "Xác nhận Xoá"
    document.getElementById('confirmDeleteBtn').addEventListener('click', () => {
        if (deleteBuildingId) {
            deleteBuilding(deleteBuildingId);
        } else {
            console.error('Không có ID nào để xóa!');
        }
    });

});

window.onload = init;