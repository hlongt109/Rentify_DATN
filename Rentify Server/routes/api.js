var express = require('express');
var router = express.Router();

// Ví dụ
// const tenRouter = require("./fileRouter")
// router.use('',tentenRouter)

//============================================

const userApi = require('./userRouter');



router.use('/', userApi);


module.exports = router; 