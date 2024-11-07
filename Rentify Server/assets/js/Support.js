let users1 = [];
let search1 = '';
let selectedRole1 = '';
let currentPage1 = 1;
const itemsPerPage1 = 5;
let editingUserId1 = null;
let currentUserId1;

const showConfirmModal1 = (userId) => {
    currentUserId1 = userId;
    const modal = document.getElementById('confirm-modal1');
    modal.style.display = 'flex';
};

const closeConfirmModal1 = () => {
    //     document.getElementById('confirm-modal1').style.display = 'none';
    // };
    const modal = document.getElementById('confirm-modal1');
    if (modal) {
        modal.style.display = 'none';  // Ẩn modal sau khi cập nhật thành công
    }
}
const confirmUpdateRole1 = async () => {
    const status1 = document.getElementById('edit-role').value;
    console.log("Status cần cập nhật:", status1);

    const isUpdated = await updateUser1(currentUserId1, status1);
    console.log("isUpdated support ", isUpdated);

    if (isUpdated) {
        console.log("Cập nhật thành công");
        Toastify({
            text: "Cập nhật thành công!",
            duration: 3000,
            close: true,
            gravity: "top",
            position: 'right',
            style: {
                background: "#4caf50"
            },
            stopOnFocus: true
        }).showToast();
        closeConfirmModal1(); // Đóng modal
    } else {
        alert("Cập nhật không thành công, vui lòng thử lại!");
    }
};


const fetchUsers1 = async () => {
    try {
        const response = await axios.get('/api/support/list');
        console.log(response.data.find);

        if (Array.isArray(response.data.find)) {
            users1 = response.data.find;
            renderTable1();
        } else {
            console.error('find is not an array:', response.data.find);
        }
    } catch (error) {
        console.error('Error fetching users:', error);
    }
};

// const updateUser1 = async (userId) => {
//     const status = document.getElementById('edit-role').value;
//     console.log("status: ", status);


//     try {
//         console.log(userId);
//         // Gọi API cập nhật với axios
//         const response = await axios.put(`/api/support/update/${userId}`, {
//             status
//         });
//         console.log("Response từ API:", response.data);

//         // Cập nhật trạng thái trong mảng `users1` để đồng bộ với dữ liệu đã cập nhật
//         const userIndex = users1.findIndex(user => user._id === userId);
//         if (userIndex !== -1) {
//             users1[userIndex].status = status;
//         }
//         console.log("chay qua day");

//         editingUserId1 = null;
//         // Làm mới bảng để phản ánh thay đổi
//         renderTable1();
//         console.log("qua dday");

//         return true;
//     } catch (error) {
//         console.error('Error updating user:', error.response?.data || error.message || error);
//         return false;
//     }
// };
const updateUser1 = async (userId) => {
    const status = document.getElementById('edit-role').value;
    console.log("User ID:", userId);
    console.log("Status:", status);

    try {
        try {
            console.log("Bắt đầu gửi yêu cầu cập nhật...");

            // Gửi yêu cầu cập nhật
            await axios.put(`/api/support/update/${userId}`, { status });

            console.log("Yêu cầu cập nhật thành công:");
            // Các bước tiếp theo nếu cập nhật thành công
        } catch (error) {
            console.error("Lỗi khi gửi yêu cầu cập nhật:", error.response?.data || error.message || error);
        }

        const userIndex = users1.findIndex(user => user._id === userId);
        if (userIndex !== -1) {
            users1[userIndex].status = status;
            console.log("Đã cập nhật status trong mảng users1.", users1);
        }

        editingUserId1 = null;
        renderTable1();

        return true;
    } catch (error) {
        console.error('Error updating user:', error.response?.data || error.message || error);
        if (error.response) {
            console.log("Error status:", error.response.status);
            console.log("Error data:", error.response.data);
        } else {
            console.log("Error không có phản hồi từ server");
        }
        return false;
    }
};



const toggleEditForm1 = (userId) => {
    editingUserId1 = (editingUserId1 === userId) ? null : userId;
    renderTable1();
};

const selectRole1 = (status) => {
    document.getElementById('roleDropdownButton').innerText = status === 'Status' ? 'Status' : status;
    selectedRole1 = status;
    console.log("status select: ", status);


    const button = document.getElementById('roleDropdownButton');
    button.style.backgroundColor = getRoleColor1(status);

    renderTable1();
};

