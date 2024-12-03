let building = []
let contract = []
let landlord_id = localStorage.getItem('user_id');
let search = '';

// Lấy thông tin các tòa nhà
const fetchBuildings = async () => {
    try {
        const response = await axios.get(`/api/buildings/${landlord_id}`);
        console.log(response.data); // Kiểm tra cấu trúc dữ liệu

        if (Array.isArray(response.data)) {
            building = response.data
            renderBuildings();
        } else {
            console.error('Dữ liệu không phải là mảng:', response.data);
        }
    } catch (error) {
        console.error('Lỗi khi lấy dữ liệu:', error);
    }
};

// Lấy hợp đồng của một tòa nhà
const fetchContracts = async (buildingId, contractList) => {
    try {
        const response = await axios.get(`/api/contracts/building/${buildingId}`);
        console.log(response.data); // Kiểm tra cấu trúc dữ liệu hợp đồng

        if (Array.isArray(response.data)) {
            contract = response.data;  // Cập nhật lại danh sách hợp đồng
            renderContracts(contractList, contract); // Gọi hàm render hợp đồng sau khi lấy được dữ liệu
        } else {
            console.error('Dữ liệu hợp đồng không phải là mảng:', response.data);
        }
    } catch (error) {
        console.error('Lỗi khi lấy hợp đồng:', error);
    }
};



// Hàm render danh sách tòa nhà
const renderBuildings = () => {
    const buildingListContainer = document.querySelector('.building-list');
    buildingListContainer.innerHTML = ''; // Xóa danh sách cũ trước khi render mới

    building.forEach(item => {
        const buildingItem = document.createElement('div');
        buildingItem.classList.add('building-item', 'd-flex', 'align-items-center', 'justify-content-between', 'p-3', 'border', 'mb-2');
        buildingItem.innerHTML = `
            <div class="building-info d-flex align-items-center justify-content-between">
                <div class="d-flex align-items-center">
                    <img src="../../public/imageContracts/iconrooms.svg" alt="Building Image" class="building-img" />
                    <span class="building-name">${item.nameBuilding}</span>
                </div>
                <div class="actions d-flex align-items-center">
                    <button class="btn btn-primary shadow-button" style="font-size: 0.85rem;" onclick="window.location.href='/landlord/AddContractPage?buildingId=${item._id}'">Thêm hợp đồng</button>
                    <i class="fas fa-chevron-right arrow-icon actionss"></i>
                </div>
            </div>
        
            <div class="contracts-${item._id} contract-list" style="display: none;"></div>
        `;

        // Đảm bảo việc click vào tòa nhà sẽ toggle hiển thị hợp đồng
        buildingItem.querySelector('.actionss').addEventListener('click', () => {
            const arrowIcon = buildingItem.querySelector('.arrow-icon');
            const contractList = buildingItem.querySelector(`.contracts-${item._id}`);

            // Xoay icon khi click
            arrowIcon.classList.toggle('rotate');

            // Kiểm tra xem hợp đồng đã được hiển thị chưa
            if (contractList.style.display === 'none' || contractList.style.display === '') {
                // Hiển thị hợp đồng
                fetchContracts(item._id, contractList);
                contractList.style.display = 'block';
            } else {
                // Ẩn hợp đồng
                contractList.style.display = 'none';
            }
        });

        buildingListContainer.appendChild(buildingItem);
    });
};



// Hàm render hợp đồng vào phần tử hợp đồng
const renderContracts = (contractList, contract) => {
    contractList.innerHTML = ''; // Xóa hợp đồng cũ trước khi render mới

    contract.forEach(item => {
        const currentDate = new Date(); // Ngày hiện tại
        const endDate = new Date(item.end_date); // Ngày kết thúc hợp đồng

        // Kiểm tra nếu ngày hết hạn trùng với ngày hiện tại hoặc đã quá hạn
        const isExpired = (
            endDate.getFullYear() < currentDate.getFullYear() || // Năm đã qua
            (endDate.getFullYear() === currentDate.getFullYear() && endDate.getMonth() < currentDate.getMonth()) || // Tháng đã qua
            (endDate.getFullYear() === currentDate.getFullYear() && endDate.getMonth() === currentDate.getMonth() && endDate.getDate() <= currentDate.getDate()) // Ngày trùng hoặc đã qua
        );
        const status = isExpired ? 'expired' : 'active';

        const contractItem = document.createElement('div');
        contractItem.classList.add('contract-item', 'p-3', 'border', 'mb-2');

        // Thay đổi màu sắc nếu hợp đồng đã hết hạn hoặc trùng ngày
        if (isExpired) {
            contractItem.style.backgroundColor = '#f8d7da'; // Màu đỏ nhạt
        }

        const roomName = item.room_id ? item.room_id.room_name : 'Chưa có thông tin phòng';

        contractItem.innerHTML = `
            <div class="contract-info d-flex align-items-center">
                <span class="contract-id">Hợp đồng phòng ${roomName}</span>
            </div>
        `;

        contractItem.addEventListener('click', () => {
            // Điều hướng đến trang thêm hợp đồng và truyền buildingId và contractId qua URL
            window.location.href = `/landlord/AddContractPage?contractId=${item._id}&status=${status}`;
        });

        contractList.appendChild(contractItem);
    });
};


// Hàm khởi tạo
const init = async () => {
    await fetchBuildings();
};

window.addEventListener('load', function () {
    const deleteRoomMessage = localStorage.getItem('updateContractMessage');

    if (deleteRoomMessage) {
        // Hiển thị thông báo Toastify
        Toastify({
            text: deleteRoomMessage,
            backgroundColor: "linear-gradient(to right, #00b09b, #96c93d)",
            duration: 3000,
        }).showToast();

        // Xóa thông báo khỏi localStorage để không hiển thị lại khi reload trang
        localStorage.removeItem('updateContractMessage');
    }
});

window.addEventListener('load', function () {
    const deleteRoomMessage = localStorage.getItem('addContractMessage');

    if (deleteRoomMessage) {
        // Hiển thị thông báo Toastify
        Toastify({
            text: deleteRoomMessage,
            backgroundColor: "linear-gradient(to right, #00b09b, #96c93d)",
            duration: 3000,
        }).showToast();

        // Xóa thông báo khỏi localStorage để không hiển thị lại khi reload trang
        localStorage.removeItem('addContractMessage');
    }
});

window.addEventListener('load', function () {
    const deleteRoomMessage = localStorage.getItem('terminateContractMessage');

    if (deleteRoomMessage) {
        // Hiển thị thông báo Toastify
        Toastify({
            text: deleteRoomMessage,
            backgroundColor: "linear-gradient(to right, #00b09b, #96c93d)",
            duration: 3000,
        }).showToast();

        // Xóa thông báo khỏi localStorage để không hiển thị lại khi reload trang
        localStorage.removeItem('terminateContractMessage');
    }
});

window.onload = init;
