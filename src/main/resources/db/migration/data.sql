-- DML Script for Garden Crop Database
-- Sample crop data with parameters

-- Insert crop data
INSERT INTO crops (id, name, spacing_requirement, size_in_squares, description, growing_season, harvest_time_days) VALUES
(1, 'tomatoes', 2, 1, 'Large fruiting plants that need support and warm weather', 'Summer', 75),
(2, 'carrots', 1, 1, 'Root vegetables that prefer loose, deep soil', 'Spring/Fall', 70),
(3, 'lettuce', 1, 1, 'Quick-growing leafy greens for cool weather', 'Spring/Fall', 45),
(4, 'basil', 1, 1, 'Aromatic herb that loves warm weather and full sun', 'Summer', 60),
(5, 'peppers', 2, 1, 'Heat-loving plants that produce spicy or sweet fruits', 'Summer', 80),
(6, 'cucumber', 2, 1, 'Vining plants that need support and consistent moisture', 'Summer', 55),
(7, 'fennel', 2, 1, 'Aromatic bulb vegetable with feathery foliage', 'Fall', 90),
(8, 'onions', 1, 1, 'Bulb vegetables that store well and repel many pests', 'Spring/Fall', 100),
(9, 'garlic', 1, 1, 'Planted in fall for summer harvest, natural pest deterrent', 'Fall planted', 240),
(10, 'spinach', 1, 1, 'Cool weather leafy green rich in nutrients', 'Spring/Fall', 40),
(11, 'radishes', 1, 1, 'Fast-growing root vegetables, good for succession planting', 'Spring/Fall', 25),
(12, 'beans', 1, 1, 'Nitrogen-fixing legumes that improve soil', 'Summer', 55),
(13, 'peas', 1, 1, 'Cool weather nitrogen-fixing legumes', 'Spring/Fall', 60),
(14, 'corn', 3, 1, 'Tall cereal grain that needs wind pollination', 'Summer', 90),
(15, 'squash', 3, 1, 'Large vining plants that need lots of space', 'Summer', 95);

-- Insert companion planting relationships
INSERT INTO crop_companions (id, crop_id, companion_crop_id, benefit_description) VALUES
-- Tomatoes companions
(1, 1, 4, 'Basil repels aphids and improves tomato flavor'),
(2, 1, 2, 'Carrots help break up soil for tomato roots'),
(3, 1, 8, 'Onions repel aphids and other tomato pests'),

-- Carrots companions
(4, 2, 1, 'Tomatoes provide shade for carrots in hot weather'),
(5, 2, 3, 'Lettuce provides ground cover and efficient space use'),
(6, 2, 8, 'Onions repel carrot flies'),
(7, 2, 13, 'Peas fix nitrogen for carrots'),

-- Lettuce companions
(8, 3, 2, 'Carrots break up soil and provide structure'),
(9, 3, 8, 'Onions repel aphids that attack lettuce'),
(10, 3, 11, 'Radishes break up soil and mark lettuce rows'),

-- Basil companions
(11, 4, 1, 'Protects tomatoes from hornworms and aphids'),
(12, 4, 5, 'Improves pepper growth and flavor'),

-- Peppers companions
(13, 5, 4, 'Basil improves pepper growth and repels pests'),
(14, 5, 8, 'Onions repel aphids and other pepper pests'),

-- Cucumber companions
(15, 6, 3, 'Lettuce provides ground cover under cucumber vines'),
(16, 6, 11, 'Radishes repel cucumber beetles'),
(17, 6, 12, 'Beans fix nitrogen for heavy-feeding cucumbers'),

-- Three Sisters companion planting
(18, 14, 12, 'Corn provides support for climbing beans'),
(19, 12, 14, 'Beans fix nitrogen for corn'),
(20, 15, 14, 'Squash provides ground cover and weed suppression'),
(21, 15, 12, 'Benefits from nitrogen fixed by beans'),

-- Onions as universal companions
(22, 8, 4, 'Repels aphids from basil'),
(23, 8, 6, 'Repels cucumber beetles'),
(24, 8, 10, 'Protects spinach from aphids'),

-- Beans and peas with leafy greens
(25, 12, 3, 'Provides nitrogen for lettuce'),
(26, 13, 10, 'Provides nitrogen for spinach'),
(27, 12, 10, 'Beans and spinach grow well together');

-- Insert antagonistic planting relationships
INSERT INTO crop_antagonists (id, crop_id, antagonist_crop_id, negative_effect) VALUES
-- Tomatoes antagonists
(1, 1, 7, 'Fennel inhibits tomato growth and attracts harmful insects'),
(2, 1, 12, 'Beans can stunt tomato growth'),
(3, 1, 14, 'Corn and tomatoes compete for nutrients'),

-- Fennel antagonists (fennel is generally antagonistic to most plants)
(4, 7, 1, 'Inhibits growth of tomatoes'),
(5, 7, 2, 'Inhibits growth of carrots'),
(6, 7, 3, 'Inhibits growth of lettuce'),
(7, 7, 5, 'Inhibits growth of peppers'),
(8, 7, 12, 'Inhibits growth of beans'),

-- Onions antagonists
(9, 8, 12, 'Onions can inhibit bean growth'),
(10, 8, 13, 'Onions can inhibit pea growth'),

-- Carrot antagonists
(11, 2, 7, 'Fennel inhibits carrot growth'),

-- Cucumber antagonists
(12, 6, 4, 'Basil can inhibit cucumber growth when planted too close'),

-- Pepper antagonists
(13, 5, 7, 'Fennel inhibits pepper growth'),

-- Corn antagonists
(14, 14, 1, 'Competes with tomatoes for nutrients'),

-- Beans antagonists
(15, 12, 1, 'Can stunt tomato growth'),
(16, 12, 7, 'Fennel inhibits bean growth'),
(17, 12, 8, 'Onions can inhibit bean growth'),

-- Peas antagonists
(18, 13, 8, 'Onions can inhibit pea growth'),

-- Additional antagonistic relationships
(19, 11, 6, 'Radishes can attract cucumber beetles'),
(20, 15, 1, 'Squash and tomatoes compete for space and nutrients');