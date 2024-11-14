const jwt = require('jsonwebtoken');

function authenticate(req, res, next) {
    try {
        const authHeader = req.headers['authorization'];
        console.log("authHeader:", authHeader);

        // Kiểm tra nếu không có header Authorization
        if (!authHeader) {
            return res.status(401).json({ message: 'Không có header Authorization' });
        }

        const token = authHeader.split(' ')[1]; // Lấy token sau từ khóa 'Bearer'

        // Kiểm tra nếu không có token
        if (!token) {
            return res.status(401).json({ message: 'Không có token trong header Authorization' });
        }

        console.log("Extracted Token:", token);

        // Xác thực token
        jwt.verify(token, 'hoan', (err, decoded) => {
            if (err) {
                console.log("Token verification error:", err.message);
                return res.status(403).json({ message: "Lỗi xác thực Token", error: err.message });
            }

            req.user = decoded; // Lưu thông tin người dùng vào request
            console.log("Token decoded successfully:", decoded);
            next(); // Tiếp tục xử lý tiếp theo
        });
    } catch (error) {
        console.error("Unexpected error in authentication:", error.message);
        return res.status(500).json({ message: "Lỗi không mong muốn trong quá trình xác thực", error: error.message });
    }
}

module.exports = authenticate;