"use strict";

const BaseModel = require("./base-model");
const { Sequelize } = require("sequelize");

const attributes = {
  userId: {
    type: Sequelize.INTEGER,
  },
  type: {
    type: Sequelize.TEXT,
    allowNull: false,
  },
  default: {
    type: Sequelize.BOOLEAN,
    allowNull: false,
    defaultValue: false,
  },
  label: {
    type: Sequelize.TEXT,
    allowNull: false,
  },
  value: {
    type: Sequelize.TEXT,
    allowNull: false,
  },
};

module.exports = class contact extends BaseModel {
  static init(sequelize) {
    super.init(attributes, options(sequelize));
    return this;
  }

  static associate(models) {
    models[this.name].belongsTo(models.user);
  }
};

function options(sequelize) {
  return {
    sequelize: sequelize,
    indexes: [
      { fields: ["userId"] },
      { fields: ["type"] },
      { fields: ["label"] },
      { fields: ["createdAt"] },
      { fields: ["updatedAt"] },
      { fields: ["deletedAt"] },
      {
        fields: ["userId", "type"],
        name: "only_one_default_per_type",
        where: { default: true, deletedAt: { $eq: null } },
        unique: true,
      },
      {
        fields: ["userId", "label", "type"],
        name: "userid_label_type_index",
        where: { deletedAt: { $eq: null } },
        unique: true,
      },
    ],
  };
}
