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
    try {
        const findID = req.params.id;
        const { role } = req.body;

        const upDB = await User.findByIdAndUpdate(
            findID,
            { role },
            { new: true, runValidators: true } // new: true để trả về đối tượng cập nhật, runValidators: true để chạy các validators
        );

        if (!upDB) {
            return res.status(404).json({ message: 'Không tìm thấy tàn khoản' });
        }

        let msg = 'Sửa thành công id: ' + findID;
        console.log(msg);
        return res.status(200).json({ message: 'Cập nhật thành công', product: upDB });
    } catch (error) {
        smg = "Lỗi: " + error.message;
        return res.status(500).json({ message: smg });
    }
});


module.exports = router