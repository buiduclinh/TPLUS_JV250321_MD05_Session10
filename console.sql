CREATE DATABASE hotel_database_1;
USE hotel_database_1;

CREATE TABLE customer
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    full_name    VARCHAR(100),
    phone_number VARCHAR(20) UNIQUE,
    email        VARCHAR(100) UNIQUE,
    password     VARCHAR(100)              NOT NULL,
    address      VARCHAR(100)              NOT NULL,
    role         ENUM ('ADMIN','CUSTOMER') NOT NULL DEFAULT ('CUSTOMER')
);
INSERT INTO customer(id, full_name, phone_number, email, password, address, role)
VALUES ('0', 'admin', '0', 'admin123@gmail.com', '1', 'HN', 'ADMIN');


-- hiên thị người dùng
DELIMITER $$
CREATE PROCEDURE view_customers()
BEGIN
    SELECT * FROM customer;
END $$
DELIMITER ;

-- thêm mới nguời dùng
DELIMITER $$
CREATE PROCEDURE register(
    IN in_full_name VARCHAR(100),
    IN in_phone_number VARCHAR(20),
    IN in_email VARCHAR(100),
    IN in_password VARCHAR(100),
    IN in_address VARCHAR(100)
)
BEGIN
    INSERT INTO customer(full_name, phone_number, email, password, address)
    VALUES (in_full_name, in_phone_number, in_email, in_password, in_address);
END $$
DELIMITER ;


-- lấy đối tượng theo phone
DELIMITER $$
CREATE PROCEDURE get_phone(
    IN in_phone VARCHAR(20)
)
BEGIN
    SELECT phone_number
    FROM customer
    WHERE phone_number = in_phone;
END $$
DELIMITER ;

-- lấy đôi tượng theo email
DELIMITER $$
CREATE PROCEDURE get_email(
    IN in_email VARCHAR(100)
)
BEGIN
    SELECT email
    FROM customer
    WHERE email = in_email;
END $$
DELIMITER ;

-- lấy đối tượng theo id
DELIMITER $$
CREATE PROCEDURE get_customer_by_id(
    IN in_customer_id INT
)
BEGIN
    SELECT *
    FROM customer
    WHERE id = in_customer_id;
END $$
DELIMITER ;

-- login
DELIMITER $$
CREATE PROCEDURE login(
    IN in_email VARCHAR(100),
    IN in_password VARCHAR(100)
)
BEGIN
    SELECT *
    FROM customer
    WHERE email = in_email
      AND password = in_password;
END $$
DELIMITER ;


CREATE TABLE room
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    room_name VARCHAR(100),
    room_type VARCHAR(100),
    status    ENUM ('PLACED','EMPTY') DEFAULT ('EMPTY'),
    is_delete BIT                     DEFAULT 0,
    price     DECIMAL(10, 2),
    image     VARCHAR(100)
);

-- get all rooms
DELIMITER $$
CREATE PROCEDURE view_all_rooms()
BEGIN
    SELECT * FROM room;
END $$
DELIMITER ;

-- add room
DELIMITER $$
CREATE PROCEDURE add_room(
    IN in_room_name VARCHAR(100),
    IN in_room_type VARCHAR(100),
    IN in_price DECIMAL(10, 2),
    IN in_image VARCHAR(100)
)
BEGIN
    INSERT INTO room(room_name, room_type, price, image)
    VALUES (in_room_name, in_room_type, in_price, in_image);
END $$
DELIMITER ;

-- update room
DELIMITER $$

CREATE PROCEDURE update_room(
    IN in_room_id INT,
    IN in_room_name VARCHAR(100),
    IN in_room_type VARCHAR(100),
    IN in_status VARCHAR(10), -- dùng VARCHAR thay cho ENUM
    IN in_is_delete BIT,
    IN in_price DECIMAL(10, 2),
    IN in_image VARCHAR(100)
)
BEGIN
    UPDATE room
    SET room_name = in_room_name,
        room_type = in_room_type,
        status    = in_status, -- MySQL sẽ tự cast sang ENUM nếu hợp lệ
        is_delete = in_is_delete,
        price     = in_price,
        image     = in_image
    WHERE id = in_room_id;
END $$

DELIMITER ;

-- delete room
DELIMITER $$
CREATE PROCEDURE delete_room(
    IN in_id INT
)
BEGIN
    UPDATE room
    SET is_delete = 1
    WHERE id = in_id;
END $$
DELIMITER ;

-- get room by id
DELIMITER $$
CREATE PROCEDURE get_room_by_id(
    IN in_id INT
)
BEGIN
    SELECT *
    FROM room
    WHERE id = in_id;
END $$
DELIMITER ;