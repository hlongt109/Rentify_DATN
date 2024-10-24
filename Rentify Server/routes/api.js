var express = require('express');
var router = express.Router();

const adminApi = require('./Api_Admin/loginApi');
const supportManageApi = require("./Api_BuildingOwner/support_manage_api")

// nối 
router.use('', adminApi);
router.use('', supportManageApi);

module.exports = router; 