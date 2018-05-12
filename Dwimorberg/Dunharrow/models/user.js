"use strict";

const BaseModel = require("./base-model");
const { Sequelize } = require("sequelize");

const attributes = {
  name: {
    type: Sequelize.TEXT,
    allowNull: false,
  },
  userId: {
    type: Sequelize.INTEGER,
    allowNull: false,
    unique: true,
  },
  meta: {
    type: Sequelize.JSONB,
    allowNull: false,
    defaultValue: {},
  },
};

module.exports = class user extends BaseModel {
  static init(sequelize) {
    super.init(attributes, options(sequelize));
    return this;
  }
};

function options(sequelize) {
  return {
    sequelize: sequelize,
    modelName: "user",
    indexes: [
      { fields: ["name"] },
      { fields: ["createdAt"] },
      { fields: ["updatedAt"] },
      { fields: ["deletedAt"] },
    ],
  };
}
