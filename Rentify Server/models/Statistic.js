const mongoose = require('mongoose')
const Schema = mongoose.Schema;
const Statistic = new Schema({
    user_id: { type: String, require: true },
    stat_type: { type: String, require: true },
    value: { type: String, require: true },
    created_at: { type: String, require: false }
})
module.exports = mongoose.model("Statistic", Statistic);