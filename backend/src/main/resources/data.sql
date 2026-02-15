-- 2. Seed the Local Pondicherry Photo Spots
-- Note: ST_MakePoint strictly requires (Longitude, Latitude)
INSERT INTO places (name, description, image_url, location) VALUES
                                                                (
                                                                    'Matrimandir',
                                                                    'The golden globe and spiritual center of Auroville.',
                                                                    '/images/matrimandir.jpg',
                                                                    ST_SetSRID(ST_MakePoint(79.8105, 12.0069), 4326)
                                                                ),
                                                                (
                                                                    'Gandhi Statue (Rock Beach)',
                                                                    'Iconic statue on the Promenade beach, perfect for sunrise views.',
                                                                    '/images/gandhi_statue.jpg',
                                                                    ST_SetSRID(ST_MakePoint(79.8340, 11.9348), 4326)
                                                                ),
                                                                (
                                                                    'Bharathi Park',
                                                                    'A serene green park in the heart of the French Quarter featuring the historic Aayi Mandapam.',
                                                                    '/images/bharathi_park.jpg',
                                                                    ST_SetSRID(ST_MakePoint(79.8336, 11.9328), 4326)
                                                                ),
                                                                (
                                                                    'Pondy Marina',
                                                                    'A vibrant coastal spot with food stalls, events, and a great view of the Bay of Bengal.',
                                                                    '/images/pondy_marina.jpg',
                                                                    ST_SetSRID(ST_MakePoint(79.8296, 11.9091), 4326)
                                                                ),
                                                                (
                                                                    'Serenity Beach',
                                                                    'A beautiful sandy beach lined with palm trees, famous for surfing and fishing boats.',
                                                                    '/images/serenity_beach.jpg',
                                                                    ST_SetSRID(ST_MakePoint(79.8456, 11.9692), 4326)
                                                                )
    ON CONFLICT (name) DO NOTHING;