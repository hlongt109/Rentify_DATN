var express = require('express');
var router = express.Router();

// Ví dụ
// const tenRouter = require("./fileRouter")
// router.use('',tentenRouter)

//============================================
const userApi = require('./Api_Admin/demoApi');

// nối 
router.use('/', userApi);


module.exports = router; 