var express = require('express');
var router = express.Router();

const User = require("../../models/User");
//Lấy danh sách người dùng
router.get("/user/list", async (req, res) => {
    try {
        const find = await User.find();
        return res.status(200).json({
            status: 200,
            message: "Lấy dữ liệu thành công",
            find
        });
    } catch (error) {
        console.error(error);
        res.status(500).send('Lỗi server');
    }
});
//sửa người dùng
router.put("/user/update/:id", async (req, res) => {

});


module.exports = router