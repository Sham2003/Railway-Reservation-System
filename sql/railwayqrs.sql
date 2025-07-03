



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
INSERT INTO trainStations values(16179,'MS','20-jun-2023 10:00:00 PM ','20-jun-2023 10:00:00 PM');
INSERT INTO trainStations values(16179,'TBM','20-jun-2023 10:29:00 PM','20-jun-2023 10:30:00 PM');
INSERT INTO trainStations values(16179,'CGL','20-jun-2023 10:58:00 PM','20-jun-2023 11:00:00 PM');
INSERT INTO trainStations values(16179,'VM','21-jun-2023 12:30:00 AM','21-jun-2023 12:40:00 AM');
INSERT INTO trainStations values(16179,'TDPR','21-jun-2023 01:26:00 AM','21-jun-2023 01:27:00 AM');
INSERT INTO trainStations values(16179,'CDM','21-jun-2023 02:14:00 AM','21-jun-2023 02:15:00 AM');
INSERT INTO trainStations values(16179,'SY','21-jun-2023 02:34:00 AM','21-jun-2023 02:35:00 AM');
INSERT INTO trainStations values(16179,'MV','21-jun-2023 03:03:00 AM','21-jun-2023 03:05:00 AM');
INSERT INTO trainStations values(16179,'KMU','21-jun-2023 03:24:00 AM','21-jun-2023 03:25:00 AM');
INSERT INTO trainStations values(16179,'PML','21-jun-2023 03:39:00 AM','21-jun-2023 03:40:00 AM');
INSERT INTO trainStations values(16179,'TJ','21-jun-2023 04:35:00 AM','21-jun-2023 04:45:00 AM');
INSERT INTO trainStations values(16179,'NMJ','21-jun-2023 05:15:00 AM','21-jun-2023 05:25:00 AM');
INSERT INTO trainStations values(16179,'MQ','21-jun-2023 06:25:00 AM','21-jun-2023 06:25:00 AM');

INSERT INTO trainStations values(12635,'MS','20-jun-2023 01:20:00 pm ','20-jun-2023 01:20:00 pm');
INSERT INTO trainStations values(12635,'TBM','20-jun-2023 01:43:00 pm ','20-jun-2023 01:45:00 pm ');
INSERT INTO trainStations values(12635,'CGL','20-jun-2023 02:13:00 pm ','20-jun-2023 02:15:00 pm ');
INSERT INTO trainStations values(12635,'VM','20-jun-2023 03:45:00 pm','20-jun-2023 03:50:00 pm');
INSERT INTO trainStations values(12635,'VRI','20-jun-2023 04:28:00 pm ','20-jun-2023 04:30:00 pm');
INSERT INTO trainStations values(12635,'ALU','20-jun-2023 05:04:00 pm','20-jun-2023 05:05:00 pm');
INSERT INTO trainStations values(12635,'TPJ','20-jun-2023 06:30:00 pm','20-jun-2023 06:35:00 pm');
INSERT INTO trainStations values(12635,'DG','20-jun-2023 08:03:00 pm','20-jun-2023 08:05:00 pm');
INSERT INTO trainStations values(12635,'SDN','20-jun-2023 08:34:00 pm','20-jun-2023 08:35:00 pm');
INSERT INTO trainStations values(12635,'MDU','20-jun-2023 09:25:00 pm','20-jun-2023 09:25:00 pm');

rem: populating train class info

INSERT INTO trainclassinfo VALUES(16179,'20-jun-2023','1AC',100,800);
INSERT INTO trainclassinfo VALUES(16179,'20-jun-2023','2AC',100,500);
INSERT INTO trainclassinfo VALUES(16179,'20-jun-2023','sleeper',600,250);

INSERT INTO trainclassinfo VALUES(16179,'21-jun-2023','1AC',100,800);
INSERT INTO trainclassinfo VALUES(16179,'21-jun-2023','2AC',100,500);
INSERT INTO trainclassinfo VALUES(16179,'21-jun-2023','sleeper',600,250);

INSERT INTO trainclassinfo VALUES(16179,'22-jun-2023','1AC',100,800);
INSERT INTO trainclassinfo VALUES(16179,'22-jun-2023','2AC',100,500);
INSERT INTO trainclassinfo VALUES(16179,'22-jun-2023','sleeper',600,250);

INSERT INTO trainclassinfo VALUES(12635,'20-jun-2023','1AC',100,900);
INSERT INTO trainclassinfo VALUES(12635,'20-jun-2023','2AC',100,600);
INSERT INTO trainclassinfo VALUES(12635,'20-jun-2023','sleeper',600,300);

INSERT INTO trainclassinfo VALUES(12635,'21-jun-2023','1AC',100,900);
INSERT INTO trainclassinfo VALUES(12635,'21-jun-2023','2AC',100,600);
INSERT INTO trainclassinfo VALUES(12635,'21-jun-2023','sleeper',600,300);

INSERT INTO trainclassinfo VALUES(12635,'22-jun-2023','1AC',100,900);
INSERT INTO trainclassinfo VALUES(12635,'22-jun-2023','2AC',100,600);
INSERT INTO trainclassinfo VALUES(12635,'22-jun-2023','sleeper',600,300);





SELECT  st.stationname,ts.arrival,ts.departure FROM trainStations ts JOIN stations st ON ts.stationid=st.stationid  WHERE trainid = ?

to find train between a source and destination on a given date

	SELECT distinct(t1.trainid)
	FROM trainStations t1
	JOIN trainStations t2 ON t1.trainid = t2.trainid
	JOIN trainclassinfo tc ON t1.trainid = tc.trainid
	WHERE t1.stationid = 'DG'
  	AND t2.stationid = 'MDU'
	AND tc.tdate = '20-jun-2023'
	AND t1.arrival < t2.arrival;

to print all stations for a train excluding the destination

	SELECT s.stationid,s.stationname 
	FROM trainStations t 
	JOIN stations s on t.stationid = s.stationid
	WHERE t.trainid = 16179 
	AND t.stationid != (select destination from trains where trainid=16179); 

information about class of a train on a particular date
	
	select c.classname, c.capacity, c.price
	FROM trainclassinfo c
	where c.trainid = 16179
	AND c.tdate = '20-jun-2023';


list of stops between a station and destination for a given trainid
	select t.stationid , s.stationname
	FROM trainStations t
	JOIN stations s ON t.stationid = s.stationid
	WHERE t.trainid = 16179
	AND TO_TIMESTAMP(t.arrival,'DD-mon-YYYY HH12:MI:SS am' ) >  (select TO_TIMESTAMP(arrival,'DD-mon-YYYY HH12:MI:SS am' ) from trainStations where stationid = 'CDM' AND trainid = 16179 );

	
departure time at the boarding station and arrival time at the destination station for a given trainid 

	SELECT t1.trainid,t1.departure AS source_departure,t2.arrival as dest_arrival,t3.trainname
	FROM trainStations t1
	JOIN trainStations t2 ON t1.trainid = t2.trainid
	JOIN trains t3 ON t1.trainid = t3.trainid 
	WHERE t1.stationid = 'CDM'
  	AND t2.stationid = 'MQ'
	AND t1.trainid = 16179;


select t.stationid , s.stationname
FROM trainStations t
JOIN stations s ON t.stationid = s.stationid
WHERE t.trainid = 16179
AND t.arrival >  (select arrival from trainStations where stationid = 'CDM' AND trainid = 16179);
