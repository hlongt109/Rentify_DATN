var express = require('express');
var router = express.Router();

// Ví dụ
// const tenRouter = require("./fileRouter")
// router.use('',tentenRouter)

//============================================

const userApi = require('./userRouter');
const adminApi = require('./adminRouter');


router.use('/', userApi);
router.use('/admin', adminApi);

module.exports = router; 