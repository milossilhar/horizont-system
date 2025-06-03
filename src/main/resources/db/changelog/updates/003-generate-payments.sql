--liquibase formatted sql
--changeset dugong:003_GENERATE_PAYMENTS

-- Step 1: Insert payment records for registrations that don't have payment_id set
insert into reg_payment (id, deposit, deposit_paid, discount_percent, discount_value, paid, price)
select
    r.id,
    et.deposit,
    false,
    null,
    null,
    false,
    et.price
from reg_registration r
    join reg_event_term et on r.event_term_id = et.id
where r.payment_id is null;

-- Step 2: Update registration table to link to the newly created payment records
update reg_registration set payment_id = id where payment_id is null;

-- Step 3.a: Update price times registered people
update reg_payment set price = price * (select count(p.ind) from reg_person p where p.registration_id = id);
-- Step 3.b: Update deposit times registered people
update reg_payment set deposit = deposit * (select count(p.ind) from reg_person p where p.registration_id = id);

-- Step 4: Update discount for event with code 25TABOR
update reg_payment payment
set discount_value = (select case when count(p.ind) = 2 then 20 when count(p.ind) > 2 then 50 else 0 end from reg_person p where p.registration_id = payment.id)
where exists (
    select e.id
    from reg_registration r
        join reg_event_term et on et.id = r.event_term_id
        join reg_event e on et.event_id = e.id
    where r.payment_id = payment.id and e.discount_type = '25TABOR'
);

-- Step 5: Update the payment sequence
select setval('seq_payment_id', (select coalesce(max(id), 0) + 1 from reg_payment));

