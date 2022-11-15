# AccountService
* Сервис обрабатывает три endpoint'а "/getId" с параметром "id", "/addValue" с параметрами "id, value"  
и "/resetStatistic".
* Сервис подключается к postgres, читает и записывает данные, кэширует их в Map'у.
