const mongooes = require('mongoose');
mongooes.set('strictQuery', true)
const url_db = "mongodb+srv://hoanglongtran1402:j5CATOjIJy19MtlH@rentifydb.xycpy.mongodb.net/?retryWrites=true&w=majority&appName=RentifyDB"
const connect = async () => {
    try {
        await mongooes.connect(url_db)
        console.log("The JS server is already running with http://localhost:3000/api/rentify/login \n")
    } catch (error) {
        console.log("Error :" + error);
        console.log('Server connection failed');
    }
}

module.exports = { connect }