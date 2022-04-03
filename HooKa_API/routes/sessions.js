const connection = require("../services/db");

var express = require('express');
var router = express.Router();

/* join session. */
router.get('/list', function(req, res, next) {

    let inputPin = req.body.userType

    let sql = `select * from session where sessionPin = ${inputPin} and sessionRunningStatus = 1`
  
    connection.query(sql, (err, data, fields) => {
      if (err) throw err
  
      res.status(200).json({
        status: "success",
        length: data?.length,
        data: data,
      });
    })
  });
