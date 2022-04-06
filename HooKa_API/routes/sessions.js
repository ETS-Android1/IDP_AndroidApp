const connection = require("../services/db");

var express = require('express');
var router = express.Router();

/* join session. :p */
router.post('/join', function(req, res, next) {

    let sessionPin = req.body.sessionPin
    let userId = req.body.userId

    let sql = `select * from session where sessionPin = ${sessionPin} and sessionRunningStatus = 0`
  
    connection.query(sql, (err, sessionData, fields) => {
      if (err) throw err

      let sessionId = sessionData[0].sessionId

      sql = `update user set joinedSession = ${sessionId} where userId = ${userId}`
  
      connection.query(sql, (err, data, fields) => {
        if (err) throw err
    
        sql = `select * from user where userId = ${userId}`
  
        connection.query(sql, (err, data, fields) => {
          if (err) throw err
      
          let userData = null
          userData = data[0]

          res.status(200).json({
            status: "success",
            length: data?.length,
            sessionData: sessionData[0],
            userData: userData,
          });
        })

      })
      
    })
    
  });

  module.exports = router;