(() => {
    'use strict';
    const Boom = require('@hapi/boom');
    const cryptii = require('../../utils/cryptii');
    const baseResponse = require('../../schemas/baseResponse');

    exports.getAllRoles = async(req, h) => {
        try{
            const isSecure = req.pre.isSecure || false;
            const roles = await req.server.methods.catalogs.getAllRoles();
            if(isSecure){
                return baseResponse.success(await cryptii.encrypt(roles));
            }
            return baseResponse.success(roles);
        }catch(e){
            throw Boom.notImplemented(JSON.stringify(e));
        }
    }

})();