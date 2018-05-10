"use strict";

const path = require("path");
const fixtures = require("sequelize-fixtures");
const init = require("../index");

module.exports = {
  up: function(queryInterface, Sequelize) {
    // queryInterface.sequelize.config will inherit environment config from --env flag of sequelize-cli
    return init(queryInterface.sequelize.config)
      .then(db =>
        fixtures.loadFile(path.resolve(`${__dirname}/seed-data/dev-users.yml`), db.models)
      )
      .catch(err => console.error(err));
  },

  down: function(queryInterface, Sequelize) {
    return _cleanUp(queryInterface, Sequelize);
  },
};

function _cleanUp(queryInterface, Sequelize) {
  return queryInterface.bulkDelete("users", null, {});
}
