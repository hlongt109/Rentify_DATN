var express = require("express");
var router = express.Router();

// Ví dụ
// const tenRouter = require("./fileRouter")
// router.use('',tentenRouter)

//============================================
//admin
const adminApi = require("./Api_Admin/loginApi");
const notificationAdmin = require("./Api_Admin/notificationApi");
const postAdmin = require("./Api_Admin/postApi");
const userAdmin = require("./Api_Admin/userApi");
const nofAdmin = require("./Api_Admin/nofApi");
const spAdmin = require("./Api_Admin/supportApi");
const statAdmin = require("./Api_Admin/statsApi");
const reportApiAdmin = require("./Api_Admin/report_mgr_api");
const authApi = require("./Api_Auth/auth_Api");
const serviceMgrApi = require("./Api_Admin/service_adm_api")
// building_owner
const invoiceMgr = require("./Api_BuildingOwner/invoices_mgr_api");
const supportManageApi = require("./Api_BuildingOwner/support_manage_api");
const staffManageApi = require("./Api_BuildingOwner/staff_manage_api");
const serviceManageApi = require("./Api_BuildingOwner/services_manage_api");
const statisticApi = require("./Api_BuildingOwner/statistic_api");
const paymentApi_BuildingOwner = require("./Api_BuildingOwner/payment_api");
const notificationApi = require("./Api_BuildingOwner/notification_api");
const UserManage = require("./Api_BuildingOwner/UserManageApi");
const PostManage = require("./Api_BuildingOwner/PostManageApi");
const PaymentManage = require("./Api_BuildingOwner/PaymentManageApi");
const ContractManage = require("./Api_BuildingOwner/ContractManageApi");
const BuildingManage = require("./Api_BuildingOwner/BuildingManageApi");
const QuanLy = require('./Api_BuildingOwner/UserNV');
const statisticMgrAPi = require("./Api_BuildingOwner/statistic_mgr_api");
const roomManager = require("./Api_BuildingOwner/RoomManager");
const bookings = require("./Api_BuildingOwner/BookingManager");
// user
const roomApi = require("./Api_User/roomApi");
const invoiceApi = require("./Api_User/invoiceApi");
const reportApi = require("./Api_User/reportApi");
const paymentApi = require("./Api_User/paymentApi");
const serviceApi = require("./Api_User/serviceApi");
const apiUser = require("./Api_User/UserApi");
const personalContractApi = require("./Api_User/personalContractApi");
const bookingApi = require("./Api_User/RoomBookingApi");
const findPostApi = require("./Api_User/findPostApi");
const post_UserApi = require("./Api_User/Post");
// staff
const post_staffApi = require("./Api_Staff/Post");
const contract_staffApi = require("./Api_Staff/Contract");
const payment_staffApi = require("./Api_Staff/Payment");
const notification_staffApi = require("./Api_Staff/Notification");
const room_staffApi = require("./Api_Staff/Room");
const invoice_staffApi = require("./Api_Staff/Invoice");
const request_staffApi = require("./Api_Staff/Request");
const login_staffApi = require("./Api_Staff/login");
const User_staffApi = require("./Api_Staff/User");
const building_staff = require("./Api_Staff/Building");


// nối
router.use("", bookings);
router.use("", serviceMgrApi)
router.use("", roomManager);
router.use("/", statisticMgrAPi)
router.use("", post_UserApi);
router.use("/", invoiceMgr)
router.use("/", authApi);
router.use("/", notificationAdmin);
router.use("/", reportApiAdmin);
router.use("", QuanLy);
router.use("/", serviceApi);
router.use("/room", roomApi);
router.use("/", invoiceApi);
router.use("/", reportApi);
router.use("/", paymentApi);
router.use("", supportManageApi);
router.use("", staffManageApi);
router.use("/", serviceManageApi);
router.use("", statisticApi);
router.use("", paymentApi_BuildingOwner);
router.use("", UserManage);
router.use("/api", notificationApi);
router.use("", apiUser);
router.use("", personalContractApi);
router.use("", bookingApi);
router.use("", findPostApi);
router.use("", adminApi);
router.use("", postAdmin);
router.use("", userAdmin);
router.use("", nofAdmin);
router.use("", spAdmin);
router.use("", statAdmin);
router.use("/staff/posts", post_staffApi); // Đổi đường dẫn cho post_staffApi
router.use("/staff/contracts", contract_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use("/staff/payments", payment_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use("/staff/notifications", notification_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use("/staff/rooms", room_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use("/staff/invoices", invoice_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use("/staff/requests", request_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use("/staff/logins", login_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use("/staff/users", User_staffApi); // Đổi đường dẫn cho contract_staffApi
router.use("/staff/buildings", building_staff);
router.use("/", UserManage);
router.use("/", PostManage);
router.use("/", PaymentManage);
router.use("/", ContractManage);
router.use("/", BuildingManage);

module.exports = router;
