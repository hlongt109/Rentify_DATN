var nodemailer = require('nodemailer');
const transporter = nodemailer.createTransport({
    service: 'gmail',
    auth:{
        user : "hlong109.it@gmail.com", 
        pass: "wymp drbg nyox wmxb", 
    }
});
module.exports = transporter;