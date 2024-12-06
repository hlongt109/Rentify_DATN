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
//
router.get('/statistic_mrg', async (req, res) => {
  res.render('Landlord_website/screens/Booking/BookingList', (err, html) => {
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
router.get('/services_mgr', async (req, res) => {
  res.render('Landlord_website/screens/QuanLydichVu', (err, html) => {
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
router.get('/staffs_mgr', async (req, res) => {
  res.render('Landlord_website/screens/QuanLyNhanVien', (err, html) => {
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
router.get('/support_mgr', async (req, res) => {
  res.render('Landlord_website/screens/Support/Support_Landlord', (err, html) => {
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
  res.render('Landlord_website/screens/Schedules', (err, html) => {
    if (err) {
      return res.status(500).send(err)
    }
    res.render('Landlord_website/LandlordIndex', {
      title: 'Quản lý bài đăng',
      body: html
    });
  })
})

//

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

router.get('/ContractPage', (req, res) => {
  res.render('Landlord_website/screens/Contract/ContractPage', (err, html) => {
    if (err) {
      return res.status(500).send(err);
    }
    res.render('Landlord_website/LandlordIndex', {
      title: 'Quản lý hợp đồng',
      body: html
    });
  });
});

router.get('/AddContractPage', (req, res) => {
  res.render('Landlord_website/screens/Contract/AddContractPage', (err, html) => {
    if (err) {
      return res.status(500).send(err);
    }
    res.render('Landlord_website/LandlordIndex', {
      title: 'Thêm hợp đồng',
      body: html
    });
  });
});

module.exports = router;