let building = []
let userId = "671a29b84e350b2df4aee4ed"

const fetchUsers = async () => {
    try {
        const response = await axios.get(`/api/buildings/${userId}`);
        console.log(response.data); // Kiểm tra cấu trúc dữ liệu

        // Kiểm tra xem dữ liệu trả về có phải là mảng hay không
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
                <button class="btn btn-primary shadow-button" style="font-size: 0.75rem;">Chi tiết toà</button>
                <button class="btn btn-danger shadow-button" style="font-size: 0.75rem;">Xoá</button>
            </td>
        `;

        tableBody.appendChild(row);
    });
};


const init = () => {
    fetchUsers(); // Gọi hàm để lấy dữ liệu người dùng
};

window.onload = init;
