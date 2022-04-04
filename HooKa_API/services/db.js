// set up db
var mysql = require('mysql2')
var connection = mysql.createConnection({
  host: 'localhost',
  user: 'myuser',
  password: 'xxxx',
  database: 'hooka'
})

connection.connect();

module.exports = connection;