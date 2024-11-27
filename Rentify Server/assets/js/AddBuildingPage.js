let services = []
let staffs = []
let landlord_id = localStorage.getItem('user_id');
const urlParams = new URLSearchParams(window.location.search);
const buildingId = urlParams.get('id');
const buildingData = urlParams.get('data') ? JSON.parse(decodeURIComponent(urlParams.get('data'))) : null;

const fetchService = async () => {
    try {
        const response = await axios.get(`/api/get-services/${landlord_id}`);
        console.log(response.data); // Kiểm tra cấu trúc dữ liệu

        // Kiểm tra xem dữ liệu trả về có phải là mảng hay không
        if (Array.isArray(response.data)) {
            services = response.data;
            renderServices();
        } else {
            console.error('Dữ liệu không phải là mảng:', response.data);
        }
    } catch (error) {
        console.error('Lỗi khi lấy dữ liệu:', error);
    }
};

const fetchStaffs = async () => {
    try {
        const response = await axios.get(`/api/get-staffs/${landlord_id}`);
        console.log(response.data); // Kiểm tra cấu trúc dữ liệu

        // Kiểm tra xem dữ liệu trả về có phải là mảng hay không
        if (Array.isArray(response.data.data)) {
            staffs = response.data.data;
            renderStaffs();
        } else {
            console.error('Dữ liệu không phải là mảng:', response.data);
        }
    } catch (error) {
        console.error('Lỗi khi lấy dữ liệu:', error);
    }
};

const renderServices = (selectedServices = []) => {
    const container = document.getElementById("services-container");
    container.innerHTML = ""; // Xóa nội dung cũ

    services.forEach((service) => {
        const serviceItem = document.createElement("div");
        serviceItem.className = "service-item";
        serviceItem.textContent = service.name;
        serviceItem.dataset.id = service._id;

        // Đánh dấu các dịch vụ đã chọn
        if (selectedServices.some((selected) => selected._id === service._id)) {
            serviceItem.classList.add("active");
        }

        // Thêm sự kiện click để chọn/bỏ chọn
        serviceItem.addEventListener("click", () => {
            serviceItem.classList.toggle("active");
        });

        container.appendChild(serviceItem);
    });

    // Ghi log kiểm tra container sau khi render
    console.log("Dịch vụ đã render:", container.innerHTML);
    console.log("Dịch vụ đã chọn:", selectedServices);
};

const renderStaffs = () => {
    const staffList = document.getElementById("staff-list");
    staffList.innerHTML = ""; // Xóa nội dung cũ

    staffs.forEach((staff) => {
        const staffItem = document.createElement("li");
        staffItem.className = "dropdown-item";
        staffItem.textContent = staff.username; // Hiển thị tên người quản lý
        staffItem.dataset.id = staff._id; // Lưu ID vào thuộc tính data

        staffItem.addEventListener("click", () => {
            document.getElementById("manager_id").value = staff.username; // Hiển thị tên người quản lý
            document.getElementById("manager_id").dataset._id = staff._id; // Gán _id vào dataset
            staffList.style.display = "none"; // Ẩn danh sách sau khi chọn
        });

        staffList.appendChild(staffItem);
    });

    staffList.style.display = "block"; // Hiển thị danh sách nhân viên
};

// Hiển thị hoặc ẩn danh sách nhân viên khi người dùng click vào input
document.getElementById("manager_id").addEventListener("focus", () => {
    renderStaffs()
    fetchStaffs(); // Lấy danh sách nhân viên khi người dùng click vào input
});

document.getElementById("manager_id").addEventListener("blur", () => {
    setTimeout(() => {
        document.getElementById("staff-list").style.display = "none"; // Ẩn danh sách khi người dùng rời khỏi input
    }, 100);
});

// phần xử lý với địa chỉ

const addressInput = document.getElementById('address');
const addressList = document.getElementById('address-list');

// Hàm gọi API lấy địa chỉ
const fetchAddressSuggestions = async (query) => {
    try {
        const response = await axios.get(`https://nominatim.openstreetmap.org/search?q=${query}&countrycodes=VN&format=json&addressdetails=1`);
        console.log(response.data); // Kiểm tra dữ liệu trả về

        // Chuyển dữ liệu trả về vào hàm renderAddressSuggestions
        renderAddressSuggestions(response.data);
    } catch (error) {
        console.error('Lỗi khi lấy địa chỉ:', error);
    }
};

