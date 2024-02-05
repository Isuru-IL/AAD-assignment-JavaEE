DROP DATABASE IF EXISTS javaEE_assignment ;
CREATE DATABASE IF NOT EXISTS javaEE_assignment;
USE javaEE_assignment;

CREATE TABLE User(
                     userName VARCHAR(20),
                     password VARCHAR(100) NOT NULL,
                     CONSTRAINT PRIMARY KEY (userName)
);

CREATE TABLE Customer(
                         id VARCHAR(10),
                         name VARCHAR(100) NOT NULL ,
                         address TEXT NOT NULL ,
                         salary DECIMAL(8,2) NOT NULL ,
                         CONSTRAINT PRIMARY KEY (id)
);

CREATE TABLE Item(
                     itemCode VARCHAR(10),
                     itemName VARCHAR(30) NOT NULL ,
                     qtyOnHand INT NOT NULL ,
                     unitPrice DECIMAL(7,2) NOT NULL ,
                     CONSTRAINT PRIMARY KEY (itemCode)
);

CREATE TABLE Orders(
                       orderId VARCHAR(10),
                       cutId VARCHAR(10),
                       total DECIMAL(8,2) NOT NULL ,
                       date DATE NOT NULL ,
                       CONSTRAINT PRIMARY KEY (orderId),
                       CONSTRAINT FOREIGN KEY (cutId) REFERENCES Customer(id)
                           ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE OrderDetail(
                            orderId VARCHAR(10),
                            itemCode VARCHAR(10),
                            qty INT NOT NULL ,
                            CONSTRAINT PRIMARY KEY (orderId,itemCode),
                            CONSTRAINT FOREIGN KEY (orderId) REFERENCES Orders(orderId)
                                ON UPDATE CASCADE ON DELETE CASCADE,
                            CONSTRAINT FOREIGN KEY (itemCode) REFERENCES item(itemCode)
                                ON UPDATE CASCADE ON DELETE CASCADE
);