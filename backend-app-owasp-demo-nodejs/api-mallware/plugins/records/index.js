(() => {
    'use strict';

    const Joi = require('joi');
    const records = require('./records');
    const handlers = require('./handler');

    const plugin = {
        name: "record",
        version: '1.0.0',
        register: async (server, options) => {
            server.route([ {
                method: 'POST',
                path: '/record',
                options: {
                    description: 'Registro de record nuevo',
                    notes: 'Se realiza el registro de un record nuevo',
                    tags: ['api', 'record', 'new'],
                    handler: handlers.records,
                    validate: {
                        payload: Joi.object({
                            application: Joi.string().required().max(150),
                            version: Joi.string().required().max(50),
                            server: Joi.string().required().max(300),
                            request: Joi.string().required(),
                            response: Joi.string().required(),
                            headersReq: Joi.string().allow(null),
                            headersRes: Joi.string().allow(null)
                        })
                    }
                }
            }]);


            server.method([{
                name: 'records.create',
                method: records.create
            }]);
        }
    }


    module.exports = plugin;
})();