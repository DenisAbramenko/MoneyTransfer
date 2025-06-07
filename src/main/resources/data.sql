-- Insert test cards
INSERT INTO card (number, valid_till, cvv, balance)
VALUES 
('1234567890123456', '12/25', '123', 10000),
('9876543210987654', '10/27', '456', 5000)
ON CONFLICT (number) DO NOTHING; 