const renderTable1 = () => {
    console.log("renderTable1 được gọi");

    const filteredUsers = users1.filter(user =>
        user.title_support &&
        removeAccents1(user.title_support.toLowerCase()).includes(removeAccents1(search1.toLowerCase())) &&
        (selectedRole1 === '' || selectedRole1 === 'Status' || user.status === selectedRole1)
    );


    const totalPages = Math.ceil(filteredUsers.length / itemsPerPage1);
    const displayedUsers = filteredUsers.slice(
        (currentPage1 - 1) * itemsPerPage1,
        currentPage1 * itemsPerPage1
    );

    const userTableBody = document.getElementById('user-table-body');
    userTableBody.innerHTML = '';

    displayedUsers.forEach((user, index) => {
        const isEvenRow = index % 2 === 0;
        const row = document.createElement('tr');
        row.style.backgroundColor = isEvenRow ? '#ffffff' : '#fafafb';


        row.innerHTML = `
            <td style="vertical-align: middle;">${index + 1 + (currentPage1 - 1) * itemsPerPage1}</td>
            <td style="vertical-align: middle;">${user._id ?? ''}</td>
            <td style="vertical-align: middle;">${user.user_id ?? ''}</td>
            <td style="vertical-align: middle;">${user.room_id ?? ''}</td>
            <td style="vertical-align: middle;">${user.title_support ?? ''}</td>
            <td style="vertical-align: middle;">${user.content_support ?? ''}</td>
            <td>
                <button class="btn text-white shadow-button" style="font-size: 0.75rem; background-color: ${getRoleColor1(user.status)}; width: 70px;">${user.status ?? 'N/A'}</button>
            </td>
            <td>
                <button class="btn btn-primary shadow-button" style="font-size: 0.75rem;" onclick="toggleEditForm1('${user._id}')">Update</button>
            </td>
        `;

        userTableBody.appendChild(row);

        if (editingUserId1 === user._id) {
            const editRow = document.createElement('tr');
            editRow.innerHTML = `
<td colspan="7">
    <form id="edit-form-${user._id}" class="p-3 transparent-form border rounded">
        <div class="row mb-3">
            <div class="col">
                <div class="form-group">
                    <label for="edit-name" class="form-label">ID</label>
                    <input type="text" id="edit-name" class="form-control" value="${user._id ?? ''}" placeholder="ID" readonly />
                </div>
            </div>
            <div class="col">
                <div class="form-group">
                    <label for="edit-email" class="form-label">ID User</label>
                    <input type="email" id="edit-email" class="form-control" value="${user.user_id ?? ''}" placeholder="ID User" readonly />
                </div>
            </div>
            <div class="col">
                <div class="form-group">
                    <label for="edit-phone" class="form-label">ID Room</label>
                    <input type="text" id="edit-phone" class="form-control" value="${user.room_id ?? ''}" placeholder="ID Room" readonly />
                </div>
            </div>
            <div class="col">
                <div class="form-group">
                    <label for="edit-address" class="form-label">Title</label>
                    <input type="text" id="edit-address" class="form-control" value="${user.title_support ?? ''}" placeholder="Title" readonly />
                </div>
            </div>
            <div class="col">
                <div class="form-group">
                    <label for="edit-dob" class="form-label">Content</label>
                    <input type="text" id="edit-dob" class="form-control" value="${user.content_support ?? ''}" placeholder="Content" readonly />
                </div>
            </div>
            
            <div class="col">
                <div class="form-group">
                    <label for="edit-role" class="form-label">Status</label>
                    <select id="edit-role" class="form-control">
                        <option value= "0" ${user.status === '0' ? 'selected' : ''}>0</option>
                        <option value= "1" ${user.status === '1' ? 'selected' : ''}>1</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="d-flex justify-content-end gap-2">
            <button type="button" class="btn btn-secondary shadow-button" style="font-size: 0.75rem; margin: 10px;" onclick="cancelEdit1()">Cancel</button>
            <button type="button" class="btn btn-primary shadow-button" style="font-size: 0.75rem; margin: 10px;" onclick="showConfirmModal1('${user._id}')">Update</button>
        </div>
    </form>
</td>

            `;
            row.after(editRow);
            setTimeout(() => {
                document.getElementById(`edit-form-${user._id}`).classList.add('show');
            }, 0);
        }
    });

    renderPagination1(totalPages);
};

const cancelEdit1 = () => {
    editingUserId1 = null;
    renderTable1();
};

const getRoleColor1 = (status) => {
    switch (status) {
        case 'admin':
            return '#f44d55';
        case 'user':
            return '#6c5ce7';
        case 'landlord':
            return '#00b894';
        case 'staffs':
            return '#0984e3';
        case 'ban':
            return '#b2bec3';
        default:
            return '#333';
    }
};

const renderPagination1 = (totalPages) => {
    const paginationContainer = document.getElementById('pagination');
    paginationContainer.innerHTML = '';

    for (let i = 1; i <= totalPages; i++) {
        const button = document.createElement('button');
        button.textContent = i;
        button.classList.add('btn', 'pagination-button');
        if (i === currentPage1) {
            button.classList.add('active');
        }

        button.addEventListener('click', () => {
            currentPage1 = i;
            renderTable1();
        });

        paginationContainer.appendChild(button);
    }
};

const removeAccents1 = (str) => {
    return str
        .normalize('NFD')
        .replace(/[\u0300-\u036f]/g, '')
        .replace(/đ/g, 'd')
        .replace(/Đ/g, 'D');
};

const init1 = () => {
    fetchUsers1();
};

document.getElementById('search-input').addEventListener('input', (event) => {
    search1 = event.target.value;
    renderTable1();
});

document.getElementById('confirm-update1').onclick = confirmUpdateRole1;
document.getElementById('cancel-update1').onclick = closeConfirmModal1;

window.onload = init1;