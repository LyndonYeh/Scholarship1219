SET GLOBAL event_scheduler = ON;
DELIMITER //

CREATE EVENT my_daily_event
ON SCHEDULE
  EVERY 1 DAY
  STARTS TIMESTAMP(CURRENT_DATE, '00:00:00')
DO
  -- 在這裡放入你想要定期執行的 SQL 語句
  DELETE FROM scholarshipv1.garbagecollection WHERE DATE(FROM_UNIXTIME(updatedTime)) < CURRENT_DATE ;

//

DELIMITER ;