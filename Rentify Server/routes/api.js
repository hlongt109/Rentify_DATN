var express = require('express');
var router = express.Router();

// Ví dụ
// const tenRouter = require("./fileRouter")
// router.use('',tentenRouter)

//============================================
const userApi = require('./Api_Admin/demoApi');
const UserManage=require("./Api_BuildingOwner/UserManageApi")
// nối 
router.use('/', userApi);
router.use('/',UserManage)

module.exports = router; 