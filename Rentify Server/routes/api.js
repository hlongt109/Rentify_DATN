var express = require("express");
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
const supportManageApi = require("./Api_BuildingOwner/support_manage_api")
const staffManageApi = require("./Api_BuildingOwner/staff_manage_api")
const serviceManageApi = require("./Api_BuildingOwner/services_manage_api")
const statisticApi = require("./Api_BuildingOwner/statistic_api")
const paymentApi = require("./Api_BuildingOwner/payment_api")
const apiUser = require("./Api_User/UserApi");
const adminApi = require('./Api_Admin/loginApi');
const postAdmin = require('./Api_Admin/postApi');
const userAdmin = require('./Api_Admin/userApi');

// nối 
router.use('/', userApi);
router.use('/', serviceApi)
router.use('/', roomApi)
router.use('/', invoiceApi)
router.use('/', reportApi)
router.use('/', paymentApi)
router.use('', supportManageApi);
router.use('', staffManageApi);
router.use('', serviceManageApi);
router.use('', statisticApi);
router.use('', paymentApi);
router.use('', notificationApi);
router.use("/", apiUser);
router.use('', adminApi);
router.use('', postAdmin);
router.use('', userAdmin);

module.exports = router;
