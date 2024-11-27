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
const roomId = params.get('roomId');

// Lấy thông tin chi tiết phòng từ API
const fetchRoomDetails = async () => {
    try {
        const response = await axios.get(`/api/room/${roomId}`);
        if (response.status === 200) {
            const room = response.data;
            document.getElementById('room_name').value = room.room_name;
            document.getElementById('status').value = room.status;
            document.getElementById('price').value = room.price;
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

const displayAmenities = () => {
    const amenitiesContainer = document.getElementById('amenities-container');
    amenitiesContainer.innerHTML = ''; 

    const selectedAmenitiesSet = new Set(selectedAmenities);

    amenities.forEach(amenity => {
        const amenityElement = document.createElement('div');
        amenityElement.classList.add('amenity-item');
        amenityElement.textContent = amenity;

        if (selectedAmenitiesSet.has(amenity)) {
            amenityElement.classList.add('active');
        }

        amenityElement.addEventListener('click', () => {
            amenityElement.classList.toggle('active');
            updateSelectedAmenities(); 
        });

        amenitiesContainer.appendChild(amenityElement);
    });
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
    const amenityItems = document.querySelectorAll('.amenity-item.active');
    amenityItems.forEach(item => {
        selectedAmenities.push(item.textContent); 
    });
    const selectedAmenitiesContainer = document.getElementById('selected-amenities');
    selectedAmenitiesContainer.innerHTML = selectedAmenities.length > 0
        ? `Các tiện nghi đã chọn: ${selectedAmenities.join(', ')}`
        : 'Chưa có tiện nghi nào được chọn';
};

document.getElementById('add-amenity').addEventListener('click', addNewAmenity);
displayAmenities();

const init = async () => {
    await fetchRoomDetails()
};

window.onload = init;
