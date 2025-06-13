-- V1: Tạo bảng roles và chèn dữ liệu ban đầu

-- Tạo bảng 'roles' nếu nó chưa tồn tại.
-- Bằng cách này, Flyway có thể quản lý schema ngay từ đầu.
CREATE TABLE IF NOT EXISTS roles (
                                     id INT PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL UNIQUE
    );

-- Chèn các vai trò cần thiết cho ứng dụng
INSERT INTO roles(id, name) VALUES(1, 'ROLE_USER') ON DUPLICATE KEY UPDATE name='ROLE_USER';
INSERT INTO roles(id, name) VALUES(2, 'ROLE_ADMIN') ON DUPLICATE KEY UPDATE name='ROLE_ADMIN';