"use strict";

const config = require("../config/config.json").development;
const init = require("../index");
const chalk = require("chalk");

init(config)
  .then(db => {
    const models = db.models;
    return Object.keys(models).forEach(key => {
      const assoc = models[key].associations;
      console.log(chalk.bold.yellow(key));
      if (assoc) {
        Object.keys(assoc).forEach((assocKey, index) => {
          console.log(chalk.gray(" ├──"), chalk.cyan(assocKey));
        });
      }
    });
  })
  .catch(err => {
    console.log(err);
  });
