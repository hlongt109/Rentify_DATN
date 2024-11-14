var express = require("express")
var router = express.Router();

const Support = require("../../models/Support")

// api
router.get("/support_customer", async (req, res) => {
    try {
        res.render('Support/supportManagement');
    } catch (error) {
        console.error(error);
        res.status(500).send('Lỗi server');
    }
})
router.get('/support/list', async (req, res) => {
    try {
        const find = await Support.find();

        return res.status(200).json({
            status: 200,
            message: "Lấy dữ liệu thành công",
            find
        });
    } catch (error) {
        console.error(error);
        res.status(500).send('Lỗi server');
    }
})

router.put("/support/update/:id", async (req, res) => {
    try {
        const findID = req.params.id;

        let { status } = req.body;

        const upDB = await Support.findByIdAndUpdate(
            findID,
            {
                status,
                updated_at: new Date().toISOString(),
            },
            { new: true, runValidators: true }

        )
        if (!upDB) {
            return res.status(404).json({ message: 'Không tìm thấy bài đăng' })
        }
        let msg = 'Sửa trạng thái thành công của ID: ' + findID;
        console.log(msg);
        return res.status(200).json({ message: 'Cap nhat thanh cong', support: upDB })
    } catch (error) {
        let msg = "Lỗi: " + error.message;
        return res.status(500).json({ message: msg });
    }

})

router.get("/api/support-customer/:id", async (req, res) => {
    try {
        const { id } = req.params;
        const support = await Support.findById(id);

        if (!support) {
            return res.status(404).json({
                status: 404,
                messenger: 'food drink not found'
            })
        }
        res.status(200).send(support)
    } catch (error) {
        handleServerError(res, error);
    }
})
module.exports = router;