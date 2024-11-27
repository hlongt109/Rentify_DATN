var express = require('express');
var router = express.Router();
const User = require('../models/User');
const Service = require('../models/Service');
const Support = require('../models/Support');
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
/////
router.get('/api/support_mgr/list/:id', async (req, res) => {
  const userId = req.params.id;

  if (!mongoose.Types.ObjectId.isValid(userId)) {
    return res.status(400).json({ message: "Invalid landlord_id format" });
  }

  const landlordObjectId = new mongoose.Types.ObjectId(userId);
  const data = await Support.find({ landlord_id: landlordObjectId });

  if (data.length === 0) {
    console.log("Không có dữ liệu");
    return res.render("Landlord_website/screens/Support_Landlord", { data: [] });
  }

  res.render('Landlord_website/screens/Support_Landlord', { data }, (err, html) => {
    if (err) {
      return res.status(500).send(err);
    }
    res.render('Landlord_website/LandlordIndex', {
      title: 'Quản lý nhân viên',
      body: html
    });
  });
});
// payment manager
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

router.get('/payment_mgr/MaintenanceCosts', (req, res, next) => {
  res.render('Landlord_website/screens/payment_mgr/MaintenanceCosts', (err, html) => {
    if (err) {
      return res.status(500).send(err);
    }
    res.render('Landlord_website/LandlordIndex', {
      title: 'Quản lý bài đăng',
      body: html
    });
  });
});

router.get('/payment_mgr/StaffSalaries', (req, res, next) => {
  res.render('Landlord_website/screens/payment_mgr/StaffSalaries', (err, html) => {
    if (err) {
      return res.status(500).send(err);
    }
    res.render('Landlord_website/LandlordIndex', {
      title: 'Quản lý bài đăng',
      body: html
    });
  });
});

router.get('/payment_mgr/ServiceFees', (req, res, next) => {
  res.render('Landlord_website/screens/payment_mgr/ServiceFees', (err, html) => {
    if (err) {
      return res.status(500).send(err);
    }
    res.render('Landlord_website/LandlordIndex', {
      title: 'Quản lý bài đăng',
      body: html
    });
  });
});

router.get('/payment_mgr/RoomRent', (req, res, next) => {
  res.render('Landlord_website/screens/payment_mgr/RoomRent', (err, html) => {
    if (err) {
      return res.status(500).send(err);
    }
    res.render('Landlord_website/LandlordIndex', {
      title: 'Quản lý bài đăng',
      body: html
    });
  });
});
// post manager
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

//statistic

router.get('/statistic_mrg', (req, res, next) => {
  res.render('Landlord_website/screens/Statistic', (err, html) => {
    if(err){
      return res.status(500).send(err)
    }
    res.render('Landlord_website/LandlordIndex', {
      title: 'Quản lý bài đăng',
      body: html
    });
  })
})

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