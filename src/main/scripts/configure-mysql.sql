## Use to run mysql db docker image (optional)
# docker run --name mysqldb12 -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql
# connect to mysql and run as root user
# create Databases
CREATE DATABASE sfg_dev;
CREATE DATABASE sfg_prod;

#Create database service accounts
CREATE USER 'sfg_dev_user'@'localhost' IDENTIFIED BY 'P@ssw0rd';
CREATE USER 'sfg_prod_user'@'localhost' IDENTIFIED BY 'P@ssw0rd';

#Database grants
GRANT SELECT ON sfg_dev.* to 'sfg_dev_user'@'localhost';
GRANT INSERT ON sfg_dev.* to 'sfg_dev_user'@'localhost';
GRANT UPDATE ON sfg_dev.* to 'sfg_dev_user'@'localhost';
GRANT DELETE ON sfg_dev.* to 'sfg_dev_user'@'localhost';

GRANT SELECT ON sfg_prod.* to 'sfg_prod_user'@'localhost';
GRANT INSERT ON sfg_prod.* to 'sfg_prod_user'@'localhost';
GRANT UPDATE ON sfg_prod.* to 'sfg_prod_user'@'localhost';
GRANT DELETE ON sfg_prod.* to 'sfg_prod_user'@'localhost';
