-- Seeding the Pondicherry Photo Spots
-- Note: ST_MakePoint takes (Longitude, Latitude)
INSERT INTO places (name, description, image_url, location) VALUES
                                                                ('Matrimandir (Auroville)', 'The golden globe and soul of the city.', 'https://upload.wikimedia.org/wikipedia/commons/thumb/c/c5/Matrimandir_auroville.jpg/800px-Matrimandir_auroville.jpg', ST_SetSRID(ST_MakePoint(79.8105, 12.0069), 4326)),
                                                                ('Rock Beach (Gandhi Statue)', 'Iconic promenade with the famous Gandhi statue. Best at sunrise.', 'https://upload.wikimedia.org/wikipedia/commons/thumb/7/7e/Gandhi_Statue_Pondicherry.jpg/800px-Gandhi_Statue_Pondicherry.jpg', ST_SetSRID(ST_MakePoint(79.8340, 11.9348), 4326)),
                                                                ('French Quarter (White Town)', 'Bright yellow colonial walls and bougainvillea flowers.', 'https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Street_in_White_Town%2C_Pondicherry.jpg/800px-Street_in_White_Town%2C_Pondicherry.jpg', ST_SetSRID(ST_MakePoint(79.8315, 11.9325), 4326))
    ON CONFLICT (name) DO NOTHING;