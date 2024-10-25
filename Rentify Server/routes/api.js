var express = require('express');
var router = express.Router();

const adminApi = require('./Api_Admin/loginApi');
const supportManageApi = require("./Api_BuildingOwner/support_manage_api")
const staffManageApi = require("./Api_BuildingOwner/staff_manage_api")
const serviceManageApi = require("./Api_BuildingOwner/services_manage_api")
// nối 
router.use('', adminApi);
router.use('', supportManageApi);
router.use('', staffManageApi);
router.use('', serviceManageApi);

module.exports = router; 