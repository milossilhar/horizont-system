--liquibase formatted sql
--changeset dugong:dev_003_chat_populate

-- =====================================================
-- DEVELOPMENT DATA FOR REGISTRATION SYSTEM
-- =====================================================

-- First, populate enumeration items (reference data)
-- These must be inserted first as other entities reference them

-- REG_PLACE (Places where events can be held)
INSERT INTO reg_enumeration_item (id, enum_name, code, name, description, ordering, hidden, latitude, longitude)
VALUES
    (nextval('seq_enumeration_item_id'), 'REG_PLACE', 'MAIN_HALL', 'Main Hall', 'Large main hall for group activities', 1, false, 48.1486, 17.1077),
    (nextval('seq_enumeration_item_id'), 'REG_PLACE', 'STUDIO_A', 'Studio A', 'Small studio for individual sessions', 2, false, 48.1486, 17.1078),
    (nextval('seq_enumeration_item_id'), 'REG_PLACE', 'STUDIO_B', 'Studio B', 'Medium studio for small groups', 3, false, 48.1486, 17.1079),
    (nextval('seq_enumeration_item_id'), 'REG_PLACE', 'OUTDOOR', 'Outdoor Area', 'Outdoor space for weather-permitting activities', 4, false, 48.1487, 17.1080),
    (nextval('seq_enumeration_item_id'), 'REG_PLACE', 'ONLINE', 'Online Platform', 'Virtual meeting space for online events', 5, false, null, null);

-- REG_PERIOD (Time periods for events)
INSERT INTO reg_enumeration_item (id, enum_name, code, name, description, ordering, hidden)
VALUES
    (nextval('seq_enumeration_item_id'), 'REG_PERIOD', 'SPRING', 'Spring Semester', 'Spring semester period', 1, false),
    (nextval('seq_enumeration_item_id'), 'REG_PERIOD', 'FALL', 'Fall Semester', 'Fall semester period', 2, false),
    (nextval('seq_enumeration_item_id'), 'REG_PERIOD', 'SUMMER', 'Summer Camp', 'Summer intensive period', 3, false),
    (nextval('seq_enumeration_item_id'), 'REG_PERIOD', 'WINTER', 'Winter Break', 'Winter holiday period', 4, false),
    (nextval('seq_enumeration_item_id'), 'REG_PERIOD', 'YEAR', 'Full Year', 'Complete academic year', 5, false);

-- REG_RELATION (Relationship types for known persons)
INSERT INTO reg_enumeration_item (id, enum_name, code, name, description, ordering, hidden)
VALUES
    (nextval('seq_enumeration_item_id'), 'REG_RELATION', 'PARENT', 'Parent', 'Parent or legal guardian', 1, false),
    (nextval('seq_enumeration_item_id'), 'REG_RELATION', 'GUARDIAN', 'Guardian', 'Legal guardian (not parent)', 2, false),
    (nextval('seq_enumeration_item_id'), 'REG_RELATION', 'SIBLING', 'Sibling', 'Brother or sister', 3, false),
    (nextval('seq_enumeration_item_id'), 'REG_RELATION', 'RELATIVE', 'Relative', 'Other family member', 4, false),
    (nextval('seq_enumeration_item_id'), 'REG_RELATION', 'FRIEND', 'Friend', 'Family friend or acquaintance', 5, false),
    (nextval('seq_enumeration_item_id'), 'REG_RELATION', 'EMERGENCY', 'Emergency', 'Emergency contact', 6, false);

-- REG_SHIRT_SIZE (T-shirt sizes)
INSERT INTO reg_enumeration_item (id, enum_name, code, name, description, ordering, hidden)
VALUES
    (nextval('seq_enumeration_item_id'), 'REG_SHIRT_SIZE', 'XS', 'Extra Small', 'Extra Small size', 1, false),
    (nextval('seq_enumeration_item_id'), 'REG_SHIRT_SIZE', 'S', 'Small', 'Small size', 2, false),
    (nextval('seq_enumeration_item_id'), 'REG_SHIRT_SIZE', 'M', 'Medium', 'Medium size', 3, false),
    (nextval('seq_enumeration_item_id'), 'REG_SHIRT_SIZE', 'L', 'Large', 'Large size', 4, false),
    (nextval('seq_enumeration_item_id'), 'REG_SHIRT_SIZE', 'XL', 'Extra Large', 'Extra Large size', 5, false),
    (nextval('seq_enumeration_item_id'), 'REG_SHIRT_SIZE', 'XXL', 'Double Extra Large', 'Double Extra Large size', 6, false);

