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

    /// Tasks
    exports.getTasks = async(username) => {
        return await knex("Tasks")
            .select("*")
            .where("username", username)
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

    /// Activities
    exports.getActivities = async(task_id) => {
        return await knex("Activities")
            .select("*")
            .where("task_id", task_id)
            .then((rows) => {
                return rows;
            })
            .catch((error) => {
                throw error;
            });
    }

    exports.deleteActivities = async(id) => {
        return await knex("Activities")
            .where("id", id)
            .del()
            .then((rows) => {
                return rows;
            })
            .catch((error) => {
                throw error;
            });
    }

    exports.updateActivities = async(id, payload) => {
        return await knex("Activities")
            .where("id", id)
            .update(payload)
            .then((rows) => {
                return rows;
            })
            .catch((error) => {
                throw error;
            });
    }

    exports.createActivities = async(payload) => {
        return await knex("Activities")
            .insert(payload)
            .then((rows) => {
                return rows;
            })
            .catch((error) => {
                throw error;
            });
    }
})();