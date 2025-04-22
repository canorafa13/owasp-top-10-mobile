(() => {
    'use strict';
    const config = require('./config');
    const knex = require('knex')(config.production);


    exports.getAllRoles = async() => {
        return await knex('Roles')
            .select('*')
            .then((rows) => {
                return rows;
            })
            .catch((error) => {
                throw error;
            })
    }

    exports.getTasks = async(username) => {
        return await knex("Tasks")
            .select("*")
            .then((rows) => {
                return rows;
            })
            .catch((error) => {
                throw error;
            });
    }

    exports.deleteTasks = async(id) => {
        return await knex("Tasks")
            .where("id", id)
            .del()
            .then((rows) => {
                return rows;
            })
            .catch((error) => {
                throw error;
            });
    }

    exports.updateTasks = async(id, payload) => {
        return await knex("Tasks")
            .where("id", id)
            .update(payload)
            .then((rows) => {
                return rows;
            })
            .catch((error) => {
                throw error;
            });
    }

    exports.createTasks = async(payload) => {
        return await knex("Tasks")
            .insert(payload)
            .then((rows) => {
                return rows;
            })
            .catch((error) => {
                throw error;
            });
    }
})();