-- REG_EVENT_CONDITION_TYPE (Event condition types)
INSERT INTO reg_enumeration_item (id, enum_name, code, name, description, ordering, hidden, condition_option)
VALUES
    (nextval('seq_enumeration_item_id'), 'REG_EVENT_CONDITION_TYPE', 'AGE_MIN', 'Minimum Age', 'Participant must be at least this age', 1, false, 'NUM_MIN'),
    (nextval('seq_enumeration_item_id'), 'REG_EVENT_CONDITION_TYPE', 'AGE_MAX', 'Maximum Age', 'Participant cannot exceed this age', 2, false, 'NUM_MAX'),
    (nextval('seq_enumeration_item_id'), 'REG_EVENT_CONDITION_TYPE', 'AGE_RANGE', 'Age Range', 'Participant must be within this age range', 3, false, 'NUM_RANGE'),
    (nextval('seq_enumeration_item_id'), 'REG_EVENT_CONDITION_TYPE', 'SKILL_LVL', 'Skill Level', 'Required skill level for participation', 4, false, 'VALUE'),
    (nextval('seq_enumeration_item_id'), 'REG_EVENT_CONDITION_TYPE', 'PREREQ', 'Prerequisites', 'Required previous course or experience', 5, false, 'VALUE'),
    (nextval('seq_enumeration_item_id'), 'REG_EVENT_CONDITION_TYPE', 'MEDICAL', 'Medical Clearance', 'Medical clearance required', 6, false, 'VALUE');

-- REG_EVENT_DISCOUNT_TYPE (Discount types)
INSERT INTO reg_enumeration_item (id, enum_name, code, name, description, ordering, hidden)
VALUES
    (nextval('seq_enumeration_item_id'), 'REG_EVENT_DISCOUNT_TYPE', 'EARLY', 'Early Bird', 'Early registration discount', 1, false),
    (nextval('seq_enumeration_item_id'), 'REG_EVENT_DISCOUNT_TYPE', 'STUDENT', 'Student Discount', 'Discount for students', 2, false),
    (nextval('seq_enumeration_item_id'), 'REG_EVENT_DISCOUNT_TYPE', 'SENIOR', 'Senior Discount', 'Discount for seniors', 3, false),
    (nextval('seq_enumeration_item_id'), 'REG_EVENT_DISCOUNT_TYPE', 'FAMILY', 'Family Discount', 'Discount for family members', 4, false),
    (nextval('seq_enumeration_item_id'), 'REG_EVENT_DISCOUNT_TYPE', 'LOYALTY', 'Loyalty Discount', 'Discount for returning participants', 5, false);

-- REG_EVENT_TERM_TAG (Tags for event terms)
INSERT INTO reg_enumeration_item (id, enum_name, code, name, description, ordering, hidden)
VALUES
    (nextval('seq_enumeration_item_id'), 'REG_EVENT_TERM_TAG', 'BEGINNER', 'Beginner Friendly', 'Suitable for beginners', 1, false),
    (nextval('seq_enumeration_item_id'), 'REG_EVENT_TERM_TAG', 'ADVANCED', 'Advanced Level', 'For advanced participants', 2, false),
    (nextval('seq_enumeration_item_id'), 'REG_EVENT_TERM_TAG', 'INTENSIVE', 'Intensive Course', 'High-intensity training', 3, false),
    (nextval('seq_enumeration_item_id'), 'REG_EVENT_TERM_TAG', 'WEEKEND', 'Weekend Only', 'Weekend sessions only', 4, false),
    (nextval('seq_enumeration_item_id'), 'REG_EVENT_TERM_TAG', 'FLEXIBLE', 'Flexible Schedule', 'Flexible timing available', 5, false);

-- =====================================================
-- SAMPLE APPLICATION PARAMETERS
-- =====================================================
INSERT INTO reg_app_param (name, value) VALUES
                                            ('REGISTRATION_EMAIL', 'registration@example.com'),
                                            ('CONTACT_PHONE', '+421 123 456 789'),
                                            ('ORGANIZATION_NAME', 'Syntax Studio'),
                                            ('MAX_REGISTRATION_DAYS', '30'),
                                            ('DEFAULT_CURRENCY', 'EUR');

