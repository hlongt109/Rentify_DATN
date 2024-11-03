var express = require('express');
var router = express.Router();

const Post = require('../models/Post')

/* GET home page. */
router.get('/', function(req, res, next) {
  // Gọi route /api/home để lấy nội dung cho body
  router.handle({ method: 'GET', url: '/api/home' }, res, next);
});

router.get('/api/home', (req, res) => {
  res.render('UserManagement/test', (err, html) => {
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

router.get('/api/post/list', async (req, res) => {
  try {
      const showList = await Post.find(); // Lấy danh sách bài đăng từ MongoDB
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


module.exports = router;
