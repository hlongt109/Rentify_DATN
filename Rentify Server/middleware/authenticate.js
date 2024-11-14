const jwt = require('jsonwebtoken');

function authenticate(req, res, next) {
    try {
        const authHeader = req.headers['authorization'];

        // Ghi log kiểm tra xem header Authorization có tồn tại không
        console.log("Authorization Header:", authHeader);

        if (!authHeader) {
            return res.status(403).json({ message: "Không có phản hồi từ Token 1" });
        }

        // Lấy token từ chuỗi "Bearer <token>"
        const token = authHeader.split(' ')[1];
        console.log("Extracted Token:", token);

        if (!token) {
            return res.status(403).json({ message: "Không có phản hồi từ Token 2" });
        }

        // Xác thực token
        jwt.verify(token, 'hoan', (err, decoded) => {
            if (err) {
                console.log("Token verification error:", err.message);
                return res.status(403).json({ message: "Lỗi xác thực Token" });
            }

            req.user = decoded; // Gán thông tin người dùng từ token vào request
            console.log("Token decoded successfully:", decoded);
            next(); // Cho phép đi tiếp
        });
    } catch (error) {
        console.error("Unexpected error in authentication:", error.message);
        return res.status(401).json({ message: "Lỗi xác thực", error: error.message });
    }
}

module.exports = authenticate;