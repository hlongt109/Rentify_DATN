const amenities = [
    "Vệ sinh khép kín",
    "Gác xép",
    "Ra vào vân tay",
    "Nuôi pet",
    "Không chung chủ"
];
let selectedAmenities = [];
const params = new URLSearchParams(window.location.search);
const buildingId = params.get('buildingId');

const previewFiles = (input) => {
    const previewContainer = input.parentNode.querySelector('.preview');
    const iconContainer = input.parentNode.querySelector('i'); // Lấy icon chứa trong label
    const files = input.files;

    // Xóa nội dung cũ
    previewContainer.innerHTML = "";

    // Nếu có tệp được chọn
    if (files && files.length > 0) {
        // Ẩn icon khi có ảnh hoặc video
        iconContainer.style.display = 'none';

        Array.from(files).forEach((file) => {
            const reader = new FileReader();
            reader.onload = (e) => {
                const fileType = file.type.split('/')[0]; // Lấy loại tệp (image/video)

                if (fileType === 'image') {
                    const img = document.createElement("img");
                    img.src = e.target.result;
                    previewContainer.appendChild(img);
                } else if (fileType === 'video') {
                    const video = document.createElement("video");
                    video.src = e.target.result;
                    video.controls = true; // Cho phép người dùng điều khiển video
                    video.autoplay = true; // Video tự động chạy
                    video.muted = true; // Video không có âm thanh khi autoplay (nếu cần)
                    previewContainer.appendChild(video);
                }
            };
            reader.readAsDataURL(file);
        });
    } else {
        // Nếu không có tệp nào, vẫn hiển thị icon
        iconContainer.style.display = 'block';
    }

};

// Lấy các tham số từ URL
const getQueryParams = () => {
    let services = [];

    const servicesParam = params.get('services');
    if (servicesParam) {
        try {
            // Nếu servicesParam không phải chuỗi rỗng và hợp lệ, mới thực hiện decode và parse
            services = JSON.parse(decodeURIComponent(servicesParam)); // Dữ liệu dịch vụ
            console.log(services); // Kiểm tra giá trị dịch vụ sau khi parse
        } catch (error) {
            console.error("Dữ liệu dịch vụ không hợp lệ:", error);
        }
    } else {
        console.warn('Tham số services không có trong URL');
    }

    return { buildingId, services };
};

let selectedServiceIds = [];
const displayServices = (services) => {
    const servicesContainer = document.getElementById('services-container');
    servicesContainer.innerHTML = ''; // Xóa nội dung cũ

    if (services && services.length > 0) {
        services.forEach(service => {
            // Tạo thẻ div chứa checkbox và label
            const serviceElement = document.createElement('div');
            serviceElement.classList.add('form-check', 'mb-2'); // Bootstrap class for styling

            // Tạo checkbox
            const checkbox = document.createElement('input');
            checkbox.type = 'checkbox';
            checkbox.classList.add('form-check-input');
            checkbox.id = `service-${service._id}`;
            checkbox.setAttribute('data-id', service._id);

            // Tạo label
            const label = document.createElement('label');
            label.classList.add('form-check-label');
            label.setAttribute('for', `service-${service._id}`);
            label.textContent = service.name;

            // Gắn checkbox và label vào div
            serviceElement.appendChild(checkbox);
            serviceElement.appendChild(label);

            // Thêm sự kiện click (nếu cần xử lý logic)
            checkbox.addEventListener('change', () => {
                if (checkbox.checked) {
                    console.log(`Dịch vụ được chọn: ${service.name}`);
                } else {
                    console.log(`Dịch vụ bị bỏ chọn: ${service.name}`);
                }
            });

            // Thêm sự kiện click cho checkbox
            checkbox.addEventListener('change', () => {
                const serviceId = service._id;
                if (checkbox.checked) {
                    // Thêm ID vào danh sách nếu được chọn
                    selectedServiceIds.push(serviceId);
                    console.log(`Dịch vụ được chọn: ${service.name}`);
                } else {
                    // Xóa ID khỏi danh sách nếu bỏ chọn
                    selectedServiceIds = selectedServiceIds.filter(id => id !== serviceId);
                    console.log(`Dịch vụ bị bỏ chọn: ${service.name}`);
                }
                console.log('Danh sách dịch vụ đã chọn:', selectedServiceIds);
            });

            // Thêm serviceElement vào container
            servicesContainer.appendChild(serviceElement);
        });
    } else {
        servicesContainer.innerHTML = '<p>Không có dịch vụ nào.</p>';
    }
};

