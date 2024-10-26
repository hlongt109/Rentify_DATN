var express = require('express');
var router = express.Router();

// Ví dụ
// const tenRouter = require("./fileRouter")
// router.use('',tentenRouter)

//============================================
const userApi = require('./Api_Admin/demoApi');
const serviceApi = require('./Api_User/serviceApi')
const roomApi = require('./Api_User/roomApi')
const invoiceApi = require('./Api_User/invoiceApi')
const reportApi = require('./Api_User/reportApi')
const paymentApi = require('./Api_User/paymentApi')

// nối 
router.use('/', userApi);
router.use('/', serviceApi)
router.use('/', roomApi)
router.use('/', invoiceApi)
router.use('/', reportApi)
router.use('/', paymentApi)

module.exports = router; 