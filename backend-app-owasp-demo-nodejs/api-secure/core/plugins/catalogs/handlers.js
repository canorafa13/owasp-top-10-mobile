(() => {
    'use strict';
    const Boom = require('@hapi/boom');

    exports.getAllRoles = async(req, h) => {
        try{
            const roles = await req.server.methods.catalogs.getAllRoles();
            return roles;
        }catch(e){
            throw Boom.notImplemented(JSON.stringify(e));
        }
    }

})();