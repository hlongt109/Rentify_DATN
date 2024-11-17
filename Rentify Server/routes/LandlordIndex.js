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

router.get('/payment_mgr', (req, res, next) => {
  res.render('Landlord_website/screens/PaymentManage', (err, html) => {
    if (err) {
      console.log(err)
      return res.status(500).send(err);
    }
    res.render('Landlord_website/LandlordIndex', {
      title: 'Quản lý thanh toán',
      body: html
    });
  });
});

router.get('/post_mgr', (req, res, next) => {
  res.render('Landlord_website/screens/PostManage', (err, html) => {
    if (err) {
      return res.status(500).send(err);
    }
    res.render('Landlord_website/LandlordIndex', {
      title: 'Quản lý bài đăng',
      body: html
    });
  });
});

module.exports = router;