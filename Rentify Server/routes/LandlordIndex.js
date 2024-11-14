var express = require('express');
var router = express.Router();

router.get('/', function (req, res, next) {
    // Gọi route /api/home để lấy nội dung cho body
    router.handle({ method: 'GET', url: '/home' }, res, next);
});

router.get('/home', (req, res, next) => {
    res.render('Landlord_website/screens/HomePage', (err, html) => {
      if (err) {
        return res.status(500).send(err);
      }
      res.render('Landlord_website/LandlordIndex', {
        title: 'Quản lý người dùng',
        body: html
      });
    });
});

module.exports = router;