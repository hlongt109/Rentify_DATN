var nodemailer = require("nodemailer");
const transporter = nodemailer.createTransport({
  service: "gmail",
  auth: {
    user: "hlong109.it@gmail.com",
    pass: "mmbr hfxz lzon zafo",
  },
});

const verifiedEmail = async (email, link) => {
  try {
    let transporter = nodemailer.createTransport({
      service: "gmail",
      auth: {
        user: "nguyenduyphong12122004@gmail.com",
        pass: "eqak iayr ubby rrgf",
      },
    });

    //send email
    let info = await transporter.sendMail({
      from: "nguyenduyphong12122004@gmail.com",
      to: email,
      subject: "Xác nhận tài khoản",
      html: `<h2>Chào mừng bạn đến với Rentify</h2>
            <p>Nhấn vào đây để xác nhận tài khoản của bạn</p>
            <a href = "${link}">Xác nhận</a> `,
    });
    console.log("email send successfuly");
  } catch (error) {
    console.log("email send failed", error);
  }
};

const forgotEmail = async (email, randomNumber) => {
  try {
    const transporter = nodemailer.createTransport({
      service: "gmail",
      auth: {
        user: "nguyenduyphong12122004@gmail.com",
        pass: "eqak iayr ubby rrgf",
      },
    });
    //sendEmail
    await transporter.sendMail({
      to: email,
      subject: 'Yêu cầu đặt lại mật khẩu',
      html: `<p>Mã xác nhận của bạn là: <strong>${randomNumber}</strong></p>
                 <p>Vui lòng nhập mã xác nhận để tiếp tục đặt lại mật khẩu.</p>`
    });
    console.log("email send successfuly");
  } catch (error) {
    console.log("email send failed", error);
  }
}
// module.exports = transporter;
module.exports = { transporter, verifiedEmail, forgotEmail };
