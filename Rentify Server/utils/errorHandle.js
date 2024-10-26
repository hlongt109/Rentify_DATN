module.exports = function handleServerError(res, error) {
    console.error("Error: " + error);
    res.status(500).json({
        status: 500,
        message: "Server error",
        error: error.message
    });
};