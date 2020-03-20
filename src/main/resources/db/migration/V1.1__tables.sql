create TABLE infections (
    id bigint PRIMARY KEY,
    name varchar(200) NOT NULL, 
    type varchar(200) NOT NULL,
    description varchar(500)
);

create sequence infection_id_seq start with 1000 increment by 50;

create TABLE doctors (
    id bigint PRIMARY KEY, 
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    gender varchar(20) NOT NULL,
    phone_number varchar(20)
);

create sequence doctor_id_seq start with 1000 increment by 50;

create TABLE patients (
    id bigint PRIMARY KEY, 
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    gender varchar(20) NOT NULL,
    social_security_number bigint NOT NULL,
    address varchar(200),
    date_of_birth date,
    date_admitted date NOT NULL,
    date_discharged date,
    death_timestamp datetime2,
    infection_id bigint,
    doctor_id bigint
);

create sequence patient_id_seq start with 1000 increment by 50;


