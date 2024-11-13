let users = []; // Khai báo mảng người dùng rỗng
let search = '';
let selectedRole = ''; // Vai trò hiện đang được chọn
let currentPage = 1;
const itemsPerPage = 5;
let editingUserId = null; // Trạng thái ID user đang chỉnh sửa
let currentUserId;

const showConfirmModal = (userId) => {
    currentUserId = userId; // Lưu ID người dùng để sử dụng sau này
    const modal = document.getElementById('confirm-modal');
    modal.style.display = 'flex'; // Đặt display thành 'flex' để căn giữa
};

const closeConfirmModal = () => {
    document.getElementById('confirm-modal').style.display = 'none'; // Đóng modal
};

const confirmUpdateRole = async () => {
    const role = document.getElementById('edit-role').value; // Lấy vai trò từ modal
    const isUpdated = await updateUser(currentUserId, role); // Gọi hàm cập nhật người dùng
    if (isUpdated) {
        Toastify({
            text: "Cập nhật thành công!",
            duration: 3000,
            close: true,
            gravity: "top",
            position: 'right',
            style: {
                background: "#4caf50" // Sử dụng style.background thay cho backgroundColor
            },
            stopOnFocus: true
        }).showToast();
        closeConfirmModal(); // Đóng modal xác nhận nếu cập nhật thành công
    } else {
        alert("Cập nhật không thành công, vui lòng thử lại!");
    }
};


const fetchUsers = async () => {
    try {
        const response = await axios.get('/api/user/list'); // Gọi API
        console.log(response.data); // Kiểm tra cấu trúc dữ liệu

        if (Array.isArray(response.data.find)) {
            users = response.data.find; // Gán mảng người dùng từ 'find'
            renderTable(); // Gọi hàm render bảng
        } else {
            console.error('find is not an array:', response.data.find);
        }
    } catch (error) {
        console.error('Error fetching users:', error);
    }
};

const updateUser = async (userId) => {
    const role = document.getElementById('edit-role').value; // Lấy giá trị role từ dropdown

    try {
        await axios.put(`/api/user/update/${userId}`, {
            role // Chỉ gửi role để cập nhật
        });

        // Cập nhật thông tin trong mảng người dùng
        const userIndex = users.findIndex(user => user._id === userId);
        if (userIndex !== -1) {
            users[userIndex].role = role; // Chỉ cập nhật role
        }

        editingUserId = null; // Đặt lại trạng thái chỉnh sửa
        renderTable(); // Rerender bảng để hiển thị thay đổi

        return true; // Trả về true khi cập nhật thành công
    } catch (error) {
        console.error('Error updating user:', error);
        return false; // Trả về false nếu có lỗi
    }
};

const toggleEditForm = (userId) => {
    // Toggle ID của user đang chỉnh sửa
    editingUserId = (editingUserId === userId) ? null : userId;
    renderTable(); // Rerender bảng để hiển thị form
};

const selectRole = (role) => {
    document.getElementById('roleDropdownButton').innerText = role === 'Vai trò' ? 'Vai trò' : role; // Hiển thị 'All Users' khi chọn 'All'
    selectedRole = role;

    // Lấy màu nền theo vai trò và áp dụng cho button
    const button = document.getElementById('roleDropdownButton');
    button.style.backgroundColor = getRoleColor(role);

    renderTable(); // Rerender bảng khi chọn vai trò mới
};

