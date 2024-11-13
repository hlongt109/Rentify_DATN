function fetchNotifications() {
    const token = localStorage.getItem('token');

    if (!token) {
        alert('Vui lòng đăng nhập để xem thông báo.');
        window.location.href = '/api/admin/login'; //
        return;
    }

    fetch('/api/notifications', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                response.json().then(error => console.error('Lỗi:', error.message));
                throw new Error('Failed to fetch notifications');
            }
            return response.json();
        })
        .then(data => {
            if (data && Array.isArray(data.notifications)) {
                updateNotificationCount(data.notifications);
                renderNotificationList(data.notifications);
            } else {
                console.error('Dữ liệu trả về không hợp lệ:', data);
            }
        })
        .catch(error => {
            console.error('Lỗi khi lấy thông báo:', error);
        });
}

// Hàm để cập nhật số lượng thông báo chưa đọc
function updateNotificationCount(notifications) {
    const unreadCount = notifications.filter(notification => notification.read_status === 'unread').length;
    document.getElementById('notification-count').innerText = unreadCount;
}

// Hàm để hiển thị danh sách thông báo
function renderNotificationList(notifications) {
    const notificationListElement = document.getElementById('notification-list');
    notificationListElement.innerHTML = '';

    notifications.forEach(notification => {
        console.log('Notification ID:', notification._id);

        const notificationElement = document.createElement('div');
        notificationElement.classList.add('notification-item');

        if (notification.read_status === 'unread') {
            notificationElement.classList.add('unread');
        } else {
            notificationElement.classList.add('read');
        }
        notificationElement.innerHTML = `
            <div class="notification-title">${notification.title}</div>
            <div class="notification-content">${notification.content}</div>
            <div class="notification-time">${new Date(notification.created_at).toLocaleString()}</div>
            <button class="delete-btn" onclick="event.stopPropagation(); deleteNotification('${notification._id}')">x</button>
        `;

        notificationElement.addEventListener('click', () => {
            viewNotificationDetail(notification);
            markNotificationAsRead(notification._id); // Gọi hàm đánh dấu là đã đọc
        });
        notificationListElement.appendChild(notificationElement);
    });
}
// Hàm để xem chi tiết thông báo
function viewNotificationDetail(notification) {
    document.getElementById('notification-detail-title').innerText = notification.title;
    document.getElementById('notification-detail-content').innerText = notification.content;
    document.getElementById('notification-detail-time').innerText = new Date(notification.created_at).toLocaleString();


    const modal = document.getElementById('notification-detail-modal');
    modal.style.display = 'block'; // Hiển thị modal
}
// Hàm để đóng modal
function closeNotificationDetail() {
    const modal = document.getElementById('notification-detail-modal');
    modal.style.display = 'none'; // Ẩn modal chi tiet
}

// Đóng modal khi nhấn ra ngoài
window.onclick = function (event) {
    const modal = document.getElementById('notification-detail-modal');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
}

//cái này dùng để tắt hộp thoại thông báo khi ấn bất kì trên màn hình
document.addEventListener('DOMContentLoaded', function () {
    // Lấy các phần tử thông báo sau khi DOM đã tải xong
    const notificationList = document.getElementById('notification-list');
    const notificationBell = document.querySelector('.notification-bell');

    document.addEventListener('click', function (event) {
        // Kiểm tra nếu các phần tử tồn tại và danh sách đang hiển thị
        if (notificationList && notificationList.classList.contains('show') &&
            !notificationList.contains(event.target) &&
            !notificationBell.contains(event.target)) {

            notificationList.classList.remove('show'); // Ẩn danh sách thông báo
        }
    });
});

// Hàm để toggle (ẩn/hiện) danh sách thông báo khi nhấn chuông
function toggleNotificationList() {
    const notificationList = document.getElementById('notification-list');
    notificationList.classList.toggle('show');
    if (!notificationList.classList.contains('show')) {
        fetchNotifications();
    }
}
// Hàm để đánh dấu thông báo là đã đọc
function markNotificationAsRead(notificationId) {
    const token = localStorage.getItem('token');

    if (!token) {
        alert('Vui lòng đăng nhập để đánh dấu thông báo là đã đọc.');
        return;
    }

    fetch(`/api/notifications/${notificationId}/read`, {
        method: 'PATCH',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to mark notification as read');
            }
            return response.json();
        })
        .then(data => {
            if (data.success) {
                fetchNotifications();
            }
        })
        .catch(error => {
            console.error('Lỗi khi đánh dấu thông báo là đã đọc:', error);
        });
}

//chức năng xóa thông báo
function deleteNotification(notificationId) {
    const token = localStorage.getItem('token');
    if (!token) {
        alert('Vui lòng đăng nhập để xóa thông báo.');
        return;
    }

    fetch(`/api/notifications/${notificationId}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to delete notification');
            }
            return response.json();
        })
        .then(data => {
            if (data.status === 200) {
                fetchNotifications();
            }
        })
        .catch(error => {
            console.error('Lỗi khi xóa thông báo:', error);
        });
}

fetchNotifications();