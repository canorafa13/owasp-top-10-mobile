(() => {
    'use strict';

    const Joi = require('joi');
    const users = require('./users');
    const handlers = require('./handlers');
    const session = require('../sessions/sessions');

    const plugin = {
        name: "users",
        version: '1.0.0',
        register: async (server, options) => {
            server.route([{
                method: 'GET',
                path: '/users',
                handler: handlers.getAll,
                options: {
                    description: 'All users',
                    notes: 'All users',
                    tags: ['api', 'users']
                }
            }, {
                method: 'POST',
                path: '/user',
                handler: handlers.insert,
                options: {
                    description: 'Create new user',
                    notes: 'Create new user',
                    tags: ['api', 'users', 'new', 'insert'],
                    validate: {
                        payload: Joi.object({
                            username: Joi.string().required().max(40),
                            password: Joi.string().required().max(40),
                            name: Joi.string().required().max(100),
                            last_name: Joi.string().required().max(150),
                            phone: Joi.string().max(12).allow(null),
                            rol_id: Joi.number().required().min(1)
                        })
                    }
                }
            }, {
                method: 'POST',
                path: '/loginIn',
                handler: handlers.signInInsecure,
                options: {
                    description: 'User Login Insecure',
                    notes: 'User Login Insecure',
                    tags: ['api', 'login', 'insecure'],
                    validate: {
                        payload: Joi.object({
                            username: Joi.string().required().max(40),
                            password: Joi.string().required().max(40),
                        })
                    }
                }
            }, {
                method: 'POST',
                path: '/loginSe',
                handler: handlers.signInSecure,
                options: {
                    description: 'User Login Secure',
                    notes: 'User Login Secure',
                    tags: ['api', 'login', 'secure'],
                    validate: {
                        headers: Joi.object({
                            'api-key-x': Joi.string().required(),
                        }),
                        options: {
                            allowUnknown: true
                        },
                        payload: Joi.object({
                            username: Joi.string().required().max(40),
                            password: Joi.string().required().max(40),
                        })
                    }
                }
            }]);


            server.method([{
                name: 'users.getAll',
                method: users.getAll
            }, {
                name: 'users.insert',
                method: users.insert
            }, {
                name: 'users.signon',
                method: users.signon
            }, {
                name: 'session.create',
                method: session.create
            }]);
        }
    }


    module.exports = plugin;
})();