-- =====================================================
-- SAMPLE USERS
-- =====================================================
INSERT INTO reg_user (id, created_at, email, name, surname, tel_phone, roles, disabled, verified)
VALUES
    (nextval('seq_user_id'), NOW(), 'admin@example.com', 'John', 'Administrator', '+421123456789', '["ADMIN", "USER"]', false, true),
    (nextval('seq_user_id'), NOW(), 'trainer@example.com', 'Sarah', 'Trainer', '+421123456790', '["TRAINER", "USER"]', false, true),
    (nextval('seq_user_id'), NOW(), 'parent@example.com', 'Michael', 'Johnson', '+421123456791', '["USER"]', false, true),
    (nextval('seq_user_id'), NOW(), 'jane.doe@example.com', 'Jane', 'Doe', '+421123456792', '["USER"]', false, true);

-- =====================================================
-- SAMPLE PERSONS
-- =====================================================
INSERT INTO reg_person (id, name, surname, date_of_birth, is_independent, shirt_size, user_id, health_notes, food_allergy_notes)
VALUES
    (nextval('seq_person_id'), 'Emma', 'Johnson', '2010-05-15', false, 'M', 3, 'No known health issues', 'Allergic to nuts'),
    (nextval('seq_person_id'), 'Alex', 'Smith', '2008-09-22', true, 'L', null, 'Mild asthma', null),
    (nextval('seq_person_id'), 'Sophie', 'Brown', '2012-01-10', false, 'S', null, null, 'Lactose intolerant'),
    (nextval('seq_person_id'), 'David', 'Wilson', '2007-11-30', true, 'XL', 4, null, null),
    (nextval('seq_person_id'), 'Maria', 'Garcia', '2009-03-18', false, 'M', null, 'Requires glasses', 'No known allergies');

-- =====================================================
-- SAMPLE EVENTS
-- =====================================================
INSERT INTO reg_event (id, created_at, uuid, name, details, event_type, status, place_code, period_code,
                       registration_starts, registration_ends, image_url)
VALUES
    (nextval('seq_event_id'), NOW(), gen_random_uuid()::text, 'Summer Dance Workshop',
     'Intensive summer dance program for all skill levels. Learn various dance styles including hip-hop, contemporary, and jazz.',
     'EVENT', 'PUBLISHED', 'MAIN_HALL', 'SUMMER',
     NOW() - INTERVAL '10 days', NOW() + INTERVAL '20 days', '/images/dance-workshop.jpg'),

    (nextval('seq_event_id'), NOW(), gen_random_uuid()::text, 'Programming Bootcamp',
     'Learn the fundamentals of programming with Python and JavaScript. Perfect for beginners.',
     'EVENT', 'PUBLISHED', 'STUDIO_A', 'FALL',
     NOW() - INTERVAL '5 days', NOW() + INTERVAL '15 days', '/images/programming-bootcamp.jpg'),

    (nextval('seq_event_id'), NOW(), gen_random_uuid()::text, 'Yoga for Kids',
     'Fun and engaging yoga sessions designed specifically for children aged 8-14.',
     'EVENT', 'PUBLISHED', 'STUDIO_B', 'SPRING',
     NOW() - INTERVAL '2 days', NOW() + INTERVAL '25 days', '/images/kids-yoga.jpg'),

    (nextval('seq_event_id'), NOW(), gen_random_uuid()::text, 'Art & Creativity Camp',
     'Explore various art forms including painting, sculpting, and digital art.',
     'EVENT', 'DRAFT', 'OUTDOOR', 'SUMMER',
     NOW() + INTERVAL '5 days', NOW() + INTERVAL '35 days', '/images/art-camp.jpg');

-- =====================================================
-- SAMPLE EVENT CONDITIONS
-- =====================================================
INSERT INTO reg_event_condition (event_id, ind, condition_type_code, min_value, max_value, value)
VALUES
    (1, 0, 'AGE_RANGE', '12', '18', null),
    (1, 1, 'SKILL_LVL', null, null, 'BEGINNER'),
    (2, 0, 'AGE_MIN', '16', null, null),
    (2, 1, 'PREREQ', null, null, 'NONE'),
    (3, 0, 'AGE_RANGE', '8', '14', null),
    (3, 1, 'MEDICAL', null, null, 'BASIC');

