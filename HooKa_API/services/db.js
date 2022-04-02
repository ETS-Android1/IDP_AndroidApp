// set up db
var mysql = require('mysql')
var connection = mysql.createConnection({
  host: 'localhost',
  user: 'myuser',
  password: 'xxxx',
  database: 'hooka'
})

connection.connect();

module.exports = connection;