// Hàm hiển thị danh sách gợi ý địa chỉ
const renderAddressSuggestions = (addresses) => {
    addressList.innerHTML = ''; // Xóa danh sách cũ

    // Kiểm tra xem addresses có phải là mảng hợp lệ không
    if (Array.isArray(addresses) && addresses.length > 0) {
        addresses.forEach(address => {
            const listItem = document.createElement('li');
            listItem.className = 'dropdown-item';
            listItem.textContent = address.display_name; // Hiển thị tên địa chỉ
            listItem.addEventListener('click', () => {
                addressInput.value = address.display_name; // Chọn địa chỉ và điền vào ô input
                addressList.style.display = 'none'; // Ẩn danh sách sau khi chọn
            });

            addressList.appendChild(listItem);
        });
        addressList.style.display = 'block'; // Hiển thị danh sách nếu có kết quả
    } else {
        addressList.style.display = 'none'; // Ẩn danh sách nếu không có kết quả
    }
};

// Lắng nghe sự kiện nhập liệu
let debounceTimer;
addressInput.addEventListener('input', (e) => {
    const query = e.target.value.trim();

    clearTimeout(debounceTimer);
    if (query.length >= 3) { // Chỉ gọi API khi có ít nhất 3 ký tự
        debounceTimer = setTimeout(() => {
            fetchAddressSuggestions(query);
        }, 300); // Delay 300ms để giảm số lượng yêu cầu API
    } else {
        addressList.style.display = 'none'; // Ẩn danh sách khi không đủ ký tự
    }
});

// Ẩn danh sách gợi ý khi người dùng click ngoài
document.addEventListener('click', (e) => {
    // Kiểm tra xem click có xảy ra bên ngoài dropdown và input không
    if (!addressInput.contains(e.target) && !addressList.contains(e.target)) {
        addressList.style.display = 'none'; // Ẩn danh sách khi click bên ngoài
    }
});

// add building 
document.getElementById("addBuildingForm").addEventListener("submit", async (event) => {
    event.preventDefault(); // Ngăn form reload trang

    const landlord_id = localStorage.getItem('user_id'); // Lấy landlord_id từ localStorage
    const manager_id = document.getElementById("manager_id").dataset._id;
    const nameBuilding = document.getElementById("nameBuilding").value.trim();
    const address = document.getElementById("address").value.trim();
    const number_of_floors = parseInt(document.getElementById("number_of_floors").value);
    const description = document.getElementById("description").value.trim();
    const serviceElements = document.querySelectorAll(".service-item.active"); // Dịch vụ được chọn
    const service = Array.from(serviceElements).map((item) => item.dataset.id); // Lấy mảng ID dịch vụ

    // Kiểm tra các trường bắt buộc
    if (!manager_id || !nameBuilding || !address || !number_of_floors || service.length === 0) {
        return Toastify({
            text: "Vui lòng điền đầy đủ thông tin!",
            backgroundColor: "linear-gradient(to right, #ff5f6d, #ffc371)",
            duration: 3000,
        }).showToast();
    }

    // Dữ liệu gửi
    const data = {
        landlord_id,
        manager_id,
        service,
        nameBuilding,
        address,
        description,
        number_of_floors,
    };

    try {
        // Nếu đang ở chế độ chỉnh sửa
        let response;
        if (buildingId) {
            // API PUT cập nhật tòa nhà
            response = await axios.put(`/api/buildings/${buildingId}`, data);
        } else {
            // API POST thêm mới tòa nhà (khi buildingId không tồn tại)
            response = await axios.post('/api/add-building', data);
        }

        if (response.status === 200 || response.status === 201) {
            Toastify({
                text: "Cập nhật tòa nhà thành công!",
                backgroundColor: "linear-gradient(to right, #00b09b, #96c93d)",
                duration: 3000,
            }).showToast();
            // Điều hướng hoặc reset form
            window.location.href = "/landlord/BuildingPage";
            document.getElementById("addBuildingForm").reset();
        }
    } catch (error) {
        console.error("Lỗi khi cập nhật tòa nhà:", error);
        Toastify({
            text: "Lỗi khi cập nhật tòa nhà!",
            backgroundColor: "linear-gradient(to right, #ff5f6d, #ffc371)",
            duration: 3000,
        }).showToast();
    }
});

const init = async () => {
    await fetchService(); // Tải dữ liệu dịch vụ và nhân viên

    if (buildingData) {
        document.querySelector('h2').textContent = "Chỉnh Sửa Toà Nhà";

        document.getElementById('manager_id').value = buildingData.manager_id.username;
        document.getElementById('manager_id').dataset._id = buildingData.manager_id._id;
        document.getElementById('nameBuilding').value = buildingData.nameBuilding;
        document.getElementById('address').value = buildingData.address;
        document.getElementById('number_of_floors').value = buildingData.number_of_floors;
        document.getElementById('description').value = buildingData.description;

        // Gọi renderServices sau khi `services` đã sẵn sàng
        console.log("Danh sách dịch vụ đã chọn từ buildingData:", buildingData.service); // Ghi log kiểm tra
        renderServices(buildingData.service);

        const submitButton = document.querySelector('button[type="submit"]');
        submitButton.textContent = "Cập Nhật Toà Nhà";
    }
};


window.onload = init;