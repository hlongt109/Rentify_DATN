router.post('/add', upload.fields([{ name: 'video' }, { name: 'photo' }]), async (req, res) => {
    const post = new Post({
        user_id: req.body.user_id,
        title: req.body.title,
        content: req.body.content,
        status: req.body.status || 0, 
        post_type: req.body.post_type,
        video: req.files['video'] ? req.files['video'].map(file => file.path.replace('public/', '')) : [], // Chỉ lưu lại đường dẫn tương đối
        photo: req.files['photo'] ? req.files['photo'].map(file => file.path.replace('public/', '')) : [], // Chỉ lưu lại đường dẫn tương đối
        price: req.body.price || 0, // Giá mặc định là 0 nếu không cung cấp
            address: req.body.address || "",
            phoneNumber: req.body.phoneNumber || "",
            room_type: req.body.room_type || "",
            amenities: req.body.amenities || [], // Danh sách tiện nghi
            services: req.body.services || [], // Danh sách dịch vụ
        created_at: new Date().toISOString(),
        updated_at: new Date().toISOString()
    });

    try {
        const savedPost = await post.save();
        res.status(201).json(savedPost);
    } catch (error) {
        res.status(400).json({ message: error.message });
    }
});