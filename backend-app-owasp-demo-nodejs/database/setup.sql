CREATE TABLE IF NOT EXISTS Roles(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    code NVARCHAR(50) NOT NULL UNIQUE KEY,
    description NVARCHAR(200) NOT NULL
);

INSERT INTO Roles(code, description) VALUES
('ROOT', 'Usuario root'),
('ADMIN', 'Usuario administrador'),
('BASIC', 'Usuario con los permisos básicos');

CREATE TABLE IF NOT EXISTS StatusUsers(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    value NVARCHAR(50) NOT NULL UNIQUE KEY
);

INSERT INTO StatusUsers(value) VALUES
('CREATED'), ('ACTIVE'), ('INACTIVE');

CREATE TABLE IF NOT EXISTS Users(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username NVARCHAR(40) NOT NULL UNIQUE KEY,
    password TEXT NOT NULL,
    name NVARCHAR(100) NOT NULL,
    last_name NVARCHAR(150) NOT NULL,
    phone NVARCHAR(12) NULL,
    url_profile NVARCHAR(150) NULL,
    status NVARCHAR(50) NOT NULL DEFAULT 'CREATED',
    rol_id INT NOT NULL,
    FOREIGN KEY(status) REFERENCES StatusUsers(value) ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(rol_id) REFERENCES Roles(id) ON UPDATE CASCADE ON DELETE RESTRICT
);

INSERT INTO Users(username, password, name, last_name, status, rol_id) VALUES
('root', '1234', 'Rafael', 'Cano Martínez', 'ACTIVE', 1),
('rcano', 'Prueba', 'Rafael', 'Cano Martínez', 'ACTIVE', 2),
('canorafa', 'Prueba2', 'Rafa', 'Cano', 'ACTIVE', 3),
('rafa', 'Prueba2', 'Rafa', 'Cano', 'INACTIVE', 3);

CREATE TABLE IF NOT EXISTS Sessions(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username NVARCHAR(40) NOT NULL UNIQUE KEY,
    also NVARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Tasks(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username NVARCHAR(40) NOT NULL,
    title NVARCHAR(50) NOT NULL,
    description NVARCHAR(200) NULL,
    FOREIGN KEY(username) REFERENCES Users(username) ON UPDATE CASCADE ON DELETE CASCADE 
);

CREATE TABLE IF NOT EXISTS Activities(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    task_id INT NOT NULL,
    description NVARCHAR(200) NOT NULL,
    FOREIGN KEY(task_id) REFERENCES Tasks(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS MallwareRecordServices(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    application NVARCHAR(150) NOT NULL,
    version NVARCHAR(50) NOT NULL,
    server NVARCHAR(300) NOT NULL,
    request TEXT NOT NULL,
    response TEXT NOT NULL,
    headersReq TEXT,
    headersRes TEXT
);