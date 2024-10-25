var express = require('express');
var router = express.Router();

const UserManage=require("./Api_BuildingOwner/UserManageApi")
const PostManage= require("./Api_BuildingOwner/PostManageApi")
const PaymentManage= require("./Api_BuildingOwner/PaymentManageApi")
const ContractManage= require("./Api_BuildingOwner/ContractManageApi")
const BuildingManage= require("./Api_BuildingOwner/BuildingManageApi")
// nối 
router.use('/',UserManage)
router.use('/',PostManage)
router.use('/',PaymentManage)
router.use('/',ContractManage)
router.use('/',BuildingManage)

module.exports = router; 