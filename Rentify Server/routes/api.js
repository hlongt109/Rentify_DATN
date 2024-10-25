var express = require('express');
var router = express.Router();

// Ví dụ
// const tenRouter = require("./fileRouter")
// router.use('',tentenRouter)

//============================================
const userApi = require('./Api_Admin/demoApi');
//NhanVien

const post_staffApi = require('./Api_Staff/Post');
const contract_staffApi = require('./Api_Staff/Contract');
const payment_staffApi = require('./Api_Staff/Payment');
const notification_staffApi = require('./Api_Staff/Notification');
const room_staffApi = require('./Api_Staff/Room');
const invoice_staffApi = require('./Api_Staff/Invoice');
const request_staffApi = require('./Api_Staff/Request');
// nối 
router.use('/', userApi);
router.use('/staff/posts', post_staffApi); // Đổi đường dẫn cho post_staffApi
router.use('/staff/contracts', contract_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use('/staff/payments', payment_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use('/staff/notifications', notification_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use('/staff/rooms', room_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use('/staff/invoices', invoice_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use('/staff/requests', request_staffApi); // Đổi đường dẫn cho contract_staffApi

module.exports = router; 