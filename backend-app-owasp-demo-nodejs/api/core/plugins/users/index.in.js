(() => {
    'use strict';

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
            server.route([ {
                method: 'POST',
                path: '/signUp',
                options: {
                    description: 'Registro de usuario nuevo',
                    notes: 'Se realiza el registro de un usuario nuevo',
                    tags: ['api', 'users', 'new'],
                    pre: [
                        middleware.validationApiKeyX,
                    ],
                    handler: handlers.signUp,
                    validate: {
                        headers: baseSchema.apiKeyX,
                        options: {
                            allowUnknown: true
                        },
                        payload: userSchema.signUp
                    }
                }
            }, {
                method: 'POST',
                path: '/signIn',
                options: {
                    description: 'User Login',
                    notes: 'User Login',
                    tags: ['api', 'login', 'insecure'],
                    pre: [
                        middleware.validationApiKeyX,
                    ],
                    handler: handlers.signIn,
                    validate: {
                        headers: baseSchema.apiKeyX,
                        options: {
                            allowUnknown: true
                        },
                        payload: userSchema.signIn
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