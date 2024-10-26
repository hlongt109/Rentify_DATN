// quản lí tài khoản (chủ tòa )
var express = require('express');
var router = express.Router();
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

// model
const User = require('../../models/User');

const JWT_SECRET = 'your_jwt_secret_key';
// #1 . đăng ký tài khoản POST : http://localhost:3000/api/register
router.post('/register', async (req, res) => {
    const { username, password, email, phoneNumber, name, dob, gender, address } = req.body;

    try {
        const existingUser = await User.findOne({ email });
        if (existingUser) {
            return res.status(400).json({ error: 'User already exists with this email.' });
        }

        const hashedPassword = await bcrypt.hash(password, 10);
        const newUser = new User({
            username,
            password: hashedPassword,
            email,
            phoneNumber,
            name,
            dob,
            gender,
            address,
            created_at: new Date().toISOString(),
            updated_at: new Date().toISOString()
        });
        await newUser.save();
        res.status(201).json({ message: 'User registered successfully!' });
    } catch (error) {
        res.status(500).json({ error: 'Registration failed.' });
    }
});

//#2. đăng nhập tài khoản POST : http://localhost:3000/api/login
router.post('/login', async (req, res) => {
    const { email, password } = req.body;

    try {
        const user = await User.findOne({ email });
        if (!user) {
            return res.status(400).json({ error: 'Invalid email or password.' });
        }
        const isMatch = await bcrypt.compare(password, user.password);
        if (!isMatch) {
            return res.status(400).json({ error: 'Invalid email or password.' });
        }
        const token = jwt.sign({ userId: user._id, role: user.role }, JWT_SECRET, { expiresIn: '1h' });
        res.status(200).json({ message: 'Login successful!', token });
    } catch (error) {
        res.status(500).json({ error: 'Login failed.' });
    }
});

router.get("/login", (req, res) => {
    res.render('login');
});

module.exports = router;


// kiểm thử postman:

// {
//   "username": "chutoa",
//   "password": "1",
//   "email": "phuc@gmail.com",
//   "phoneNumber": "0981139895",
//   "name": "Vu Van Phuc",
//   "dob": "2004-08-06",
//   "gender": "nam",
//   "address": "Nam Tu Liem, Ha Noi"
// }
