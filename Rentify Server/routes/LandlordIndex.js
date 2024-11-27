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

router.get('/BuildingPage', (req, res) => {
  res.render('Landlord_website/screens/Building/BuildingPage', (err, html) => {
      if (err) {
          console.error(err);
          return res.status(500).send(err.message);
      }
      res.render('Landlord_website/LandlordIndex', {
          title: 'Toà nhà & căn hộ',
          body: html
      });
  });
});

router.get('/AddBuildingPage', (req, res) => {
  res.render('Landlord_website/screens/Building/AddBuildingPage', (err, html) => {
    if (err) {
      return res.status(500).send(err);
    }
    res.render('Landlord_website/LandlordIndex', {
      title: 'Thêm toà nhà',
      body: html
    });
  });
});

router.get('/AddRoom', (req, res) => {
  res.render('Landlord_website/screens/Rooms/AddRoomPage', (err, html) => {
    if (err) {
      return res.status(500).send(err);
    }
    res.render('Landlord_website/LandlordIndex', {
      title: 'Thêm phòng',
      body: html
    });
  });
});

router.get('/UpdateRoom', (req, res) => {
  res.render('Landlord_website/screens/Rooms/UpdateRoomPage', (err, html) => {
    if (err) {
      return res.status(500).send(err);
    }
    res.render('Landlord_website/LandlordIndex', {
      title: 'Chỉnh sửa phòng',
      body: html
    });
  });
});

module.exports = router;