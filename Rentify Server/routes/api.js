var express = require('express');
var router = express.Router();

const userApi = require('./Api_Admin/demoApi');
const supportManageApi = require("./Api_BuildingOwner/support_manage_api")

// nối 
router.use('', userApi);
router.use('', supportManageApi);

module.exports = router; 