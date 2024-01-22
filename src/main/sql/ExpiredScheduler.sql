SET GLOBAL event_scheduler = ON;
DELIMITER //

CREATE EVENT my_daily_event2
ON SCHEDULE
  EVERY 1 DAY
  STARTS TIMESTAMP(CURRENT_DATE, '00:00:00')
DO
  
  UPDATE scholarshipv1.scholarshiprecord Set isExpired = 0 WHERE EndDate > CURRENT_DATE ;

//

DELIMITER ;