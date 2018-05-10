"use strict";

const dbInit = require("../index");
const config = require("../config/config.json");

const environment = process.argv[2] || "local";

dbInit(config[environment])
  .then(db => db.sequelize.sync().then(() => db.closeConnections()))
  .catch(error => {
    console.log("error", error.stack);
  });
