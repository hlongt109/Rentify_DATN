var express = require('express');
var router = express.Router();

const Invoice = require("../../models/Invoice")
const User = require("../../models/User");

const handleServerError = require("../../utils/errorHandle");

router.get("/stats/sum", async (req, res) => {
    try {
        const totalAccounts = await User.countDocuments();
        console.log(`Tổng số tài khoản: ${totalAccounts}`);
        res.render("Stats/listStats", { totalAccounts });
    } catch (error) {
        console.error('Lỗi khi đếm số tài khoản:', error);
    }
})


// helper function
const getAggregateData = async (fromDate, toDate, transactionType) => {
    return await Invoice.aggregate([
        {
            $match: {
                transaction_type: transactionType,
                payment_status: 'paid',
                created_at: { $gte: new Date(fromDate), $lte: new Date(toDate) }
            }
        },
        {
            $group: {
                _id: null,
                totalAmount: { $sum: "$amount" }
            }
        }
    ])
}
// doanh thu theo tháng
router.get("/api/invoices/month", async (req, res) => {
    try {
        const { from, to } = req.query;
        const data = await getAggregateData(from, to, "income");
        if (data && data.length > 0) {
            res.json({ revenue: data[0].totalAmount });
        } else {
            res.status(400).json({ status: 400, message: "No revenue data found for the selected month", revenue: 0 });
        }
    } catch (error) {
        handleServerError(res, error)
    }
})
// doanh thu theo năm
router.get("/api/invoices/year", async (req, res) => {
    try {
        const { year } = req.query;
        const from = `${year}-01-01`;
        const to = `${year}-12-31`;
        const data = await getAggregateData(from, to, 'income');
        if (data && data.length > 0) {
            res.json({ revenue: data[0].totalAmount });
        } else {
            res.status(400).json({ status: 400, message: "No revenue data found for the selected year", revenue: 0 });
        }
    } catch (error) {
        handleServerError(res, error);
    }
});
// chi phí theo tháng
router.get("/api/invoices/month", async (req, res) => {
    try {
        const { from, to } = req.query;
        const data = await getAggregateData(from, to, 'expense');
        if (data && data.length > 0) {
            res.json({ expenses: data[0].totalAmount });
        } else {
            res.status(400).json({ status: 400, message: "No expense data found for the selected month", expenses: 0 });
        }
    } catch (error) {
        handleServerError(res, error);
    }
});
// chi phí theo năm 
router.get("/api/invoices/year", async (req, res) => {
    try {
        const { year } = req.query;
        const from = `${year}-01-01`;
        const to = `${year}-12-31`;
        const data = await getAggregateData(from, to, 'expense');
        if (data && data.length > 0) {
            res.json({ expenses: data[0].totalAmount });
        } else {
            res.status(400).json({ status: 400, message: "No expense data found for the selected year", expenses: 0 });
        }
    } catch (error) {
        handleServerError(res, error);
    }
});
// lợi nhuận theo tháng
router.get("/api/invoices/month", async (req, res) => {
    try {
        const { from, to } = req.query;
        const incomeData = await getAggregateData(from, to, 'income');
        const expenseData = await getAggregateData(from, to, 'expense');
        if ((incomeData && incomeData.length > 0) || (expenseData && expenseData.length > 0)) {
            const profit = (incomeData[0]?.totalAmount || 0) - (expenseData[0]?.totalAmount || 0);
            res.json({ profit });
        } else {
            res.status(400).json({ status: 400, message: "No profit data found for the selected month", profit: 0 });
        }
    } catch (error) {
        handleServerError(res, error);
    }
});
// lợi nhuận theo năm
router.get("/api/invoices/year", async (req, res) => {
    try {
        const { year } = req.query;
        const from = `${year}-01-01`;
        const to = `${year}-12-31`;
        const incomeData = await getAggregateData(from, to, 'income');
        const expenseData = await getAggregateData(from, to, 'expense');
        if ((incomeData && incomeData.length > 0) || (expenseData && expenseData.length > 0)) {
            const profit = (incomeData[0]?.totalAmount || 0) - (expenseData[0]?.totalAmount || 0);
            res.json({ profit });
        } else {
            res.status(400).json({ status: 400, message: "No profit data found for the selected year", profit: 0 });
        }
    } catch (error) {
        handleServerError(res, error);
    }
});

module.exports = router;
