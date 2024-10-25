var express = require('express');
var router = express.Router();


const supportManageApi = require("./Api_BuildingOwner/support_manage_api")
const staffManageApi = require("./Api_BuildingOwner/staff_manage_api")
const serviceManageApi = require("./Api_BuildingOwner/services_manage_api")

router.use('', supportManageApi);
router.use('', staffManageApi);
router.use('', serviceManageApi);

//============================================
//const userApi = require('./Api_Admin/demoApi');

//admin
const adminApi = require('./Api_Admin/loginApi');
const postAdmin = require('./Api_Admin/postApi');
const userAdmin = require('./Api_Admin/userApi'); //user ở đây là model chứ không phải role
// nối 
router.use('', adminApi);
router.use('', userAdmin);
//============================================

//Chủ nhà

//Nối

//============================================


//NhanVien
const post_staffApi = require('./Api_Staff/Post_Staff_Api');
const contract_staffApi = require('./Api_Staff/Contract_Staff_Api');
const payment_staffApi = require('./Api_Staff/Payment_Staff_Api');
const notification_staffApi = require('./Api_Staff/Notification_Staff_Api');
const room_staffApi = require('./Api_Staff/Room_Staff_Api');
const invoice_staffApi = require('./Api_Staff/Invoice_Staff_Api');
const request_staffApi = require('./Api_Staff/Request_Staff_Api');
// nối 
//router.use('/', userApi);
router.use('/staff/posts', post_staffApi); // Đổi đường dẫn cho post_staffApi
router.use('/staff/contracts', contract_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use('/staff/payments', payment_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use('/staff/notifications', notification_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use('/staff/rooms', room_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use('/staff/invoices', invoice_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use('/staff/requests', request_staffApi); // Đổi đường dẫn cho contract_staffApi

//User

//Nối

//==========================================


module.exports = router; 