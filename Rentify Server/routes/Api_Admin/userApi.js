var express = require('express');
var router = express.Router();

const User = require("../../models/User");
//Lấy danh sách người dùng
router.get("/user/list", async (req, res) => {
    try {
        const find = await User.find();
        res.render('Accounts/listAccount', { showList: find });
    } catch (error) {
        console.error(error);
        res.status(500).send('Lỗi server');
    }
});

//sửa role người dùng
router.get("/user/update1/:id", async (req, res) => {
    const postId = req.params.id;
    const upDB = await User.findById(postId); // Tìm bài viết theo ID

    if (!upDB) {
        return res.status(404).json({ message: 'Account không tồn tại' });
    }

    res.render("Accounts/updateAccount", { upDB });
})
router.post("/user/update/:id", async (req, res) => {
    try {
        const findID = req.params.id;
        const { role } = req.body;

        const upDB = await User.findByIdAndUpdate(
            findID,
            { role },// thứ cần sửa ở đây
            { new: true, runValidators: true }
        );

        if (!upDB) {
            return res.status(404).json({ message: 'Không tìm thấy tàn khoản' });
        }
        let msg = 'Sửa thành công id: ' + findID;
        console.log(msg);
        return res.status(200).json({ message: 'Cập nhật thành công' });
    } catch (error) {
        smg = "Lỗi: " + error.message;
        return res.status(500).json({ message: smg });
    }
});
router.delete("/user/delete/:id", async (req, res) => {
    try {
        const findID = req.params.id;
        const deleteID = await User.findByIdAndDelete(findID);
        if (!deleteID) {
            return res.status(404).json({ message: "Khong tim thay tai khoan" });

        }
        return res.status(200).json({ message: "Xoa tai khoan thanh cong" });
    } catch (error) {
        return res.status(500).json({ message: 'Loi ' + error.message });

    }
});


module.exports = router