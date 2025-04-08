(() => {
    'use strict';
    
    const catalogs = require('./catalogs');
    const handlers = require('./handlers');

    const plugin = {
        name: "catalogs",
        version: '1.0.0',
        register: async (server, options) => {
            server.route([{
                method: 'GET',
                path: '/roles',
                handler: handlers.getAllRoles,
                options: {
                    description: 'All roles',
                    notes: 'All roles',
                    tags: ['api', 'roles']
                }
            }]);


            server.method([{
                name: 'catalogs.getAllRoles',
                method: catalogs.getAllRoles
            }]);
        }
    }


    module.exports = plugin;
})();