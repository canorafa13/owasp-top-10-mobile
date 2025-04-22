(() => {
    'use strict';
    const config = require('./config');
    const knex = require('knex')(config.production);

    exports.signUp = async(payload) => {
        return await knex('Users')
            .insert(payload)
            .then((rows) => {
                return rows;
            })
            .catch((error) => {
                throw error;
            });
    }

    exports.signIn = async(username, password) => {
        return await knex('Users')
            .select('id', 'name', 'last_name', 'url_profile', 'status')
            .where('username', username)
            .where('password', password)
            .then((rows) => {
                return rows;
            })
            .catch((error) => {
                throw error;
            })
    }
})();