const renderTable = () => {
    const filteredUsers = users.filter(user =>
        user.username &&
        removeAccents(user.username.toLowerCase()).includes(removeAccents(search.toLowerCase())) &&
        (selectedRole === '' || selectedRole === 'Vai trò' || user.role === selectedRole) // Kiểm tra vai trò
    );

    const totalPages = Math.ceil(filteredUsers.length / itemsPerPage);
    const displayedUsers = filteredUsers.slice(
        (currentPage - 1) * itemsPerPage,
        currentPage * itemsPerPage
    );

    const userTableBody = document.getElementById('user-table-body');
    userTableBody.innerHTML = ''; // Clear previous rows

    displayedUsers.forEach((user, index) => {
        const isEvenRow = index % 2 === 0;
        const row = document.createElement('tr');
        row.style.backgroundColor = isEvenRow ? '#ffffff' : '#fafafb';

        // Sử dụng toán tử nullish để kiểm tra và thay thế giá trị thiếu
        row.innerHTML = `
            <td style="vertical-align: middle;">${index + 1 + (currentPage - 1) * itemsPerPage}</td>
            <td style="vertical-align: middle;">${user.username ?? ''}</td>
            <td style="vertical-align: middle;">${user.email ?? ''}</td>
            <td style="vertical-align: middle;">${user.phoneNumber ?? ''}</td>
            <td style="vertical-align: middle;">${user.address ?? ''}</td>
            <td>
                <button class="btn text-white shadow-button" style="font-size: 0.75rem; background-color: ${getRoleColor(user.role)}; width: 70px;">${user.role ?? 'N/A'}</button>
            </td>
            <td>
                <button class="btn btn-primary shadow-button" style="font-size: 0.75rem;" onclick="toggleEditForm('${user._id}')">Cập nhật</button>
            </td>
        `;

        userTableBody.appendChild(row);

        if (editingUserId === user._id) {
            const editRow = document.createElement('tr');
            editRow.innerHTML = `
<td colspan="7">
    <form id="edit-form" class="p-3 transparent-form border rounded">
        <div class="row mb-3">
            <div class="col">
                <div class="form-group">
                    <label for="edit-name" class="form-label">Name</label>
                    <input type="text" id="edit-name" class="form-control" value="${user.username ?? ''}" placeholder="Name" readonly />
                </div>
            </div>
            <div class="col">
                <div class="form-group">
                    <label for="edit-email" class="form-label">Email</label>
                    <input type="email" id="edit-email" class="form-control" value="${user.email ?? ''}" placeholder="Email" readonly />
                </div>
            </div>            
            <div class="col">
                <div class="form-group">
                    <label for="edit-phone" class="form-label">Phone Number</label>
                    <input type="text" id="edit-phone" class="form-control" value="${user.phoneNumber ?? ''}" placeholder="Phone Number" readonly />
                </div>
            </div>
            <div class="col">
                <div class="form-group">
                    <label for="edit-address" class="form-label">Address</label>
                    <input type="text" id="edit-address" class="form-control" value="${user.address ?? ''}" placeholder="Address" readonly />
                </div>
            </div>
            <div class="col">
                <div class="form-group">
                    <label for="edit-dob" class="form-label">Date of Birth</label>
                    <input type="text" id="edit-dob" class="form-control" value="${user.dob ?? ''}" placeholder="Date of Birth" readonly />
                </div>
            </div>
            <div class="col">
                <div class="form-group">
                    <label for="edit-gender" class="form-label">Gender</label>
                    <input type="text" id="edit-gender" class="form-control" value="${user.gender ?? ''}" placeholder="Gender" readonly />
                </div>
            </div>
            <div class="col">
                <div class="form-group">
                    <label for="edit-role" class="form-label">Role</label>
                    <select id="edit-role" class="form-control">
                        <option value="admin" ${user.role === 'admin' ? 'selected' : ''}>Admin</option>
                        <option value="landlord" ${user.role === 'landlord' ? 'selected' : ''}>Landlord</option>
                        <option value="staffs" ${user.role === 'staffs' ? 'selected' : ''}>Staffs</option>
                        <option value="user" ${user.role === 'user' ? 'selected' : ''}>User</option>
                        <option value="ban" ${user.role === 'ban' ? 'selected' : ''}>Ban</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="d-flex justify-content-end gap-2">
            <button type="button" class="btn btn-secondary shadow-button" style="font-size: 0.75rem; margin: 10px;" onclick="cancelEdit()">Huỷ</button>
            <button type="button" class="btn btn-primary shadow-button" style="font-size: 0.75rem; margin: 10px;" onclick="showConfirmModal('${user._id}')">Cập nhật</button>
        </div>
    </form>
</td>

            `;
            row.after(editRow);
            setTimeout(() => {
                document.getElementById('edit-form').classList.add('show'); // Thêm lớp 'show' để kích hoạt hiệu ứng fade-in
            }, 0);
        }
    });

    renderPagination(totalPages);
};

const cancelEdit = () => {
    editingUserId = null;
    renderTable();
};

const getRoleColor = (role) => {
    switch (role) {
        case 'admin':
            return '#f44d55'; // Màu đỏ cho admin
        case 'user':
            return '#6c5ce7'; // Màu tím cho user
        case 'landlord':
            return '#00b894'; // Màu xanh lá cây cho landlord
        case 'staffs':
            return '#0984e3'; // Màu xanh dương cho staffs
        case 'ban':
            return '#b2bec3'; // Màu xám cho ban
        default:
            return '#333'; // Màu mặc định
    }
};


const renderPagination = (totalPages) => {
    const paginationContainer = document.getElementById('pagination');
    paginationContainer.innerHTML = ''; // Clear previous pagination

    for (let i = 1; i <= totalPages; i++) {
        const button = document.createElement('button');
        button.textContent = i;
        button.classList.add('btn', 'pagination-button');
        if (i === currentPage) {
            button.classList.add('active');
        }

        button.addEventListener('click', () => {
            currentPage = i;
            renderTable();
        });

        paginationContainer.appendChild(button);
    }
};

const removeAccents = (str) => {
    return str
        .normalize('NFD')
        .replace(/[\u0300-\u036f]/g, '')
        .replace(/đ/g, 'd')
        .replace(/Đ/g, 'D');
};

const init = () => {
    fetchUsers(); // Gọi hàm để lấy dữ liệu người dùng
};

document.getElementById('search-input').addEventListener('input', (event) => {
    search = event.target.value;
    renderTable();
});

document.getElementById('confirm-update').onclick = confirmUpdateRole;
document.getElementById('cancel-update').onclick = closeConfirmModal;



// Khởi tạo bảng khi trang được tải
window.onload = init;