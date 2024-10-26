var express = require('express');
var router = express.Router();

const supportManageApi = require("./Api_BuildingOwner/support_manage_api")
const staffManageApi = require("./Api_BuildingOwner/staff_manage_api")
const serviceManageApi = require("./Api_BuildingOwner/services_manage_api")
const statisticApi = require("./Api_BuildingOwner/statistic_api")
const paymentApi = require("./Api_BuildingOwner/payment_api")
const notificationApi = require("./Api_BuildingOwner/notification_api")
// nối 
router.use('', supportManageApi);
router.use('', staffManageApi);
router.use('', serviceManageApi);
router.use('', statisticApi);
router.use('', paymentApi);
router.use('', notificationApi);

module.exports = router; 