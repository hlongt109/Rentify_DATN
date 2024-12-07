const amenities = [
    "Vệ sinh khép kín",
    "Gác xép",
    "Ra vào vân tay",
    "Nuôi pet",
    "Không chung chủ"
];
let selectedAmenities = [];
let services = [];
let allAmenitie = []
const params = new URLSearchParams(window.location.search);
const buildingId = params.get('buildingId');
const roomId = params.get('roomId');
let selectedServiceIds = [];
let selectedAmenitieIds = [];

const fetchService = async () => {
    try {
        const response = await axios.get(`/api/building/${buildingId}/services`);
        console.log(response.data); // Kiểm tra cấu trúc dữ liệu

        // Kiểm tra xem dữ liệu trả về có phải là mảng hay không
        if (Array.isArray(response.data)) {
            services = response.data;
            console.log("Danh sách service :", services)
            renderServices();
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
        // Tạo phần tử bao gồm checkbox và nhãn
        const serviceItem = document.createElement("div");
        serviceItem.className = "form-check mb-3"; // Sử dụng Bootstrap để tạo kiểu

        // Tạo checkbox
        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.className = "form-check-input";
        checkbox.id = `service-${service._id}`;
        checkbox.dataset.id = service._id;

        // Đánh dấu checkbox nếu service ID có trong selectedServices
        if (selectedServices.includes(service._id)) {
            checkbox.checked = true;
        }

        // Tạo nhãn (label) cho checkbox
        const label = document.createElement("label");
        label.className = "form-check-label";
        label.setAttribute("for", `service-${service._id}`);
        label.textContent = service.name;

        // Gắn checkbox và nhãn vào `serviceItem`
        serviceItem.appendChild(checkbox);
        serviceItem.appendChild(label);

        // Thêm sự kiện để cập nhật selectedServiceIds khi chọn hoặc bỏ chọn
        checkbox.addEventListener("change", (event) => {
            const serviceId = event.target.dataset.id;

            if (event.target.checked) {
                // Nếu checkbox được chọn, thêm ID vào selectedServiceIds
                if (!selectedServiceIds.includes(serviceId)) {
                    selectedServiceIds.push(serviceId);
                }
                console.log(`Dịch vụ được chọn: ${serviceId}`);
            } else {
                // Nếu checkbox bị bỏ chọn, xóa ID khỏi selectedServiceIds
                selectedServiceIds = selectedServiceIds.filter(id => id !== serviceId);
                console.log(`Dịch vụ bị bỏ chọn: ${serviceId}`);
            }

            // Cập nhật lại danh sách ID đã chọn
            console.log("Danh sách dịch vụ đã chọn:", selectedServiceIds);
        });

        // Thêm serviceItem vào container
        container.appendChild(serviceItem);
    });
    selectedServiceIds = Array.from(container.querySelectorAll("input[type='checkbox']:checked"))
        .map(checkbox => checkbox.dataset.id);
    console.log("Danh sách dịch vụ đã chọn sau khi render:", selectedServiceIds);
};

const displayAmenities = () => {
    const amenitiesContainer = document.getElementById('amenities-container');
    amenitiesContainer.innerHTML = ''; // Xóa nội dung cũ

    const allAmenities = [...new Set([...amenities, ...allAmenitie])]; // Gộp và loại bỏ trùng lặp
    const selectedAmenitiesSet = new Set(selectedAmenitieIds); // Sử dụng Set để lưu các amenity đã chọn

    allAmenities.forEach(amenity => {
        const amenityElement = document.createElement('div');
        amenityElement.classList.add('form-check', 'mb-3'); // Sử dụng Bootstrap để tạo kiểu

        // Tạo checkbox
        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.classList.add('form-check-input');
        checkbox.id = `amenity-${amenity}`;
        checkbox.dataset.id = amenity;

        // Đánh dấu checkbox nếu amenity có trong selectedAmenitieIds
        if (selectedAmenitiesSet.has(amenity)) {
            checkbox.checked = true;
        }

        // Tạo nhãn (label) cho checkbox
        const label = document.createElement('label');
        label.classList.add('form-check-label');
        label.setAttribute('for', `amenity-${amenity}`);
        label.textContent = amenity;

        // Gắn checkbox và nhãn vào `amenityElement`
        amenityElement.appendChild(checkbox);
        amenityElement.appendChild(label);

        // Thêm sự kiện để cập nhật selectedAmenitieIds khi chọn hoặc bỏ chọn
        checkbox.addEventListener('change', (event) => {
            const amenityId = event.target.dataset.id;

            if (event.target.checked) {
                // Nếu checkbox được chọn, thêm ID vào selectedAmenitieIds
                if (!selectedAmenitieIds.includes(amenityId)) {
                    selectedAmenitieIds.push(amenityId);
                }
            } else {
                // Nếu checkbox bị bỏ chọn, xóa ID khỏi selectedAmenitieIds
                selectedAmenitieIds = selectedAmenitieIds.filter(id => id !== amenityId);
            }

            // Cập nhật lại danh sách ID đã chọn
            console.log('Danh sách tiện ích đã chọn:', selectedAmenitieIds);
        });

        // Thêm amenityElement vào container
        amenitiesContainer.appendChild(amenityElement);
    });

    // Lấy tất cả checkbox đã được chọn sau khi render và cập nhật selectedAmenitieIds
    selectedAmenitieIds = Array.from(amenitiesContainer.querySelectorAll("input[type='checkbox']:checked"))
        .map(checkbox => checkbox.dataset.id);

    // In ra danh sách các tiện ích đã chọn
    console.log('Danh sách tiện ích đã chọn sau khi render:', selectedAmenitieIds);
};



