=# 🚆 Railway Reservation System – Database Schema (Oracle SQL)

This repository contains the SQL schema for a Railway Reservation System designed using **Oracle SQL**. The structure is built to support functionalities like passenger bookings, train listings, seat availability, and travel routes.

---

## 📂 Schema Overview

### 🧱 Tables

| Table Name         | Description            |
|--------------------|------------------------|
| `Address`          | Stores area details (zipcode, city, state, etc.) |
| `passengers`       | Holds passenger information and contact          |
| `stations`         | Registry of station codes and names              |
| `trains`           | Train list with source & destination stations    |
| `trainclassinfo`   | Train-wise class, seat, and fare info            |
| `trainStations`    | Defines train route via intermediate stations    |
| `ticket`           | Stores ticket info like class, PNR, and fare     |
| `passengerTickets` | Mapping table for tickets and passengers         |

---

### ⚙️ Stored Procedure

| Procedure Name      | Description            |
|---------------------|------------------------|
| `insert_passengers` | Inserts passenger conditionally and maps to a ticket |

---

### 🔁 Trigger

| Trigger Name        | Description            |
|---------------------|------------------------|
| `update_classinfo`  | Updates seat count after ticket booking |

---

## 🧪 Features Covered

- 🔍 Passenger and Address lookup  
- 🎫 Booking tickets and handling seat limits  
- 🧍 Handling multiple passengers per ticket  
- 🛤️ Full route traversal and train-station mapping  
- ⬇️ Automatic seat count update via trigger  

---

## 📦 Requirements

- Oracle Database (tested on 11g/12c+)
- SQL Developer or any compatible IDE

---

## 🚀 Usage

1. Execute `drop` statements to clean up old data  
2. Run `create table` statements in order  
3. Define procedure and trigger  
4. Test using `INSERT` queries or app logic

---
