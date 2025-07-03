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
	city varchar(15),
	state varchar(15)
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
	classname varchar(10),
	capacity INT,
	price INT,
	PRIMARY KEY(trainid,classname,tdate)
);

create table trainStations(
	trainid INT constraint fk_train2 REFERENCES trains(trainid) ,
	stationid varchar(5) constraint fk_station REFERENCES stations(stationid),
	arrival varchar(10),
	departure varchar(10),
	PRIMARY KEY(trainid,stationid)
);

create table ticket(
	pnr INT PRIMARY KEY,
	trainid constraint fk_train REFERENCES trains(trainid),
	dateofjourney DATE,
	source varchar(10)  constraint fk_source REFERENCES stations(stationid) ,
	destination varchar(10)  constraint fk_destination REFERENCES stations(stationid),
	totalamt INT,
	class varchar(5) ,
	totaltickets INT
);

create table passengerTickets(
	pnr INT,
	passengerid INT,
	PRIMARY KEY(pnr,passengerid)
);


REM: ADDRESS
INSERT INTO Address values(600083,'chennai','tamilnadu');
Insert into Address values(614001,'mannargudi','tamilnadu');

rem:Insertion into stations.
INSERT INTO Stations values('MS','Chennai Egmore');
INSERT INTO Stations values('TBM','Tambaram');
INSERT INTO Stations values('CGL','Chengalpattu');
INSERT INTO Stations values('VM','Villupuram Junction');
INSERT INTO Stations values('TDPR','Tirupadripulyur');
INSERT INTO Stations values('CDM','Chidambaram');
INSERT INTO Stations values('SY','Sirkazhi');
INSERT INTO Stations values('MV','Mayiladuturai');
INSERT INTO Stations values('KMU','Kumbakonam');
INSERT INTO Stations values('PML','Papanasam');
INSERT INTO Stations values('TJ','Thanjavur');
INSERT INTO Stations values('NMJ','Nidamangalam');
INSERT INTO Stations values('MQ','Mannargudi');
INSERT INTO Stations values('VRI','Vridhachalam');
INSERT INTO Stations values('ALU','Ariyalur');
INSERT INTO Stations values('TPJ','Tiruchirapalli');
INSERT INTO Stations values('DG','Dindigul junction');
INSERT INTO Stations values('SDN','Sholavandhan');
INSERT INTO Stations values('MDU','Madurai');



rem:Insertion into trains
INSERT INTO trains values(16179,'MS','MQ','Mannai Express');
INSERT INTO trains values(12635,'MS','MDU','Vaigai Express');

rem:Insertion into train stations
INSERT INTO trainStations values(16179,'MS','22:00:00','22:00:00');
INSERT INTO trainStations values(16179,'TBM','22:29:00','22:30:00');
INSERT INTO trainStations values(16179,'CGL','22:58:00','23:00:00');
INSERT INTO trainStations values(16179,'VM','00:30:00','00:40:00');
INSERT INTO trainStations values(16179,'TDPR','01:26:00','01:27:00');
INSERT INTO trainStations values(16179,'CDM','02:14:00','02:15:00');
INSERT INTO trainStations values(16179,'SY','02:34:00','02:35:00');
INSERT INTO trainStations values(16179,'MV','03:03:00','03:05:00');
INSERT INTO trainStations values(16179,'KMU','03:24:00','03:25:00');
INSERT INTO trainStations values(16179,'PML','03:39:00','03:40:00');
INSERT INTO trainStations values(16179,'TJ','04:35:00','04:45:00');
INSERT INTO trainStations values(16179,'NMJ','05:15:00','05:25:00');
INSERT INTO trainStations values(16179,'MQ','06:25:00','06:25:00');

INSERT INTO trainStations values(12635,'MS','13:20:00','13:20:00');
INSERT INTO trainStations values(12635,'TBM','13:43:00','13:45:00');
INSERT INTO trainStations values(12635,'CGL','14:13:00','14:15:00');
INSERT INTO trainStations values(12635,'VM','15:45:00','15:50:00');
INSERT INTO trainStations values(12635,'VRI','16:28:00','16:30:00');
INSERT INTO trainStations values(12635,'ALU','17:04:00','17:05:00');
INSERT INTO trainStations values(12635,'TPJ','18:30:00','18:35:00');
INSERT INTO trainStations values(12635,'DG','20:03:00','20:05:00');
INSERT INTO trainStations values(12635,'SDN','20:34:00','20:35:00');
INSERT INTO trainStations values(12635,'MDU','21:25:00','21:25:00');





rem: populating train class info

INSERT INTO trainclassinfo VALUES(16179,'20-06-2023','1AC',100,800);
INSERT INTO trainclassinfo VALUES(16179,'20-06-2023','2AC',100,500);
INSERT INTO trainclassinfo VALUES(16179,'20-06-2023','sleeper',600,250);

INSERT INTO trainclassinfo VALUES(16179,'21-06-2023','1AC',100,800);
INSERT INTO trainclassinfo VALUES(16179,'21-06-2023','2AC',100,500);
INSERT INTO trainclassinfo VALUES(16179,'21-06-2023','sleeper',600,250);

INSERT INTO trainclassinfo VALUES(16179,'22-06-2023','1AC',100,800);
INSERT INTO trainclassinfo VALUES(16179,'22-06-2023','2AC',100,500);
INSERT INTO trainclassinfo VALUES(16179,'22-06-2023','sleeper',600,250);

INSERT INTO trainclassinfo VALUES(12635,'20-06-2023','1AC',100,900);
INSERT INTO trainclassinfo VALUES(12635,'20-06-2023','2AC',100,600);
INSERT INTO trainclassinfo VALUES(12635,'20-06-2023','sleeper',600,300);

INSERT INTO trainclassinfo VALUES(12635,'21-06-2023','1AC',100,900);
INSERT INTO trainclassinfo VALUES(12635,'21-06-2023','2AC',100,600);
INSERT INTO trainclassinfo VALUES(12635,'21-06-2023','sleeper',600,300);

INSERT INTO trainclassinfo VALUES(12635,'22-06-2023','1AC',100,900);
INSERT INTO trainclassinfo VALUES(12635,'22-06-2023','2AC',100,600);
INSERT INTO trainclassinfo VALUES(12635,'22-06-2023','sleeper',600,300);

COMMIT;







