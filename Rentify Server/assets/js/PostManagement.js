function updatePostStatus(index) {
    const status = document.getElementById(`post-status-${index}`).value;
    const form = document.getElementById(`post-form-${index}`);
    const formData = new FormData(form);

    fetch(form.action, {
        method: form.method,
        body: formData
    }).then(response => {
        if (!response.ok) {
            throw new Error('Có lỗi xảy ra khi cập nhật');
        }
        return response.json();
    })
        .then(data => {
            // Hiển thị thông báo thành công bằng Toastify
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
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

// Hàm validateForm
function validateForm(event, index) {
    const status = document.getElementById(`post-status-${index}`).value;
    if (status !== '0' && status !== '1') {
        alert('Status chỉ có thể là số 0 hoặc 1.');
        event.preventDefault();
        return false;
    }
    return true;
}

// Hàm hủy bỏ chỉnh sửa
function cancelEdit(index) {
    const row = document.getElementById(`detail-form-row-${index}`);
    const form = document.getElementById(`post-form-${index}`);
    // Chuyển form về trạng thái ban đầu hoặc đóng form
    form.classList.remove("show"); // Bỏ lớp show để không chạy fadeIn nữa
    form.classList.add("fade-out"); // Thêm lớp fade-out để chạy animation fadeOut
    setTimeout(() => {
        row.style.display = "none"; // Ẩn hàng sau khi fadeOut kết thúc
        form.classList.remove("fade-out"); // Bỏ lớp fade-out để reset trạng thái
    }, 300); // Thời gian chờ khớp với thời lượng animation
}


// xóa
function confirmDelete(id) {
    if (confirm('Bạn có chắc muốn xóa sản phẩm này?')) {
        fetch(`/api/post/delete/${id}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Xóa không thành công');
                }
                return response.json();
            })
            .then(data => {
                alert(data.message || 'Xóa thành công');
                window.location.href = '/api/post/list'; // Quay lại trang danh sách bài đăng
            })
            .catch(error => {
                console.error(error);
                alert('Đã xảy ra lỗi khi xóa');
            });
    }
}

function toggleEditForm(index) {
    const row = document.getElementById(`detail-form-row-${index}`);
    const form = document.getElementById(`post-form-${index}`);

    if (row.style.display === "none") {
        // Hiển thị form với fadeIn
        row.style.display = "table-row";
        form.classList.remove("fade-out");
        form.classList.add("show"); // Thêm lớp show để chạy animation fadeIn
    } else {
        // Ẩn form với fadeOut
        form.classList.remove("show"); // Bỏ lớp show để không chạy fadeIn nữa
        form.classList.add("fade-out"); // Thêm lớp fade-out để chạy animation fadeOut
        setTimeout(() => {
            row.style.display = "none"; // Ẩn hàng sau khi fadeOut kết thúc
            form.classList.remove("fade-out"); // Bỏ lớp fade-out để reset trạng thái
        }, 300); // Thời gian chờ khớp với thời lượng animation
    }
}

function searchByUsername() {
    const input = document.getElementById('search-input').value.toLowerCase();
    const rows = document.querySelectorAll("tbody tr");

    // Ẩn tất cả các form đang mở
    rows.forEach(row => {
        const form = row.querySelector("form");
        if (form) {
            form.classList.remove("show", "fade-out"); // Bỏ lớp hiển thị
            row.style.display = "none"; // Ẩn form
        }
    });

    rows.forEach(row => {
        const usernameCell = row.querySelector("td:nth-child(2)");
        const username = usernameCell ? usernameCell.textContent.toLowerCase() : "";

        if (username.includes(input)) {
            row.style.display = ""; // Hiển thị nếu tìm thấy
        } else {
            row.style.display = "none"; // Ẩn nếu không tìm thấy
        }
    });
}


function filterByStatus(status) {
    const rows = document.querySelectorAll("tbody tr");

    // Lọc các hàng dựa trên trạng thái
    rows.forEach(row => {
        const statusButton = row.querySelector("td:nth-child(4) button");
        const isActive = statusButton && statusButton.classList.contains("btn-success");
        const isInactive = statusButton && statusButton.classList.contains("btn-secondary");

        // Kiểm tra và hiển thị lại dựa trên trạng thái lọc
        if (status === 'all') {
            row.style.display = ""; // Hiển thị lại tất cả hàng
        } else if (status === 'active' && isActive) {
            row.style.display = ""; // Hiển thị nếu là active
        } else if (status === 'inactive' && isInactive) {
            row.style.display = ""; // Hiển thị nếu là inactive
        } else {
            row.style.display = "none"; // Ẩn nếu không phù hợp với bộ lọc
        }
    });

    // Ẩn tất cả các form đang mở
    rows.forEach(row => {
        const formRow = row.nextElementSibling; // Lấy hàng chứa form update
        if (formRow && formRow.classList.contains("detail-form-row")) {
            formRow.style.display = "none"; // Ẩn form update
        }
        //row.style.display = "none"; // Ẩn hàng chính
    });
}

async function fetchReports() {
    try {
        const response = await fetch('/api/reports/post');
        const data = await response.json();

        if (data.message === "Reports fetched successfully") {
            const reports = data.data;
            const rows = document.querySelectorAll("tbody tr");

            rows.forEach(row => {
                const postId = row.getAttribute('data-id');
                if (postId) {
                    const reportCount = reports.filter(report => report.id_problem === postId && report.status === 1).length;

                    const reportButtonCell = row.querySelector('.report-status');
                    if (reportButtonCell) {
                        if (reportCount > 0) {
                            reportButtonCell.innerHTML = `
                                <button class="btn btn-danger shadow-button" style="font-size: 0.75rem; width: 120px;">
                                    Đã báo bị cáo (${reportCount})
                                </button>
                            `;
                            reportButtonCell.querySelector('button').addEventListener('click', () => showReportModal(postId, reports));
                        } else {
                            reportButtonCell.innerHTML = `<span>Chưa bị báo cáo</span>`;
                        }
                    }
                }
            });
        }
    } catch (error) {
        console.error("Error fetching reports:", error);
    }
}

function fetchPosts() {
    axios.get('/api/posts')
        .then(response => {
            if (response.status === 200) {
                const posts = response.data; // Kiểm tra posts để xác minh cấu trúc
                updateTable(posts);
                fetchReports();
            } else {
                console.error("Error: Unable to fetch post list.");
            }
        })
        .catch(error => console.error("Fetch error:", error));
}

function updateTable(posts) {
    const tableBody = document.querySelector("tbody");
    
    // Kiểm tra nếu không tìm thấy tableBody, sẽ thông báo lỗi
    if (!tableBody) {
        console.error("Error: Table body not found.");
        return;
    }

    // Xóa nội dung hiện tại
    tableBody.innerHTML = "";

    posts.forEach((post, index) => {
        const row = document.createElement("tr");
        row.setAttribute("data-id", post._id);

        // Xây dựng hàng bảng HTML
        row.innerHTML = `
            <td style="vertical-align: middle;">${post.title}</td>
            <td style="vertical-align: middle;">${post.user_id?.username || "N/A"}</td>
            <td style="vertical-align: middle;">${post.content}</td>
            <td style="vertical-align: middle;">
                ${getStatusButton(post.status)}
            </td>
            <td style="vertical-align: middle;">${post.post_type}</td>
            <td class="report-status" style="vertical-align: middle;">
                <span>Chưa báo cáo</span>
            </td>
            <td class="text-center" style="vertical-align: middle;">
                <div class="d-flex justify-content-center align-items-center gap-2">
                    <button class="btn btn-primary shadow-button"
                        style="font-size: 0.75rem; margin: 10px;"
                        onclick="toggleEditForm('${index}')">Cập nhật</button>
                    <button class="btn btn-danger shadow-button"
                        style="font-size: 0.75rem; margin: 10px;"
                        onclick="confirmDelete('${post._id}')">Xoá</button>
                </div>
            </td>
        `;
        
        // Thêm dòng mới vào bảng
        tableBody.appendChild(row);
    });
}

function getStatusButton(status) {
    if (status === 0) {
        return '<button class="btn btn-success shadow-button" style="font-size: 0.75rem; width: 120px;">Đang hoạt động</button>';
    } else if (status === 1) {
        return '<button class="btn btn-secondary shadow-button" style="font-size: 0.75rem; width: 120px;">Dừng hoạt động</button>';
    } else if (status === 2) {
        return '<button class="btn btn-danger shadow-button" style="font-size: 0.75rem; width: 120px;">Bị Ban</button>';
    }
    return '';
}


function closeModal() {
    const modal = document.getElementById('reportModal');
    modal.style.display = 'none'; // Ẩn modal
}

// Tạo thông báo mới
function createNotification(userId, title, content) {
    // Gọi API POST để tạo thông báo
    axios.post('/api/post/notifications', {
        user_id: userId,
        title: title,
        content: content,
        status: 'unread'
    })
        .then(response => {
            if (response.data.status === 200) {
                console.log("Thông báo đã được tạo!");
            } else {
                console.log("Lỗi tạo thông báo");
            }
        })
        .catch(error => console.error("Error:", error));

}

// Gửi phản hồi khách hàng và cập nhật trạng thái báo cáo
function submitCustomerReply(index, reportId, userId) {
    // Lấy nội dung phản hồi từ ô textarea
    const replyContent = document.getElementById(`customerReply-${index}`).value;

    // Gửi phản hồi cho báo cáo
    axios.put(`/api/reports/post/${reportId}/status`, {
        status: 0,
        customer_reply: replyContent
    })
        .then(response => {
            if (response.status === 200) {
                closeModal();
                fetchReports();
                alert("Phản hồi khách hàng thành công!");
            } else {
                alert("Có lỗi xảy ra khi phản hồi.");
            }
        })
        .catch(error => console.error("Error:", error));
}

// Ban bài viết và cập nhật trạng thái bài đăng
function banPost(index, postId, userId) {
    console.log("postId:", postId, "userId:", userId); // Kiểm tra giá trị postId và userId

    if (!postId || !userId) {
        alert("Thông tin bài viết hoặc người dùng không hợp lệ.");
        return;
    }

    axios.put(`/api/posts/${postId}/status`, {
        status: 2
    })
        .then(response => {
            if (response.status === 200) {
                closeModal();
                fetchPosts();
                alert("Cập nhật trạng thái bài đăng thành công!");
            } else {
                alert("Có lỗi xảy ra khi ban bài viết.");
            }
        })
        .catch(error => console.error("Error:", error));
}

// Hàm hiển thị modal với chi tiết báo cáo
function showReportModal(postId, reports) {
    const modal = document.getElementById('reportModal');
    const modalReportDetails = document.getElementById('modalReportDetails');

    // Tìm báo cáo liên quan đến postId
    const postReports = reports.filter(report => report.id_problem === postId && report.status === 1);

    if (postReports.length > 0) {
        modalReportDetails.innerHTML = postReports.map((report, index) => `
            <div class="report-item">
    <button class="btn btn-light report-button" style="width: 100%; text-align: left;" 
            onclick="toggleReportDetail(${index})">
        <span class="report-title">${index + 1}. ${report.title_support}</span>
        <i class="fas fa-chevron-down float-right" id="icon-${index}"></i>
    </button>
    <div class="report-detail" id="detail-${index}" style="display: none;">
        <div class="mt-3">
            <strong>Hình ảnh:</strong>
            <div class="image-gallery d-flex flex-wrap">
                ${report.image.map((img) => `
                    <div class="image-item" style="margin: 5px;">
                        <img src="${img}" alt="Report Image" style="height: 300px; width: auto; object-fit: cover;">
                    </div>
                `).join('')}
            </div>
        </div>

        <p><strong>Tiêu đề:</strong> ${report.title_support}</p>
        <p><strong>Nội dung báo cáo:</strong></p>
        <textarea class="form-control" rows="4" readonly>${report.content_support}</textarea>
        <div class="mt-3">
            <strong>Phản hồi khách hàng:</strong>
            <textarea class="form-control" rows="3" id="customerReply-${index}"></textarea>
        </div>
        <!-- Nút Phản hồi khách hàng -->
        <button class="btn btn-primary mt-3" onclick="submitCustomerReply(${index}, '${report._id}', '${report.user_id}') ">
            Phản hồi khách hàng
        </button>
        
        <!-- Nút Ban bài viết -->
        <button class="btn btn-danger mt-3" onclick="banPost(${index}, '${report.id_problem}', '${report.user_id}')">
            Ban bài viết
        </button>
    </div>
</div>
        `).join('');
    } else {
        modalReportDetails.innerHTML = `<p>Không có thông tin báo cáo cho bài viết này.</p>`;
    }

    // Hiển thị modal
    modal.style.display = 'block';
}

// Hàm đóng modal
function closeModal() {
    const modal = document.getElementById('reportModal');
    modal.style.display = 'none';
}

// Hàm chuyển đổi hiển thị chi tiết báo cáo
function toggleReportDetail(index) {
    const detail = document.getElementById(`detail-${index}`);
    const icon = document.getElementById(`icon-${index}`);
    if (detail.style.display === 'none') {
        detail.style.display = 'block';
        icon.classList.remove('fa-chevron-down');
        icon.classList.add('fa-chevron-up');
    } else {
        detail.style.display = 'none';
        icon.classList.remove('fa-chevron-up');
        icon.classList.add('fa-chevron-down');
    }
}


// Đảm bảo khi người dùng click bên ngoài modal thì đóng modal
window.addEventListener('click', function (event) {
    const modal = document.getElementById('reportModal');
    if (event.target === modal) {
        closeModal();
    }
});

// Gọi hàm khi trang web tải
window.onload = fetchReports;