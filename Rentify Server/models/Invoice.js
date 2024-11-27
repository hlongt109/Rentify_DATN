const mongoose = require("mongoose");
const Schema = mongoose.Schema;
const Invoice = new Schema({
<<<<<<< HEAD
  user_id: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "User",
    required: true,
  },
  room_id: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "Room",
    required: true,
  },
  description: [
    {
      service_name: { type: String, required: true }, // tên dịch vụ (ví dụ: điện, nước)
      quantity: { type: Number }, // số lượng sử dụng (ví dụ: số kWh, m3 nước)
      price_per_unit: { type: Number }, // giá mỗi đơn vị
      total: { type: Number, required: true }, // tổng chi phí cho dịch vụ đó
    },
  ],
  amount: { type: Number, require: true }, // số tien
  transaction_type: {
    type: String,
    enum: ["income", "expense"],
    require: true,
    default: "expense",
  },
  due_date: { type: String }, // hạn chót
  payment_status: { type: String, enum: ["paid", "unpaid"], default: "unpaid" },
  created_at: { type: String },
  detail_invoice: [
    {
      name: { type: String }, //ten dich vu
      fee: { type: Number }, // tien dich vu
      quantity: { type: Number }, // so luong (so dien, so nuoc)
    },
  ],
});
module.exports = mongoose.model("Invoice", Invoice);
=======
    user_id: { // id nhân viên 
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
    },
    building_id: { // cho hóa đơn phí dv, lương, bảo trì
        type: mongoose.Schema.Types.ObjectId,
        ref: "Building",
    },
    room_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Room',
    },
    description: [{
        service_name: { type: String }, // tên dịch vụ (ví dụ: điện, nước)
        quantity: { type: Number }, // số lượng sử dụng (ví dụ: số kWh, m3 nước)
        price_per_unit: { type: Number }, // giá mỗi đơn vị
        total: { type: Number } // tổng chi phí cho dịch vụ đó
    }],
    describe: {type: String},
    type_invoice: {type: String, enum: ["rent","electric", "water", "salary", "maintain"]},
    amount: { type: Number, require: true }, // số tien
    transaction_type: { type: String, enum: ['income', 'expense'], require: true },
    due_date: { type: String }, // hạn chót
    payment_status: { type: String, enum: ["paid", "unpaid"]},
    created_at: { type: String }
})
module.exports = mongoose.model("Invoice", Invoice);
>>>>>>> dev_long
