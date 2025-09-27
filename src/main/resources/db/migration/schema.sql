-- DDL Script for Garden Crop Database
-- Compatible with both H2 and PostgreSQL

-- Drop tables if they exist (for fresh setup)
DROP TABLE IF EXISTS crop_companions;
DROP TABLE IF EXISTS crop_antagonists;
DROP TABLE IF EXISTS crops;

-- Create crops table
CREATE TABLE crops (
    id BIGINT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    spacing_requirement INTEGER NOT NULL DEFAULT 1,
    size_in_squares INTEGER NOT NULL DEFAULT 1,
    description VARCHAR(500),
    growing_season VARCHAR(50),
    harvest_time_days INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create companion plants table (many-to-many relationship)
CREATE TABLE crop_companions (
    id BIGINT PRIMARY KEY,
    crop_id BIGINT NOT NULL,
    companion_crop_id BIGINT NOT NULL,
    benefit_description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (crop_id) REFERENCES crops(id) ON DELETE CASCADE,
    FOREIGN KEY (companion_crop_id) REFERENCES crops(id) ON DELETE CASCADE,
    UNIQUE(crop_id, companion_crop_id)
);

-- Create antagonistic plants table (many-to-many relationship)
CREATE TABLE crop_antagonists (
    id BIGINT PRIMARY KEY,
    crop_id BIGINT NOT NULL,
    antagonist_crop_id BIGINT NOT NULL,
    negative_effect VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (crop_id) REFERENCES crops(id) ON DELETE CASCADE,
    FOREIGN KEY (antagonist_crop_id) REFERENCES crops(id) ON DELETE CASCADE,
    UNIQUE(crop_id, antagonist_crop_id)
);

-- Create indexes for better performance
CREATE INDEX idx_crops_name ON crops(name);
CREATE INDEX idx_crop_companions_crop_id ON crop_companions(crop_id);
CREATE INDEX idx_crop_companions_companion_id ON crop_companions(companion_crop_id);
CREATE INDEX idx_crop_antagonists_crop_id ON crop_antagonists(crop_id);
CREATE INDEX idx_crop_antagonists_antagonist_id ON crop_antagonists(antagonist_crop_id);

-- Create sequences for ID generation (PostgreSQL style, H2 compatible)
CREATE SEQUENCE IF NOT EXISTS crops_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS crop_companions_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS crop_antagonists_seq START WITH 1 INCREMENT BY 1;