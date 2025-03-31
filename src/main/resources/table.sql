CREATE TABLE macros (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    x INTEGER NOT NULL,
    y INTEGER NOT NULL,
    z INTEGER NOT NULL,
    world VARCHAR(255) NOT NULL,
    command VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
