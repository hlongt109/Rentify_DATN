var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  // Gọi route /api/home để lấy nội dung cho body
  router.handle({ method: 'GET', url: '/api/home' }, res, next);
});

router.get('/api/home', (req, res) => {
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

router.get('/post/list', (req, res) => {
  res.render('Posts/listPost', { list }, (err, html) => {
    if (err) {
      return res.status(500).send(err);
    }
    res.render('index', {
      title: 'Quản lý người dùng',
      body: html
    });
  });
});


module.exports = router;
