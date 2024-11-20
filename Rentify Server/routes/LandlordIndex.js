var express = require('express');
var router = express.Router();
const User = require('../models/User');
const Service = require('../models/Service');
const mongoose = require('mongoose');

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

/////
router.get('/api/services_mgr/list/:id', async (req, res) => {
  const userId = req.params.id;

  if (!mongoose.Types.ObjectId.isValid(userId)) {
    return res.status(400).json({ message: "Invalid landlord_id format" });
  }

  const landlordObjectId = new mongoose.Types.ObjectId(userId);
  const data = await Service.find({ landlord_id: landlordObjectId });

  if (data.length === 0) {
    console.log("Không có dữ liệu");
    return res.render("Landlord_website/screens/QuanLydichVu", { data: [] });
  }

  res.render('Landlord_website/screens/QuanLydichVu', { data }, (err, html) => {
    if (err) {
      return res.status(500).send(err);
    }
    res.render('Landlord_website/LandlordIndex', {
      title: 'Quản lý dịch vụ',
      body: html
    });
  });
});
/////
router.get('/api/staffs_mgr/list/:id', async (req, res) => {
  const userId = req.params.id;

  if (!mongoose.Types.ObjectId.isValid(userId)) {
    return res.status(400).json({ message: "Invalid landlord_id format" });
  }

  const landlordObjectId = new mongoose.Types.ObjectId(userId);
  const data = await User.find({ landlord_id: landlordObjectId });

  if (data.length === 0) {
    console.log("Không có dữ liệu");
    return res.render("Landlord_website/screens/QuanLyNhanVien", { data: [] });
  }

  res.render('Landlord_website/screens/QuanLyNhanVien', { data }, (err, html) => {
    if (err) {
      return res.status(500).send(err);
    }
    res.render('Landlord_website/LandlordIndex', {
      title: 'Quản lý nhân viên',
      body: html
    });
  });
});

module.exports = router;