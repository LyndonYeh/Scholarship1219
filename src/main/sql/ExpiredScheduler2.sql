SET GLOBAL event_scheduler = ON;
DELIMITER //

create EVENT my_daily_event2
ON SCHEDULE
  EVERY 5 second
  STARTS TIMESTAMP(CURRENT_DATE, '00:00:00')
DO
  -- 在這裡放入你想要定期執行的 SQL 語句
  update scholarshipv1.scholarshiprecord set isExpired =1,isUpdated= 0 WHERE endDate < CURRENT_DATE ;

//

DELIMITER ;