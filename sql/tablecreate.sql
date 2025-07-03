drop table ticket;
drop table passengerTickets;
drop table trainStations;
drop table trainclassinfo;
drop table trains;
drop table stations;
drop table passengers;
drop table Address;

rem: address table

create table Address(
	zipcode INT PRIMARY KEY,
	postoffice varchar(30),
	district varchar(30),
	city varchar(30),
	state varchar(30)
);

rem: Passenger table
create table passengers(
	passengerid INT PRIMARY KEY,
	name varchar(15),
	phone NUMBER,
	age INT,
	gender varchar(5),
	zipcode INT constraint fk_zip REFERENCES Address(zipcode)
);

create table stations(
	stationid varchar(5) PRIMARY KEY,
	stationname varchar(20) UNIQUE
);

create table trains(
	trainid INT PRIMARY KEY,
	source varchar(10) constraint fk_src REFERENCES stations(stationid),
	destination varchar(10)  constraint fk_dst REFERENCES stations(stationid),
	trainname varchar(20)
);

create table trainclassinfo(
	trainid constraint fk_train1 REFERENCES trains(trainid),
	tdate DATE,
	classname varchar(8),
	capacity INT,
	price INT,
	PRIMARY KEY(trainid,classname,tdate)
);

create table trainStations(
	trainid INT constraint fk_train2 REFERENCES trains(trainid) ,
	stationid varchar(5) constraint fk_station REFERENCES stations(stationid),
	arrival TIMESTAMP,
	departure TIMESTAMP,
	PRIMARY KEY(trainid,stationid)
);

create table ticket(
	pnr INT PRIMARY KEY,
	trainid INT constraint fk_train REFERENCES trains(trainid),
	dateofjourney DATE,
	source varchar(10)  constraint fk_source REFERENCES stations(stationid) ,
	destination varchar(10)  constraint fk_destination REFERENCES stations(stationid),
	totalamt INT,
	class varchar(8),
	totaltickets INT
);

create table passengerTickets(
	pnr INT,
	passengerid INT,
	PRIMARY KEY(pnr,passengerid)
);


CREATE OR REPLACE PROCEDURE insert_passengers(
pid IN passengers.passengerid%type,
pname IN passengers.name%type,
pmobile IN passengers.phone%type,
page IN passengers.age%type,
pgender IN passengers.gender%type,
pzip IN passengers.zipcode%type,
ticketid IN ticket.pnr%type)

IS
	ppid INT;
BEGIN
	BEGIN
		SELECT passengerid into ppid from passengers where passengerid = pid;
	EXCEPTION
		WHEN NO_DATA_FOUND THEN
			null;
	END;
	IF sql%NOTFOUND THEN
		INSERT INTO passengers VALUES (pid,pname,pmobile,page,pgender,pzip);
	END IF;
	INSERT INTO passengerTickets values(ticketid,pid);
END;
/
 
CREATE OR REPLACE TRIGGER update_classinfo
AFTER INSERT ON ticket
FOR EACH ROW
BEGIN
	UPDATE trainclassinfo
	SET capacity = capacity - :new.totaltickets
	WHERE trainid = :new.trainid AND
	tdate = :new.dateofjourney AND
	classname = :new.class;
END;
/

	