// Lấy thông tin chi tiết phòng từ API
const fetchRoomDetails = async () => {
    try {
        const response = await axios.get(`/api/room/${roomId}`);
        if (response.status === 200) {
            const room = response.data;
            renderServices(room.service);
            console.log(room.service);

            document.getElementById('room_name').value = room.room_name;
            document.getElementById('status').value = room.status;
            document.getElementById('price').value = room.price;
            document.getElementById('decrease').value = room.decrease;
            document.getElementById('room_type').value = room.room_type;
            document.getElementById('size').value = room.size;
            document.getElementById('limit_person').value = room.limit_person;
            document.getElementById('description').value = room.description;

            const servicesContainer = document.getElementById('services-container');
            room.service.forEach(serviceId => {
                const serviceElement = servicesContainer.querySelector(`[data-id="${serviceId}"]`);
                if (serviceElement) {
                    serviceElement.classList.add('active');
                }
            });

            selectedAmenities = room.amenities;
            displayAmenities();

            const photosContainer = document.querySelector('#photos_room').parentNode.querySelector('.preview');
            photosContainer.innerHTML = '';
            room.photos_room.forEach(photoPath => {
                const sanitizedPath = photoPath.replace(/^\/landlord\//, '').replace(/^public\//, '');
                const imgElement = document.createElement('img');
                imgElement.src = `/public/${sanitizedPath}`;
                imgElement.alt = 'Ảnh phòng';
                imgElement.style.width = '100%';
                imgElement.style.marginBottom = '10px';
                photosContainer.appendChild(imgElement);
            });

            const videosContainer = document.querySelector('#video_room').parentNode.querySelector('.preview');
            videosContainer.innerHTML = '';
            room.video_room.forEach(videoPath => {
                const sanitizedPath = videoPath.replace(/^\/landlord\//, '').replace(/^public\//, '');
                const videoElement = document.createElement('video');
                videoElement.src = `/public/${sanitizedPath}`;
                videoElement.controls = true;
                videoElement.autoplay = true;
                videoElement.muted = true;
                videoElement.style.width = '100%';
                videosContainer.appendChild(videoElement);
            });

            const photosIconContainer = document.querySelector('#photos_room').parentNode.querySelector('i');
            const videosIconContainer = document.querySelector('#video_room').parentNode.querySelector('i');
            if (room.photos_room.length > 0) {
                photosIconContainer.style.display = 'none';
            }
            if (room.video_room.length > 0) {
                videosIconContainer.style.display = 'none';
            }
        }
    } catch (error) {
        console.error('Error fetching room details:', error);
    }
};


const previewFiles = (input) => {
    const previewContainer = input.parentNode.querySelector('.preview');
    const iconContainer = input.parentNode.querySelector('i');
    const files = input.files;

    previewContainer.innerHTML = "";

    if (files && files.length > 0) {
        iconContainer.style.display = 'none';

        Array.from(files).forEach((file) => {
            const reader = new FileReader();
            reader.onload = (e) => {
                const fileType = file.type.split('/')[0];

                if (fileType === 'image') {
                    const img = document.createElement("img");
                    img.src = e.target.result;
                    previewContainer.appendChild(img);
                } else if (fileType === 'video') {
                    const video = document.createElement("video");
                    video.src = e.target.result;
                    video.controls = true;
                    video.autoplay = true;
                    video.muted = true;
                    previewContainer.appendChild(video);
                }
            };
            reader.readAsDataURL(file);
        });
    } else {
        iconContainer.style.display = 'block';
    }
};

const fetchAmenities = async () => {
    try {
        const response = await axios.get(`/api/room/${roomId}/amenities`);
        console.log(response.data); // Kiểm tra cấu trúc dữ liệu

        // Kiểm tra xem dữ liệu trả về có phải là mảng hay không
        if (Array.isArray(response.data)) {
            allAmenitie = response.data;
            selectedAmenitieIds = allAmenitie
            displayAmenities();
            renderServices();
        } else {
            console.error('Dữ liệu không phải là mảng:', response.data);
        }
    } catch (error) {
        console.error('Lỗi khi lấy dữ liệu:', error);
    }
};


const addNewAmenity = () => {
    const newAmenityInput = document.getElementById('new-amenity');
    const newAmenity = newAmenityInput.value.trim();

    if (newAmenity && !amenities.includes(newAmenity)) {
        amenities.push(newAmenity);
        displayAmenities();
    }

    newAmenityInput.value = '';
};

const updateSelectedAmenities = () => {
    selectedAmenities = [];
    const amenityItems = document.querySelectorAll('.form-check-input:checked');  // Lấy các checkbox đã được chọn
    amenityItems.forEach(item => {
        selectedAmenities.push(item.dataset.id); // Thêm ID tiện ích vào danh sách đã chọn
    });

    // Cập nhật hiển thị danh sách tiện ích đã chọn
    const selectedAmenitiesContainer = document.getElementById('selected-amenities');
    selectedAmenitiesContainer.innerHTML = selectedAmenities.length > 0
        ? `Các tiện nghi đã chọn: ${selectedAmenities.join(', ')}`
        : 'Chưa có tiện nghi nào được chọn';

    // In ra danh sách các tiện ích đã chọn (console log)
    console.log("Danh sách tiện ích đã chọn:", selectedAmenities);
};

document.getElementById('add-amenity').addEventListener('click', addNewAmenity);

document.getElementById('update-room-form').addEventListener('submit', async (event) => {
    event.preventDefault();

    const formData = new FormData();
    formData.append('room_name', document.getElementById('room_name').value);
    formData.append('status', document.getElementById('status').value);
    formData.append('price', document.getElementById('price').value);
    formData.append('decrease', document.getElementById('decrease').value);
    formData.append('room_type', document.getElementById('room_type').value);
    formData.append('size', document.getElementById('size').value);
    formData.append('limit_person', document.getElementById('limit_person').value);
    formData.append('description', document.getElementById('description').value);

    const photosInput = document.getElementById('photos_room');
    for (let i = 0; i < photosInput.files.length; i++) {
        formData.append('photos_room', photosInput.files[i]);
    }

    const videosInput = document.getElementById('video_room');
    for (let i = 0; i < videosInput.files.length; i++) {
        formData.append('video_room', videosInput.files[i]);
    }

    const selectedAmenities = selectedAmenitieIds.forEach((amenity, index) => {
        formData.append(`amenities[${index}]`, amenity);
    });

    const activeServices = selectedServiceIds.forEach((serviceId, index) => {
        formData.append(`service[${index}]`, serviceId);
    });

    try {
        const response = await axios.put(`/api/update-room/${roomId}`, formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });

        if (response.status === 200) {
            console.log('Room updated successfully!', response.data);
            console.log('Updated fields:', {
                room_name: document.getElementById('room_name').value,
                status: document.getElementById('status').value,
                price: document.getElementById('price').value,
                decrease: document.getElementById('decrease').value,
                room_type: document.getElementById('room_type').value,
                size: document.getElementById('size').value,
                limit_person: document.getElementById('limit_person').value,
                description: document.getElementById('description').value,
                amenities: selectedAmenities,
                service: activeServices,
                photos_room: Array.from(photosInput.files).map(file => file.name),
                video_room: Array.from(videosInput.files).map(file => file.name)
            });
            localStorage.setItem('updateRoomMessage', 'Chỉnh sửa phòng thành công!');
            window.location.href = `/landlord/BuildingPage`;
        }
    } catch (error) {
        console.error('Error updating room:', error);
        Toastify({
            text: "Có lỗi xảy ra khi chỉnh sửa phòng!",
            backgroundColor: "linear-gradient(to right, #ff5f6d, #ffc371)",
            duration: 3000,
        }).showToast();
    }
});

document.getElementById('delete-room').addEventListener('click', function () {
    $('#deleteRoomModal').modal('show'); // Hiển thị modal xác nhận
});

document.getElementById('confirmDeleteBtn').addEventListener('click', function () {
    // Gọi API xóa phòng với axios
    axios.delete(`/api/delete-room/${roomId}`)
        .then(response => {
            if (response.data.message === 'Room deleted successfully!') {
                localStorage.setItem('deleteRoomMessage', 'Xoá phòng thành công!');
                window.location.href = '/landlord/BuildingPage'; // Quay lại trang quản lý phòng
            } else {
                Toastify({
                    text: "Có lỗi xảy ra khi xoá phòng!",
                    backgroundColor: "linear-gradient(to right, #ff5f6d, #ffc371)",
                    duration: 3000,
                }).showToast();
            }
        })
        .catch(error => {
            console.error('Error:', error);
            Toastify({
                text: "Có lỗi xảy ra khi xoá phòng!",
                backgroundColor: "linear-gradient(to right, #ff5f6d, #ffc371)",
                duration: 3000,
            }).showToast();
        });
    $('#deleteRoomModal').modal('hide'); // Ẩn modal sau khi xác nhận xóa
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


// displayAmenities();

const init = async () => {
    await fetchService();
    await fetchAmenities();
    await fetchRoomDetails()
};

window.onload = init;