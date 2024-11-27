
const multer = require('multer');
const path = require('path');

// Define storage for the images and videos
const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, 'public/uploads');
    },
    filename: (req, file, cb) => {
        const uniqueSuffix = Date.now() + '-' + Math.round(Math.random() * 1E9);
        const extname = path.extname(file.originalname);
        cb(null, `${file.fieldname}-${uniqueSuffix}${extname}`);
    },
});

// Function to check if file type is allowed
const fileFilter = (req, file, cb) => {
    // Define allowed file extensions and corresponding MIME types
    const allowedFileTypes = {
        '.jpeg': 'image/jpeg',
        '.jpg': 'image/jpeg',
        '.png': 'image/png',
        '.mp4': 'video/mp4',
    };

    const extname = path.extname(file.originalname).toLowerCase();
    const isValidFileType = Object.keys(allowedFileTypes).includes(extname);
    const isValidMimeType = allowedFileTypes[extname] === file.mimetype;

    if (isValidFileType && isValidMimeType) {
        cb(null, true);
    } else {
        cb(new Error('Only image and video files are allowed!'));
    }
};

// Initialize multer with storage and file filter
const uploadFile = multer({
    storage: storage,
    fileFilter: fileFilter,
    limits: { fileSize: 1024 * 1024 * 100 } // Limit file size to 100MB
});

module.exports = uploadFile;