-- =====================================================
-- SAMPLE EVENT TERMS
-- =====================================================
INSERT INTO reg_event_term (id, created_at, event_id, capacity, price, deposit, duration_minutes,
                            start_date, end_date, start_time, repeat_type, day_of_week, number_of_lessons,
                            has_attendance, expected_trainers, tags)
VALUES
    (nextval('seq_event_term_id'), NOW(), 1, 20, 150.00, 50.00, 120,
     '2024-07-01', '2024-07-31', '10:00:00', 'WEEKLY', 'MONDAY', 4,
     true, '["trainer@example.com"]', '["BEGINNER", "INTENSIVE"]'),

    (nextval('seq_event_term_id'), NOW(), 1, 15, 150.00, 50.00, 120,
     '2024-07-01', '2024-07-31', '14:00:00', 'WEEKLY', 'WEDNESDAY', 4,
     true, '["trainer@example.com"]', '["ADVANCED", "INTENSIVE"]'),

    (nextval('seq_event_term_id'), NOW(), 2, 12, 200.00, 75.00, 180,
     '2024-09-01', '2024-12-15', '18:00:00', 'WEEKLY', 'TUESDAY', 16,
     true, '["admin@example.com"]', '["BEGINNER", "WEEKEND"]'),

    (nextval('seq_event_term_id'), NOW(), 3, 10, 80.00, 20.00, 60,
     '2024-04-01', '2024-06-30', '16:00:00', 'WEEKLY', 'FRIDAY', 12,
     true, '["trainer@example.com"]', '["BEGINNER", "FLEXIBLE"]');

-- =====================================================
-- SAMPLE PAYMENTS
-- =====================================================
INSERT INTO reg_payment (id, price, deposit, discount_value, discount_percent, deposit_paid, paid)
VALUES
    (nextval('seq_payment_id'), 150.00, 50.00, null, 10.0, true, false),
    (nextval('seq_payment_id'), 200.00, 75.00, 20.00, null, true, true),
    (nextval('seq_payment_id'), 80.00, 20.00, null, null, false, false),
    (nextval('seq_payment_id'), 150.00, 50.00, null, 15.0, true, true),
    (nextval('seq_payment_id'), 200.00, 75.00, null, null, true, false);

-- =====================================================
-- SAMPLE REGISTRATIONS
-- =====================================================
INSERT INTO reg_registration (id, created_at, uuid, event_term_id, person_id, status,
                              name, surname, email, tel_phone, consent_gdpr, consent_photo, payment_scheme)
VALUES
    (nextval('seq_registration_id'), NOW(), gen_random_uuid()::text, 1, 1, 'CONFIRMED',
     'Emma', 'Johnson', 'parent@example.com', '+421123456791', true, true, 'FULL'),

    (nextval('seq_registration_id'), NOW(), gen_random_uuid()::text, 2, 2, 'CONFIRMED',
     'Alex', 'Smith', 'alex.smith@example.com', '+421123456793', true, false, 'DEPOSIT'),

    (nextval('seq_registration_id'), NOW(), gen_random_uuid()::text, 3, 3, 'PENDING',
     'Sophie', 'Brown', 'sophie.brown@example.com', '+421123456794', true, true, 'FULL'),

    (nextval('seq_registration_id'), NOW(), gen_random_uuid()::text, 1, 4, 'CONFIRMED',
     'David', 'Wilson', 'jane.doe@example.com', '+421123456792', true, true, 'DEPOSIT'),

    (nextval('seq_registration_id'), NOW(), gen_random_uuid()::text, 4, 5, 'WAITLIST',
     'Maria', 'Garcia', 'maria.garcia@example.com', '+421123456795', true, false, 'FULL');

-- =====================================================
-- SAMPLE KNOWN PERSONS (for registrations)
-- =====================================================
INSERT INTO reg_known_person (registration_id, ind, name, surname, relation_code)
VALUES
    (1, 0, 'Michael', 'Johnson', 'PARENT'),
    (1, 1, 'Lisa', 'Johnson', 'PARENT'),
    (3, 0, 'Robert', 'Brown', 'PARENT'),
    (3, 1, 'Anna', 'Brown', 'PARENT'),
    (5, 0, 'Carlos', 'Garcia', 'PARENT');

