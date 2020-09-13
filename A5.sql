SHOW databases;
use DBMSL; 

show tables;

-- Creating customer table 
CREATE TABLE customer (
	customer_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    payment_date DATE NOT NULL,
	scheme_name VARCHAR(20) NOT NULL,
    status VARCHAR(2) NOT NULL,
    PRIMARY KEY(customer_id)
);
-- YEAR - MONTH - DAY
INSERT into customer (name, payment_date, scheme_name, status)
VALUES ('abhishek', '20-FEB-20', 'ABC', 'N');


-- Creating fine table
CREATE TABLE fine (
	customer_id INT NOT NULL,
    fine_date DATE NOT NULL,
    amount INT NOT NULL
);

DELIMITER $$
CREATE PROCEDURE check_fine(
	IN cid int
)
BEGIN
	DECLARE namesch VARCHAR(10);
	DECLARE dateop date;
	DECLARE amount real;
	DECLARE currdate date DEFAULT '2020-09-1';
	DECLARE days int;
	DECLARE val int;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SHOW ERRORS;  -- this is the only one which you need
        ROLLBACK;   
    END; 

	select COUNT(*) into val from customer where customer_id = cid;
	if (val = 0) then
		set val := 0;
	end if;
  
	select payment_date into dateop from customer where customer_id = cid;  
	set days := DATEDIFF(currdate, dateop);
  
	if (days>15 and days<30) then
		set amount := days*5;
		insert into fine values(cid,currdate,amount);
		update customer set status = 'P' where customer_id = cid;
	end if;
  
	if (days>30) then
		set amount := 150+((days-30)*50);  
		insert into fine values(cid,currdate,amount);
		update customer set status = 'P' where customer_id = cid;
	end if;
    
END$$ 
DELIMITER ;   

call check_fine(1);
drop procedure check_fine;







 





















CREATE OR REPLACE PROCEDURE check_fine(cid in int) is

  namesch varchar(20);
  dateop date;
  amount real;
  currdate date := '04-SEP-19';
  days int;
  val number;
  invalid_entry exception;
  
BEGIN
  select count(*) into val from Customer where cust_id = cid;
  if (val = 0) then
    raise invalid_entry;
  end if;
  
  select DOP into dateop from Customer where Cust_id = cid;  
  days := trunc(currdate) - trunc(dateop);
  
  if (days>15 and days<30) then
    amount := days*5;
    insert into Fine values(cid,currdate,amount);
    update Customer set status = 'N' where Cust_id = cid;
  end if;
  
  if (days>30) then
    amount := 150+((days-30)*50);  
    insert into Fine values(cid,currdate,amount);
    update Customer set status = 'N' where Cust_id = cid;
  end if;
  
EXCEPTION
  when invalid_entry then  
    dbms_output.put_line('Invalid ID'); 

END;
/      
