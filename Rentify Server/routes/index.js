var express = require('express');
var router = express.Router();

const Post = require('../models/Post')
const Service = require('../models/Service');
const User = require('../models/User');
const Report = require('../models/Report');
const Support = require('../models/Support');
const authenticate = require('../middleware/authenticate');
const checkRole = require('../middleware/checkRole');

/* GET home page. */
router.get('/', function (req, res, next) {
  // Gọi route /api/home để lấy nội dung cho body
  router.handle({ method: 'GET', url: '/api/home' }, res, next);
});

router.get('/api/home', (req, res, next) => {
  res.render('Home/Home', (err, html) => {
    if (err) {
      return res.status(500).send(err);
    }
    res.render('index', {
      title: 'Quản lý người dùng',
      body: html
    });
  });
});
router.get('/api/home1', (req, res, next) => {
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

router.get('/api/user/show-list', (req, res) => {
  res.render('UserManagement/UserManagement', (err, html) => {
    if (err) {
      return res.status(500).send(err);
    }
    res.render('index', {
      title: 'Quản lý người dùng',
      body: html
    });
  });
});

router.get('/api/support_customer', (req, res) => {
  res.render('Support/supportManagement', (err, html) => {
    if (err) {
      return res.status(500).send(err);
    }
    res.render('index', {
      title: 'Quản lý người dùng',
      body: html
    });
  });
});


router.get('/api/post/list', async (req, res) => {
  try {
    const showList = await Post.find().populate('user_id', 'username'); // Lấy danh sách bài đăng từ MongoDB
    res.render('Posts/listPost', { list: showList }, (err, html) => { // Truyền biến list vào template
      if (err) {
        return res.status(500).send(err);
      }
      res.render('index', {
        title: 'Quản lý bài đăng',
        body: html
      });
    });
  } catch (error) {
    console.error(error);
    res.status(500).send('Lỗi server');
  }
});

router.get('/api/stats/sum', async (req, res) => {
  const totalAccounts = await User.countDocuments();
  res.render("Stats/listStats", { totalAccounts }, (err, html) => {
    if (err) {
      return res.status(500).send(err);
    }
    res.render('index', {
      title: 'Quản lý bài đăng',
      body: html
    });
  });
})
// report
router.get("/api/reports/list", async (req, res) => {
  res.render("Reports/ReportManager", (err, html) => {
    if (err) {
      return res.status(500).send(err);
    }
    res.render('index', {
      title: 'Quản lý báo cáo',
      body: html
    });
  })
});
// service
router.get("/api/service/adm", async (req, res) => {
  res.render("Services/ServiceManager", (err, html) => {
    if (err) {
      return res.status(500).send(err);
    }
    res.render('index', {
      title: 'Quản lý dịch vụ',
      body: html
    });
  })
});

module.exports = router;