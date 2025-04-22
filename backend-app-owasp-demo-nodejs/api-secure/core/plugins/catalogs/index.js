(() => {
    'use strict';
    
    const catalogs = require('./catalogs');
    const handlers = require('./handlers');
    const middleware = require('../../utils/middleware');
    const session = require('../sessions/sessions');
    const baseSchema = require('../../schemas/baseSchema');

    const plugin = {
        name: "catalogs",
        version: '1.0.0',
        register: async (server, options) => {
            server.route([{
                method: 'GET',
                path: '/roles',
                options: {
                    description: 'All roles',
                    notes: 'All roles',
                    tags: ['api', 'roles'],
                    pre: [
                        middleware.validateServer,
                        middleware.verifyToken
                    ],
                    handler: handlers.getAllRoles,
                    validate: {
                        headers: baseSchema.authorization,
                        options: {
                            allowUnknown: true
                        }
                    }
                }
            }]);


            server.method([{
                name: 'catalogs.getAllRoles',
                method: catalogs.getAllRoles
            }, {
                name: 'session.verify',
                method: session.verify
            }]);
        }
    }


    module.exports = plugin;
})();