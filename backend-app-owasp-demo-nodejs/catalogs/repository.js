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
})();