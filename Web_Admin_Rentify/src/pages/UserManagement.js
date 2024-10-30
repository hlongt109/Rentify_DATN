import React, { useState } from 'react';
import '../assets/style/User.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { FaSearch } from 'react-icons/fa';
import removeAccents from 'remove-accents'; // Import thư viện

function UserManagement() {
    const [search, setSearch] = useState('');
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 5;

    const users = [
        {
            _id: { $oid: '6720bca03a83ecbc6a225899' },
            username: 'chutoa',
            email: 'phuc@gmail.com',
            phoneNumber: '0981139895',
            name: 'Vu Van Phuc',
            dob: '2004-08-06',
            gender: 'nam',
            address: 'Nam Tu Liem, Ha Noi',
            role: 'admin',
            created_at: '2024-10-24T11:04:24.489Z',
        },
        {
            _id: { $oid: '6720bca03a83ecbc6a225899' },
            username: 'chutoa',
            email: 'thienntph31209@fpt.edu.vn',
            phoneNumber: '0981139895',
            name: 'Nguyễn Thiên Thiên',
            dob: '2004-08-06',
            gender: 'nam',
            address: 'Nam Tu Liem, Ha Noi',
            role: 'user',
            created_at: '2024-10-24T11:04:24.489Z',
        },
    ];

    // Cập nhật hàm lọc người dùng
    const filteredUsers = users.filter(user =>
        removeAccents(user.name.toLowerCase()).includes(removeAccents(search.toLowerCase()))
    );

    const totalPages = Math.ceil(filteredUsers.length / itemsPerPage);
    const displayedUsers = filteredUsers.slice(
        (currentPage - 1) * itemsPerPage,
        currentPage * itemsPerPage
    );

    const getRoleColor = (role) => {
        const roleColors = {
            admin: '#6c5ce7',
            landlord: '#00b894',
            staffs: '#0984e3',
            user: '#b2bec3',
            ban: '#d63031',
        };
        return roleColors[role] || '#ffffff'; // Màu mặc định
    };

    return (
        <div className="container">
            <div className="table-container">
                <h4>Quản lý người dùng</h4>
                <div className="search-input-container1">
                    <div className="search-input-container">
                        <input
                            type="text"
                            placeholder="Search here ..."
                            value={search}
                            onChange={(e) => setSearch(e.target.value)}
                            className="search-input"
                        />
                        <FaSearch className="search-icon" />
                    </div>
                </div>

                <table className="table">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Phone Number</th>
                            <th>Address</th>
                            <th>Role</th>
                            <th>Act</th>
                        </tr>
                    </thead>
                    <tbody>
                        {displayedUsers.map((user, index) => {
                            const isEvenRow = index % 2 === 0;
                            return (
                                <tr key={user._id.$oid} style={{ backgroundColor: isEvenRow ? '#fafafb' : '#ffffff' }}>
                                    <td style={{ backgroundColor: isEvenRow ? '#fafafb' : '#ffffff' }}>{index + 1 + (currentPage - 1) * itemsPerPage}</td>
                                    <td style={{ backgroundColor: isEvenRow ? '#fafafb' : '#ffffff' }}>{user.name}</td>
                                    <td style={{ backgroundColor: isEvenRow ? '#fafafb' : '#ffffff' }}>{user.email}</td>
                                    <td style={{ backgroundColor: isEvenRow ? '#fafafb' : '#ffffff' }}>{user.phoneNumber}</td>
                                    <td style={{ backgroundColor: isEvenRow ? '#fafafb' : '#ffffff' }}>{user.address}</td>
                                    <td style={{ backgroundColor: isEvenRow ? '#fafafb' : '#ffffff' }}>
                                        <button
                                            className="btn text-white shadow-button"
                                            style={{
                                                fontSize: '0.75rem',
                                                fontFamily: 'Nunito, sans-serif',
                                                lineHeight: 1.66,
                                                fontWeight: 600,
                                                width: '100px',
                                                height: '40px',
                                                backgroundColor: getRoleColor(user.role),
                                            }}
                                        >
                                            {user.role}
                                        </button>
                                    </td>
                                    <td style={{ backgroundColor: isEvenRow ? '#fafafb' : '#ffffff' }}>
                                        <button className="btn btn-primary shadow-button" style={{
                                            fontSize: '0.75rem',
                                            fontFamily: 'Nunito, sans-serif',
                                            lineHeight: 1.66,
                                            fontWeight: 600,
                                            width: '100px',
                                            height: '40px',
                                        }}>
                                            Update
                                        </button>
                                    </td>
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
                <div className="pagination">
                    <button onClick={() => setCurrentPage((prev) => Math.max(prev - 1, 1))} disabled={currentPage === 1}>
                        &lt;
                    </button>
                    {[...Array(totalPages)].map((_, i) => (
                        <button
                            key={i}
                            onClick={() => setCurrentPage(i + 1)}
                            className={currentPage === i + 1 ? 'active' : ''}>
                            {i + 1}
                        </button>
                    ))}
                    <button onClick={() => setCurrentPage((prev) => Math.min(prev + 1, totalPages))} disabled={currentPage === totalPages}>
                        &gt;
                    </button>
                </div>
            </div>
        </div>
    );
}

export default UserManagement;
