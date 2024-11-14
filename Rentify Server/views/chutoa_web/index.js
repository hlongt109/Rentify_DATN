var express = require('express');
var router = express.Router();


router.get('/api/staffs_mgr', (req, res) => {
    res.render('chutoa_web/ejs/QuanLyNhanVien', (err, html) => {
        if (err) {
            return res.status(500).send(err);
        }
        res.render('chutoa_web/ejs/index', {
            title: 'Quản lý người dùng',
            body: html
        });
    });
})

module.exports = router;