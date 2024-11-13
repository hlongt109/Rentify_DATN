const checkRole = async (req, res, next) => {
    console.log("User Role: ", req.user?.role);
    // Kiểm tra nếu người dùng không có thông tin về vai trò
    if (!req.user || !req.user.role) {
        return res.status(403).json({ message: "Quyền truy cập bị từ chối: Thiếu thông tin role." });
    }

    // Kiểm tra xem vai trò có phải là 'admin' hoặc 'landlord' không
    if (req.user && req.user.role === 'admin') {
        return next(); // Tiếp tục nếu người dùng có quyền
    } else {
        return res.status(403).json({ message: "Bạn không có quyền truy cập vào trang này." });
    }
}
module.exports = checkRole;