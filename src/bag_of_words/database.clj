(ns bag-of-words.database
  (:require [clojure.java.jdbc :as j]
            [bag-of-words.files :as files]))

;TO-DO parse db-info from file (json / xml)
(let [db-conf "database_info.xml"
      db-host (files/read-xml db-conf :db_host)
      db-port (files/read-xml db-conf :db_port)
      db-name (files/read-xml db-conf :db_name)]

  (def db {:classname "org.postgresql.Driver" ; must be in classpath
           :subprotocol "postgresql"
           :subname (str "//" db-host ":" db-port "/" db-name)
           ; Any additional keys are passed to the driver
           ; as driver-specific properties.
           :user (files/read-xml db-conf :db_user)
           :password (files/read-xml db-conf :db_password)}))

