# pacemaker-console
An activity tracker console to track a users activities, console  formatted with btc-ascii-table library

Console Commands:
abbrev name               params
lu list-users             ()
cu create-user            (first name, last name, email, password)
lu list-user              (email)
lius list-user            (id)
la list-activities        (userid, sortBy: type, location, distance, date, duration)
la list-activities        (user id)
du delete-user            (id)
aa add-activity           (user-id, type, location, distance, datetime, duration)
al add-location           (activity-id, latitude, longitude)
cff change-file-format    (file format: xml, json)
l load ()
s store () 
