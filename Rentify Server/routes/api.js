var express = require("express");
var router = express.Router();

// Ví dụ
// const tenRouter = require("./fileRouter")
// router.use('',tentenRouter)

//============================================
const serviceApi = require("./Api_User/serviceApi");
const roomApi = require("./Api_User/roomApi");
const invoiceApi = require("./Api_User/invoiceApi");
const reportApi = require("./Api_User/reportApi");
const paymentApi = require("./Api_User/paymentApi");
const supportManageApi = require("./Api_BuildingOwner/support_manage_api");
const staffManageApi = require("./Api_BuildingOwner/staff_manage_api");
const serviceManageApi = require("./Api_BuildingOwner/services_manage_api");
const statisticApi = require("./Api_BuildingOwner/statistic_api");
const paymentApi_BuildingOwner = require("./Api_BuildingOwner/payment_api");
const apiUser = require("./Api_User/UserApi");
const adminApi = require("./Api_Admin/loginApi");
const postAdmin = require("./Api_Admin/postApi");
const userAdmin = require("./Api_Admin/userApi");
const post_staffApi = require("./Api_Staff/Post");
const contract_staffApi = require("./Api_Staff/Contract");
const payment_staffApi = require("./Api_Staff/Payment");
const notification_staffApi = require("./Api_Staff/Notification");
const room_staffApi = require("./Api_Staff/Room");
const invoice_staffApi = require("./Api_Staff/Invoice");
const request_staffApi = require("./Api_Staff/Request");
const UserManage = require("./Api_BuildingOwner/UserManageApi");
const PostManage = require("./Api_BuildingOwner/PostManageApi");
const PaymentManage = require("./Api_BuildingOwner/PaymentManageApi");
const ContractManage = require("./Api_BuildingOwner/ContractManageApi");
const BuildingManage = require("./Api_BuildingOwner/BuildingManageApi");
const notificationApi = require("./Api_BuildingOwner/notification_api");
const findPostApi = require("./Api_User/findPostApi");
const roomBookingApi = require("./Api_User/RoomBookingApi");
const personalContractApi = require("./Api_User/personalContractApi");
// nối
router.use("/", serviceApi);
router.use("/", roomApi);
router.use("/", invoiceApi);
router.use("/", reportApi);
router.use("/", paymentApi);
router.use("", supportManageApi);
router.use("", staffManageApi);
router.use("", serviceManageApi);
router.use("", statisticApi);
router.use("", paymentApi_BuildingOwner);
router.use("", notificationApi);
router.use("/", apiUser);
router.use("", adminApi);
router.use("", postAdmin);
router.use("", userAdmin);
router.use("/staff/posts", post_staffApi); // Đổi đường dẫn cho post_staffApi
router.use("/staff/contracts", contract_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use("/staff/payments", payment_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use("/staff/notifications", notification_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use("/staff/rooms", room_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use("/staff/invoices", invoice_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use("/staff/requests", request_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use("/", UserManage);
router.use("/", PostManage);
router.use("/", PaymentManage);
router.use("/", ContractManage);
router.use("/", BuildingManage);
router.use("/", findPostApi);
router.use("/", roomBookingApi);
router.use("/", personalContractApi);

module.exports = router;
