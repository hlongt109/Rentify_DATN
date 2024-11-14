var express = require('express');
var router = express.Router();


router.get('/api/home1', (req, res) => {
    res.render('chutoa_web/ejs/Home', (err, html) => {
        if (err) {
            return res.status(500).send(err);
        }
        res.render('chutoa_web/ejs/index', {
            title: 'Quản lý người dùng',
            body: html
        });
    });
});


module.exports = router;