const connection = require("../services/db");

var express = require('express');
var router = express.Router();

/* GET users listing. */
router.get('/list', function(req, res, next) {
  let sql = 'select * from user'

  connection.query(sql, (err, data, fields) => {
    if (err) throw err

    res.status(200).json({
      status: "success",
      length: data?.length,
      data: data,
    });
  })
});

/* Create a new user. */
router.post('/create', function(req, res) {
  console.log(req.body)

  let userType = req.body.userType
  let fullname = req.body.fullname
  let mobile = req.body.mobile
  let password = req.body.password
  let joinedSession = 0;


  let sql = `insert into user (userType, fullname, mobile, password, joinedSession) values ('${userType}', '${fullname}', '${mobile}', '${password}', '${joinedSession}')`
  console.log(sql)

  connection.query(sql, (err, data, fields) => {
    if (err) throw err

    res.status(200).json({
      status: "success",
      length: data?.length,
      data: data,
    });
  })
});

/* Create a new user. */
router.post('/delete', function(req, res) {
  console.log(req.body)

  let id = req.body.id

  let sql = `delete from user where userId = '${id}'`
  console.log(sql)

  connection.query(sql, (err, data, fields) => {
    if (err) throw err

    res.status(200).json({
      status: "success",
      length: data?.length,
      data: data,
    });
  })
});

/* Log into a user. */
router.post('/login', function(req, res) {
  console.log(req.body)
  
  let fullname = req.body.fullname
  let password = req.body.password

  let sql = `select * from user where fullname = '${fullname}' and password = '${password}'`
  console.log(sql)

  connection.query(sql, (err, data, fields) => {
    if (err) throw err

    let isAuthenticated = false
    let userData = null

    if (data?.length > 0) {
      isAuthenticated = true
      userData = data[0]
    }

    res.status(200).json({
      status: "success",
      isAuthenticated: isAuthenticated,
      userData: userData,
    });
  })
});


module.exports = router;
