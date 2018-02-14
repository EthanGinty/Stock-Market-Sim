-- *****************************************************************************
-- This script contains INSERT statements for populating tables with seed data
-- *****************************************************************************

BEGIN;

-- Test data

INSERT INTO app_user(user_name, password, salt, first_name, last_name) VALUES ('gates', 'bill', '', 'billiam', 'gators');
INSERT INTO app_user(user_name, password, salt, first_name, last_name) VALUES ('jobs', 'steve', '', 'tooker', 'jerbs');
INSERT INTO app_user(user_name, password, salt, first_name, last_name) VALUES ('woz', 'steve', '', 'wiz', 'zard');
INSERT INTO app_user(user_name, password, salt, first_name, last_name) VALUES ('ellison', 'larry', '', 'lazarus', 'yaisaidit');
INSERT INTO app_user(user_name, password, salt, first_name, last_name) VALUES ('kildall', 'gary', '', 'killer','dan');
INSERT INTO app_user(user_name, password, salt, first_name, last_name) VALUES ('admin', 'UZ4iL41B8SZEdTdG04nupg==', 'FagMPo9MgmyO/us17EtPMLBzfZTJUcUV5F189tnpMjSv7W+Ks/7vHlsabjl1euFt5n40cnQdNjmEwKIHLB0Oj/VC5WnJnsA8lq+5r5RXwynhcTHZcocf/UObOwCtgvFlKyIIFK90xz6pi1HHr/Oli1sCwj9PshEZ0CwTROrmaW4=', 'admin','admin');

INSERT INTO game (name_of_game, start_date, end_date, owner_name) VALUES ('javabeans', '2017-9-12', '2017-12-12','gates');
INSERT INTO game (name_of_game, start_date, end_date, owner_name) VALUES ('llamapumapants', '2017-9-12', '2018-01-31', 'jobs');
INSERT INTO game (name_of_game, start_date, end_date, owner_name) VALUES ('benhatesme', '2017-10-11', '2018-03-03', 'jobs');
INSERT INTO game (name_of_game, start_date, end_date, owner_name) VALUES ('imaprettyunicorn', '2018-01-01', '2018-06-11', 'woz');
INSERT INTO game (name_of_game, start_date, end_date, owner_name) VALUES ('eatmorecheese', '2017-6-16', '2018-01-01', 'jobs');

INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('1','1', '101000.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('1','2', '100000.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('1','3', '103000.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('1','4', '100300.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('1','5', '100500.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('1','6', '10234000.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('2','1', '145000.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('2','2', '10560000.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('2','3', '1000234500.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('2','4', '100345000.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('2','5', '10450000.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('2','6', '10650000.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('3','1', '100034500.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('3','2', '100345000.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('3','3', '10560000.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('3','4', '1023450000.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('3','5', '1003452000.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('3','6', '10006500.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('4','1', '1006345000.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('4','2', '1003425000.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('4','3', '106450000.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('4','4', '100345000.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('4','5', '100645000.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('4','6', '1055000.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('5','1', '1065450000.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('5','2', '103520000.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('5','3', '106354320000.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('5','4', '105460000.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('5','5', '10540000.00');
INSERT INTO game_user (game_id, user_id, available_balance) VALUES ('5','6', '1004000.00');


INSERT INTO stock (symbol) VALUES ('FOXA');
INSERT INTO stock (symbol) VALUES ('V');
INSERT INTO stock (symbol) VALUES ('MU');
INSERT INTO stock (symbol) VALUES ('SIRI');
INSERT INTO stock (symbol) VALUES ('KODK');
INSERT INTO stock (symbol) VALUES ('GE');
INSERT INTO stock (symbol) VALUES ('CAT');
INSERT INTO stock (symbol) VALUES ('T');
INSERT INTO stock (symbol) VALUES ('CX');
INSERT INTO stock (symbol) VALUES ('JNK');
INSERT INTO stock (symbol) VALUES ('EFA');
INSERT INTO stock (symbol) VALUES ('SPY');
INSERT INTO stock (symbol) VALUES ('BAC');
INSERT INTO stock (symbol) VALUES ('INTC');
INSERT INTO stock (symbol) VALUES ('F');
INSERT INTO stock (symbol) VALUES ('XLF');
INSERT INTO stock (symbol) VALUES ('NVAX');
INSERT INTO stock (symbol) VALUES ('EEM');
INSERT INTO stock (symbol) VALUES ('AMD');
INSERT INTO stock (symbol) VALUES ('MDR');
INSERT INTO stock (symbol) VALUES ('ORCL');
INSERT INTO stock (symbol) VALUES ('CZR');
INSERT INTO stock (symbol) VALUES ('DBEU');
INSERT INTO stock (symbol) VALUES ('FCX');
INSERT INTO stock (symbol) VALUES ('NTCT');
INSERT INTO stock (symbol) VALUES ('DWDP');
INSERT INTO stock (symbol) VALUES ('SSNC');
INSERT INTO stock (symbol) VALUES ('WFT');
INSERT INTO stock (symbol) VALUES ('BSX');
INSERT INTO stock (symbol) VALUES ('MSFT');
INSERT INTO stock (symbol) VALUES ('SWN');
INSERT INTO stock (symbol) VALUES ('AET');
INSERT INTO stock (symbol) VALUES ('CMCSA');
INSERT INTO stock (symbol) VALUES ('IAU');
INSERT INTO stock (symbol) VALUES ('VEA');
INSERT INTO stock (symbol) VALUES ('VWO');
INSERT INTO stock (symbol) VALUES ('AKS');
INSERT INTO stock (symbol) VALUES ('NOK');
INSERT INTO stock (symbol) VALUES ('AAPL');
INSERT INTO stock (symbol) VALUES ('EBAY');
INSERT INTO stock (symbol) VALUES ('RAD');
INSERT INTO stock (symbol) VALUES ('JD');
INSERT INTO stock (symbol) VALUES ('DISCA');
INSERT INTO stock (symbol) VALUES ('HPE');
INSERT INTO stock (symbol) VALUES ('HYG');
INSERT INTO stock (symbol) VALUES ('HRL');
INSERT INTO stock (symbol) VALUES ('AIG');
INSERT INTO stock (symbol) VALUES ('QQQ');
INSERT INTO stock (symbol) VALUES ('ECA');
INSERT INTO stock (symbol) VALUES ('TXN');

INSERT INTO transaction (price, buy_sell, number_of_shares, user_id, stock_id, game_id) VALUES ('10.19','buy', '20', '1', '19','1');
INSERT INTO transaction (price, buy_sell, number_of_shares, user_id, stock_id, game_id) VALUES ('15.1','buy', '20', '2', '1','1');
INSERT INTO transaction (price, buy_sell, number_of_shares, user_id, stock_id, game_id) VALUES ('1.19','buy', '20', '3', '2','1');
INSERT INTO transaction (price, buy_sell, number_of_shares, user_id, stock_id, game_id) VALUES ('10.09','buy', '20', '4', '3','1');
INSERT INTO transaction (price, buy_sell, number_of_shares, user_id, stock_id, game_id) VALUES ('50.65','buy', '20', '5', '4','1');
INSERT INTO transaction (price, buy_sell, number_of_shares, user_id, stock_id, game_id) VALUES ('11.11','buy', '20', '6', '5','1');

INSERT INTO transaction (price, buy_sell, number_of_shares, user_id, stock_id, game_id) VALUES ('10.19','sell', '10', '1', '19','1');
INSERT INTO transaction (price, buy_sell, number_of_shares, user_id, stock_id, game_id) VALUES ('15.1','sell', '10', '2', '1','1');
INSERT INTO transaction (price, buy_sell, number_of_shares, user_id, stock_id, game_id) VALUES ('1.19','sell', '10', '3', '2','1');
INSERT INTO transaction (price, buy_sell, number_of_shares, user_id, stock_id, game_id) VALUES ('10.09','sell', '10', '4', '3','1');
INSERT INTO transaction (price, buy_sell, number_of_shares, user_id, stock_id, game_id) VALUES ('50.65','sell', '10', '5', '4','1');
INSERT INTO transaction (price, buy_sell, number_of_shares, user_id, stock_id, game_id) VALUES ('11.11','sell', '10', '6', '5','1');



COMMIT;