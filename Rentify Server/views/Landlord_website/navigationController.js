var express = require('express');
var router = express.Router();

router.get("/ld", function(req, res) {
    res.render("Landlord_website/index");
});

router.get("/contracts_mgr", async(req, res) => {
    res.render("Landlord_website/screens/HomePage")
})

router.get("/payments_mgr", async(req, res) => {
    res.render("Landlord_website/screens/HomePage")
})

router.get("/posts_mgr", async(req, res) => {
    res.render("Landlord_website/screens/HomePage")
})

router.get("/staffs_mgr", async(req, res) => {
    res.render("Landlord_website/screens/HomePage")
})

router.get("/services_mgr", async(req, res) => {
    res.render("Landlord_website/screens/HomePage")
})

router.get("/supports_mgr", async(req, res) => {
    res.render("Landlord_website/screens/HomePage")
})

router.get("/statistics_ld", async(req, res) => {
    res.render("Landlord_website/screens/HomePage")
})

router.get("/payments_ld", async(req, res) => {
    res.render("Landlord_website/screens/HomePage")
})

module.exports = router;


// quan ly toa nha
// quan ly hop dong
// quan ly thanh toan
// quan ly bai dang
// quan ly nhan vien
// quan ky dich vu
// quan ly ho tro khach hang
// thong ke
// thanh toan

// thong bao
// nhan tin 
// chia se
// phan hoi danh gia 