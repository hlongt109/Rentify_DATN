var express = require("express");
var router = express.Router();

// Ví dụ
// const tenRouter = require("./fileRouter")
// router.use('',tentenRouter)

//============================================
// const userApi = require("./Api_Admin/demoApi");
const apiUser = require("./Api_User/UserApi");
// nối
// router.use("/", userApi);
router.use("/", apiUser);

module.exports = router;
