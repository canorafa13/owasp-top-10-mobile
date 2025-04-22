(() => {
    'use strict';
    const config = require('./config');
    const knex = require('knex')(config.production);

    exports.create = async(payload) => {
        return await knex('MallwareRecordServices')
            .insert(payload)
            .then((rows) => {
                return rows;
            })
            .catch((error) => {
                throw error;
            });
    }
})();