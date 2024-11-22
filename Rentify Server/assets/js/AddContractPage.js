const params = new URLSearchParams(window.location.search);
const buildingId = params.get('buildingId');
let userId = localStorage.getItem('user_id');
const contractId = params.get('contractId');
const contractStatus  = params.get('status');

const buttonContainer = document.getElementById('buttonContainer');
console.log(buttonContainer);

if (contractStatus == 'expired') {
    const terminateButton = document.createElement('button');
    terminateButton.type = 'button';
    terminateButton.className = 'btn btn-danger ml-2';
    terminateButton.textContent = 'Kết thúc hợp đồng';

    terminateButton.addEventListener('click', () => {
        $('#deleteBuildingModal').modal('show');
        document.getElementById('confirmDeleteBtn').addEventListener('click', async () => {
            try {
                const response = await axios.delete(`/api/endcontracts/${contractId}`);
                if (response.status === 200) {
                    localStorage.setItem('terminateContractMessage', 'Hợp đồng đã được kết thúc thành công!');
                    window.location.href = '/landlord/ContractPage';
                }
            } catch (error) {
                console.error('Failed to terminate contract:', error);
                Toastify({
                    text: "Kết thúc hợp đồng thất bại!",
                    duration: 3000,
                    gravity: "top",
                    position: "right",
                    backgroundColor: "#d9534f"
                }).showToast();
            }
        });
    });

    buttonContainer.appendChild(terminateButton);
}


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
                const img = document.createElement("img");
                img.src = e.target.result;
                previewContainer.appendChild(img);
            };
            reader.readAsDataURL(file);
        });
    } else {
        iconContainer.style.display = 'block';
    }
};

const fetchAvailableRooms = async () => {
    try {
        const response = await axios.get(`/api/contracts/available-rooms/${buildingId}`);
        const rooms = response.data;
        const roomSelect = document.getElementById('room_id');
        rooms.forEach(room => {
            const option = document.createElement('option');
            option.value = room._id;
            option.textContent = room.room_name;
            roomSelect.appendChild(option);
        });
    } catch (error) {
        console.error('Failed to fetch available rooms:', error);
    }
};

const fetchContractDetails = async () => {
    if (contractId) {
        try {
            const response = await axios.get(`/api/contracts/details/${contractId}`);
            const contract = response.data;

            // Populate form fields with contract details
            const roomSelect = document.getElementById('room_id');
            const roomOption = document.createElement('option');
            roomOption.value = contract.room_id._id;
            roomOption.textContent = contract.room_id.room_name; // Display room name
            roomSelect.appendChild(roomOption);

            // Set the selected room
            roomSelect.value = contract.room_id._id;
            document.getElementById('content').value = contract.content;
            document.getElementById('start_date').value = contract.start_date;
            document.getElementById('end_date').value = contract.end_date;
            document.getElementById('status').value = contract.status;

            // Display contract photos
            const previewContainer = document.querySelector('.preview');
            previewContainer.innerHTML = "";
            contract.photos_contract.forEach(photo => {
                const img = document.createElement("img");
                img.src = `/${photo}`;
                previewContainer.appendChild(img);
            });
            
            const photosIconContainer = document.querySelector('#photos_room').parentNode.querySelector('i');
            if (contract.photos_contract.length > 0) {
                photosIconContainer.style.display = 'none';
            }

            // Change form submit button text to "Cập Nhật Hợp Đồng"
            document.querySelector('h2').textContent = 'Cập Nhật Hợp Đồng';
            document.querySelector('button[type="submit"]').textContent = 'Cập Nhật Hợp Đồng';
        } catch (error) {
            console.error('Failed to fetch contract details:', error);
        }
    }
};

document.addEventListener('DOMContentLoaded', () => {
    if (!contractId) {
        fetchAvailableRooms();
    }
    if (contractId) {
        // Disable các trường
        document.getElementById('room_id').disabled = true;
        document.getElementById('start_date').disabled = true;
        document.getElementById('end_date').disabled = true;
    
        // Có thể thêm logic để điền giá trị vào các trường nếu cần
        fetchContractDetails();  // Hàm bạn đã định nghĩa trước đó để lấy thông tin hợp đồng
    }
    fetchContractDetails();
});

document.getElementById('addContractForm').addEventListener('submit', async function (event) {
    event.preventDefault();

    const formData = new FormData();
    formData.append('user_id', userId);
    formData.append('room_id', document.getElementById('room_id').value);
    formData.append('content', document.getElementById('content').value);
    formData.append('start_date', document.getElementById('start_date').value);
    formData.append('end_date', document.getElementById('end_date').value);
    formData.append('status', document.getElementById('status').value);

    const photosRoom = document.getElementById('photos_room').files;
    for (let i = 0; i < photosRoom.length; i++) {
        formData.append('photos_contract', photosRoom[i]);
    }

    if (contractId) {
        try {
            const response = await axios.put(`/api/contracts/update-images/${contractId}`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });

            if (response.status === 200) {
                localStorage.setItem('updateContractMessage', 'Hợp đồng đã được cập nhật thành công!');
                window.location.href = '/landlord/ContractPage';
            }
        } catch (error) {
            console.error('Failed to update contract:', error);
            Toastify({
                text: "Cập nhật hợp đồng thất bại!",
                duration: 3000,
                gravity: "top",
                position: "right",
                backgroundColor: "#d9534f"
            }).showToast();
        }
    } else {
        try {
            const response = await axios.post('/api/contracts/with-images', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });

            if (response.status === 201) {
                localStorage.setItem('addContractMessage', 'Hợp đồng đã được thêm thành công!');
                window.location.href = '/landlord/ContractPage';
            }
        } catch (error) {
            console.error('Failed to add contract:', error);
            Toastify({
                text: "Thêm hợp đồng thất bại!",
                duration: 3000,
                gravity: "top",
                position: "right",
                backgroundColor: "#d9534f"
            }).showToast();
        }
    }
});

if (!contractId) {
    document.addEventListener('DOMContentLoaded', fetchAvailableRooms);
}
