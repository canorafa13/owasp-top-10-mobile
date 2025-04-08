(() => {
    'use strict';
    const config = require('./config');
    const knex = require('knex')(config.production);
    const md5 = require('md5');


    exports.getAll = async() => {
        return await knex('Users')
            .select('username', 'name', 'last_name', 'phone', 'url_profile', 'status', 'rol_id')
            .then((rows) => {
                return rows;
            })
            .catch((error) => {
                throw error;
            });
    }

    exports.insert = async(payload) => {
        return await knex('Users')
            .insert(payload)
            .then((rows) => {
                return rows;
            })
            .catch((error) => {
                throw error;
            });
    }

    exports.signon = async(username, password) => {
        return await knex('Users')
            .select('id', 'name', 'last_name', 'url_profile', 'status')
            .where('username', username)
            .where('password', password)// md5(password))
            .then((rows) => {
                return rows;
            })
            .catch((error) => {
                throw error;
            })
    }
})();