SET GLOBAL event_scheduler = ON;
DELIMITER //

CREATE EVENT my_daily_event
ON SCHEDULE
  EVERY 1 DAY
  STARTS TIMESTAMP(CURRENT_DATE, '00:00:00')
DO
  -- 在這裡放入你想要定期執行的 SQL 語句
DELETE FROM scholarshipv1.garbagecollection 
WHERE UNIX_TIMESTAMP(STR_TO_DATE(updatedTime, '%Y-%m-%d %H:%i:%s')) < UNIX_TIMESTAMP(DATE_ADD(NOW(), INTERVAL 30 DAY));



//

DELIMITER ;