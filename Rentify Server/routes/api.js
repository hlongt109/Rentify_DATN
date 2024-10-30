var express = require("express");
var router = express.Router();

// Ví dụ
// const tenRouter = require("./fileRouter")
// router.use('',tentenRouter)

//============================================
//admin
const adminApi = require('./Api_Admin/loginApi');
const postAdmin = require('./Api_Admin/postApi');
const userAdmin = require('./Api_Admin/userApi');
const nofAdmin = require('./Api_Admin/nofApi');
const spAdmin = require('./Api_Admin/supportApi');
const statAdmin = require('./Api_Admin/statsApi');
// building_owner
const supportManageApi = require("./Api_BuildingOwner/support_manage_api")
const staffManageApi = require("./Api_BuildingOwner/staff_manage_api")
const serviceManageApi = require("./Api_BuildingOwner/services_manage_api")
const statisticApi = require("./Api_BuildingOwner/statistic_api")
const paymentApi_BuildingOwner = require("./Api_BuildingOwner/payment_api")
const notificationApi = require('./Api_BuildingOwner/notification_api');
const UserManage = require("./Api_BuildingOwner/UserManageApi")
const PostManage = require("./Api_BuildingOwner/PostManageApi")
const PaymentManage = require("./Api_BuildingOwner/PaymentManageApi")
const ContractManage = require("./Api_BuildingOwner/ContractManageApi")
const BuildingManage = require("./Api_BuildingOwner/BuildingManageApi")
// user
const roomApi = require('./Api_User/roomApi')
const invoiceApi = require('./Api_User/invoiceApi')
const reportApi = require('./Api_User/reportApi')
const paymentApi = require('./Api_User/paymentApi')
const serviceApi = require('./Api_User/serviceApi')
const apiUser = require("./Api_User/UserApi");
// staff
const post_staffApi = require('./Api_Staff/Post');
const contract_staffApi = require('./Api_Staff/Contract');
const payment_staffApi = require('./Api_Staff/Payment');
const notification_staffApi = require('./Api_Staff/Notification');
const room_staffApi = require('./Api_Staff/Room');
const invoice_staffApi = require('./Api_Staff/Invoice');
const request_staffApi = require('./Api_Staff/Request');

// nối 
router.use('/', serviceApi)
router.use('/', roomApi)
router.use('/', invoiceApi)
router.use('/', reportApi)
router.use('/', paymentApi)
router.use('', supportManageApi);
router.use('', staffManageApi);
router.use('', serviceManageApi);
router.use('', statisticApi);
router.use('', paymentApi_BuildingOwner);
router.use('', notificationApi);
router.use("/", apiUser);
router.use('', adminApi);
router.use('', postAdmin);
router.use('', userAdmin);
router.use('', nofAdmin);
router.use('', spAdmin);
router.use('', statAdmin);
router.use('/staff/posts', post_staffApi); // Đổi đường dẫn cho post_staffApi
router.use('/staff/contracts', contract_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use('/staff/payments', payment_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use('/staff/notifications', notification_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use('/staff/rooms', room_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use('/staff/invoices', invoice_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use('/staff/requests', request_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use('/', UserManage)
router.use('/', PostManage)
router.use('/', PaymentManage)
router.use('/', ContractManage)
router.use('/', BuildingManage)

module.exports = router;
