/*
  the purpose of this class is to add methods to all sequelize models.
*/

"use strict";

const { Model } = require("sequelize");

module.exports = class BaseModel extends Model {
  constructor(values, options) {
    super(values, options);
  }
};