// Khởi tạo và hiển thị dữ liệu
const initAddRoomPage = () => {
    const titleScreen = document.getElementById('titleScreen');
    titleScreen.textContent = 'Thêm Phòng';
    const { buildingId, services } = getQueryParams();
    // Hiển thị các dịch vụ
    displayServices(services);
};

let selectedAmenitieIds = [];

// Hàm hiển thị các tiện nghi có sẵn
const displayAmenities = () => {
    const amenitiesContainer = document.getElementById('amenities-container');
    amenitiesContainer.innerHTML = ''; // Xóa nội dung cũ

    // Lưu lại các tiện nghi đã chọn trước đó
    const selectedAmenitiesSet = new Set(selectedAmenitieIds);

    amenities.forEach(amenity => {
        const amenityElement = document.createElement('div');
        amenityElement.classList.add('form-check', 'mb-3'); // Thêm kiểu cho checkbox

        // Tạo checkbox cho tiện nghi
        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.classList.add('form-check-input');
        checkbox.id = `amenity-${amenity}`;
        checkbox.dataset.id = amenity; // Lưu ID của tiện nghi vào data-id

        // Nếu tiện nghi đã được chọn trước đó, đánh dấu checkbox là đã chọn
        if (selectedAmenitiesSet.has(amenity)) {
            checkbox.checked = true;
        }

        // Tạo nhãn cho checkbox
        const label = document.createElement('label');
        label.classList.add('form-check-label');
        label.setAttribute('for', `amenity-${amenity}`);
        label.textContent = amenity;

        // Gắn checkbox và nhãn vào `amenityElement`
        amenityElement.appendChild(checkbox);
        amenityElement.appendChild(label);

        // Thêm sự kiện thay đổi khi người dùng chọn hoặc bỏ chọn checkbox
        checkbox.addEventListener('change', (event) => {
            const amenityId = event.target.dataset.id;

            // Nếu checkbox được chọn, thêm vào selectedAmenitieIds
            if (event.target.checked) {
                if (!selectedAmenitieIds.includes(amenityId)) {
                    selectedAmenitieIds.push(amenityId);
                }
            } else {
                // Nếu checkbox bị bỏ chọn, xóa khỏi selectedAmenitieIds
                selectedAmenitieIds = selectedAmenitieIds.filter(id => id !== amenityId);
            }

            // Cập nhật lại danh sách các tiện nghi đã chọn
            console.log('Danh sách tiện nghi đã chọn:', selectedAmenitieIds);
        });

        // Thêm amenityElement vào container
        amenitiesContainer.appendChild(amenityElement);
    });
};


// Hàm thêm tiện nghi mới từ người dùng
const addNewAmenity = () => {
    const newAmenityInput = document.getElementById('new-amenity');
    const newAmenity = newAmenityInput.value.trim();

    if (newAmenity && !amenities.includes(newAmenity)) {
        amenities.push(newAmenity); // Thêm tiện nghi mới vào danh sách
        displayAmenities(); // Hiển thị lại danh sách tiện nghi
    }

    // Xóa ô nhập liệu sau khi thêm
    newAmenityInput.value = '';
};

// Cập nhật danh sách tiện nghi đã chọn
const updateSelectedAmenities = () => {
    selectedAmenities = []; // Đặt lại danh sách tiện nghi đã chọn

    // Lấy tất cả các tiện nghi đã chọn (có lớp active)
    const amenityItems = document.querySelectorAll('.amenity-item.active');
    amenityItems.forEach(item => {
        selectedAmenities.push(item.textContent); // Thêm tiện nghi vào danh sách đã chọn
    });

    // Hiển thị tiện nghi đã chọn vào phần tử #selected-amenities
    const selectedAmenitiesContainer = document.getElementById('selected-amenities');
    selectedAmenitiesContainer.innerHTML = selectedAmenities.length > 0
        ? `Các tiện nghi đã chọn: ${selectedAmenities.join(', ')}`
        : 'Chưa có tiện nghi nào được chọn';
};

// Lắng nghe sự kiện click vào nút thêm tiện nghi mới
document.getElementById('add-amenity').addEventListener('click', addNewAmenity);

