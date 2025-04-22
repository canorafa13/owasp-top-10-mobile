(() => {
    'use strict';

    const Joi = require('joi');
    const users = require('./users');
    const handlers = require('./handlers');
    const session = require('../sessions/sessions');
    const middleware = require('../../utils/middleware');
    const baseSchema = require('../../schemas/baseSchema');
    const userSchema = require('../../schemas/usersSchema');

    const plugin = {
        name: "users",
        version: '1.0.0',
        register: async (server, options) => {
            server.route([{
                method: 'POST',
                path: '/cryptii',
                handler: handlers.cryptii,
                options: {
                    description: 'Cifrado de datos',
                    notes: 'Se realiza el cifrado de una cadena',
                    tags: ['api', 'cryptii', 'word'],
                    validate: {
                        payload: Joi.object({
                            word: Joi.string().required(),
                            type: Joi.number().required()
                        })
                    }
                }
            }, {
                method: 'POST',
                path: '/signUp',
                options: {
                    description: 'Registro de usuario nuevo',
                    notes: 'Se realiza el registro de un usuario nuevo',
                    tags: ['api', 'users', 'new'],
                    pre: [
                        middleware.validationApiKeyX,
                        middleware.validationSchema(userSchema.signUp)
                    ],
                    handler: handlers.signUp,
                    validate: {
                        headers: baseSchema.apiKeyX,
                        options: {
                            allowUnknown: true
                        },
                        payload: baseSchema.base
                    }
                }
            }, {
                method: 'POST',
                path: '/signIn',
                options: {
                    description: 'User Login Secure',
                    notes: 'User Login Secure',
                    tags: ['api', 'login', 'secure'],
                    pre: [
                        middleware.validationApiKeyX,
                        middleware.validationSchema(userSchema.signIn)
                    ],
                    handler: handlers.signIn,
                    validate: {
                        headers: baseSchema.apiKeyX,
                        options: {
                            allowUnknown: true
                        },
                        payload: baseSchema.base
                    }
                }
            }]);


            server.method([{
                name: 'users.signUp',
                method: users.signUp
            }, {
                name: 'users.signIn',
                method: users.signIn
            }, {
                name: 'session.create',
                method: session.create
            }]);
        }
    }


    module.exports = plugin;
})();