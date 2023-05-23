create table policy
(
    policy_id           text,
    policy_tenure       numeric,
    age_of_car          numeric,
    age_of_policyholder numeric,
    population_density  integer,
    displacement        integer,
    turning_radius      numeric,
    length              integer,
    width               integer,
    height              integer,
    gross_weight        integer
);

alter table policy
    owner to postgres;