// Lắng nghe sự kiện submit của form
document.getElementById('addUpdateRoomForm').addEventListener('submit', async function (event) {
    event.preventDefault();

    const formData = new FormData();

    const id = buildingId;
    const roomName = document.getElementById('room_name').value.trim();
    const roomType = document.getElementById('room_type').value;
    const description = document.getElementById('description').value;
    const price = document.getElementById('price').value;
    const decrease = document.getElementById('decrease').value;
    const size = document.getElementById('size').value;
    const limitPerson = document.getElementById('limit_person').value;
    const status = document.getElementById('status').value;
    const photosRoom = document.getElementById('photos_room');
    const videoRoom = document.getElementById('video_room');

    if (!id || !roomName || !roomType || !description || !price || !size || !status || !decrease) {
        alert('Một số trường dữ liệu không hợp lệ hoặc không tìm thấy.');
        return;
    }

    formData.append('building_id', id);
    formData.append('room_name', roomName);
    formData.append('room_type', roomType);
    formData.append('description', description);
    formData.append('price', price);
    formData.append('decrease', decrease);
    formData.append('size', size);

    // Thêm từng phần tử của amenities vào FormData
    selectedAmenitieIds.forEach((amenity, index) => {
        formData.append(`amenities[${index}]`, amenity);
    });

    // Thêm từng phần tử của services vào FormData
    selectedServiceIds.forEach((serviceId, index) => {
        formData.append(`service[${index}]`, serviceId);
    });

    formData.append('limit_person', limitPerson);
    formData.append('status', status);

    if (photosRoom && photosRoom.files) {
        for (let i = 0; i < photosRoom.files.length; i++) {
            formData.append('photos_room', photosRoom.files[i]);
        }
    }

    if (videoRoom && videoRoom.files) {
        for (let i = 0; i < videoRoom.files.length; i++) {
            formData.append('video_room', videoRoom.files[i]);
        }
    }

    console.log("FormData Preview:");
    for (let [key, value] of formData.entries()) {
        console.log(`${key}:`, value);
    }

    try {
        const response = await axios.post('/api/add-room', formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        });

        if (response.status === 201) {
            Toastify({
                text: "Thêm phòng thành công!",
                backgroundColor: "linear-gradient(to right, #00b09b, #96c93d)",
                duration: 3000,
            }).showToast();
            // Reset lại trạng thái active của các dịch vụ
            const allServiceElements = document.querySelectorAll(".service-item");
            allServiceElements.forEach(item => {
                item.classList.remove("active");
            });

            // Reset lại trạng thái active của các tiện nghi
            const allAmenityElements = document.querySelectorAll(".amenity-item");
            allAmenityElements.forEach(item => {
                item.classList.remove("active");
            });
            // Reset lại các file ảnh đã chọn
            const photosRoom = document.getElementById("photos_room");
            if (photosRoom) {
                photosRoom.value = "";  // Xóa ảnh đã chọn
                // Xóa preview ảnh, nhưng giữ lại icon
                const previewContainer = photosRoom.parentNode.querySelector('.preview');
                const iconContainer = photosRoom.parentNode.querySelector('i'); // Giữ lại icon
                if (previewContainer) {
                    previewContainer.innerHTML = ''; // Xóa nội dung preview ảnh
                }
                if (iconContainer) {
                    iconContainer.style.display = 'block'; // Đảm bảo icon ảnh hiển thị lại
                }
            }

            // Reset lại video đã chọn
            const videoRoom = document.getElementById("video_room");
            if (videoRoom) {
                videoRoom.value = "";  // Xóa video đã chọn
                // Xóa preview video, nhưng giữ lại icon
                const previewContainer = videoRoom.parentNode.querySelector('.preview');
                const iconContainer = videoRoom.parentNode.querySelector('i'); // Giữ lại icon
                if (previewContainer) {
                    previewContainer.innerHTML = ''; // Xóa nội dung preview video
                }
                if (iconContainer) {
                    iconContainer.style.display = 'block'; // Đảm bảo icon video hiển thị lại
                }
            }
            // Điều hướng hoặc reset form
            document.getElementById("addUpdateRoomForm").reset();
        }
    } catch (error) {
        console.error("Lỗi khi thêm phòng:", error);
        Toastify({
            text: "Có lỗi khi thêm phòng!",
            backgroundColor: "linear-gradient(to right, #ff5f6d, #ffc371)",
            duration: 3000,
        }).showToast();
    }
});

const decreaseInput = document.getElementById('decrease');

// Xóa giá trị mặc định khi focus vào trường nhập
decreaseInput.addEventListener('focus', () => {
    if (decreaseInput.value === '0') {
        decreaseInput.value = '';
    }
});

// Hiển thị lại 0 nếu trường nhập bị bỏ trống khi blur
decreaseInput.addEventListener('blur', () => {
    if (decreaseInput.value === '') {
        decreaseInput.value = '0';
    }
});

// Ngăn nhập giá trị âm
decreaseInput.addEventListener('input', () => {
    // Nếu giá trị âm, set lại thành 0
    if (parseFloat(decreaseInput.value) < 0) {
        decreaseInput.value = '0';
    }
});

// Gọi hàm để hiển thị các tiện nghi
displayAmenities();

window.onload = initAddRoomPage;