-- =====================================================
-- SAMPLE USER KNOWN PERSONS
-- =====================================================
INSERT INTO reg_user_known_person (user_id, ind, name, surname, relation_code)
VALUES
    (3, 0, 'Emma', 'Johnson', 'PARENT'),
    (4, 0, 'David', 'Wilson', 'PARENT'),
    (4, 1, 'Sophie', 'Wilson', 'SIBLING');

-- =====================================================
-- SAMPLE LESSONS
-- =====================================================
INSERT INTO reg_lesson (id, created_at, event_term_id, start_at, duration_minutes, place_code, expected_trainers)
VALUES
    (nextval('seq_lesson_id'), NOW(), 1, '2024-07-01 10:00:00', 120, 'MAIN_HALL', '["trainer@example.com"]'),
    (nextval('seq_lesson_id'), NOW(), 1, '2024-07-08 10:00:00', 120, 'MAIN_HALL', '["trainer@example.com"]'),
    (nextval('seq_lesson_id'), NOW(), 2, '2024-07-03 14:00:00', 120, 'MAIN_HALL', '["trainer@example.com"]'),
    (nextval('seq_lesson_id'), NOW(), 3, '2024-09-03 18:00:00', 180, 'STUDIO_A', '["admin@example.com"]'),
    (nextval('seq_lesson_id'), NOW(), 4, '2024-04-05 16:00:00', 60, 'STUDIO_B', '["trainer@example.com"]');

-- =====================================================
-- SAMPLE ATTENDANCE
-- =====================================================
INSERT INTO reg_attendance (lesson_id, person_id, status, created_at)
VALUES
    (1, 1, 'PRESENT', NOW()),
    (1, 4, 'PRESENT', NOW()),
    (2, 1, 'ABSENT', NOW()),
    (2, 4, 'PRESENT', NOW()),
    (3, 2, 'PRESENT', NOW()),
    (4, 2, 'PRESENT', NOW()),
    (5, 3, 'PRESENT', NOW());

-- =====================================================
-- SAMPLE TRAINER ATTENDANCE
-- =====================================================
INSERT INTO reg_attendance_trainer (lesson_id, person_id, duration_minutes, created_at)
VALUES
    (1, 2, 120, NOW()),
    (2, 2, 120, NOW()),
    (3, 2, 120, NOW()),
    (4, 4, 180, NOW()),
    (5, 2, 60, NOW());

-- =====================================================
-- SAMPLE COMMUNICATIONS
-- =====================================================
INSERT INTO reg_communication (id, created_at, channel, type, status, message, recipient_email, recipient_phone, reference, reference_id)
VALUES
    (nextval('seq_communication_id'), NOW(), 'EMAIL', 'REGISTRATION_CONFIRMATION', 'SENT',
     'Your registration has been confirmed for Summer Dance Workshop.', 'parent@example.com', null, 'REGISTRATION', 1),

    (nextval('seq_communication_id'), NOW(), 'EMAIL', 'LESSON_REMINDER', 'SENT',
     'Reminder: Your lesson starts tomorrow at 10:00 AM.', null, '+421123456791', 'EVENT_TERM', 1),

    (nextval('seq_communication_id'), NOW(), 'EMAIL', 'PAYMENT_REMINDER', 'PENDING',
     'Payment reminder for Programming Bootcamp registration.', 'alex.smith@example.com', null, 'REGISTRATION', 2);

-- =====================================================
-- SAMPLE EMAIL LOGS
-- =====================================================
INSERT INTO reg_email_logs (id, created_at, email_type, recipients, registration_id, html)
VALUES
    (nextval('seq_email_logs_id'), NOW(), 'REGISTRATION_CONFIRMATION', 'parent@example.com', 1,
     '<html><body><h1>Registration Confirmed</h1><p>Thank you for registering for Summer Dance Workshop.</p></body></html>'),

    (nextval('seq_email_logs_id'), NOW(), 'PAYMENT_CONFIRMATION', 'alex.smith@example.com', 2,
     '<html><body><h1>Payment Received</h1><p>Your payment has been successfully processed.</p></body></html>');

-- =====================================================
-- SAMPLE SUBSTITUTE LESSONS
-- =====================================================
INSERT INTO reg_substitute_lesson (person_id, ind, lesson_id, expires_at, created_at)
VALUES
    (1, 0, 2, NOW() + INTERVAL '30 days', NOW()),
    (4, 0, null, NOW() + INTERVAL '60 days', NOW());