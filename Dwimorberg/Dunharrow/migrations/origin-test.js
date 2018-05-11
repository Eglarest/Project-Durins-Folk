"use strict";

module.exports = {
  up: function(queryInterface, Sequelize) {
    // return queryInterface.showAllTables().then(tableNames => {
    //   if (tableNames.indexOf("users") < 0) {
    //     return queryInterface.sequelize.query(`
    //      CREATE TABLE "users" (
    //          id integer NOT NULL,
    //          "userId" integer,
    //          meta jsonb DEFAULT '{}'::jsonb NOT NULL,
    //          "createdAt" timestamp with time zone NOT NULL,
    //          "updatedAt" timestamp with time zone NOT NULL,
    //          "deletedAt" timestamp with time zone
    //      );
    //    `);
    //   }
    // });
  },

  down: function(queryInterface, Sequelize) {

  },
};
