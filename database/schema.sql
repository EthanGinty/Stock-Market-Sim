-- *************************************************************************************************
-- This script creates all of the database objects (tables, sequences, etc) for the database
-- *************************************************************************************************
BEGIN;

CREATE TABLE app_user (
  user_id serial primary key,
  user_name varchar(32) NOT NULL,
  password varchar(32) NOT NULL,
  salt varchar(255) NOT NULL,
  first_name varchar(32) NOT NULL,
  last_name varchar(32) NOT NULL
);

CREATE TABLE stock (
stock_id serial primary key,
symbol varchar (10) NOT NULL
);

CREATE TABLE game (
game_id serial primary key,
name_of_game varchar (64) NOT NULL,
start_date date NOT NULL,
end_date date NOT NULL,
owner_name varchar(64) NOT NULL,
is_real_game boolean default true
);

CREATE TABLE transaction (
transaction_id serial primary key,
price money DEFAULT 0 NOT NULL,
buy_sell varchar(10) NOT NULL,
number_of_shares integer DEFAULT 0 NOT NULL,
user_id integer,
stock_id integer,
game_id integer
);

CREATE TABLE game_user (
game_user_id serial primary key,
game_id integer,
user_id integer,
available_balance numeric default 0,
total_balance numeric default 0
);

ALTER table transaction ADD FOREIGN KEY (user_id) REFERENCES app_user(user_id);
ALTER table transaction ADD FOREIGN KEY (stock_id) REFERENCES stock(stock_id);
ALTER table transaction ADD FOREIGN KEY (game_id) REFERENCES game(game_id);
ALTER table game_user ADD FOREIGN KEY (user_id) REFERENCES app_user(user_id);
ALTER table game_user ADD FOREIGN KEY (game_id) REFERENCES game(game_id);
COMMIT;