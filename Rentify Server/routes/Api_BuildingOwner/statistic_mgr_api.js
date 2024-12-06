var express = require('express');
var router = express.Router();
const mongoose = require("mongoose");

const Invoice = require("../../models/Invoice")
const handleServerError = require("../../utils/errorHandle");

const formatMonth = (month) => {
    return month.toString().padStart(2, '0');
}

const calculateMonthlyStatistics = async (buildingId, year, month) => {
    try {
        const monthFormated = formatMonth(month)
        const startOfMonth = new Date(year, monthFormated - 1, 1); // Ngày đầu tháng
        const endOfMonth = new Date(year, monthFormated, 0);       // Ngày cuối tháng

        const invoices = await Invoice.aggregate([
            {
                $match: {
                    building_id: new mongoose.Types.ObjectId(buildingId), // Lọc theo building_id
                    payment_status: "paid",                           // Chỉ hóa đơn đã thanh toán
                    created_at: {
                        $gte: startOfMonth.toISOString(), // Lớn hơn hoặc bằng ngày đầu tháng
                        $lte: endOfMonth.toISOString()   // Nhỏ hơn hoặc bằng ngày cuối tháng
                    }
                }
            },
            {
                $group: {
                    _id: "$transaction_type", // Nhóm theo loại giao dịch
                    totalAmount: { $sum: "$amount" }, // Tính tổng tiền cho mỗi loại
                    details: { $push: "$description" }
                }
            }
        ]);

        let revenue = 0; // Doanh thu
        let expense = 0; // Chi phí
        let incomeDetails = [];
        let expenseDetails = [];

        invoices.forEach((item) => {
            if (item._id === "income") {
                revenue = item.totalAmount;

                item.details.forEach(detail => {
                    detail.forEach(item => {
                        if(item.service_name){
                            const existingService = incomeDetails.find(e => e.service_name === item.service_name);
                            if (existingService) {
                                existingService.total += item.total;
                            } else {
                                incomeDetails.push({
                                    service_name: item.service_name,
                                    total: item.total
                                });
                            }
                        }
                    })
                })
            }

            if (item._id === "expense") {
                expense = item.totalAmount;

                item.details.forEach(detail => {
                    detail.forEach(service => {
                        if (service.service_name) {
                            // Tính tổng cho từng loại dịch vụ (nước, điện, lương, bảo trì)
                            const existingService = expenseDetails.find(e => e.service_name === service.service_name);
                            if (existingService) {
                                existingService.total += service.total;
                            } else {
                                expenseDetails.push({
                                    service_name: service.service_name,
                                    total: service.total
                                });
                            }
                        }
                    });
                });
            }
        });

        const profit = revenue - expense; // Lợi nhuận

        return { revenue, expense, profit, incomeDetails, expenseDetails };
    } catch (error) {
        console.error("Error calculating monthly statistics:", error);
        throw error;
    }
};


const calculateYearlyStatistics = async (buildingId, year) => {
    try {
        const startOfYear = new Date(year, 0, 1);  // Ngày đầu năm
        const endOfYear = new Date(year, 11, 31); // Ngày cuối năm

        const invoices = await Invoice.aggregate([
            {
                $match: {
                    building_id: new mongoose.Types.ObjectId(buildingId), // Lọc theo building_id
                    payment_status: "paid",                           // Chỉ hóa đơn đã thanh toán
                    created_at: {
                        $gte: startOfYear.toISOString(), // Lớn hơn hoặc bằng ngày đầu năm
                        $lte: endOfYear.toISOString()   // Nhỏ hơn hoặc bằng ngày cuối năm
                    }
                }
            },
            {
                $group: {
                    _id: "$transaction_type", // Nhóm theo loại giao dịch
                    totalAmount: { $sum: "$amount" },
                    details: { $push: "$description" }
                }
            }
        ]);

        let revenue = 0; // Doanh thu
        let expense = 0; // Chi phí
        let incomeDetails = [];
        let expenseDetails = [];

        invoices.forEach((item) => {
            if (item._id === "income") {
                revenue = item.totalAmount;

                item.details.forEach(detail => {
                    detail.forEach(item => {
                        if(item.service_name){
                            const existingService = incomeDetails.find(e => e.service_name === item.service_name);
                            if (existingService) {
                                existingService.total += item.total;
                            } else {
                                incomeDetails.push({
                                    service_name: item.service_name,
                                    total: item.total
                                });
                            }
                        }
                    })
                })
            }

            if (item._id === "expense") {
                expense = item.totalAmount;

                item.details.forEach(detail => {
                    detail.forEach(service => {
                        if (service.service_name) {
                            // Tính tổng cho từng loại dịch vụ (nước, điện, lương, bảo trì)
                            const existingService = expenseDetails.find(e => e.service_name === service.service_name);
                            if (existingService) {
                                existingService.total += service.total;
                            } else {
                                expenseDetails.push({
                                    service_name: service.service_name,
                                    total: service.total
                                });
                            }
                        }
                    });
                });
            }
        });

        const profit = revenue - expense; // Lợi nhuận

        return { revenue, expense, profit, incomeDetails, expenseDetails };
    } catch (error) {
        console.error("Error calculating yearly statistics:", error);
        throw error;
    }
};

router.get("/stt_mgr/:building_id/monthly", async (req, res) => {
    try {
        const { building_id } = req.params;
        const { month, year } = req.query;

        const numericYear = parseInt(year, 10);
        const numericMonth = parseInt(month, 10);

        const data = await calculateMonthlyStatistics(building_id, numericYear, numericMonth)
        if (data) {
            res.status(200).json({ revenueData: data.revenue, expenseData: data.expense, profitData: data.profit, incomeDetails: data.incomeDetails, details: data.expenseDetails })
        } else {
            res.status(400).json({ message: 'No data found the calculate Monthly Statistics.', revenueData: [], expenseData: [], profitData: [],incomeDetails:[], details: [] })
        }

    } catch (error) {
        handleServerError(res, error);
    }
})

router.get("/stt_mgr/:building_id/yearly", async (req, res) => {
    try {
        const { building_id } = req.params;
        const { year } = req.query;

        const numericYear = parseInt(year, 10);

        const data = await calculateYearlyStatistics(building_id, numericYear)
        if (data) {
            res.status(200).json({ revenueData: data.revenue, expenseData: data.expense, profitData: data.profit,incomeDetails: data.incomeDetails, details: data.expenseDetails })
        } else {
            res.status(400).json({ message: 'No data found the calculate Monthly Statistics.', revenueData: [], expenseData: [], profitData: [],incomeDetails:[], details: [] })
        }
    } catch (error) {
        handleServerError(res, error);
    }
})

module.exports = router;
