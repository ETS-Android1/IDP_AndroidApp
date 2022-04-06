const connection = require("../services/db");

var express = require('express');
var router = express.Router();

/* join session. */
router.get('/join', function(req, res, next) {

    let sessionId = req.body.sessionId
    let userId = req.body.userId

    let sql = `select * from session where sessionPin = ${sessionId} and sessionRunningStatus = 0`
    let sql1 = `update user set joinedSession = ${sessionId} where userId = ${userId}`
  
    connection.query(sql, (err, data, fields) => {
      if (err) throw err
  
      res.status(200).json({
        status: "success",
        length: data?.length,
        data: data,
      });
    })

    connection.query(sql1, (err, data, fields) => {
      if (err) throw err
  
      res.status(200).json({
        status: "success",
        length: data?.length,
        data: data,
      });
    })
  });

  module.exports = router;