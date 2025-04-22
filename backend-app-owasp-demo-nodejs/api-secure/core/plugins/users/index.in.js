(() => {
    'use strict';

    const users = require('./users');
    const handlers = require('./handlers');
    const session = require('../sessions/sessions');
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
                    handler: handlers.signUp,
                    validate: {
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
                    handler: handlers.signIn,
                    validate: {
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